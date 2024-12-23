package model;

/**
 * Factory class for creating Contract instances with necessary validation.
 * This class ensures that contracts are created only if all conditions
 * (e.g., availability of item, sufficient renter credits) are met.
 */
public class ContractFactory {

  public ContractFactory() {
  }
  // private Contract contract;
  // private Time time;

  // public void contract(Item item, Member renter, int startDay, int endDay, Time
  // time) {
  // this.time = new Time();
  // }

  /**
   * Creates a new contract for renting an item, with validation to ensure
   * that the rental period is valid, the renter has sufficient credits,
   * and the item is not rented by its owner.
   *
   * @param item     The item to be rented.
   * @param renter   The member renting the item.
   * @param startDay The start day of the rental period.
   * @param endDay   The end day of the rental period.
   * @return A new Contract instance if validation passes.
   * @throws IllegalArgumentException If any validation check fails, such as
   *                                  null item or renter, owner renting own item,
   *                                  invalid rental period, conflicting rental
   *                                  dates,
   *                                  or insufficient credits.
   */
  public Contract createContract(Item item, Member renter, int startDay, int endDay) {
    if (item == null || renter == null) {
      throw new IllegalArgumentException("Item and renter must not be null.");
    }
    if (item.getOwner().equals(renter)) {
      throw new IllegalArgumentException("Owner cannot rent their own item.");
    }
    if (startDay < 0 || endDay < startDay) {
      throw new IllegalArgumentException("Invalid start or end day.");
    }

    Contract contract = new Contract(item, renter, startDay, endDay);

    if (contract.hasDateConflict(item, startDay, endDay)) {
      throw new IllegalArgumentException("The rental period conflicts with an existing contract.");
    }
    if (renter.getCredits() < contract.calculateTotalCost(item, startDay, endDay)) {
      throw new IllegalArgumentException("Renter does not have enough credits.");
    }
    return new Contract(item, renter, startDay, endDay);
  }
}
