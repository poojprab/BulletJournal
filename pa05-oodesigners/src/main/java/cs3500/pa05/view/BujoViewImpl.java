package cs3500.pa05.view;

import cs3500.pa05.controller.BujoController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a simple Java Bujo GUI view.
 */
public class BujoViewImpl implements BujoView {
  private final FXMLLoader loader;

  /**
   * constructor for the BujoViewImpl class
   *
   * @param controller the controller used for this Java Bujo
   */
  public BujoViewImpl(BujoController controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("bujo.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Loads a scene from a Java Bujo GUI layout.
   *
   * @return the layout
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
