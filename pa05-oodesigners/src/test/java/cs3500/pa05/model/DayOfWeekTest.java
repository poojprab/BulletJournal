package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * represents tests for the DayOfWeek enumeration
 */
class DayOfWeekTest {

  /**
   * test the method convertValue(int)
   */
  @Test
  public void convertValue() {
    assertEquals(DayOfWeek.SUNDAY, DayOfWeek.convertValue(1));
    assertEquals(DayOfWeek.MONDAY, DayOfWeek.convertValue(2));
    assertEquals(DayOfWeek.TUESDAY, DayOfWeek.convertValue(3));
    assertEquals(DayOfWeek.WEDNESDAY, DayOfWeek.convertValue(4));
    assertEquals(DayOfWeek.THURSDAY, DayOfWeek.convertValue(5));
    assertEquals(DayOfWeek.FRIDAY, DayOfWeek.convertValue(6));
    assertEquals(DayOfWeek.SATURDAY, DayOfWeek.convertValue(7));
    IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
        () -> DayOfWeek.convertValue(8));
    assertEquals("invalid day", iae.getMessage());
  }

  /**
   * test the method getValue(int)
   */
  @Test
  public void getValue() {
    assertEquals(1, DayOfWeek.SUNDAY.getValue());
    assertEquals(2, DayOfWeek.MONDAY.getValue());
    assertEquals(3, DayOfWeek.TUESDAY.getValue());
    assertEquals(4, DayOfWeek.WEDNESDAY.getValue());
    assertEquals(5, DayOfWeek.THURSDAY.getValue());
    assertEquals(6, DayOfWeek.FRIDAY.getValue());
    assertEquals(7, DayOfWeek.SATURDAY.getValue());
  }
}