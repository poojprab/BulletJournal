package cs3500.pa05.controller;

import static javafx.geometry.Pos.BASELINE_LEFT;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.Driver;
import cs3500.pa05.model.CalendarJson;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * represents bujo controller implementation
 */
public class BujoControllerImpl implements BujoController {
  @FXML
  private Scene scene;
  @FXML
  private GridPane calendar;
  @FXML
  private HBox menuBar;
  @FXML
  private VBox taskBar;
  @FXML
  private Button newTaskButton;
  @FXML
  private Button newEventButton;
  @FXML
  private Button saveButton;
  @FXML
  private Button newWeekButton;
  @FXML
  private Button openButton;
  @FXML
  private Button changeTasksMaxButton;
  @FXML
  private Button changeEventsMaxButton;
  @FXML
  private Label maxTasksLabel;
  @FXML
  private Label maxEventsLabel;
  @FXML
  private Label taskExceptionPrinted;
  @FXML
  private Label eventExceptionPrinted;
  @FXML
  private VBox quotesAndNotes;
  @FXML
  private Button addQuoteNote;
  @FXML
  private Label taskBarLabel;
  @FXML
  private TextField weekName;
  @FXML
  private Label weekdayExceptionPrinted;
  @FXML
  private ImageView imageView;
  private Path bujoFilePath;
  private final Stage primaryStage;
  private final ProxyConverter converter;
  private List<String> quotesNotes;
  private List<Task> totalListOfTasks;
  private List<Event> totalListOfEvents;
  private int numMaxTasks;
  private int numMaxEvents;
  private int tempObjects;
  private Map<DayOfWeek, VBox> tasksVboxMap;
  private Map<DayOfWeek, VBox> eventsVboxMap;

  /**
   * constructor for bujo controller implementation
   *
   * @param stage the stage
   */
  public BujoControllerImpl(Stage stage) {
    primaryStage = stage;
    totalListOfTasks = new ArrayList<>();
    totalListOfEvents = new ArrayList<>();
    quotesNotes = new ArrayList<>();
    tasksVboxMap = new LinkedHashMap<>();
    eventsVboxMap = new LinkedHashMap<>();
    this.converter = new ProxyConverter();
  }

  /**
   * runs the program
   */
  public void run() {
    handleOpenClick();
  }

  /**
   * initializes calendar
   */
  private void initCalendar() {
    this.eventsVboxMap = new LinkedHashMap<>();
    this.tasksVboxMap = new LinkedHashMap<>();
    for (int i = 1; i < this.calendar.getColumnCount(); i++) {
      for (int j = 1; j < this.calendar.getRowCount(); j++) {
        if (j == 2) {
          VBox veBox = new VBox();
          this.calendar.add(veBox, i, j);
          this.eventsVboxMap.put(DayOfWeek.convertValue(i), veBox);
        } else {
          VBox veBox = new VBox();
          this.calendar.add(veBox, i, j);
          this.tasksVboxMap.put(DayOfWeek.convertValue(i), veBox);
        }
      }
    }
    Image gif = new Image(new File("src/main/resources/journal.gif").toURI().toString());
    imageView.setImage(gif);
  }

  /**
   * handlers for buttons
   */
  private void initButtons() {
    styleMenuButton(newTaskButton);
    newTaskButton.setOnAction(event -> handleTaskDialog());
    styleMenuButton(newEventButton);
    newEventButton.setOnAction(event -> handleEventDialog());
    styleMenuButton(saveButton);
    saveButton.setOnAction(event -> handleSaveBujo());
    styleMenuButton(openButton);
    openButton.setOnAction(event -> handleOpenClick());
    styleUpdateButton(changeTasksMaxButton);
    changeTasksMaxButton.setOnAction(event ->
        numMaxTasks = handleMaxCalendarObjects(maxTasksLabel, numMaxTasks, taskExceptionPrinted));
    styleUpdateButton(changeEventsMaxButton);
    changeEventsMaxButton.setOnAction(event ->
        numMaxEvents = handleMaxCalendarObjects(maxEventsLabel, numMaxEvents,
            eventExceptionPrinted));
    addQuoteNote.setOnAction(event -> handleAddQuoteNote());
    styleMenuButton(newWeekButton);
    newWeekButton.setOnAction(event -> handleNewWeekClick());
  }

