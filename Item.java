package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents an item that can be rented in the system. Each item has attributes
 * such as name, description, category, cost per day, and availability status.
 */
public class Item {
  private List<Item> itemList = new ArrayList<>();
  private static int nextId = 1; // Improved ID generation to avoid duplicates
  private int id;
  private String name;
  private String description;
  private ItemCategory category;
  private double costPerDay;
  private Member owner;
  private boolean isAvailable;

  /**
   * Constructs a new Item with the specified details.
   *
   * @param name        The name of the item.
   * @param description The description of the item.
   * @param category    The category of the item, as an ItemCategory enum.
   * @param costPerDay  The daily rental cost of the item.
   * @param owner       The owner of the item.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public Item(
      String name,
      String description,
      ItemCategory category,
      double costPerDay,
      Member owner) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be empty.");
    }
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null.");
    }
    if (costPerDay < 0) {
      throw new IllegalArgumentException("Cost per day cannot be negative.");
    }

    this.id = generateId();
    this.name = name;
    this.description = description;
    this.category = category;
    this.costPerDay = costPerDay;
    this.owner = new Member(owner);
    this.isAvailable = true;
    addItem(this);
    this.owner.addItem(this); // Ensures the owner has a reference to this item
  }

  /**
   * Copy constructor to create a new Item instance based on an existing item.
   *
   * @param item The item to copy.
   */
  public Item(Item item) {
    this.id = item.id;
    this.name = item.name;
    this.description = item.description;
    this.category = item.category;
    this.costPerDay = item.costPerDay;
    this.owner = item.owner;
    this.isAvailable = item.isAvailable;
  }

  public Item() {

  }

  /**
   * Finds an item by its unique ID.
   *
   * @param id The ID of the item to find.
   * @return An Optional containing the item if found, or empty if not found.
   */
  public Optional<Item> findItemById(int id) {
    return itemList.stream().filter(item -> item.id == id).findFirst();
  }

  /**
   * Updates the details of this item.
   *
   * @param name        The new name of the item.
   * @param description The new description of the item.
   * @param category    The new category of the item, as an ItemCategory enum.
   * @param costPerDay  The new daily rental cost of the item.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public void updateItem(
      String name,
      String description,
      ItemCategory category,
      double costPerDay) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Description cannot be empty.");
    }
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null.");
    }
    if (costPerDay < 0) {
      throw new IllegalArgumentException("Cost per day cannot be negative.");
    }

    this.name = name;
    this.description = description;
    this.category = category;
    this.costPerDay = costPerDay;
  }

  /**
   * Adds an item to the global item list.
   *
   * @param item The item to add.
   */
  public void addItem(Item item) {
    itemList.add(item);
  }

  /**
   * Removes an item from the global item list.
   *
   * @param item The item to remove.
   * @return True if the item was removed; false otherwise.
   */
  public boolean removeItem(Item item) {
    return itemList.remove(item);
  }

  /**
   * Clears all items from the global item list.
   */
  public void clearItems() {
    itemList.clear();
    nextId = 1; // Reset item ID counter
  }

  /**
   * Retrieves all items in the system as shallow copies.
   *
   * @return A list of all items, each as a copy to maintain encapsulation.
   */
  public List<Item> getAllItemsCopy() {
    List<Item> itemCopies = new ArrayList<>();
    for (Item item : itemList) {
      itemCopies.add(new Item(item)); // Using a copy constructor
    }
    return itemCopies;
  }

  /**
   * static method to generate an Id.
   */
  private int generateId() {
    return nextId++;
  }

  /**
   * Gets the owner of this item.
   *
   * @return The member who owns this item.
   */
  public Member getOwner() {
    return new Member(owner);
  }

  /**
   * return a copy of list items.
   */
  public Member getOwnerCopy() {
    return new Member(owner.getName(), owner.getEmail(), owner.getPhone());
  }

  /**
   * Gets the daily rental cost of this item.
   *
   * @return The cost per day to rent the item.
   */
  public double getCostPerDay() {
    return costPerDay;
  }

  /**
   * Checks if the item is currently available for rent.
   *
   * @return True if the item is available; false otherwise.
   */
  public boolean isAvailable() {
    return isAvailable;
  }

  /**
   * Sets the availability status of the item.
   *
   * @param available True to make the item available; false to mark it as
   *                  unavailable.
   */
  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  /**
   * Gets the unique ID of this item.
   *
   * @return The item ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the item.
   *
   * @return The name of the item.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the description of the item.
   *
   * @return The description of the item.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the category of the item.
   *
   * @return The category of the item, as an ItemCategory enum.
   */
  public ItemCategory getCategory() {
    return category;
  }
}
