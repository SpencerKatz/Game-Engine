package oogasalad.view.playing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import oogasalad.Main;
import oogasalad.model.api.GameFactory;
import oogasalad.view.Tool;
import oogasalad.view.playing.component.BagItem;
import oogasalad.view.playing.component.BagView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class BagItemTest extends ApplicationTest {

  private BagItem bagItem;
  private BagView bagView;

  @BeforeAll
  public static void setUpClass() throws Exception {
    ApplicationTest.launch(Main.class);
  }

  @BeforeEach
  public void setUp() throws MalformedURLException {
    bagView = new BagView(new GameFactory().createGame(), 5, 50, 50,
        50, 50);
    bagItem = new BagItem("test.png", "TestItem", 50, 50, bagView, 5);
  }

  @Test
  public void bagItemIsCreatedWithCorrectName() {
    assertEquals("TestItem", bagItem.getName());
  }

  @Test
  public void bagItemIsCreatedWithCorrectImage() {
    Image expectedImage = new Image(new File("data/images/test.png").toURI().toString(), 50, 50,
        false, true);
    assertEquals(expectedImage.getUrl(), bagItem.getImageView().getImage().getUrl());
  }

  @Test
  public void bagItemIsCreatedWithCorrectView() {
    StackPane expectedView = bagItem;
    assertNotNull(expectedView);
  }

  @Test
  public void bagSelectCorrectly() {
    bagItem.select();
    assertEquals(1, bagItem.getOpacity());
  }

  @Test
  public void bagItemUpdatesImageCorrectly() throws MalformedURLException {
    bagItem.setImage(Tool.getImagePath("newTest.png"));
    Image expectedImage = new Image(new File("data/images/newTest.png").toURI().toString(), 50, 50,
        false, true);
    assertEquals(expectedImage.getUrl(), bagItem.getImageView().getImage().getUrl());
  }

}