package cs3500.pa05.model;

/**
 * represents day of week
 */
public enum DayOfWeek {
  SUNDAY(1),
  MONDAY(2),
  TUESDAY(3),
  WEDNESDAY(4),
  THURSDAY(5),
  FRIDAY(6),
  SATURDAY(7);

  private final int value;

  /**
   * constructor for day of week
   *
   * @param value value of weekday
   */
  DayOfWeek(int value) {
    this.value = value;
  }

  /**
   * checks for valid day of week
   *
   * @param val day of week
   * @return valid day of week
   */
  public static DayOfWeek convertValue(int val) {
    for (DayOfWeek day : DayOfWeek.values()) {
      if (day.value == val) {
        return day;
      }
    }
    throw new IllegalArgumentException("invalid day");
  }


  public int getValue() {
    return this.value;
  }
}
