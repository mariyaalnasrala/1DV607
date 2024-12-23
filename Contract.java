package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a contract for renting an item.
 */
public class Contract {
  private List<Contract> contractList = new ArrayList<>();
  private String id; // Unique contract ID
  private Item item;
  private Member renter;
  private int startDay;
  private int endDay;
  private boolean processed;
  private ContractStatus status;
  private Time time;

  /**
   * Enum representing the status of a contract.
   */
  public enum ContractStatus {
    ACTIVE, PROCESSED
  }

  /**
   * Package-private constructor to create a Contract instance with specified
   * item, renter, start day, and end day.
   * Sets the item as unavailable.
   *
   * @param item     The item being rented.
   * @param renter   The member renting the item.
   * @param startDay The start day of the rental.
   * @param endDay   The end day of the rental.
   */
  public Contract(Item item, Member renter, int startDay, int endDay) {
    this.id = UUID.randomUUID().toString();
    this.item = new Item(item);
    this.renter = new Member(renter);
    this.startDay = startDay;
    this.endDay = endDay;
    this.processed = false;
    this.status = ContractStatus.ACTIVE;
    this.item.setAvailable(false);
    this.time = new Time();
  }

  /**
   * constructor.
   */
  public Contract() {
  }

  /**
   * con. fild time.
   */
  public Contract(Time time) {
    if (time == null) {
      throw new IllegalArgumentException();
    }
    this.time = time;
  }

  public Contract copy() {
    return new Contract(this);
  }

  /**
   * Factory method to create a validated Contract instance.
   *
   * @param item     The item being rented.
   * @param renter   The member renting the item.
   * @param startDay The start day of the rental.
   * @param endDay   The end day of the rental.
   * @return A validated Contract instance.
   * @throws IllegalArgumentException If validation fails.
   */
  public Contract createContract(Item item, Member renter, int startDay, int endDay) {
    if (item == null || renter == null) {
      throw new IllegalArgumentException("Item and renter must not be null.");
    }
    if (startDay < 0 || endDay < startDay) {
      throw new IllegalArgumentException("Invalid start or end day.");
    }

    if (hasDateConflict(item, startDay, endDay)) {
      throw new IllegalArgumentException("The rental period conflicts with an existing contract.");
    }
    double totalCost = calculateTotalCost(item, startDay, endDay);
    if (renter.getCredits() < totalCost) {
      throw new IllegalArgumentException("Renter does not have enough credits.");
    }
    return new Contract(item, renter, startDay, endDay);
  }

  /**
   * Adds a contract to the system.
   *
   * @param contract The contract to add.
   */
  public void addContract(Contract contract) {
    contractList.add(contract);
  }

  /**
   * Returns a list of all contracts as shallow copies to maintain encapsulation.
   *
   * @return A list of all contracts.
   */
  public List<Contract> getAllContractsCopy() {
    List<Contract> contractCopies = new ArrayList<>();
    for (Contract contract : contractList) {
      contractCopies.add(new Contract(contract)); // Using a copy constructor
    }
    return contractCopies;
  }

  /**
   * Copy constructor to create a copy of a Contract instance.
   *
   * @param contract The contract to copy.
   */
  public Contract(Contract contract) {
    this.id = contract.id;
    this.item = new Item(contract.item);
    this.renter = new Member(contract.renter);
    this.startDay = contract.startDay;
    this.endDay = contract.endDay;
    this.processed = contract.processed;
    this.status = contract.status;
  }

  /**
   * Calculates the total cost of renting an item from the start day to the end
   * day.
   *
   * @param item     The item being rented.
   * @param startDay The start day of the rental.
   * @param endDay   The end day of the rental.
   * @return The total rental cost.
   */
  public double calculateTotalCost(Item item, int startDay, int endDay) {
    int rentalDays = endDay - startDay + 1;
    return rentalDays * item.getCostPerDay();
  }

  /**
   * Gets the total cost of this contract.
   *
   * @return The total cost of the contract.
   */
  public double getTotalCost() {
    return calculateTotalCost(this.item, this.startDay, this.endDay);
  }

  /**
   * Checks if there is a date conflict for renting an item in the specified
   * range.
   *
   * @param item     The item to check.
   * @param startDay The start day of the rental.
   * @param endDay   The end day of the rental.
   * @return True if a conflict exists; otherwise false.
   */
  public boolean hasDateConflict(Item item, int startDay, int endDay) {

    if (contractList == null) {
      return false;
    }
    for (Contract contract : contractList) {
      if (contract.item != null && contract.item.equals(item) && !contract.processed) {
        int existingStart = contract.startDay;
        int existingEnd = contract.endDay;
        if (!(endDay < existingStart || startDay > existingEnd)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks if an item is involved in a future or active contract.
   *
   * @param item The item to check.
   * @return True if the item has a future or active contract; otherwise false.
   */
  public boolean isItemInvolvedInFutureOrActiveContract(Item item) {
    if (item == null || contractList == null || this.time == null) {
      return false;
    }

    int currentDay = time.getCurrentDay();
    for (Contract contract : contractList) {
      if (contract.item != null && contract.item.equals(item) && !contract.processed && contract.endDay >= currentDay) {
        return true;
      }
    }
    return false;
  }

  /**
   * Processes this contract, transferring credits and marking it as processed.
   *
   * @throws IllegalStateException If the renter has insufficient credits.
   */
  public void processContract() {
    if (processed) {
      return;
    }
    double totalCost = getTotalCost();
    if (renter.getCredits() < totalCost) {
      throw new IllegalStateException("Insufficient credits. Contract cannot be processed.");
    }

    renter.setCredits(renter.getCredits() - totalCost);
    item.getOwner().setCredits(item.getOwner().getCredits() + totalCost);
    processed = true;
    status = ContractStatus.PROCESSED;
    item.setAvailable(true);
  }

  /**
   * Checks if the contract is due for processing and processes it if so.
   *
   * @param currentDay The current system day.
   */
  public void checkAndProcessContract(int currentDay) {
    if (!processed && currentDay >= endDay) {
      processContract();
    }
  }

  /**
   * Processes all contracts due as of the specified day.
   *
   * @param currentDay The current system day.
   */
  public void processDueContracts(int currentDay) {
    for (Contract contract : contractList) {
      contract.checkAndProcessContract(currentDay);
    }
  }

  /**
   * Gets the status of this contract.
   *
   * @return The status of the contract.
   */
  public ContractStatus getStatus() {
    return status;
  }

  // Getters for contract fields

  /**
   * Gets the unique ID of this contract.
   *
   * @return The contract ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the item associated with this contract.
   *
   * @return The rented item.
   */
  public Item getItem() {
    return new Item(item);
  }

  /**
   * Gets the renter associated with this contract.
   *
   * @return The member renting the item.
   */
  public Member getRenter() {
    return new Member(renter);
  }

  /**
   * Gets the start day of the rental period for this contract.
   *
   * @return The start day of the contract.
   */
  public int getStartDay() {
    return startDay;
  }

  /**
   * Gets the end day of the rental period for this contract.
   *
   * @return The end day of the contract.
   */
  public int getEndDay() {
    return endDay;
  }

  /**
   * Checks if this contract has been processed.
   *
   * @return True if the contract is processed; otherwise false.
   */
  public boolean isProcessed() {
    return processed;
  }
}
