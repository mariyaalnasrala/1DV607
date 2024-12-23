package view;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.HashSet;
>>>>>>> b973a75a4057880a24dbbe8e4d7f045fea123c43
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import model.Item;
import model.Member;

/**
 * The MemberInfoView class handles the display and input of member-related
 * information. It provides methods for listing members, viewing details,
 * and collecting input for creating or updating members.
 */
public class MemberInfoView {

  private Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  // Regex patterns for validation
  private Pattern namePattern = Pattern.compile("^[A-Za-z]+\\s[A-Za-z]+$");
  private Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private Pattern phonePattern = Pattern.compile("^\\d{8,15}$");

  /**
   * send a copy.
   */
  public MemberInfoView copy() {
    return new MemberInfoView();
  }

  /**
   * Displays a simple list of members, showing each member's ID, name, email,
   * credits, and the number of owned items. If no members are found, a message
   * is displayed.
   * The list of members to be displayed.
   */
  public void displayMembersSimple(List<Member> membersFromDataInitializer, List<Member> membersFromRepo) {
    // Skapa en kombinerad lista direkt
    List<Member> allMembers = new ArrayList<>(membersFromDataInitializer);
<<<<<<< HEAD
    allMembers.addAll(membersFromRepo);
=======
    // allMembers.addAll(membersFromRepo);

    membersFromRepo.stream()
        .filter(member -> allMembers.stream().noneMatch(m -> m.getId().equals(member.getId())))
        .forEach(allMembers::add);
>>>>>>> b973a75a4057880a24dbbe8e4d7f045fea123c43

    System.out.println("\nSimple Member List:");
    if (allMembers.isEmpty()) {
      System.out.println("No members found.");
    } else {
      for (Member member : allMembers) {
<<<<<<< HEAD
=======
        Set<Item> uniqueItems = new HashSet<>(member.getOwnedItemsCopy());

>>>>>>> b973a75a4057880a24dbbe8e4d7f045fea123c43
        System.out.printf("ID: %s, Name: %s, Email: %s, Credits: %.2f, Owned Items: %d%n",
            member.getId(), member.getName(), member.getEmail(), member.getCredits(),
            uniqueItems.size());
      }
    }
  }

  /**
   * Displays a detailed list of members, including each member's verbose
   * information, owned items, and other detailed info.
   * The list of members to be displayed.
   */
  public void displayMembersVerbose(List<Member> membersFromDataInitializer, List<Member> membersFromRepo) {
    // Skapa en kombinerad lista direkt
    List<Member> allMembers = new ArrayList<>(membersFromDataInitializer);
<<<<<<< HEAD
    allMembers.addAll(membersFromRepo);
=======
    // allMembers.addAll(membersFromRepo);

    membersFromRepo.stream()
        .filter(member -> allMembers.stream().noneMatch(m -> m.getId().equals(member.getId())))
        .forEach(allMembers::add);
>>>>>>> b973a75a4057880a24dbbe8e4d7f045fea123c43

    System.out.println("\nVerbose Member List:");
    if (allMembers.isEmpty()) {
      System.out.println("No members found.");
    } else {
      for (Member member : allMembers) {
        displayMemberDetails(member);
      }
    }
  }

  /**
   * Displays detailed information for a single member, including owned items
   * and credits.
   * The member to display.
   */
  public void displayMemberDetails(Member member) {
    System.out.printf("%nMember ID: %s%nName: %s%nEmail: %s%nPhone: %s%nCredits: %.2f%nOwned Items:%n",
        member.getId(), member.getName(), member.getEmail(), member.getPhone(), member.getCredits());
    List<Item> uniqueItems = member.getOwnedItemsCopy().stream().distinct()
        .distinct() // Filtrera dubbletter.
        .toList();

    if (uniqueItems.isEmpty()) {
      System.out.println("No items owned.");
    } else {
      uniqueItems.forEach(item -> System.out.printf("  - Item ID: %d, Name: %s, Cost per Day: %.2f%n",
          item.getId(), item.getName(), item.getCostPerDay()));
    }
  }

