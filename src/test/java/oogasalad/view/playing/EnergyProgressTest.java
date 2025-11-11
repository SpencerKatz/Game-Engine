package oogasalad.view.playing;

import static org.junit.Assert.assertEquals;

import javafx.application.Platform;
import oogasalad.Main;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.view.playing.component.EnergyProgress;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;

public class EnergyProgressTest extends ApplicationTest {

  @BeforeClass
  public static void setUpClass() throws Exception {
    Platform.startup(() -> {
    });
  }

  @BeforeAll
  public static void setUp() throws Exception {
    ApplicationTest.launch(Main.class);
  }

  @Test
  public void testInitialization() {
    GameInterface game = new GameFactory().createGame();
    EnergyProgress energyProgress = new EnergyProgress(game);
    assertEquals(1, energyProgress.getProgress(), 0.01);
  }

}
