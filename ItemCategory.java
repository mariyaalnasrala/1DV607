package model;

/**
 * Enum representing predefined item categories.
 */
public enum ItemCategory {
  VEHICLE,
  TOOL,
  ELECTRONICS,
  FURNITURE,
  OTHER;

  /**
   * Converts a string to an ItemCategory if it matches any enum value
   * (case-insensitive).
   *
   * @param category The category name to check.
   * @return The matching ItemCategory, or null if no match is found.
   */
  public static ItemCategory fromString(String category) {

    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Item category cannot be null or empty.");
    }

    try {
      return ItemCategory.valueOf(category.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid category: " + category);
    }
  }

  @Override
  public String toString() {
    return name();
  }
}
