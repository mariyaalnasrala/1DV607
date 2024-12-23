package controller;

/**
 * Enum representing the options in the main menu.
 * Each option corresponds to a menu item, associated with an integer value.
 */
public enum MenuOption {
  ADD_MEMBER(1),
  LIST_MEMBERS_SIMPLE(2),
  UPDATE_MEMBER(3),
  DELETE_MEMBER(4),
  ADD_ITEM(5),
  LIST_ITEMS(6),
  UPDATE_ITEM(7),
  DELETE_ITEM(8),
  CREATE_CONTRACT(9),
  LIST_CONTRACTS(10),
  ADVANCE_DAY(11),
  EXIT(12),
  LIST_MEMBERS_VERBOSE(13);

  private final int value;

  /**
   * Constructor for MenuOption.
   *
   * @param value The integer value associated with this menu option.
   */
  MenuOption(int value) {
    this.value = value;
  }

  /**
   * Retrieves the integer value associated with this menu option.
   *
   * @return The integer value of the menu option.
   */
  public int getValue() {
    return value;
  }

  /**
   * Converts an integer to the corresponding MenuOption.
   *
   * @param option The integer value representing a menu option.
   * @return The corresponding MenuOption if valid; otherwise, null.
   */
  public static MenuOption fromInt(int option) {
    for (MenuOption menuOption : MenuOption.values()) {
      if (menuOption.getValue() == option) {
        return menuOption;
      }
    }
    return null;
  }
}
