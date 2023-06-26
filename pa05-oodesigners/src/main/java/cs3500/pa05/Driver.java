package cs3500.pa05;

import cs3500.pa05.controller.BujoControllerImpl;
import cs3500.pa05.view.BujoViewImpl;
import cs3500.pa05.view.SplashViewImpl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 * The main Driver of a Java Bujo application
 */
public class Driver extends Application {

  private static HostServices hostServices;

  /**starts the GUI for the Java Bujo
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  @Override
  public void start(Stage stage) {
    BujoControllerImpl bci = new BujoControllerImpl(stage);
    SplashViewImpl splash = new SplashViewImpl(bci);
    stage.setScene(splash.load());
    stage.show();
  }

  /**
   * loads main stage
   *
   * @param stage main stage
   */
  public static void loadMain(Stage stage) {
    BujoControllerImpl bci = new BujoControllerImpl(stage);
    BujoViewImpl bvi = new BujoViewImpl(bci);
    try {
      stage.setTitle("BuJo");
      stage.setScene(bvi.load());
      bci.run();
      stage.show();
    } catch (IllegalStateException ise) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * Entry point for a Java Bujo
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * allows controller to get access to host services
   */
  @Override
  public void init()  {
    hostServices = getHostServices();
  }

  /**
   * used to help in opening URL
   *
   * @return host services
   */
  public static HostServices getHostServicesInstance() {
    return hostServices;
  }
}
