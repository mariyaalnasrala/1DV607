package model;

/**
 * Factory class for creating Item instances with validation. This class ensures
 * that items are created only if all necessary conditions (e.g., non-empty
 * name,
 * positive cost) are met.
 */
public class ItemFactory {

  public ItemFactory() {
  }

  /**
   * Creates a new Item instance with validation checks to ensure that each
   * attribute
   * meets the required criteria.
   *
   * @param name        The name of the item; must be non-null and non-empty.
   * @param description The description of the item; must be non-null and
   *                    non-empty.
   * @param category    The category of the item, as an ItemCategory enum.
   * @param costPerDay  The rental cost per day for the item; must be positive.
   * @param owner       The owner of the item; must be a valid, non-null Member.
   * @return A validated Item instance.
   * @throws IllegalArgumentException if any parameter is invalid, such as a null
   *                                  or empty
   *                                  name, description, category, non-positive
   *                                  costPerDay,
   *                                  or null owner.
   */
  public Item createItem(
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
    if (costPerDay <= 0) {
      throw new IllegalArgumentException("Cost per day must be a positive number.");
    }
    if (owner == null) {
      throw new IllegalArgumentException("Owner must be a valid member.");
    }
    return new Item(
        name,
        description,
        category,
        costPerDay,
        owner);
  }
}
