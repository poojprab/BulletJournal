package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the Task class
 */
class TaskTest {
  Task task;

  /**
   * initializes data for tests
   */
  @BeforeEach
  public void initData() {
    task = new Task("Finish PA05", "CS3500 Java Journal", DayOfWeek.SUNDAY);
  }

  /**
   * test the method updateIsComplete(boolean)
   */
  @Test
  public void updateIsComplete() {
    assertFalse(task.getIsComplete());
    task.updateIsComplete(true);
    assertTrue(task.getIsComplete());
  }
}