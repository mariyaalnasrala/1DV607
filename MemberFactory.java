package model;

import java.util.regex.Pattern;

/**
 * Factory class for creating Member instances with validation. This class
 * ensures that each member has unique email and phone attributes and that
 * all parameters meet required conditions.
 */
public class MemberFactory {

  private Member member = new Member();
  // Renamed constants to follow CheckStyle naming conventions
  private Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private Pattern phonePattern = Pattern.compile("^\\d{8,15}$");


  /**
   * Creates a new Member instance with validation for name, email, and phone.
   * Ensures that name, email, and phone are non-empty and unique within the
   * system.
   *
   * @param name  The name of the member; must be non-null and non-empty.
   * @param email The email of the member; must be non-null, non-empty, and
   *              unique.
   * @param phone The phone number of the member; must be non-null, non-empty, and
   *              unique.
   * @return A validated Member instance.
   * @throws IllegalArgumentException if any parameter is invalid, such as a null
   *                                  or empty name, email, or phone, or if the
   *                                  email
   *                                  or phone is not unique.
   */
  public Member createMember(String name, String email, String phone) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (email == null || email.isBlank() || !emailPattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Email cannot be empty and must be valid.");
    }
    if (phone == null || phone.isBlank() || !phonePattern.matcher(phone).matches()) {
      throw new IllegalArgumentException("Phone cannot be empty and must be valid.");
    }
    if (!member.isEmailOrPhoneUnique(email, phone)) {
      throw new IllegalArgumentException("Email or phone number already exists.");
    }

    return new Member(name, email, phone);
  }
}
