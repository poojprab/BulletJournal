package cs3500.pa05.view;

import cs3500.pa05.Driver;
import cs3500.pa05.controller.BujoController;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * represents splash screen view
 */
public class SplashViewImpl implements BujoView {
  private final FXMLLoader loader;

  /**
   * constructor for the SplashViewImpl class
   *
   * @param controller the controller used for this Java Bujo
   */
  public SplashViewImpl(BujoController controller) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("splashScreen.fxml"));
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
      AnchorPane root = new AnchorPane();
      StackPane pane = loader.load();
      root.getChildren().setAll(pane);

      FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
      fadeIn.setFromValue(0);
      fadeIn.setToValue(1);
      fadeIn.setCycleCount(1);

      FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
      fadeOut.setToValue(1);
      fadeOut.setToValue(0);
      fadeOut.setCycleCount(1);

      fadeIn.play();
      fadeIn.setOnFinished((e) -> fadeOut.play());

      fadeOut.setOnFinished((e) -> {
        Driver.loadMain(new Stage());
      });

      return new Scene(root);
    } catch (IOException e) {
      throw new IllegalStateException("Encountered unexpected exception");
    }
  }
}
