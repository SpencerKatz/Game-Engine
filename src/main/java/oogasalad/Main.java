package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.view.start.StartScreen;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }

  @Override
  public void start(Stage primaryStage) {
    new StartScreen(primaryStage, "English", null).open();
  }
}