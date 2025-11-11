package oogasalad.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.data.GameConfigurablesStore;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.GameObject;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameobject.Land;
import oogasalad.model.gameplay.GameState;
import oogasalad.model.gameplay.GameWorld;
import org.junit.jupiter.api.Test;

public class DataGeneration {

  /**
   * Create a default game with grasses as the tiles 10 x 15 with grass
   */
  @Test
  void generateGameWorld() throws IOException {
//    The file to save the gameWorld to
    String fileName = "testWorld1.json";
//    the id for the grass land
    String id = "grass_land";
    String configName = "TempGameConfiguration.json";
    GameConfiguration gameConfiguration = GameConfiguration.of(configName);
    GameConfigurablesStore editableConfigurablesStore = GameConfiguration.getEditableConfigurablesStore();
    Map<String, Properties> allEditableConfigurables = editableConfigurablesStore.getAllEditableConfigurables();

    Properties property = new Properties();
    property.getProperties().put("image", "/img/grass.jpg");
    property.getProperties().put("updatable", "false");
    property.getProperties().put("updateTime", "10");
    property.getProperties().put("expirable", "false");
    allEditableConfigurables.put(id, property);
    GameState gameState = new GameState(gameConfiguration.getRules());
    GameWorld gameWorld = gameState.getEditableGameWorld();
    GameObject land = new Land(id, gameState.getEditableGameTime().copy());
    //   horizontal -x, vertical  - y
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 10; j++) {
        //gameWorld.setTileGameObject(id, i, j, 0);
      }
    }
//    gameWorld.update(new GameTime(1, 1, 1));
//    editableConfigurablesStore.save(fileName);
    gameConfiguration.save(fileName);
    gameState.save(fileName);
  }

  /**
   * Create a default game with grasses as the tiles 10 x 15 with grass and some default values
   */
  @Test
  void generateGameWorldWithItemInBag() throws IOException {
//    The file to save the gameWorld to
    String fileName = "testWorld1.json";
//    the id for the grass land
    String id = "grass_land";
    String configName = "TempGameConfiguration.json";
    GameConfiguration gameConfiguration = GameConfiguration.of(configName);
    GameConfigurablesStore editableConfigurablesStore = GameConfiguration.getEditableConfigurablesStore();
    Map<String, Properties> allEditableConfigurables = editableConfigurablesStore.getAllEditableConfigurables();
    Properties property = new Properties();
    property.getProperties().put("image", "/img/tool.jpg");
    property.getProperties().put("updatable", "false");
    property.getProperties().put("updateTime", "10");
    property.getProperties().put("expirable", "false");
    Map<String, String> value = new HashMap<>();
    value.put("hoe", "dirt");
    property.getMapProperties().put("interactTransformations", value);
    allEditableConfigurables.put(id, property);
    GameState gameState = new GameState(gameConfiguration.getRules());
    GameWorld gameWorld = gameState.getEditableGameWorld();
    GameObject land = new Land(id, gameState.getEditableGameTime().copy());
    //   horizontal -x, vertical  - y
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 10; j++) {
        //gameWorld.setTileGameObject(id, i, j, 0);
      }
    }
    Map<ReadOnlyItem, Integer> items = gameState.getBag().getItems();
    ReadOnlyItem readOnlyItem = new Item("axe");
    gameConfiguration.save(fileName);
    gameState.save(fileName);
  }

  @Test
  void testGeneratedWorld() throws IOException {
//    the code for the previous test
    String fileName = "testWorld1.json";
    GameConfiguration gameConfiguration = GameConfiguration.of("testWorld1.json");
    GameFactory gameFactory = new GameFactory();
    GameInterface game = gameFactory.createGame(fileName, fileName);
    List<String> imagePath = game.getGameState().getGameWorld().getImagePath(1, 2, 0);
    System.out.println(imagePath);
  }


}
