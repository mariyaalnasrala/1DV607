package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Initializes the data for the system, including members, items, and contracts.
 * This class is responsible for setting up default data and ensuring the
 * relationships
 * between entities are properly managed.
 */
public class DataInitializer {

  private MemberFactory memberFac = new MemberFactory();
  private List<Member> memberRepo = new ArrayList<>();
  private ItemFactory itemFac = new ItemFactory();
  private ContractFactory contractFac = new ContractFactory();
  private Item itemRepo = new Item();
  private List<Contract> contractRepo = new ArrayList<>();

  /**
   * Constructs a new instance of {@code DataInitializer} and initializes required
   * dependencies.
   */
  public DataInitializer() {
  }

  /**
   * Initializes sample data, including members, items, and contracts.
   * This method clears any existing data to avoid duplication and creates
   * predefined members, items, and rental contracts for testing purposes.
   */
  public void initialize() {
    // Item item = new Item();
    // Clear existing items to avoid duplication on multiple initializations
    itemRepo.clearItems();

    // Initialize and add members with validation
    Optional<Member> m1 = createAndAddMember("Alice", "alice@example.com", "1234567890", 500);
    createAndAddMember("Bob", "bob@example.com", "0987654321", 100);
    Optional<Member> m3 = createAndAddMember("Charlie", "charlie@example.com", "2345678901", 100);

    // Initialize and add exactly two items for m1 with validation
    if (m1.isPresent()) {
      createAndAddItem("Bicycle", "Mountain bike", ItemCategory.VEHICLE, 50, m1.get());
      createAndAddItem("Hammer", "A sturdy hammer", ItemCategory.TOOL, 10, m1.get());

      // Create a rental contract if items and m3 are available
      if (m3.isPresent()) {
        Optional<Item> i2 = itemRepo.findItemById(2); // Assuming the item with ID 2 exists
        if (i2.isPresent()) {
          createAndAddContract(i2.get(), m3.get(), 5, 7); // Contract from day 5 to day 7
        }
      }
    }
  }

  private Optional<Member> createAndAddMember(
      String name,
      String email,
      String phone,
      double credits) {
    try {
      Member member = memberFac.createMember(name, email, phone);
      member.setCredits(credits);
      memberRepo.add(member);
      return Optional.of(member);
    } catch (IllegalArgumentException e) {
      System.err.println("Failed to create member: " + e.getMessage());
      return Optional.empty();
    }
  }

  public List<Member> getAllMembersCopy() {
    return new ArrayList<>(memberRepo);
  }

  private Optional<Item> createAndAddItem(
      String name,
      String description,
      ItemCategory category,
      double costPerDay,
      Member owner) {
    try {
      Item item = itemFac.createItem(name, description, category, costPerDay, owner);
      itemRepo.addItem(item);
      owner.addItem(item); // Explicitly manage ownership relationship here
      return Optional.of(item);
    } catch (IllegalArgumentException e) {
      System.err.println("Failed to create item: " + e.getMessage());
      return Optional.empty();
    }
  }

  private Optional<Contract> createAndAddContract(
      Item item,
      Member renter,
      int startDay,
      int endDay) {
    try {
      Contract contract = contractFac.createContract(item, renter, startDay, endDay);
      contract.addContract(contract);
      contractRepo.add(contract);
      return Optional.of(contract);
    } catch (IllegalArgumentException e) {
      System.err.println("Failed to create contract: " + e.getMessage());
      return Optional.empty();
    }
  }
}
