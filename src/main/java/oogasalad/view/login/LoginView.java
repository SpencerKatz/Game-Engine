package oogasalad.view.login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.api.GameInterface;

/**
 * This class is responsible for displaying the new load screen for the game. This screen will allow
 * the user to either login, start a new game, load a game, or go back to the previous screen.
 */

public class LoginView extends Application {

  private final GameInterface game;
  private Label helloLabel;
  private Scene loginScene;
  private VBox vbox;

  public LoginView(GameInterface game) {
    super();
    this.game = game;
  }


  @Override
  public void start(Stage primaryStage) {
    helloLabel = new Label("Welcome Guest!");
    int userId = UserSession.getUserId();
    setupUI(primaryStage);
    if (userId != -1) {
      String username = UserSession.getUsername();
      helloLabel.setText("Welcome " + username + "!");
      Button goBackButton = new Button("Go!");
      goBackButton.setOnAction(event -> {
        GameFileOperations gameFileOperations =
            new GameFileOperations(primaryStage, loginScene, userId, game);
        try {
          primaryStage.setScene(gameFileOperations.createScene());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        primaryStage.show();
      });
      vbox.getChildren().add(goBackButton);
    }
    primaryStage.setScene(loginScene);
    primaryStage.show();
  }

  private void setupUI(Stage primaryStage) {
    Button loginButton = new Button("Login");
    loginButton.setId("loginButton");
    loginButton.setOnAction(event -> {
      Login login = new Login(primaryStage, primaryStage.getScene(), game);
      try {
        primaryStage.setScene(login.createSceneFromConfig());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      primaryStage.show();
      login.setOnLoginSuccess((username, id) -> {
        helloLabel.setText("Welcome " + username + "!");
        UserSession.saveUserLogin(id, username);
      });
    });

    vbox = new VBox(10, loginButton);
    vbox.setAlignment(Pos.CENTER);
    BorderPane root = new BorderPane(vbox, helloLabel, null, null, null);
    helloLabel.setAlignment(Pos.TOP_RIGHT);
    root.setTop(helloLabel);
    loginScene = new Scene(root, 300, 300);
  }
}
