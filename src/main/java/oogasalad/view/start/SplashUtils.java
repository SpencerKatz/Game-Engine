package oogasalad.view.start;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Utility class for the start screen and splash screen.
 */
public class SplashUtils {

  /**
   * Reads comma-separated lines from a CSV file.
   *
   * @param filename the name of the CSV file
   * @return a list of comma-separated values
   */
  public static List<String[]> readCommaSeparatedCSVLines(String filename) {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(", ");
        data.add(values);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  /**
   * Reads comma-separated values from a CSV file.
   *
   * @param filename the name of the CSV file
   * @return an array of comma-separated values
   */
  public static String[] readCommaSeparatedCSV(String filename) {
    String[] values;
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      values = br.readLine().split(", ");

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return values;
  }

  /**
   * Creates buttons from a CSV file.
   *
   * @param filename     the name of the CSV file
   * @param primaryStage the primary stage
   * @param root         the root layout
   * @param language     the language
   * @param myScene      the scene
   */
  public static void createButtonsFromFile(String filename, Stage primaryStage, VBox root,
      String language, Scene myScene) {
    List<String[]> buttonData = SplashUtils.readCommaSeparatedCSVLines(filename);
    makeButton(buttonData, primaryStage, root, language, myScene);
  }

  /**
   * Makes buttons from the button data.
   *
   * @param buttonData   the button data
   * @param primaryStage the primary stage
   * @param root         the root layout
   * @param language     the language
   * @param myScene      the scene
   */
  public static void makeButton(List<String[]> buttonData, Stage primaryStage, VBox root,
      String language, Scene myScene) {

    for (String[] data : buttonData) {
      ChangePageButton button = new ChangePageButton(data[0], data[1]);
      String className = data[2];
      String methodName = data[3];
      String[] parameters = new String[data.length - 4];
      System.arraycopy(data, 4, parameters, 0, parameters.length);
      button.setOnAction(
          new ButtonActionHandler(className, methodName, primaryStage, language, myScene,
              parameters));
      root.getChildren().add(button);
    }
  }

  /**
   * Animates a label's position.
   *
   * @param l      the label to animate
   * @param newVal the new value
   */
  public static void titleBob(Label l, Number newVal) {
    Animation animation = createAnimation(l, newVal);
    animation.play();
    animation.setOnFinished(event -> {
      animation.setRate(-animation.getRate());
      animation.play();
    });
  }

  /**
   * Creates an animation for a label.
   *
   * @param l      the label
   * @param newVal the new value
   * @return the animation
   */
  public static Animation createAnimation(Label l, Number newVal) {
    Path path = new Path();
    path.getElements().addAll(new MoveTo(l.getLayoutX() + newVal.doubleValue() / 2, l.getLayoutY()),
        new LineTo(l.getLayoutX() + newVal.doubleValue() / 2, l.getLayoutY() + 10));

    PathTransition pt = new PathTransition(Duration.seconds(1), path, l);
    return new SequentialTransition(l, pt);
  }
}