  /**
   * handlers for keyboard shortcuts
   */
  private void initButtonKeyShortcuts() {
    this.scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
      if (event.isShortcutDown() && event.getCode() == KeyCode.T) {
        handleTaskDialog();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.E) {
        handleEventDialog();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.S) {
        handleSaveBujo();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.O) {
        handleOpenClick();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.N) {
        handleNewWeekClick();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.Q) {
        handleAddQuoteNote();
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.X) {
        handleMaxCalendarObjects(this.maxEventsLabel, this.numMaxEvents,
            this.eventExceptionPrinted);
      } else if (event.isShortcutDown() && event.getCode() == KeyCode.M) {
        handleMaxCalendarObjects(this.maxTasksLabel, this.numMaxTasks, this.taskExceptionPrinted);
      }
    });
  }

  /**
   * sets the maximum for abstract calendar object
   *
   * @param objectType task or event
   * @return maximum number of type
   */
  private int handleMaxCalendarObjects(Label objectType, int objectMax, Label objectException) {
    HBox okOrCancel = new HBox();
    styleHbox(okOrCancel);
    Button okayButton = new Button("Okay");
    styleButton(okayButton);
    //new dialog
    Dialog<Node> dialog = new Dialog<>();
    TextField newMaxField = new TextField();
    okayButton.setOnAction(event -> {
      objectType.setText(newMaxField.getText());
      dialog.setResult(new Button("Okay"));
      try {
        tempObjects = Integer.parseInt(newMaxField.getText());
        dialog.close();
        if (tempObjects >= objectMax) {
          System.out.println(tempObjects + " + " + objectMax);
          objectException.setText(" ");
        } else {
          objectException.setText("Invalid data for maximum number");
        }
      } catch (NumberFormatException e) {
        objectException.setText("Invalid input");
        tempObjects = 0;
        objectType.setText(String.valueOf(tempObjects));
      }
    });
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);
    cancelButton.setOnAction(event -> {
      dialog.setResult(new Button("Cancel"));
      dialog.close();
    });
    showDialogPane(newMaxField, dialog, okOrCancel, okayButton, cancelButton);
    return tempObjects;
  }

  /**
   * shows dialog for setting max calendar objects
   *
   * @param newMaxField  the textfield to enter number of max fields in
   * @param dialog       the dialog being shown
   * @param okOrCancel   HBox for the okay and cancel buttons
   * @param okayButton   the okay button
   * @param cancelButton the cancel button
   */
  private void showDialogPane(TextField newMaxField, Dialog<Node> dialog, HBox okOrCancel,
                              Button okayButton, Button cancelButton) {
    okOrCancel.getChildren().addAll(cancelButton, okayButton);

    VBox veBox = new VBox();
    styleVbox(veBox);
    veBox.getChildren().addAll(newMaxField, okOrCancel);

    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(veBox);

    dialog.setDialogPane(dialogPane);
    dialog.showAndWait();
  }

  /**
   * creates calendar dialog for abstract calendar object
   *
   * @param userText    map of dialog and text fields
   * @param name        name of task or event
   * @param description description of task or event
   * @param dayOfWeek   day of week task or event is on
   * @return dialog
   */
  private VBox createAbstractCalendarDialog(LinkedHashMap<DialogInfo, TextField> userText,
                                            String name,
                                            String description,
                                            String dayOfWeek) {
    VBox veBox = new VBox();
    styleVbox(veBox);

    this.createMiniViewerFeatures(veBox, "Description: ", description, userText,
        DialogInfo.DESCRIPTION);

    this.createMiniViewerFeatures(veBox, "Name: ", name, userText, DialogInfo.NAME);

    this.createMiniViewerFeatures(veBox, "Day of Week: ", dayOfWeek, userText,
        DialogInfo.DAYOFWEEK);

    return veBox;
  }

  /**
   * styles menu button
   *
   * @param button desired button
   */
  private void styleMenuButton(Button button) {
    button.setStyle("-fx-background-color: #84abf5; -fx-text-fill: white; "
        + "-fx-font-family: \"Mali\"; -fx-font-size: 20px;" + "-fx-background-radius: 15px;");
  }

  private void styleUpdateButton(Button button) {
    button.setStyle("-fx-background-color: #99defc; -fx-text-fill: black; "
        + "-fx-font-family: \"Mali\"; -fx-font-size: 15px;" + "-fx-border-color: #1360cd;");
  }

  /**
   * styles a basic button
   *
   * @param button desired button
   */
  private void styleButton(Button button) {
    button.setStyle("-fx-background-color: #84abf5; -fx-text-fill: white; "
        + "-fx-font-family: \"Mali\";");
  }

  /**
   * styles a basic label
   *
   * @param label desired label
   */
  private void styleLabel(Label label) {
    label.setStyle("-fx-font-family: \"Mali\";");
  }

  /**
   * styles a horizontal box
   *
   * @param hoBox desired box
   */
  private void styleHbox(HBox hoBox) {
    hoBox.setStyle("-fx-background-color: #ebf6ff;");
  }

  /**
   * styles a vertical box
   *
   * @param veBox desired box
   */
  private void styleVbox(VBox veBox) {
    veBox.setStyle("-fx-background-color: #ebf6ff;");
  }

  /**
   * creates task popup/task view
   *
   * @param task          current task
   * @param hoBoxCalendar box for task container
   * @param check         task checkbox
   */
  private void createTaskPopup(Task task, HBox hoBoxCalendar, CheckBox check) {
    LinkedHashMap<DialogInfo, TextField> userText = new LinkedHashMap<>();

    VBox veBox = this.createAbstractCalendarDialog(userText, task.getName(), task.getDescription(),
        task.getWeekday().toString());
    Label completeLabel = new Label("Is this task complete?: " + check.isSelected());
    styleLabel(completeLabel);
    veBox.getChildren().add(veBox.getChildren().size(), completeLabel);

    //add the buttons
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);

    Dialog<Node> dialog = new Dialog<>();
    cancelButton.setOnAction(event -> {
      dialog.setResult(new Button("Cancel"));
      dialog.close();
    });
    Button deleteButton = new Button("Delete");
    styleButton(deleteButton);
    deleteButton.setOnAction(event -> {
      this.tasksVboxMap.get(task.getWeekday()).getChildren().remove(hoBoxCalendar);
      this.taskBar.getChildren().remove(this.totalListOfTasks.indexOf(task) + 1);
      totalListOfTasks.remove(task);
      dialog.setResult(new Button("Delete"));
      dialog.close();
    });
    Button updateButton = new Button("Update");
    styleButton(updateButton);
    updateButton.setOnAction(event -> this.updateTaskBox(dialog, userText, task, hoBoxCalendar));
    HBox buttons = new HBox();
    styleHbox(buttons);
    buttons.getChildren().addAll(cancelButton, deleteButton, updateButton);
    veBox.getChildren().add(buttons);
    veBox.getChildren().add(this.handleOpenUrl(task.getDescription()));

    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(veBox);

    dialog.setDialogPane(dialogPane);
    dialog.showAndWait();
  }

  /**
   * updates the task
   *
   * @param dialog        task dialog
   * @param userText      map of dialog information and text field
   * @param task          task to update
   * @param hoBoxCalendar box for task container
   */
  private void updateTaskBox(Dialog<Node> dialog, Map<DialogInfo, TextField> userText,
                             Task task, HBox hoBoxCalendar) {
    String weekdayInput = userText.get(DialogInfo.DAYOFWEEK).getText().toUpperCase().trim();
    try {
      //makes new task and replaces the old one with the new one
      Task updatedTask = new Task(userText.get(DialogInfo.NAME).getText(),
          userText.get(DialogInfo.DESCRIPTION).getText(),
          DayOfWeek.valueOf(weekdayInput));
      //rest of the code for updating the task and other operations
      this.totalListOfTasks.add(this.totalListOfTasks.indexOf(task), updatedTask);
      this.totalListOfTasks.remove(task);

      CheckBox checkBox = new CheckBox();
      checkBox.getStyleClass().add("custom-checkbox");
      scene.getStylesheets().add("customStyles.css");
      Label label = new Label();
      label.setText("Name: " + userText.get(DialogInfo.NAME).getText() + "\n" + "Description: "
          + userText.get(DialogInfo.DESCRIPTION).getText() + "\n" + "IsComplete?: "
          + this.totalListOfTasks.get(this.totalListOfTasks.size() - 1).getIsComplete());
      styleLabel(label);

      this.taskBar.getChildren().add(this.totalListOfTasks.indexOf(updatedTask) + 1, label);
      this.taskBar.getChildren().remove(this.totalListOfTasks.indexOf(updatedTask) + 2);

      if (!updatedTask.getWeekday().equals(task.getWeekday())) {
        this.tasksVboxMap.get(updatedTask.getWeekday()).getChildren().add(
            this.createTaskButtonBox(updatedTask, checkBox));
      } else {
        this.tasksVboxMap.get(task.getWeekday()).getChildren().add(
            this.tasksVboxMap.get(task.getWeekday()).getChildren().indexOf(hoBoxCalendar),
            this.createTaskButtonBox(updatedTask, checkBox));
      }
      //removes the specific HBOX from the calendar
      this.tasksVboxMap.get(task.getWeekday()).getChildren().remove(hoBoxCalendar);

      Button updateButton = new Button("Update");
      styleButton(updateButton);
      dialog.setResult(updateButton);
      dialog.close();
      weekdayExceptionPrinted.setText("");
    } catch (IllegalArgumentException e) {
      weekdayExceptionPrinted.setText("Entered invalid weekday: " + weekdayInput);
      System.err.println("Entered invalid weekday: " + weekdayInput);
    }
  }

  /**
   * creates popup for event
   *
   * @param event       current event
   * @param eventButton button with event
   */
  private void createEventPopup(Event event, Button eventButton) {
    LinkedHashMap<DialogInfo, TextField> userText = new LinkedHashMap<>();

    VBox veBox = this.createAbstractCalendarDialog(userText, event.getName(),
        event.getDescription(), event.getWeekday().toString());

    this.createMiniViewerFeatures(veBox, "StartTime: ", event.getStartTime(), userText,
        DialogInfo.STARTTIME);
    this.createMiniViewerFeatures(veBox, "Duration: ", String.valueOf(event.getDuration()),
        userText, DialogInfo.DURATION);

    //add the buttons
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);
    Dialog<Node> dialog = new Dialog<>();
    cancelButton.setOnAction(e -> {
      dialog.setResult(new Button("Cancel"));
      dialog.close();
    });
    Button deleteButton = new Button("Delete");
    styleButton(deleteButton);
    deleteButton.setOnAction(e -> {
      this.eventsVboxMap.get(event.getWeekday()).getChildren().remove(eventButton);
      dialog.setResult(new Button("Delete"));
      dialog.close();
    });
    Button updateButton = new Button("Update");
    styleButton(updateButton);
    updateButton.setOnAction(e -> this.updateEventBox(dialog, userText, event, eventButton));

    HBox buttons = new HBox();
    styleHbox(buttons);
    buttons.getChildren().addAll(cancelButton, deleteButton, updateButton);
    veBox.getChildren().add(buttons);
    veBox.getChildren().add(this.handleOpenUrl(event.getDescription()));

    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(veBox);
    dialog.setDialogPane(dialogPane);
    dialog.showAndWait();
  }

  /**
   * creates the mini calendar object viewer's features
   *
   * @param veBox      the box to add the features to
   * @param textLabel  the text for the viewer's label
   * @param text       the text in the text field
   * @param userText   the map to add each text field into
   * @param dialogInfo the components of the object in the viewer
   */
  private void createMiniViewerFeatures(VBox veBox, String textLabel, String text, Map<DialogInfo,
      TextField> userText, DialogInfo dialogInfo) {
    HBox box = new HBox();
    styleHbox(box);
    Label label = new Label(textLabel);
    styleLabel(label);
    TextField textField = new TextField(text);
    userText.put(dialogInfo, textField);
    box.getChildren().addAll(label, textField);
    if (veBox.getChildren().size() > 0) {
      veBox.getChildren().add(veBox.getChildren().size() - 1, box);
    } else {
      veBox.getChildren().add(box);
    }
  }

  /**
   * updates event display
   *
   * @param dialog      event dialog
   * @param userText    map of dialog information and text field
   * @param event       event to update
   * @param eventButton button with event
   */
  private void updateEventBox(Dialog<Node> dialog, Map<DialogInfo, TextField> userText,
                              Event event, Button eventButton) {
    try {
      Event updatedEvent = new Event(userText.get(DialogInfo.NAME).getText(),
          userText.get(DialogInfo.DESCRIPTION).getText(),
          DayOfWeek.valueOf(userText.get(DialogInfo.DAYOFWEEK).getText().toUpperCase()),
          userText.get(DialogInfo.STARTTIME).getText(),
          Integer.parseInt(userText.get(DialogInfo.DURATION).getText()));

      if (!updatedEvent.getWeekday().equals(event.getWeekday())) {
        this.eventsVboxMap.get(updatedEvent.getWeekday()).getChildren().add(
            this.createEventButtonBox(updatedEvent));
        this.eventsVboxMap.get(event.getWeekday()).getChildren().remove(eventButton);
      } else {
        this.eventsVboxMap.get(event.getWeekday()).getChildren().add(
            this.eventsVboxMap.get(event.getWeekday()).getChildren().indexOf(eventButton),
            this.createEventButtonBox(updatedEvent));
        this.eventsVboxMap.get(event.getWeekday()).getChildren().remove(eventButton);
      }
      this.totalListOfEvents.add(this.totalListOfEvents.indexOf(event), updatedEvent);
      this.totalListOfEvents.remove(event);
      Button updateButton = new Button("Update");
      styleButton(updateButton);
      dialog.setResult(updateButton);
      dialog.close();
      weekdayExceptionPrinted.setText("");
    } catch (IllegalArgumentException e) {
      weekdayExceptionPrinted.setText("Entered invalid input");
      System.err.println("Entered invalid input");
    }
  }

  /**
   * handles displaying task dialog
   */
  private void handleTaskDialog() {
    //new dialog
    Dialog<Node> dialog = new Dialog<>();

    LinkedHashMap<DialogInfo, TextField> userText = new LinkedHashMap<>();

    HBox okOrCancel = new HBox();
    styleHbox(okOrCancel);
    Button okayButton = new Button("Okay");
    styleButton(okayButton);
    okayButton.setOnAction(event -> createTask(userText, dialog));
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);
    cancelButton.setOnAction(event -> {
      Button newCancel = new Button("Cancel");
      styleButton(newCancel);
      dialog.setResult(newCancel);
      dialog.close();
    });
    okOrCancel.getChildren().addAll(cancelButton, okayButton);

    VBox veBox = createAbstractCalendarDialog(userText, "", "", "");
    veBox.getChildren().add(okOrCancel);

    DialogPane dialogPane = new DialogPane();
    dialogPane.setContent(veBox);

    dialog.setDialogPane(dialogPane);
    dialog.showAndWait();
  }

  /**
   * handles displaying event dialog
   */
  private void handleEventDialog() {
    LinkedHashMap<DialogInfo, TextField> userText = new LinkedHashMap<>();

    VBox veBox = createAbstractCalendarDialog(userText, "", "", "");

    this.createMiniViewerFeatures(veBox, "StartTime: ", "", userText, DialogInfo.STARTTIME);

    this.createMiniViewerFeatures(veBox, "Duration: ", "", userText, DialogInfo.DURATION);

    HBox okOrCancel = new HBox();
    styleHbox(okOrCancel);
    Button okayButton = new Button("Okay");
    styleButton(okayButton);
    Dialog<Node> dialog = new Dialog<>();
    okayButton.setOnAction(event -> createEvent(userText, dialog));
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);
    cancelButton.setOnAction(event -> {
      Button newCancel = new Button("Cancel");
      styleButton(newCancel);
      dialog.setResult(newCancel);
      dialog.close();
    });
    okOrCancel.getChildren().addAll(cancelButton, okayButton);

    veBox.getChildren().add(okOrCancel);

    DialogPane dialogPane = new DialogPane();

    dialogPane.setContent(veBox);

    dialog.setDialogPane(dialogPane);
    dialog.showAndWait();
  }

  /**
   * creates a task
   *
   * @param userText map of dialog information and text field
   * @param dialog   task dialog
   */
  private void createTask(Map<DialogInfo, TextField> userText, Dialog<Node> dialog) {
    Button okayButton = new Button("Okay");
    styleButton(okayButton);
    dialog.setResult(okayButton);
    dialog.close();

    String weekdayInput = userText.get(DialogInfo.DAYOFWEEK).getText().toUpperCase().trim();
    try {
      Task addTask = new Task(userText.get(DialogInfo.NAME).getText(),
          userText.get(DialogInfo.DESCRIPTION).getText(),
          DayOfWeek.valueOf(userText.get(DialogInfo.DAYOFWEEK).getText().toUpperCase().trim()));
      if ((this.tasksVboxMap.get(addTask.getWeekday()).getChildren().size() + 1)
          > this.numMaxTasks) {
        taskExceptionPrinted.setText("Uh oh! Your number of tasks exceeds your maximum.");
        weekdayExceptionPrinted.setText("");
      } else {
        taskExceptionPrinted.setText("");
        weekdayExceptionPrinted.setText("");
        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("custom-checkbox");
        scene.getStylesheets().add("customStyles.css");
        Label label = new Label("Name: " + addTask.getName()
            + "\n" + "Description: " + addTask.getDescription() + "\n" + "Is Complete?: "
            + checkBox.isSelected());
        styleLabel(label);
        checkBox.setOnAction(e -> {
          addTask.updateIsComplete(checkBox.isSelected());
          label.setText("Name: " + addTask.getName()
              + "\n" + "Description: " + addTask.getDescription() + "\n" + "Is Complete?: "
              + checkBox.isSelected());
          styleLabel(label);
        });
        this.tasksVboxMap.get(addTask.getWeekday()).getChildren()
            .add(this.createTaskButtonBox(addTask, checkBox));
        addTask.updateIsComplete(checkBox.isSelected());
        this.taskBar.getChildren().addAll(label);
        System.out.println(totalListOfTasks.size());
        this.totalListOfTasks.add(addTask);
        System.out.println(totalListOfTasks.size());
        weekdayExceptionPrinted.setText("");
      }
    } catch (IllegalArgumentException e) {
      weekdayExceptionPrinted.setText("Entered invalid weekday: " + weekdayInput);
      System.err.println("Entered invalid weekday: " + weekdayInput);
    }
  }

  /**
   * creates an event
   *
   * @param userText map of dialog information and text field
   * @param dialog   event dialog
   */
  private void createEvent(Map<DialogInfo, TextField> userText, Dialog<Node> dialog) {
    Button okayButton = new Button("Okay");
    styleButton(okayButton);
    dialog.setResult(okayButton);
    dialog.close();

    try {
      Event addEvent = new Event(userText.get(DialogInfo.NAME).getText(),
          userText.get(DialogInfo.DESCRIPTION).getText(),
          DayOfWeek.valueOf(userText.get(DialogInfo.DAYOFWEEK).getText().toUpperCase().trim()),
          userText.get(DialogInfo.STARTTIME).getText(),
          Integer.parseInt(userText.get(DialogInfo.DURATION).getText()));
      if ((this.eventsVboxMap.get(addEvent.getWeekday()).getChildren().size() + 1)
          > this.numMaxEvents) {
        eventExceptionPrinted.setText("Uh oh! Your number of events exceeds your maximum.");
      } else {
        eventExceptionPrinted.setText("");
        weekdayExceptionPrinted.setText("");
        this.eventsVboxMap.get(addEvent.getWeekday()).getChildren()
            .add(this.createEventButtonBox(addEvent));
      }
      this.totalListOfEvents.add(addEvent);
    } catch (IllegalArgumentException e) {
      eventExceptionPrinted.setText("Entered invalid input");
      System.err.println("Entered invalid input");
    }
  }

  /**
   * creates task box display
   *
   * @param task  current task
   * @param check checkbox
   * @return horizontal box with task
   */
  private HBox createTaskButtonBox(Task task, CheckBox check) {
    Button button = new Button("Name: " + task.getName() + "\n" + "Description: "
        + task.getDescription() + "\n");
    styleButton(button);
    button.setPrefWidth(135);
    button.setAlignment(Pos.BASELINE_LEFT);

    HBox hoBox = new HBox();
    button.setOnAction(e -> createTaskPopup(task, hoBox, check));
    check.setOnAction(event -> {
      task.updateIsComplete(check.isSelected());
      Label label = new Label();
      label.setText("Name: " + task.getName()
          + "\n" + "Description: " + task.getDescription() + "\n" + "Is Complete?: "
          + check.isSelected());
      styleLabel(label);
      this.taskBar.getChildren().add(this.totalListOfTasks.indexOf(task) + 1, label);
      this.taskBar.getChildren().remove(this.totalListOfTasks.indexOf(task) + 2);
    });
    hoBox.getChildren().addAll(check, button);
    return hoBox;
  }

  /**
   * creates event button display
   *
   * @param event current event
   * @return button with event
   */
  private Button createEventButtonBox(Event event) {
    Button addButton = new Button("Name: " + event.getName() + "\n" + "Description: "
        + event.getDescription() + "\n" + "Start Time: " + event.getStartTime()
        + "\n" + "Duration: " + event.getDuration());
    styleButton(addButton);
    addButton.setPrefWidth(135);
    addButton.setAlignment(BASELINE_LEFT);

    addButton.setOnAction(e -> createEventPopup(event, addButton));
    return addButton;
  }

  /**
   * handles adding quotes/notes
   */
  private void handleAddQuoteNote() {
    Dialog<Node> dialog = new Dialog<>();
    TextField userInput = new TextField();
    HBox addOrCancel = new HBox();
    styleHbox(addOrCancel);
    Button addButton = new Button("Add");
    styleButton(addButton);
    addButton.setOnAction(event -> {
      Label label = new Label(userInput.getText());
      styleLabel(label);
      quotesAndNotes.getChildren().add(quotesAndNotes.getChildren().size() - 1, label);
      this.quotesNotes.add(label.getText());
      Button newCancel = new Button("Cancel");
      styleButton(newCancel);
      dialog.setResult(newCancel);
      dialog.close();
    });
    Button cancelButton = new Button("Cancel");
    styleButton(cancelButton);
    cancelButton.setOnAction(event -> {
      Button newCancel = new Button("Cancel");
      styleButton(newCancel);
      dialog.setResult(newCancel);
      dialog.close();
    });
    showDialogPane(userInput, dialog, addOrCancel, addButton, cancelButton);
  }

  /**
   * saves calendar content to a .bujo file
   */
  private void handleSaveBujo() {
    promptFileChooser();
    ObjectMapper objectMapper = new ObjectMapper();
    try (PrintWriter writer = new PrintWriter(this.bujoFilePath.toFile())) {
      String s = objectMapper.writeValueAsString(converter.createCalendar(
          weekName.getText(), numMaxEvents, numMaxTasks, this.totalListOfTasks,
          this.totalListOfEvents, this.quotesNotes));
      writer.println(s);
      System.out.println("JSON data printed to the file successfully.");
    } catch (IOException e) {
      System.out.println("File not found or cannot be created.");
    }
  }

  /**
   * opens a file chooser prompt
   */
  private void promptFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Bujo File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));
    File selectedFile = fileChooser.showOpenDialog(primaryStage);
    if (selectedFile != null) {
      this.bujoFilePath = selectedFile.toPath();
    }
  }

  /**
   * handles opening file
   */
  private void handleOpenClick() {
    promptFileChooser();
    initButtons();
    this.handleNewWeekClick();
    initButtonKeyShortcuts();
    try {
      //this.converter = new ProxyConverter(totalListOfEvents, totalListOfTasks, quotesNotes);
      CalendarJson calendarJson = converter.receiveCalendar(this.bujoFilePath);
      readWeekday(calendarJson.sundayObjects(), DayOfWeek.SUNDAY.getValue());
      readWeekday(calendarJson.mondayObjects(), DayOfWeek.MONDAY.getValue());
      readWeekday(calendarJson.tuesdayObjects(), DayOfWeek.TUESDAY.getValue());
      readWeekday(calendarJson.wednesdayObjects(), DayOfWeek.WEDNESDAY.getValue());
      readWeekday(calendarJson.thursdayObjects(), DayOfWeek.THURSDAY.getValue());
      readWeekday(calendarJson.fridayObjects(), DayOfWeek.FRIDAY.getValue());
      readWeekday(calendarJson.saturdayObjects(), DayOfWeek.SATURDAY.getValue());
      this.weekName.setText(calendarJson.weekName());
      for (String s : calendarJson.quotesAndNotes()) {
        Label label = new Label(s);
        styleLabel(label);
        this.quotesAndNotes.getChildren().add(this.quotesAndNotes.getChildren().size() - 1, label);
        this.quotesNotes.add(s);
      }
      this.numMaxEvents = calendarJson.eventRestriction();
      this.maxEventsLabel.setText(String.valueOf(calendarJson.eventRestriction()));
      this.numMaxTasks = calendarJson.taskRestriction();
      this.maxTasksLabel.setText(String.valueOf(calendarJson.taskRestriction()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * finds tasks and events of a weekday given a list of calendar objects
   *
   * @param objects  the calendar objects in the weekday
   * @param location the value of the weekday that the objects belong to
   */
  private void readWeekday(List<String> objects, int location) {
    ObjectMapper objectMapper = new ObjectMapper();
    for (String s : objects) {
      if (s.contains("isComplete")) {
        try {
          CheckBox check = new CheckBox();
          if (s.contains("true")) {
            check.setSelected(true);
          }
          check.getStyleClass().add("custom-checkbox");
          scene.getStylesheets().add("customStyles.css");
          Task task = objectMapper.readValue(s, Task.class);
          this.tasksVboxMap.get(DayOfWeek.convertValue(location)).getChildren()
              .add(this.createTaskButtonBox(task, check));
          this.totalListOfTasks.add(task);
          Label label = new Label("Name: " + task.getName()
              + "\n" + "Description: " + task.getDescription() + "\n" + "Is Complete?: "
              + check.isSelected());
          styleLabel(label);
          this.taskBar.getChildren().add(label);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      } else {
        try {
          Event event = objectMapper.readValue(s, Event.class);
          this.eventsVboxMap.get(DayOfWeek.convertValue(location)).getChildren()
              .add(this.createEventButtonBox(event));
          this.totalListOfEvents.add(event);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /**
   * handles creating new week
   */
  private void handleNewWeekClick() {
    this.taskBar.getChildren().clear();
    this.taskBar.getChildren().add(taskBarLabel);
    this.numMaxTasks = 0;
    this.numMaxEvents = 0;
    totalListOfTasks = new ArrayList<>();
    totalListOfEvents = new ArrayList<>();
    quotesNotes = new ArrayList<>();
    this.maxEventsLabel.setText(String.valueOf(numMaxEvents));
    this.maxTasksLabel.setText(String.valueOf(numMaxTasks));

    for (int i = 10; i < this.calendar.getChildren().size(); i++) {
      this.calendar.getChildren().remove(this.calendar.getChildren().get(i));
      i--;
    }

    this.weekName.clear();

    for (int i = 1; i < this.quotesAndNotes.getChildren().size() - 1; i++) {
      this.quotesAndNotes.getChildren().remove(this.quotesAndNotes.getChildren().get(i));
      i--;
    }

    this.tasksVboxMap = new LinkedHashMap<>();
    this.eventsVboxMap = new LinkedHashMap<>();

    this.taskExceptionPrinted.setText("");
    this.eventExceptionPrinted.setText("");
    this.weekdayExceptionPrinted.setText("");
    initCalendar();
  }

  /**
   * handles opening url from description
   *
   * @param description calendar object description
   * @return node with link or text with message
   */
  private Node handleOpenUrl(String description) {
    Pattern pattern = Pattern.compile("\\bhttps?://\\S+\\b");
    Matcher matcher = pattern.matcher(description);
    if (matcher.find()) {
      String link = matcher.group();
      Hyperlink hyperlink = new Hyperlink();
      hyperlink.setText("URL: " + link);
      hyperlink.setOnAction(event -> {
        HostServices hostServices = Driver.getHostServicesInstance();
        hostServices.showDocument(link);
      });
      return hyperlink;
    } else {
      Text text = new Text("No URL Found");
      text.setStyle("-fx-font-family: \"Mali\";");
      return text;
    }
  }
}
