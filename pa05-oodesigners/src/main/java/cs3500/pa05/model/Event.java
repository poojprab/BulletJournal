package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

/**
 * represents an event
 */
public class Event extends AbstractCalendarObject {

  public String startTime;
  public int duration;

  /**
   * constructor for event
   *
   * @param name event name
   * @param description event description
   * @param weekday event weekday
   * @param startTime event start time
   * @param duration event duration
   */
  @JsonCreator
  public Event(
      @JsonProperty("name") String name,
      @JsonProperty("description") String description,
      @JsonProperty("weekday") DayOfWeek weekday,
      @JsonProperty("startTime") String startTime,
      @JsonProperty("duration") int duration) {
    super(name, description, weekday);
    this.startTime = startTime;
    this.duration = duration;
  }

  /**
   * getter for start time
   *
   * @return start time
   */
  public String getStartTime() {
    return this.startTime;
  }

  /**
   * getter for duration
   *
   * @return duration
   */
  public int getDuration() {
    return this.duration;
  }
}