  /**
   * Collects information for creating a new member with real-time validation.
   *
   * @return An array containing the member's name, email, and phone, or null
   *         if any required input is invalid.
   */
  public Object[] collectMemberCreationInput() {
    String name = promptForValidName();
    String email = promptForValidEmail();
    String phone = promptForValidPhone();

    return new Object[] { name, email, phone };
  }

  /**
   * Collects updated information for an existing member with real-time
   * validation.
   *
   * @return An array containing the updated member's name, email, and phone.
   */
  public Object[] collectMemberUpdateInput() {
    String name = promptForValidName();
    String email = promptForValidEmail();
    String phone = promptForValidPhone();

    return new Object[] { name, email, phone };
  }

  /**
   * Prompts the user for a valid name and validates the format using a regex.
   *
   * @return A valid name input.
   */
  private String promptForValidName() {
    while (true) {
      System.out.println("Enter the member name (First Last):");
      String name = scanner.nextLine().trim();
      if (namePattern.matcher(name).matches()) {
        return name;
      }
      System.out.println("Error: Invalid name format. Please enter a first and last name, e.g., 'John Doe'.");
    }
  }

  /**
   * Prompts the user for a valid email and validates the format using a regex.
   *
   * @return A valid email input.
   */
  private String promptForValidEmail() {
    while (true) {
      System.out.println("Enter the member email (example@domain.com):");
      String email = scanner.nextLine().trim();
      if (emailPattern.matcher(email).matches()) {
        return email;
      }
      System.out.println("Error: Invalid email format. Please use the format 'example@domain.com'.");
    }
  }

  /**
   * Prompts the user for a valid phone number and validates the format using a
   * regex.
   *
   * @return A valid phone input.
   */
  private String promptForValidPhone() {
    while (true) {
      System.out.println("Enter the member phone (8 to 15 digits):");
      String phone = scanner.nextLine().trim();
      if (phonePattern.matcher(phone).matches()) {
        return phone;
      }
      System.out.println("Error: Invalid phone format. The phone number must be between 8 and 15 digits.");
    }
  }

  /**
   * Displays an error message for invalid input.
   *
   * @param message The error message to be displayed.
   */
  public void displayInvalidInputMessage(String message) {
    System.out.println("Invalid input: " + message);
  }

  /**
   * Displays a success message when a member is successfully created,
   * showing the member's details.
   *
   * @param member The member that was successfully created.
   */
  public void displayMemberCreationSuccess(Member member) {
    System.out.println(" \n Member created successfully:");
    displayMemberDetails(member);
  }

  /**
   * Displays a success message when a member is successfully updated,
   * showing the updated member's details.
   *
   * @param member The member that was successfully updated.
   */
  public void displayMemberUpdateSuccess(Member member) {
    System.out.println("\n Member updated successfully:");
    displayMemberDetails(member);
  }

  /**
   * Displays a custom message to the user.
   *
   * @param message The message to be displayed.
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * check entered information.
   */
  public String validateMemberInputs(String name, String email, String phone) {
    if (name == null || !namePattern.matcher(name).matches()) {
      return "Name format is invalid.";
    }
    if (email == null || !emailPattern.matcher(email).matches()) {
      return "Email is invalid.";
    }
    if (phone == null || !phonePattern.matcher(phone).matches()) {
      return "Phone number is invalid.";
    }
    return null;
  }

  /**
   * metod to update member.
   */
  public void updateMember(Member member, String newName, String newEmail, String newPhone) {

    member.updateMember(newName, newEmail, newPhone);
    System.out.println("Member has been updated:");
    System.out.println("Name: " + member.getName());
    System.out.println("Email: " + member.getEmail());
    System.out.println("Phone number: " + member.getPhone());
  }
}
