package oogasalad.view.playing;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class PlayingPageTest extends DukeApplicationTest {

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
    Button shoppingButton = (Button) lookup("#shopButton").queryButton();
    clickOn(shoppingButton);
    sleep(1000);
    assertNotEquals(stage.getScene().toString(), scene);
  }


}
