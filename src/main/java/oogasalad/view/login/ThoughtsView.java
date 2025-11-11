package oogasalad.view.login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.database.InfoService;
import oogasalad.database.Thought;
import oogasalad.view.branch.BranchBase;

/**
 * This class is responsible for displaying the thoughts view for the game. Do not have to refactor
 * this class.
 */

public class ThoughtsView extends BranchBase {


  public ThoughtsView(Stage stage, Scene previousScene) {
    super(stage, previousScene);
  }

  public Scene createScene() {
    VBox thoughtBox = new VBox();
    thoughtBox.setSpacing(10);
    thoughtBox.setPadding(new Insets(10));

    updateThoughtsView(thoughtBox);

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(thoughtBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    Button backButton = new Button("Back");
    backButton.setId("backButton");
    backButton.setOnAction(e -> getStage().setScene(getPreviousScene()));

    Button sendThoughtButton = new Button("Send Thought");
    sendThoughtButton.setId("sendThoughtButton");
    sendThoughtButton.setOnAction(e -> openSendThoughtPopup());

    HBox buttonBox = new HBox(backButton, sendThoughtButton);

    VBox root = new VBox(scrollPane, buttonBox);
    Scene scene = new Scene(root, 400, 350);
    return scene;
  }

  private void updateThoughtsView(VBox thoughtBox) {
    thoughtBox.getChildren().clear();
    List<Thought> thoughts = InfoService.getAllThoughts();

    for (Thought thought : thoughts) {
      Label usernameLabel = new Label("Username: " + thought.getUsername());
      Label messageLabel = new Label("Message: " + thought.getText());
      Label timeLabel = new Label("Time: " + thought.getTime());

      VBox thoughtDetails = new VBox(usernameLabel, messageLabel, timeLabel);
      thoughtDetails.setStyle(
          "-fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 5px;");
      thoughtBox.getChildren().add(thoughtDetails);
    }
  }

  private void openSendThoughtPopup() {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle("Send Thought");

    BorderPane borderPane = new BorderPane();
    TextArea thoughtTextArea = new TextArea();
    thoughtTextArea.setId("thoughtTextArea");
    Button submitButton = new Button("Submit");
    submitButton.setId("submitButton");
    submitButton.setOnAction(e -> {
      String thoughtText = thoughtTextArea.getText();
      int userId = 1;
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedTime = currentTime.format(formatter);
      InfoService.addThought(userId, thoughtText, formattedTime);
      updateThoughtsView(
          (VBox) ((ScrollPane) getStage().getScene().getRoot().getChildrenUnmodifiable()
              .get(0)).getContent());
      popupStage.close();
    });

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> popupStage.close());

    HBox buttonBox = new HBox(backButton, submitButton);
    buttonBox.setSpacing(10);

    borderPane.setCenter(thoughtTextArea);
    borderPane.setBottom(buttonBox);

    Scene popupScene = new Scene(borderPane, 300, 200);
    popupStage.setScene(popupScene);
    popupStage.showAndWait();
  }

}