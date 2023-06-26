package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the AbstractCalendarObject class
 */
class CalendarObjectTest {
  AbstractCalendarObject task;
  AbstractCalendarObject event;

  /**
   * initializes CalendarObjects for tests
   */
  @BeforeEach
  public void initData() {
    task = new Task("Finish PA05", "CS3500 Java Journal", DayOfWeek.SUNDAY);
    event = new Event("CS3500 Lecture", "In WVF", DayOfWeek.MONDAY,
       "09:50:00", 100);
  }

  /**
   * test the method getName()
   */
  @Test
  public void getName() {
    assertEquals("Finish PA05", this.task.getName());
    assertEquals("CS3500 Lecture", this.event.getName());
  }

  /**
   * test the method getDescription()
   */
  @Test
  public void getDescription() {
    assertEquals("CS3500 Java Journal", this.task.getDescription());
    assertEquals("In WVF", this.event.getDescription());
  }

  /**
   * test the method getWeekday()
   */
  @Test
  public void getWeekday() {
    assertEquals(DayOfWeek.SUNDAY, this.task.getWeekday());
    assertEquals(DayOfWeek.MONDAY, this.event.getWeekday());
  }
}