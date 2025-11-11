package oogasalad.view.popup;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class PopUpStackPane extends StackPane {

  private final static double centerBackgroundWidth = 300;
  private final static double centerBackgroundHeight = 200;
  private final static String centerBackgroundImage = "img/pop-up/pop-up-background.png";
  private final static String backgroundImagePath = "img/pop-up/bg-shadow.png";
  private final String csvFilePath;
  private final VBox vBox = new VBox(10);
  private final HBox buttonBox = new HBox(10);
  private final PopUpButtonActionHandler actionHandler;
  private final ResourceBundle resourceBundle;
  private Label messageLabel;
  private ImageView centerBackgroundImageView;

  public PopUpStackPane(ResourceBundle resourceBundle, StackPane parentStackPane,
      Consumer<Boolean> choiceCallback, String csvFilePath) {
    super();
    this.resourceBundle = resourceBundle;
    this.actionHandler = new PopUpButtonActionHandler(choiceCallback, parentStackPane, this);
    this.csvFilePath = csvFilePath;
    initialize();
  }

  private void initialize() {
    readAndCreateButtonsFromFile(csvFilePath);
    createMessageLabel();
    createVBox();
    createCenterBackgroundImageView();
    setBackground(createBackground());
    setAlignment(Pos.CENTER);
    getChildren().addAll(centerBackgroundImageView, vBox);
  }

  private Background createBackground() {
    Image backgroundImage = new Image(backgroundImagePath);
    BackgroundImage background =
        new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    return new Background(background);
  }


  private void createCenterBackgroundImageView() {
    centerBackgroundImageView = new ImageView(new Image(centerBackgroundImage));
    centerBackgroundImageView.setFitWidth(centerBackgroundWidth);
    centerBackgroundImageView.setFitHeight(centerBackgroundHeight);
  }

  private void createVBox() {
    vBox.getChildren().addAll(messageLabel, buttonBox);
    buttonBox.setAlignment(Pos.CENTER);
    messageLabel.setAlignment(Pos.CENTER);
    vBox.setAlignment(Pos.CENTER);
  }

  private void createMessageLabel() {
    messageLabel = new Label(resourceBundle.getString("are_you_sure"));
    messageLabel.setId("pop-up-label");
  }

  private void readAndCreateButtonsFromFile(String filePath) {
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        Button button = createButtonFromCSV(line);
        addToButtonBox(button);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private Button createButtonFromCSV(String csvLine) {
    String[] parts = csvLine.split(",");
    String buttonId = parts[0];
    String buttonText = parts[1];
    String methodName = parts[2];

    try {
      Method method = PopUpButtonActionHandler.class.getMethod(methodName);
      Button button = new Button(resourceBundle.getString(buttonText));
      button.setId(buttonId);

      button.setOnAction(event -> {
        try {
          method.invoke(actionHandler);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      return button;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  private void addToButtonBox(Button button) {
    buttonBox.getChildren().add(button);
  }

}
