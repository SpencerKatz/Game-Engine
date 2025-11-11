package oogasalad.view.shopping;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.view.playing.PlayingPageView;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

public class OverAllShopTest extends DukeApplicationTest {

  private Stage stage;
  private PlayingPageView playingPageView;
  private GameInterface game;

  @Start
  public void start(Stage stage) {
    this.stage = stage;
    game = new GameFactory().createGame();
    playingPageView = new PlayingPageView(stage, "English", null);
    playingPageView.start();
    playingPageView.start();
    Button shoppingButton = (Button) lookup("#shopButton").queryButton();
    clickOn(shoppingButton);
  }

  @Test
  public void testClickSell() {
    Map<ReadOnlyItem, Integer> bagItem = game.getGameState().getBag().getItems();
    for (ReadOnlyItem item : bagItem.keySet()) {
      Button sellButton = (Button) lookup("#sell-button-" + item.getName()).queryButton();
      assertNotNull(sellButton);
      clickOn(sellButton);
      break;//???
    }
  }

  @Test
  public void testClickBuy() {
    Map<ReadOnlyItem, Double> shopItem = game.getGameState().getShop().getItems();
    for (ReadOnlyItem item : shopItem.keySet()) {
      Button sellButton = (Button) lookup("#buy-button-" + item.getName()).queryButton();
      assertNotNull(sellButton);
      clickOn(sellButton);
      break;
    }
  }

  @Test
  public void testClickBack() {
    Button backButton = (Button) lookup("#shopBackButton").queryButton();
    assertNotNull(backButton);
    clickOn(backButton);
  }

  @Test
  public void testSellYes() {
    String before = lookup("#shopMoneyLabel").queryLabeled().getText();
    Map<ReadOnlyItem, Integer> bagItem = game.getGameState().getBag().getItems();
    for (ReadOnlyItem item : bagItem.keySet()) {
      Button sellButton = (Button) lookup("#sell-button-" + item.getName()).queryButton();
      assertNotNull(sellButton);
      clickOn(sellButton);
      Button yesButton = (Button) lookup("#yes-button").queryButton();
      assertNotNull(yesButton);
      clickOn(yesButton);
      String after = lookup("#shopMoneyLabel").queryLabeled().getText();
      assertNotEquals(before, after);
      break;
    }
  }

  @Test
  public void testSellNo() {
    String before = lookup("#shopMoneyLabel").queryLabeled().getText();
    Map<ReadOnlyItem, Integer> bagItem = game.getGameState().getBag().getItems();
    for (ReadOnlyItem item : bagItem.keySet()) {
      Button sellButton = (Button) lookup("#sell-button-" + item.getName()).queryButton();
      assertNotNull(sellButton);
      clickOn(sellButton);
      Button yesButton = (Button) lookup("#no-button").queryButton();
      assertNotNull(yesButton);
      clickOn(yesButton);
      String after = lookup("#shopMoneyLabel").queryLabeled().getText();
      assertEquals(before, after);
      break;
    }
  }

  @Test
  public void testPageChangeButton() {
    Button sellLeftButton = (Button) lookup("#left-page-change-button-sell").queryButton();
    Button sellRightButton = (Button) lookup("#right-page-change-button-sell").queryButton();
    Button buyLeftButton = (Button) lookup("#left-page-change-button-buy").queryButton();
    Button buyRightButton = (Button) lookup("#right-page-change-button-buy").queryButton();
    assertTrue(sellLeftButton.isDisable());
    assertTrue(buyLeftButton.isDisable());
    int sellSize = game.getGameState().getBag().getItems().size();
    int buySize = game.getGameState().getShop().getItems().size();
    if (sellSize > 5) {
      clickOn(sellRightButton);
      assertTrue(!sellLeftButton.isDisable());
    } else {
      assertTrue(sellRightButton.isDisable());
    }
    if (buySize > 5) {
      clickOn(buyRightButton);
      assertTrue(!buyLeftButton.isDisable());
    } else {
      assertTrue(buyRightButton.isDisable());
    }
  }

}
