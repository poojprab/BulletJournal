package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * a JSON record to represent a calendar
 *
 * @param weekName the name of the calendar
 * @param sundayObjects the objects for Sunday in the calendar (represented as a String)
 * @param mondayObjects the objects for Monday in the calendar (represented as a String)
 * @param tuesdayObjects the objects for Tuesday in the calendar (represented as a String)
 * @param wednesdayObjects the objects for Wednesday in the calendar (represented as a String)
 * @param thursdayObjects the objects for Thursday in the calendar (represented as a String)
 * @param fridayObjects the objects for Friday in the calendar (represented as a String)
 * @param saturdayObjects the objects for Saturday in the calendar (represented as a String)
 * @param taskRestriction the number of task restrictions per day in the calendar
 * @param eventRestriction the number of event restrictions per day in the calendar
 * @param quotesAndNotes the quotes and notes of the calendar (represented as a String)
 */
public record CalendarJson(
    @JsonProperty("weekName") String weekName,
    @JsonProperty("Sunday") List<String> sundayObjects,
    @JsonProperty("Monday") List<String> mondayObjects,
    @JsonProperty("Tuesday") List<String> tuesdayObjects,
    @JsonProperty("Wednesday") List<String> wednesdayObjects,
    @JsonProperty("Thursday") List<String> thursdayObjects,
    @JsonProperty("Friday") List<String> fridayObjects,
    @JsonProperty("Saturday") List<String> saturdayObjects,
    @JsonProperty("eventRestriction") int eventRestriction,
    @JsonProperty("taskRestriction") int taskRestriction,
    @JsonProperty("QuotesAndNotes") List<String> quotesAndNotes
) {
}
