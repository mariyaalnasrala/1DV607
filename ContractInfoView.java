package view;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import model.Contract;

/**
 * The ContractInfoView class handles the display of contract-related
 * information and collection of input data related to contracts. It provides
 * methods for displaying contracts, gathering contract creation data, and
 * showing error or success messages to the user.
 */
public class ContractInfoView {

  private Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

  /**
   * Displays a list of contracts. If no contracts exist, a message indicating
   * "No contracts found" is displayed.
   *
   * @param contracts The list of contracts to be displayed.
   */
  public void displayContracts(List<Contract> contractsFromDatainitializer, List<Contract> contracts) {
    Set<Contract> uniqueContracts = new HashSet<>(contractsFromDatainitializer);
    uniqueContracts.addAll(contracts);

    System.out.println("\nList of Contracts:");
    if (uniqueContracts.isEmpty()) {
      System.out.println("No contracts found.");
    } else {
      for (Contract contract : uniqueContracts) {
        displayContractDetails(contract);
      }
    }
  }

  /**
   * Displays detailed information for a single contract, including the item,
   * owner, renter details, and rental period.
   *
   * @param contract The contract to display.
   */
  public void displayContractDetails(Contract contract) {
    System.out.printf(
        "Contract ID: %s%n"
            + "Item: %s (ID: %d)%n"
            + "Owner: %s (ID: %s)%n"
            + "Renter: %s (ID: %s)%n"
            + "Start Day: %d%n"
            + "End Day: %d%n"
            + "Total Cost: %.2f%n"
            + "Status: %s%n",
        contract.getId(),
        contract.getItem().getName(), contract.getItem().getId(),
        contract.getItem().getOwner().getName(), contract.getItem().getOwner().getId(),
        contract.getRenter().getName(), contract.getRenter().getId(),
        contract.getStartDay(),
        contract.getEndDay(),
        contract.getTotalCost(),
        (contract.isProcessed() ? "Processed" : "Pending"));
  }

  /**
   * Collects information from the user for creating a new contract, including
   * the item ID, renter ID, start day, and end day.
   *
   * @return An array containing the item ID, renter ID, start day, and end day.
   */
  public Object[] collectContractCreationInput() {
    System.out.println("Enter the item ID:");
    final int itemId = promptForInt();

    System.out.println("Enter the renter member ID:");
    String renterId = scanner.nextLine().trim();

    System.out.println("Enter the start day (e.g., day 5):");
    int startDay = promptForInt();

    System.out.println("Enter the end day (e.g., day 7):");
    int endDay = promptForInt();

    return new Object[] { itemId, renterId, startDay, endDay };
  }

  /**
   * Prompts the user for an integer input with validation to ensure a valid
   * integer is entered.
   *
   * @return The integer input provided by the user.
   */
  private int promptForInt() {
    while (true) {
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
      }
    }
  }

  /**
   * Displays a success message upon successful creation of a contract.
   *
   * @param contract The contract that was successfully created.
   */
  public void displayContractCreationSuccess(Contract contract) {
    System.out.println("Contract created successfully:");
    displayContractDetails(contract);
  }

  /**
   * Displays an error message indicating insufficient funds for the renter to
   * cover the rental cost.
   */
  public void displayInsufficientFundsError() {
    System.out.println("Error: The renter does not have enough credits to cover the rental cost.");
  }

  /**
   * Displays an error message when a date conflict occurs, indicating that the
   * specified rental period overlaps with an existing contract.
   */
  public void displayDateConflictError() {
    System.out.println("Error: The specified rental period conflicts with an existing contract.");
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
