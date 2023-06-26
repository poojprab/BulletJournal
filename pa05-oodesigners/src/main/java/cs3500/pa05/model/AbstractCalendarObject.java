package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents abstract calendar object
 */
public abstract class AbstractCalendarObject implements CalendarObject {

  public String name;

  public String description;
  public DayOfWeek weekday;

  /**
   * constructor for calendar object
   *
   * @param name name of object
   * @param description object description
   * @param weekday object day of week
   */
  @JsonCreator
  AbstractCalendarObject(
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("weekday") DayOfWeek weekday) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
  }

  /**
   * getter for name
   *
   * @return calendar object name
   */
  public String getName() {
    return this.name;
  }

  /**
   * getter for description
   *
   * @return calendar object description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * getter for weekday
   *
   * @return calendar object day of week
   */
  public DayOfWeek getWeekday() {
    return this.weekday;
  }
}
