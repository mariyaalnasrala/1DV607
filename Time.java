package model;

/**
 * The Time class manages the progression of time within the system by tracking
 * the current day. It provides methods to advance the day, retrieve the current
 * day,
 * and reset the day count. Contracts are processed based on the passage of
 * days.
 */
public class Time {
  private int currentDay = 0;
  private Contract contract = new Contract();

  /**
   * Advances the current day by a specified number of days and processes any
   * contracts due up to the new current day.
   *
   * @param days The number of days to advance; must be non-negative.
   * @throws IllegalArgumentException if days is negative.
   */
  public void advanceDays(int days) {
    if (days < 0) {
      throw new IllegalArgumentException("Days to advance cannot be negative.");
    }
    currentDay += days;
    contract.processDueContracts(currentDay); // Process any contracts due by this new day
  }

  /**
   * Gets the current day in the system.
   *
   * @return The current day as an integer, representing the number of days that
   *         have passed since the system started.
   */
  public int getCurrentDay() {
    return currentDay;
  }

  /**
   * Resets the current day to zero. Typically used during system initialization
   * to restart the day count.
   */
  public void resetDay() {
    currentDay = 0;
  }
}
