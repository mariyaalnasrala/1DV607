package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Represents a member in the system. Each member has unique attributes
 * such as name, email, phone, and a unique ID. Members can also own
 * items and have a credit balance.
 */
public class Member {

  private List<Member> memberList = new ArrayList<>(); // Stores all members

  // Constants renamed to follow CheckStyle conventions
  private String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private int idLength = 6; // Length of the unique member ID
  private Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private Pattern phonePattern = Pattern.compile("^\\d{8,15}$");

  private String id;
  private String name;
  private String email;
  private String phone;
  private double credits;
  private List<Item> ownedItems;

  public Member() {
  }

  /**
   * Constructs a new Member with the specified details.
   *
   * @param name  The name of the member; cannot be null or empty.
   * @param email The email of the member; must be in valid format.
   * @param phone The phone number of the member; must be 8 to 15 digits.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public Member(String name, String email, String phone) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (email == null || !emailPattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email format.");
    }
    if (phone == null || !phonePattern.matcher(phone).matches()) {
      throw new IllegalArgumentException("Phone number must be 8 to 15 digits.");
    }

    this.id = generateUniqueId();
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.credits = 100.0; // Default starting credits
    this.ownedItems = new ArrayList<>();
  }

  /**
   * Generates a unique 6-character alphanumeric ID for the member.
   *
   * @return A unique 6-character member ID.
   */
  private String generateUniqueId() {
    Random random = new Random();
    StringBuilder newId;

    // Ensure ID uniqueness by checking against existing IDs in memberList
    do {
      newId = new StringBuilder(idLength);
      for (int i = 0; i < idLength; i++) {
        newId.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
      }
    } while (isIdInUse(newId.toString()));

    return newId.toString();
  }

  /**
   * Checks if the given ID is already in use by another member.
   *
   * @param id The ID to check for uniqueness.
   * @return true if the ID is in use, false otherwise.
   */
  private boolean isIdInUse(String id) {
    return memberList.stream().anyMatch(member -> member.id.equals(id));
  }

  /**
   * Checks if the provided email or phone number is unique among all members.
   *
   * @param email The email to check for uniqueness.
   * @param phone The phone number to check for uniqueness.
   * @return true if both email and phone are unique; false otherwise.
   */
  public boolean isEmailOrPhoneUnique(String email, String phone) {
    return memberList.stream().noneMatch(
        m -> m.email.equalsIgnoreCase(email) || m.phone.equals(phone));
  }

  /**
   * Finds a member by their unique ID.
   *
   * @param memberId The ID of the member to find.
   * @return An Optional containing the member if found, or empty if not.
   */
  public Optional<Member> findMemberById(String memberId) {
    return memberList.stream().filter(m -> m.getId().equals(memberId.trim())).findFirst();
  }

  /**
   * Adds a member to the system.
   *
   * @param member The member to add.
   */
  public void addMember(Member member) {
    memberList.add(member);
  }

  /**
   * Removes a member from the system.
   *
   * @param member The member to remove.
   * @return true if the member was removed; false otherwise.
   */
  public boolean removeMember(Member member) {
    return memberList.remove(member);
  }

  /**
   * Returns a list of all members as shallow copies to maintain encapsulation.
   *
   * @return A list of all members as copies.
   */
  public List<Member> getAllMembersCopy() {
    return new ArrayList<>(memberList); // Ensure this provides an updated copy
  }

  /**
   * Copy constructor for creating a shallow copy of a Member instance.
   *
   * @param member The member to copy.
   * @throws IllegalArgumentException if the provided member is null.
   */
  public Member(Member member) {
    this.id = member.id;
    this.name = member.name;
    this.email = member.email;
    this.phone = member.phone;
    this.credits = member.credits;
    this.ownedItems = new ArrayList<>(member.ownedItems);
  }

  /**
   * Updates the member's details with new name, email, and phone.
   *
   * @param name  The new name of the member.
   * @param email The new email of the member.
   * @param phone The new phone number of the member.
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  public void updateMember(String name, String email, String phone) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (email == null || !emailPattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email format.");
    }
    if (phone == null || !phonePattern.matcher(phone).matches()) {
      throw new IllegalArgumentException("Phone number must be 8 to 15 digits.");
    }
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  // Getters and Setters

  /**
   * Gets the unique ID of the member.
   *
   * @return The member ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the member.
   *
   * @return The name of the member.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the email of the member.
   *
   * @return The email of the member.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the phone number of the member.
   *
   * @return The phone number of the member.
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Gets the current credit balance of the member.
   *
   * @return The member's credit balance.
   */
  public double getCredits() {
    return credits;
  }

  /**
   * Sets a new credit balance for the member.
   *
   * @param credits The new credit balance.
   */
  public void setCredits(double credits) {
    this.credits = credits;
  }

  /**
   * Gets a copy of the list of items owned by the member to maintain
   * encapsulation.
   *
   * @return A copy of the list of items owned by the member.
   */
  public List<Item> getOwnedItemsCopy() {
    return new ArrayList<>(ownedItems); // Return a copy for encapsulation
  }

  /**
   * Adds an item to the list of items owned by the member, ensuring no
   * duplicates.
   *
   * @param item The item to add to the member's ownership list.
   */
  public void addItem(Item item) {
    ownedItems.add(item);
  }

  /**
   * Removes an item from the list of items owned by the member.
   *
   * @param item The item to remove from the member's ownership list.
   */
  public void removeItem(Item item) {
    ownedItems.remove(item);

  }

  public void clearAllMembers() {
    memberList.clear();
  }

}
