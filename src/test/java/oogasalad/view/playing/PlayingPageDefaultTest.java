package oogasalad.view.playing;

import static org.junit.jupiter.api.Assertions.assertFalse;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import util.DukeApplicationTest;

public class PlayingPageDefaultTest extends DukeApplicationTest {

  private Stage stage;
  private PlayingPageView playingPageView;

  @Start
  public void start(Stage stage) {
    this.stage = stage;
    playingPageView = new PlayingPageView(stage, "English", null);
    playingPageView.start();
  }

  @Test
  public void testShoppingButton() {
    String scene = stage.getScene().toString();
    Button shoppingButton = (javafx.scene.control.Button) lookup("#shopButton").queryButton();
    clickOn(shoppingButton);
  }

  @Test
  public void testSleepButton() {
    Label label = lookup("#time-label").query();
    String prev = label.getText();
    Button sleepButton = lookup("#sleep-button").queryButton();
    clickOn(sleepButton);
    WaitForAsyncUtils.waitForFxEvents();
    String newLabel = label.getText();
    assertFalse(prev.equals(newLabel)); //???
  }

  @Test
  public void testGameKeyInput() {
    press(KeyCode.M);
  }

  @Test
  public void testButtonMenu() {
    Button menuButton = lookup("#menu_button").queryButton();
    clickOn(menuButton);
    clickOn(menuButton);
  }

  @Test
  public void testSleep() {
    Button sleepButton = lookup("#sleep-button").queryButton();
    clickOn(sleepButton);
  }

  @Test
  public void testHelp() {
    Button helpButton = lookup("#help-button").queryButton();
    clickOn(helpButton);
  }

  @Test
  public void testLogin() {
    Button loginButton = lookup("#login-button").queryButton();
    clickOn(loginButton);
  }

  @Test
  public void testSave() {
    Button saveButton = lookup("#save-button").queryButton();
    clickOn(saveButton);
    press(KeyCode.ESCAPE);
    clickOn(saveButton);
    press(KeyCode.ENTER);
    press(KeyCode.Y);
  }

  @Test
  public void testClickItem() {
    StackPane query = lookup("#bagView").query();
    BorderPane node = (BorderPane) query.getChildren().get(1);
    GridPane center = (GridPane) node.getCenter();
    Node node1 = center.getChildren().get(0);
    clickOn(node1, 0, 0);
  }

  @Test
  public void testSwitchPage() {
    StackPane query = lookup("#bagView").query();
    BorderPane node = (BorderPane) query.getChildren().get(1);
    clickOn(node.getLeft(), 0, 0);
    clickOn(node.getRight(), 0, 0);
  }

  @Test
  public void testHarvest() {
    StackPane query = lookup("#bagView").query();
    BorderPane node = (BorderPane) query.getChildren().get(1);
    GridPane center = (GridPane) node.getCenter();
    Node node1 = center.getChildren().get(0);
    clickOn(node1, 0, 0);
    GridPane landView = lookup("#landView").query();
    Node node2 = landView.getChildren().get(0);
    clickOn(node2, 0, 0);
    clickOn(node2, 0, 0);
    sleep(100);
  }

  @Test
  public void testItemGoDown() {
    StackPane query = lookup("#bagView").query();
    BorderPane node = (BorderPane) query.getChildren().get(1);
    GridPane center = (GridPane) node.getCenter();
    // the hoe
    Node node1 = center.getChildren().get(2);
    clickOn(node1, 0, 0);
    GridPane landView = lookup("#landView").query();
    clickOn(landView.getChildren().get(2), 0, 0);
    clickOn(landView.getChildren().get(6), 0, 0);
    clickOn(landView.getChildren().get(14), 0, 0);
    clickOn(landView.getChildren().get(15), 0, 0);
    clickOn(landView.getChildren().get(18), 0, 0);
    //plant
    Node node2 = center.getChildren().get(1);
    clickOn(node2, 0, 0);
    clickOn(landView.getChildren().get(2), 0, 0);
    clickOn(landView.getChildren().get(6), 0, 0);
    clickOn(landView.getChildren().get(14), 0, 0);
    clickOn(landView.getChildren().get(15), 0, 0);
    clickOn(landView.getChildren().get(18), 0, 0);
    sleep(100);
  }

}
