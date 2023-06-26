package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a task
 */
public class Task extends AbstractCalendarObject {
  public boolean isComplete;

  /**
   * constructor for task
   *
   * @param name task name
   * @param description task description
   * @param weekday task weekday
   */
  @JsonCreator
  public Task(
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("weekday") DayOfWeek weekday) {
    super(name, description, weekday);
    isComplete = false;
  }

  /**
   * updates whether task is complete
   *
   * @param updateTo value to change completion status to
   */
  public void updateIsComplete(boolean updateTo) {
    this.isComplete = updateTo;
  }

  /**
   * getter for isComplete
   *
   * @return isComplete
   */
  public boolean getIsComplete() {
    return this.isComplete;
  }
}