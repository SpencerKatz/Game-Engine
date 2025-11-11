package oogasalad.view.start;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;
import oogasalad.view.start.ChangePageButton;
import oogasalad.view.start.StartScreen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class StartScreenTest extends DukeApplicationTest {

  private Stage stage;
  private StartScreen ss;
  private ChangePageButton create;
  private ChangePageButton play;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    ss = new StartScreen(stage, "English", null);
    ss.open();
    this.create = (ChangePageButton) lookup("#Create").queryButton();
    this.play = (ChangePageButton) lookup("#Play").queryButton();
  }

  @Test
  @DisplayName("Test Create Button")
  public void openEditor() {
    sleep(500);
    clickOn(create);
    sleep(500);
    assertNotEquals(stage.getScene(), ss.getStartScreen());
  }

  @Test
  @DisplayName("Test Play Button")
  public void openPlayMode() {
    sleep(500);
    clickOn(play);
    sleep(500);
    assertTrue(stage.getTitle().equals("Play Mode"));
  }
}
