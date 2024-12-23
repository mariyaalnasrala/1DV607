package view;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import model.Item;
import model.ItemCategory;
import model.Member;

/**
 * The ItemInfoView class handles the display of item-related information
 * and the collection of input data for items. It provides methods to display
 * lists of items, detailed item information, and gather item creation or update
 * data from the user.
 */
public class ItemInfoView {

  private Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
  // private Member member = new Member();

  /**
   * Displays a list of items to the user. If no items are available, a message
   * indicating "No items found" is displayed.
   *
   * @param items The of items to be displayed.
   */
  public void displayItems(List<Item> itemsFromDataInitializer, List<Item> items) {
    System.out.println("\nList of Items:");
    List<Item> combinedItems = new ArrayList<>();
    combinedItems.addAll(itemsFromDataInitializer);
    combinedItems.addAll(items);
    Set<Integer> seenIds = new HashSet<>();

    // Filtrera och visa endast unika items baserat på ID
    combinedItems.stream()
        .filter(item -> item != null && seenIds.add(item.getId()))
        .forEach(this::displayItemDetails);
  }

  /**
   * Displays detailed information for a single item, including its ID, name,
   * description, category, cost per day, owner, and availability.
   *
   * @param item The item to display.
   */
  public void displayItemDetails(Item item) {
    System.out.printf("Item ID: %d%nName: %s%nDescription: %s%nCategory: %s%n"
        + "Cost per Day: %.2f%nOwner: %s (ID: %s)%nAvailable: %s%n",
        item.getId(), item.getName(), item.getDescription(), item.getCategory(),
        item.getCostPerDay(), item.getOwner().getName(), item.getOwner().getId(),
        item.isAvailable() ? "Yes" : "No");
  }

  /**
   * Collects information for creating a new item, prompting the user for item
   * name, description, category, cost per day, and owner ID.
   *
   * @return An array containing the item name, description, category (as
   *         ItemCategory),
   *         cost per day, and owner ID, or null if any required input is invalid.
   */
  public Object[] collectItemCreationInput(List<Member> members) {

    String name;
    String description;
    ItemCategory category = null;
    Double costPerDay = null;
    Member owner = null;

    // loop to collect item name.
    while (true) {
      System.out.println("Enter the item name:");
      name = scanner.nextLine().trim();

      if (!name.isEmpty()) {
        break;
      }

      displayInvalidInputMessage("Item name cannot be empty.");
    }

    // loop to collect the item description.
    while (true) {
      System.out.println("Enter the item description:");
      description = scanner.nextLine().trim();
      if (!description.isEmpty()) {
        break;
      }
      displayInvalidInputMessage("Item description cannot be empty.");
    }

    // loop to collect item category.
    while (true) {
      System.out.println("Enter the item category (Options: VEHICLE, TOOL, ELECTRONICS, FURNITURE, OTHER):");
      String categoryStr = scanner.nextLine().trim();
      try {
        category = ItemCategory.fromString(categoryStr); // Försök att konvertera till ItemCategory
        if (category != null) {
          break;
        }
      } catch (IllegalArgumentException e) {
        displayInvalidInputMessage(
            "Invalid category. Please enter one of the following: VEHICLE, TOOL, ELECTRONICS, FURNITURE, OTHER.");
      }
    }

    // loop to collect cost per day.
    while (true) {
      System.out.println("Enter the cost per day:");
      costPerDay = promptForDouble();
      if (costPerDay != null && costPerDay > 0) {
        break;
      }
      displayInvalidInputMessage("Cost per day must be a positive number.");
    }

    // loop to get correct member id.

    while (true) {
      System.out.println("Enter the owner member ID:");
      String ownerId = scanner.nextLine().trim();

      if (ownerId.isEmpty()) {
        displayInvalidInputMessage("Owner ID cannot be empty. Please enter a valid member ID.");
        continue;
      }

      // found the member.
      Optional<Member> memberOpt = members.stream()
          .filter(member -> member.getId().equals(ownerId))
          .findFirst();

      if (memberOpt.isPresent()) {
        owner = memberOpt.get();
        // Item newItem = new Item(name, description, category, costPerDay,
        // memberOpt.get());
        // owner.addItem(newItem);
        // System.out.println("Item added to member: " + memberOpt.get().getName());
        break;
      } else {
        displayInvalidInputMessage("Owner not found. Please enter a valid member ID.");
      }
    }

    Item newItem = new Item(name, description, category, costPerDay, owner);
    owner.addItem(newItem);
    System.out.printf("Item '%s' added to member '%s' (ID: %s).%n ", name, owner.getName(), owner.getId());

    return new Object[] { name, description, category, costPerDay, owner };
  }

  /**
   * Collects updated information for an existing item, including name,
   * description,
   * category, and cost per day.
   *
   * @return An array containing the updated item name, description, category, and
   *         cost per day.
   */
  public Object[] collectItemUpdateInput() {

    String name;
    while (true) {
      System.out.println("Enter the new item name:");
      name = scanner.nextLine().trim();
      if (!name.isEmpty()) {
        break;
      }
      displayInvalidInputMessage("Item name cannot be empty.");
    }

    String description;
    while (true) {
      System.out.println("Enter the new item description:");
      description = scanner.nextLine().trim();
      if (!description.isEmpty()) {
        break;
      }

      displayInvalidInputMessage("Item description cannot be empty.");

    }

    ItemCategory category = null;
    while (true) {
      System.out.println("Enter the new item category (Options: VEHICLE, TOOL, ELECTRONICS, FURNITURE, OTHER):");
      String categoryStr = scanner.nextLine().trim();
      try {
        category = ItemCategory.fromString(categoryStr);
        if (category != null) {
          break;
        }
      } catch (IllegalArgumentException e) {
        displayInvalidInputMessage(
            "Invalid category. Please enter one of the following: VEHICLE, TOOL, ELECTRONICS, FURNITURE, OTHER.");
      }
    }

    Double costPerDay;
    while (true) {
      System.out.println("Enter the new cost per day:");
      costPerDay = promptForDouble();
      if (costPerDay != null && costPerDay > 0) {
        break;
      }
      displayInvalidInputMessage("Cost per day must be a positive number.");
    }

    return new Object[] { name, description, category, costPerDay };
  }

  /**
   * Prompts the user for a double input, validating that the input is a
   * valid number.
   *
   * @return The double input from the user, or null if invalid.
   */
  private Double promptForDouble() {
    while (true) {
      try {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
          System.out.println("Invalid input. Please enter a valid number.");
          continue;
        }
        return Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
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
   * Displays a success message when an item is successfully created, showing
   * the details of the created item.
   *
   * @param item The item that was successfully created.
   */
  public void displayItemCreationSuccess(Item item) {
    System.out.println("Item created successfully:");
    displayItemDetails(item);
  }

  /**
   * Displays a success message when an item is successfully updated, showing
   * the details of the updated item.
   *
   * @param item The item that was successfully updated.
   */
  public void displayItemUpdateSuccess(Item item) {
    System.out.println("Item updated successfully:");
    displayItemDetails(item);
  }

  /**
   * Displays a custom message to the user.
   *
   * @param message The message to be displayed.
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }
}
