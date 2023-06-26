package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the CalendarJSON record
 */
class CalendarJsonTest {
  CalendarJson calendar;
  Event mondayEvent;
  Task sundayTask;
  String monEventAsString;
  String sunTaskAsString;

  /**
   * initializes data for tests
   */
  @BeforeEach
  public void initData() {
    mondayEvent = new Event("Juneteenth", "holiday", DayOfWeek.MONDAY,
        "12:00", 1440);
    sundayTask = new Task("Alpha Deadline", "PA05 section 1, 2, 3 implementation due",
        DayOfWeek.MONDAY);
    try {
      monEventAsString = new ObjectMapper().writeValueAsString(mondayEvent);
      sunTaskAsString = new ObjectMapper().writeValueAsString(mondayEvent);
    } catch (IOException exc) {
      System.err.println("could not convert object to string");
    }

    calendar = new CalendarJson("last week of summer 1",
        new ArrayList<>(Arrays.asList(sunTaskAsString)),
            new ArrayList<>(Arrays.asList(monEventAsString)),
               new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), 2, 1,
                new ArrayList<>(Arrays.asList("hehe", "test note", "test quote")));
  }

  /**
   * test the weekName() method that gets the calendar's weekname
   */
  @Test
  public void weekName() {
    assertEquals("last week of summer 1", calendar.weekName());
  }

  /**
   * test the sundayObjects() method that gets the list of sunday objects as strings
   */
  @Test
  public void sundayObjects() {
    assertEquals(new ArrayList<String>(Arrays.asList("{\"name\":\"Juneteenth\",\"description"
            + "\":\"holiday\",\"weekday\":\"MONDAY\",\"startTime\":\"12:00\",\"duration\":1440}")),
        calendar.sundayObjects());
  }

  /**
   * test the mondayObjects() method that gets the list of monday objects as strings
   */
  @Test
  public void mondayObjects() {
    assertEquals(new ArrayList<String>(Arrays.asList("{\"name\":\"Juneteenth\",\"description"
            + "\":\"holiday\",\"weekday\":\"MONDAY\",\"startTime\":\"12:00\",\"duration\":1440}")),
        calendar.mondayObjects());
  }

  /**
   * test the tuesdayObjects() method that gets the list of tuesday objects as strings
   */
  @Test
  public void tuesdayObjects() {
    assertEquals(new ArrayList<String>(), calendar.tuesdayObjects());
  }

  /**
   * test the wednesdayObjects() method that gets the list of wednesday objects as strings
   */
  @Test
  public void wednesdayObjects() {
    assertEquals(new ArrayList<String>(), calendar.wednesdayObjects());
  }

  /**
   * test the thursdayObjects() method that gets the list of thursday objects as strings
   */
  @Test
  public void thursdayObjects() {
    assertEquals(new ArrayList<String>(), calendar.thursdayObjects());
  }

  /**
   * test the fridayObjects() method that gets the list of friday objects as strings
   */
  @Test
  public void fridayObjects() {
    assertEquals(new ArrayList<String>(), calendar.fridayObjects());
  }

  /**
   * test the saturdayObjects() method that gets the list of saturday objects as strings
   */
  @Test
  public void saturdayObjects() {
    assertEquals(new ArrayList<String>(), calendar.saturdayObjects());
  }

  /**
   * test the taskRestriction() method
   */
  @Test
  public void taskRestriction() {
    assertEquals(1, calendar.taskRestriction());
  }

  @Test
  public void eventRestriction() {
    assertEquals(2, calendar.eventRestriction());
  }

  /**
   * test the quotesAndNotes() method
   */
  @Test
  public void quotesAndNotes() {
    assertEquals(new ArrayList<String>(Arrays.asList("hehe", "test note", "test quote")),
        calendar.quotesAndNotes());
  }
}