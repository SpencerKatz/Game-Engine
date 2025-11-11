package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GameTest {

  @BeforeAll
  static void setup() {
    new GameConfiguration(); // set the default GameConfigurablesStore
  }

  @Test
  void collectGoal() throws IOException {
    GameConfiguration configuration =
        new GameConfiguration(Properties.of("test/collectGoalConditionTest"));
    assertEquals("COLLECTGOALTEST", configuration.getRules().getString("configName"));

    Game game = new Game(configuration);
    assertEquals("COLLECTGOALTEST", game.getGameConfiguration().getRules().getString("configName"));
    assertFalse(game.isGameOver());
    ((Bag) game.getGameState().getBag()).addItems(
        configuration.getRules().getStringIntegerMap("collectGoal"));
    assertTrue(game.isGameOver());
  }

  @Test
  void timeGoal() throws IOException {
    GameConfiguration configuration =
        new GameConfiguration(Properties.of("test/timeGoalConditionTest"));
    assertEquals("TIMEGOALTEST", configuration.getRules().getString("configName"));
    Game game = new Game(configuration);

    assertFalse(game.isGameOver());
    for (int i = 0; i < 7; i++) {
      game.sleep();
      ((GameTime) game.getGameState().getGameTime()).advanceTo(0, 0);
    }
    assertTrue(game.isGameOver());
  }

  @Test
  void shopping() {
    Game game = new Game();
    Shop editableShop = (Shop) game.getGameState().getShop();
    ReadOnlyItem pickaxe = new Item("Pickaxe");
    editableShop.setPossibleItems(List.of(pickaxe));
    editableShop.forceItemRotation(new GameTime(0, 0, 0));

    assertFalse(game.buyItem("Pickaxe"));
    ((GameState) game.getGameState()).addMoney(editableShop.getItems().get(pickaxe).intValue());
    assertTrue(game.buyItem("Pickaxe"));
    assertTrue(game.getGameState().getBag().getItems().containsKey(pickaxe));
    assertEquals(0, game.getGameState().getMoney());

    assertDoesNotThrow(() -> game.sellItem("Pickaxe"));
    assertEquals(pickaxe.getWorth(), game.getGameState().getMoney());
  }

  @Test
  void energyInteractions() {
    Game game = new Game();
    game.getEditableGameState().getEditableBag().addItem("Cake", 1);
    BuildableTileMap map = ((BuildableTileMap) game.getEditableGameState().getEditableGameWorld());
    for (int i = 0; i < 3; i++) {
      map.removeTileTop(0, 0, 0);
    }
    map.setTileGameObject("Dirt", 0, 0, 0);

    double originalEnergy = game.getGameState().getEnergy();
    game.selectItem("Hoe");
    game.interact(0, 0, 0);
    assertEquals(originalEnergy + (new Item("Hoe")).getEnergyChange(),
        game.getGameState().getEnergy());
    assertTrue(game.getGameState().getBag().getItems().containsKey(new Item("Hoe")));

    game.selectItem("Cake");
    game.interact(0, 0, 0);
    assertEquals(game.getGameConfiguration().getRules().getDouble("energyAmount"),
        game.getGameState().getEnergy());
    assertFalse(game.getGameState().getBag().getItems().containsKey(new Item("Cake")));
  }

  @Test
  void collapseSystems() {
    Game game = new Game();
    game.getEditableGameState().restoreEnergy(-10000);

    game.getEditableConfiguration().updateRule("onZeroEnergyStrategy", "none");
    GameTime previousGametime = new GameTime(game.getGameState().getGameTime());
    game.update();
    previousGametime.advanceOneUnit();
    assertEquals(previousGametime, game.getGameState().getGameTime());

    game.getEditableConfiguration().updateRule("onZeroEnergyStrategy", "collapse");
    previousGametime = new GameTime(game.getGameState().getGameTime());
    game.update();
    previousGametime.advance(
        game.getGameConfiguration().getRules().getInteger("collapseTimePenalty"));
    assertEquals(previousGametime, game.getGameState().getGameTime());

    game.getEditableConfiguration().updateRule("onZeroEnergyStrategy", "death");
    game.getEditableGameState().restoreEnergy(-10000);
    game.update();
    assertTrue(game.isGameOver());
  }

  @Test
  void loadConfigWithNewState() throws IOException {
    GameConfiguration defaultConfig = new GameConfiguration();
    final Map<String, String> startingItems = Map.of("Cake", "20");
    final List<String> shopPossibleItems = List.of("Cake", "Scythe");
    defaultConfig.updateRule("startingItems", startingItems);
    defaultConfig.updateRule("shopPossibleItems", shopPossibleItems);
    defaultConfig.getEditableInitialState().getEditableMap()
        .setTileGameObject("Germinating Wheat", 0, 0, 0);
    Game actual = new Game(defaultConfig);

    assertTrue(actual.getEditableGameState().getEditableBag().contains(Map.of("Cake", 20)));
    for (ReadOnlyItem item : actual.getEditableGameState().getEditableShop().getItems().keySet()) {
      assertTrue(shopPossibleItems.contains(item.getName()));
    }
    assertEquals("Germinating Wheat",
        actual.getEditableGameState().getEditableMap().getTileContents(0, 0, 0).get(1));
  }

  @Test
  void loadConfigWithNewStateEnergyShouldBeMax() throws IOException {
    GameConfiguration defaultConfig = new GameConfiguration();
    Game actual = new Game(defaultConfig);

    assertEquals(defaultConfig.getRules().getInteger("energyAmount"), actual.getGameState().getEnergy());
  }
}