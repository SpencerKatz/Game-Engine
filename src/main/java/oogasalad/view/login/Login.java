package oogasalad.view.login;

import static oogasalad.view.login.Utils.showAlert;

import java.util.function.BiConsumer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oogasalad.database.InfoService;
import oogasalad.model.api.GameInterface;
import oogasalad.view.branch.BranchBase;

/**
 * This class is responsible for displaying the login page for the game.
 */

public class Login extends BranchBase {

  private String validUsername;
  private int id;
  private BiConsumer<String, Integer> onLoginSuccessCallback;
  private final GameInterface game;

  /**
   * This is the constructor for the Login class.
   *
   * @param stage
   * @param previousScene
   */
  public Login(Stage stage, Scene previousScene, GameInterface game) {
    super(stage, previousScene);
    this.game = game;
  }

  public void setOnLoginSuccess(BiConsumer<String, Integer> callback) {
    this.onLoginSuccessCallback = callback;
  }

  private void invokeOnLoginSuccessCallback() {
    if (onLoginSuccessCallback != null) {
      onLoginSuccessCallback.accept(validUsername, id);
    }
  }

  public Scene createSceneFromConfig() throws Exception {
    return FileUtility.createScene("src/main/resources/view/login/login_layout.json",
        this::setupEventHandlers);
  }


  private void setupEventHandlers(Object instance, String eventName) {
    if (!(instance instanceof Button button)) {
      return;
    }
    button.setOnAction(e -> handleButtonAction(eventName));
  }

  private void handleButtonAction(String action) {
    try {
      switch (action) {
        case "submit":
          handleLogin();
          break;
        case "register":
          handleRegister();
          break;
        case "back":
          handleBack();
          break;
        default:
          System.out.println("Action not recognized");
      }
    } catch (Exception ex) {
      showAlert("Error", "An error occurred: " + ex.getMessage());
    }
  }


  private void handleLogin() throws Exception {
    TextField usernameField = (TextField) getStage().getScene().lookup("#usernameField");
    PasswordField passwordField = (PasswordField) getStage().getScene().lookup("#passwordField");

    String username = usernameField.getText();
    String password = passwordField.getText();

    if (InfoService.isValidUser(username, password)) {
      validUsername = username;
      id = InfoService.getUserId(username);
      invokeOnLoginSuccessCallback();
      GameFileOperations gameOps =
          new GameFileOperations(getStage(), getStage().getScene(), id, game);
      getStage().setScene(gameOps.createScene());
    } else {
      showAlert("Login Failed", "Invalid username or password");
    }
  }


  private void handleRegister() throws Exception {
    Register registerView = new Register(getStage(), getStage().getScene());
    getStage().setScene(registerView.getScene());
    getStage().show();
  }


  private void handleBack() {
    getStage().setScene(getPreviousScene());
  }

}

