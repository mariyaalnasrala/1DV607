package view;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import model.Member;

/**
 * The Ui class provides a centralized user interface for interacting with the
 * rental system. It displays the main menu, handles general user input, and
 * provides access to specific views for managing members, items, and contracts.
 */
public class Ui implements UserInterface {
  private Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
  private MemberInfoView memberInfoView = new MemberInfoView();
  private ItemInfoView itemInfoView = new ItemInfoView();
  private ContractInfoView contractInfoView = new ContractInfoView();

  /**
   * send a copy.
   */
  public Ui copy() {
    return new Ui();
  }

  /**
   * user interface class.
   */
  public void displayMenu() {
    System.out.println("\nRental System Menu:");
    System.out.println("1. Add Member");
    System.out.println("2. List Members (Simple)");
    System.out.println("3. Update Member");
    System.out.println("4. Delete Member");
    System.out.println("5. Add Item");
    System.out.println("6. List Items");
    System.out.println("7. Update Item");
    System.out.println("8. Delete Item");
    System.out.println("9. Create Contract");
    System.out.println("10. List Contracts");
    System.out.println("11. Advance Day");
    System.out.println("12. Exit");
    System.out.println("13. List Members (Verbose)");
  }

  public int getMenuOption() {
    displayMenu();
    return promptForInt("Choose an option:");
  }

  public String getMemberId() {
    // displayMenu();
    return promptForString("Enter member ID to update:");
  }

  public String deleteMemberById() {
    // displayMenu();
    return promptForString("Enter member ID to delete:");
  }

  public String promptForString(String message) {
    System.out.println(message);
    return scanner.nextLine().trim();
  }

  public int updateItemById() {
    // displayMenu();
    return promptForInt("Enter item ID to update:");
  }

  public int deleteItemById() {
    // displayMenu();
    return promptForInt("Enter item ID to delete:");
  }

  public int numOfDays() {
    // displayMenu();
    return promptForInt("Enter number of days to advance:");
  }

  public void displayDayAdvanced(int currentDay) {
    System.out.println("Current day has been advanced to: " + currentDay);
  }

  public void displayMessage(String message) {
    System.out.println(message);
  }

  public void deleteItemSuccess() {
    System.out.println("Item deleted successfully.");
  }

  public void displayErrorMessage() {
    System.out.println("Invalid input / option. Please enter a valid integer.");
  }

  public void displayInfoMessage(String message) {
    System.out.println("Info: " + message);
  }

  public void displayExitMessage() {
    System.out.println("Exiting the system. Goodbye!");
  }

  public MemberInfoView getMemberInfoView() {
    return memberInfoView;
  }

  public ItemInfoView getItemInfoView() {
    return itemInfoView;
  }

  public ContractInfoView getContractInfoView() {
    return contractInfoView;
  }

  /**
   * display error messages.
   */
  public void deleteMemberErrorMessage() {
    System.out.println("Cannot delete member with active items or contracts.");
  }

  public void deleteMemberSuccessMessage() {
    System.out.println("Member deleted successfull");
  }

  public void memberNotFound() {
    System.out.println("Member not found!");
  }

  public void negativeCost() {
    System.out.println("Cost per day cannot be negative.");
  }

  public void ownerNotFound() {
    System.out.println("Owner not found.");
  }

  public void itemNotFound() {
    System.out.println("Item not found.");
  }

  public void deleteItemFaild() {
    System.out.println("Cannot delete an item that has active or future contracts.");
  }

  public void invalidRenterId() {
    System.out.println("Invalid item or renter ID.");
  }

  public void negativeNumOfDays() {
    System.out.println("Number of days to advance must be positive.");
  }

  public void nameFormat() {
    System.out.println("Invalid name format. Please enter a first and last name, e.g., 'John Doe'.");
  }

  public void invaildEmail() {
    System.out.println("Invalid email format. Please use the format 'example@domain.com'.");
  }

  public void invaildCategory() {
    System.out.println("Invalid gategory. Please try again.");
  }

  public void invaildPhoneNum() {
    System.out.println("Invalid phone format. The phone number must be 8 to 15 digits.");
  }

  /**
   * error message for invaild int input.
   */
  public int promptForInt(String message) {
    while (true) {
      try {
        System.out.println(message);
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input, please enter a valid integer.");
      }
    }
  }

  /**
   * displaymembers simple.
   */
  public void displayMembersSimple(List<Member> members) {
    if (members.isEmpty()) {
      System.out.println("No members found.");
    } else {
      for (Member member : members) {
        System.out.println(member.getName()); // Visa endast namn
      }
    }
  }

  /**
   * display members verbose.
   */
  public void displayMembersVerbose(List<Member> members) {
    if (members.isEmpty()) {
      System.out.println("No members found.");
    } else {
      for (Member member : members) {
        System.out.println("Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Phone: " + member.getPhone());
        System.out.println("Credits: " + member.getCredits());
        System.out.println("-----------");
      }
    }
  }


  public void contractNotFound() {
    System.out.println("Contract not found");
  }

}
