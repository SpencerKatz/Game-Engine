package oogasalad.view.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import oogasalad.view.end.EndView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class EndTest extends DukeApplicationTest {

  private Stage stage;
  private EndView endView;


  @Start
  public void start(Stage stage) {
    this.stage = stage;
    endView = new EndView(300, 400, 100, 3);
    stage.setScene(new Scene(endView));
    stage.show();
  }

  @Test
  public void testEndViewScoreLabel() {
    Label scoreLabel = lookup("#end-scoreLabel").query();
    assertEquals("100", scoreLabel.getText());
  }

  @Test
  public void testEndViewButtonNum() {
    assertEquals(3, endView.getButtonDetails().size());
  }


  @Test
  public void testButton() {
    Button button1 = lookup("#end-back-to-menu-button").query();
    assertNotNull(button1);
    Button button2 = lookup("#end-restart-button").query();
    assertNotNull(button2);
    Button button3 = lookup("#end-next-button").query();
    assertNotNull(button3);
  }

}
