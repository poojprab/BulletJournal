package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.CalendarJson;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * represents a class that uses Proxy Pattern to convert a calendar's attributes to a JSON
 */
public class ProxyConverter {
  ObjectMapper mapper;

  /**
   * constructor for the ProxyConverter class
   *
   */
  public ProxyConverter() {
    mapper = new ObjectMapper();
  }

  /**
   * creates a CalendarJSON with the data of a calendar
   *
   * @param weekName the week name of the calendar
   * @param maxEvents the max events allowed per day in the calendar
   * @param maxTasks the max tasks allowed per day in the calendar
   * @param taskList the list of tasks in the taskbar in the calendar
   * @return the CalendarJson created with the data
   */
  public CalendarJson createCalendar(String weekName, int maxEvents, int maxTasks,
                                     List<Task> taskList, List<Event> eventList,
                                     List<String> quotesNotes) {
    ArrayList<ArrayList<String>> allObjects = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    for (int i = 1; i < 8; i++) {
      allObjects.add(new ArrayList<>());
    }
    for (Task t : taskList) {
      try {
        String jsonString = objectMapper.writeValueAsString(t);
        allObjects.get(t.getWeekday().getValue() - 1).add(jsonString);
      } catch (IOException e) {
        System.err.println(e);
      }
    }
    for (Event e : eventList) {
      try {
        String jsonString = objectMapper.writeValueAsString(e);
        allObjects.get(e.getWeekday().getValue() - 1).add(jsonString);
      } catch (IOException exc) {
        System.err.println(exc);
      }
    }

    return new CalendarJson(weekName, allObjects.get(0),
        allObjects.get(1), allObjects.get(2), allObjects.get(3), allObjects.get(4),
        allObjects.get(5), allObjects.get(6), maxEvents, maxTasks, quotesNotes);
  }

  /**
   * converts a .bujo file's contents into a CalendarJSON (receives the calendar information)
   *
   * @param p the path of the .bujo file
   * @return the created CalendarJSON
   */
  public CalendarJson receiveCalendar(Path p) {
    try {
      StringBuilder sb = new StringBuilder();
      Scanner scanner = new Scanner(p);
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
      }
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(sb.toString(), CalendarJson.class);
    } catch (IOException exc) {
      System.err.println("could not read .bujo file");
    }
    throw new IllegalArgumentException("could not read .bujo file contents");
  }
}
