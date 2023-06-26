package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the Event class
 */
class EventTest {
  Event event;

  /**
   * initializes event for tests
   */
  @BeforeEach
  public void initData() {
    event = new Event("CS3500 Lecture", "In WVF", DayOfWeek.MONDAY,
        "09:50:00", 100);
  }

  /**
   * test the method getStartTime()
   */
  @Test
  public void getStartTime() {
    assertEquals("09:50:00", event.getStartTime());
  }

  /**
   * test the method getDuration()
   */
  @Test
  public void getDuration() {
    assertEquals(100, this.event.getDuration());
  }
}