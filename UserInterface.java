package view;

/**
 * Interface defining the methods needed for UI interactions, allowing for a
 * consistent
 * way to prompt and display information across different views in the
 * application.
 */
public interface UserInterface {

  /**
   * Displays the main menu to the user.
   */
  void displayMenu();

  /**
   * Prompts the user to enter a string based on the provided message.
   *
   * @param message The message to display as a prompt.
   * @return The string input provided by the user.
   */
  String promptForString(String message);

  /**
   * Displays a generic message to the user, typically for general information or
   * feedback.
   *
   * @param message The message to be displayed.
   */
  void displayMessage(String message);

  /**
   * Displays an error message to the user with a standardized "Error:" prefix.
   */
  void displayErrorMessage();

  /**
   * Displays an informational message to the user with a standardized "Info:"
   * prefix.
   *
   * @param message The informational message to be displayed.
   */
  void displayInfoMessage(String message);

  /**
   * Displays an exit message to the user when the application is closing.
   */
  void displayExitMessage();

  /**
   * Provides access to the member information view for handling member-related
   * UI interactions.
   *
   * @return The MemberInfoView instance.
   */
  MemberInfoView getMemberInfoView();

  /**
   * Provides access to the item information view for handling item-related
   * UI interactions.
   *
   * @return The ItemInfoView instance.
   */
  ItemInfoView getItemInfoView();

  /**
   * Provides access to the contract information view for handling
   * contract-related
   * UI interactions.
   *
   * @return The ContractInfoView instance.
   */
  ContractInfoView getContractInfoView();

  /**
   * display message to user(can not delete member).
   */
  void deleteMemberErrorMessage();

  /**
   * display message to user(member not found).
   */
  void memberNotFound();

  /**
   * display message if cost is negative.
   */
  void negativeCost();

  /**
   * display error message if owner not found.
   */
  void ownerNotFound();

  /**
   * dispaly error message if item not found.
   */
  void itemNotFound();

  /**
   * display error message if item has active or future contracts.
   */
  void deleteItemFaild();

  /**
   * display error message if Invalid item or renter ID.
   */
  void invalidRenterId();

  /**
   * display error message if num of days is negative.
   */
  void negativeNumOfDays();

  /**
   * Invalid name format.
   */
  void nameFormat();

  /**
   * Invalid email format.
   */

  void invaildEmail();

  /**
   * Invalid phone format.
   */
  void invaildPhoneNum();

  /**
   * check the option.
   */
  int getMenuOption();

  /**
   * check the input member id.
   */
  String getMemberId();

  /**
   * delete members by inputed id.
   */
  String deleteMemberById();

  /**
   * reteurn errro message (if id not correct).
   */
  int updateItemById();

  /**
   * message to delete item by id.
   */
  int deleteItemById();

  /**
   * success message for delete items.
   */
  void deleteItemSuccess();

  /**
   * num of advance days.
   */
  int numOfDays();

  /**
   * display current day.
   */
  void displayDayAdvanced(int currentDay);

  /**
   * UI copy.
   */
  UserInterface copy();

  /**
   * category.
   */
  void invaildCategory();

  /**
   * message (deleted member).
   */
  void deleteMemberSuccessMessage();

<<<<<<< HEAD
void contractNotFound();
=======
  /**
   * error message(contract not found).
   */
  void contractNotFound();
>>>>>>> b973a75a4057880a24dbbe8e4d7f045fea123c43

}
