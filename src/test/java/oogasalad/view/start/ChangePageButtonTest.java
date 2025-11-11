package oogasalad.view.start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePageButtonTest extends ApplicationTest {
  private Stage mainStage;
  private Scene mainScene;
  ChangePageButton button;
  ChangePageButton controlCase;

  @Override
  public void start(Stage stage) {
    mainStage = stage;

    button = new ChangePageButton("Test Button", "blue");
    controlCase = new ChangePageButton("Control Case", "red");

    mainScene = new Scene(new HBox(button, controlCase));
    mainStage.setScene(mainScene);
    mainStage.show();
  }

  @Test
  public void testId() {
    assertTrue("Test Button".equals(button.getId()));
  }

  @Test
  public void testButtonHover() {

    // Move mouse over button
    moveTo(button);
    assertEquals("-fx-background-color: gray;", button.getStyle());

    // Move mouse out of button
    moveTo(controlCase);
    assertEquals("-fx-background-color: blue;", button.getStyle());
  }
}