package cs3500.pa05.model;

/**
 * represents an object in a calendar
 */
public interface CalendarObject {

  /**
   * getter for name
   *
   * @return calendar object name
   */
  String getName();

  /**
   * getter for description
   *
   * @return calendar object description
   */
  String getDescription();

  /**
   * getter for weekday
   *
   * @return calendar object day of week
   */
  DayOfWeek getWeekday();
}
