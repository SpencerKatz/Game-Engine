package oogasalad.view.end;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * The EndView class is a view that displays the end screen of the game.
 */

public class EndView extends StackPane {

  private final Map<String, String> buttonDetails;

  /**
   * Constructor for the EndView class.
   *
   * @param width   the width of the end screen
   * @param height  the height of the end screen
   * @param score   the score of the player
   * @param starNum the number of stars the player has
   */
  public EndView(double width, double height, int score, int starNum) {
    super();
    Image backgroundImage = new Image("img/end/end" + starNum + ".png");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(width);
    backgroundImageView.setFitHeight(height);

    Label scoreLabel = new Label("" + score);
    scoreLabel.setId("end-scoreLabel");

    HBox buttonBox = new HBox();
    buttonBox.setSpacing(10);
    buttonDetails = readButtonDetails();
    for (String key : buttonDetails.keySet()) {
      String[] details = buttonDetails.get(key).split(",");
      String id = details[0];
      String className = details[1];
      String methodName = details[2];
      Button button = createButton(id, className, methodName);
      buttonBox.getChildren().add(button);
    }

    buttonBox.setAlignment(Pos.BOTTOM_CENTER);
    setMargin(scoreLabel, new Insets(100, 0, 0, 0));
    setMargin(buttonBox, new Insets(0, 0, 50, 10));

    getChildren().addAll(backgroundImageView, scoreLabel, buttonBox);

    setPrefSize(width, height);
  }

  /**
   * Reads the button details from a csv file.
   *
   * @return the button details
   */
  private Map<String, String> readButtonDetails() {
    Map<String, String> buttonDetails = new HashMap<>();
    try {
      BufferedReader csvReader =
          new BufferedReader(new FileReader("src/main/resources/view/end/EndViewButtonInfo.csv"));
      String row;
      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(",");
        buttonDetails.put(data[0], data[0] + "," + data[1] + "," + data[2]);
      }
      csvReader.close();

      Properties prop = new Properties();
      prop.load(new FileReader("src/main/resources/view/end/button-layout.properties"));
      String[] buttonNames = prop.getProperty("ButtonPanel").split(",");
      for (String buttonName : buttonNames) {
        buttonDetails.put(buttonName, buttonDetails.get(buttonName));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buttonDetails;
  }

  /**
   * Creates a button.
   *
   * @param id         the id of the button
   * @param className  the class name of the button
   * @param methodName the method name of the button
   * @return the button
   */
  private Button createButton(String id, String className, String methodName) {
    try {
      Class<?> clazz = Class.forName(className);
      Object instance = clazz.getDeclaredConstructor().newInstance();
      Method method = clazz.getMethod(methodName, MouseEvent.class);
      Button button = new Button();
      button.setId(id);
      button.setOnMouseClicked(e -> {
        try {
          method.invoke(instance, e);
        } catch (IllegalAccessException | InvocationTargetException ex) {
          ex.printStackTrace();
        }
      });
      return button;
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
             IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Map<String, String> getButtonDetails() {
    return buttonDetails;
  }
}
