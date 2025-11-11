package oogasalad.view.login;

import static oogasalad.view.login.Utils.showAlert;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oogasalad.database.InfoService;
import oogasalad.view.branch.BranchBase;

/**
 * This class is responsible for displaying the registration page for the game.
 */
public class Register extends BranchBase {

  public Register(Stage stage, Scene previousScene) {
    super(stage, previousScene);
  }

  public Scene getScene() throws Exception {
    return FileUtility.createScene("src/main/resources/view/login/register_layout.json",
        this::setupEventHandlers);
  }

  private void setupEventHandlers(Object instance, String eventName) {
    if (instance instanceof Button button) {
      switch (eventName) {
        case "register" -> button.setOnAction(e -> handleRegister());
        case "back" -> button.setOnAction(e -> getStage().setScene(getPreviousScene()));
      }
    }
  }

  private void handleRegister() {
    TextField usernameField = (TextField) getStage().getScene().lookup("#usernameField");
    TextField emailField = (TextField) getStage().getScene().lookup("#emailField");
    PasswordField passwordField = (PasswordField) getStage().getScene().lookup("#passwordField");

    String username = usernameField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();

    if (InfoService.addUser(username, email, password)) {
      showAlert("Registration Successful", "User registered successfully");
    } else {
      showAlert("Registration Failed", "Username already exists");
    }
  }
}
