package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.data.GameConfigurablesStore;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.data.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameConfigurationTest {

  static Logger LOG = LogManager.getLogger(GameConfigurationTest.class);

  @Test
  void defaultRules() {
    GameConfiguration config = new GameConfiguration();
    boolean doEnergy =
        Assertions.assertDoesNotThrow(() -> config.getRules().getBoolean("doEnergy"));
    LOG.debug(config.getRules().getCopyOfProperties());
    assertTrue(doEnergy);
  }

  @Test
  void checkDefaultConfigurablesStore() throws IOException {
    GameConfiguration defaultGame = new GameConfiguration();
    assertNotEquals(GameConfiguration.getConfigurablesStore().getAllConfigurables().size(), 0);
  }

  @Test
  void addItemToGameStore() throws IOException {
    GameConfiguration gameConfiguration = GameConfiguration.of("TempGameConfiguration.json");
    GameConfigurablesStore editableConfigurablesStore =
        GameConfiguration.getEditableConfigurablesStore();
    Map<String, Properties> allEditableConfigurables =
        editableConfigurablesStore.getAllEditableConfigurables();
    Properties properties = new Properties();
    System.out.println(allEditableConfigurables);
    properties.getProperties().put("wheat", "100");
    allEditableConfigurables.put("store", properties);
    editableConfigurablesStore.save("test.json");
    gameConfiguration.save("test.json");
  }

  @Test
  void testGameConfiguration() throws IOException {
    String fileName = "test.json";
    GameConfiguration gameConfiguration = GameConfiguration.of(fileName);
    GameConfigurablesStore gameConfigurablesStore =
        GameConfiguration.getEditableConfigurablesStore();
    System.out.println(gameConfigurablesStore.getAllConfigurables());
    assertEquals(gameConfigurablesStore.getAllConfigurables().size(), 1);
  }

  @Test
  void addPropertiesToConfigurablesStore() throws IOException {
//    Create Game config and game config store from a file name
    String fileName = "test.json";
    GameConfiguration gameConfiguration = GameConfiguration.of(fileName);
    GameConfigurablesStore gameConfigurablesStore =
        GameConfiguration.getEditableConfigurablesStore();
//    get the map of the properties <id, properties>
    Map<String, Properties> allConfigurables = gameConfigurablesStore.getAllEditableConfigurables();
//    Put id and properties inside
    Properties properties = new Properties();
    allConfigurables.put("test", properties);
//    Get the properties
    ReadOnlyProperties test = gameConfigurablesStore.getConfigurableProperties("test");
    assertNotNull(test);
  }

  @Test
  void updateRules() {
    GameConfiguration config = new GameConfiguration();

    assertTrue(config.getRules().getBoolean("doEnergy"));
    config.updateRule("doEnergy", "false");
    assertFalse(config.getRules().getBoolean("doEnergy"));

    assertTrue(config.getRules().getStringList("shopPossibleItems").contains("Hoe"));
    config.updateRule("shopPossibleItems", List.of("Scythe"));
    assertFalse(config.getRules().getStringList("shopPossibleItems").contains("Hoe"));

    assertTrue(config.getRules().getStringIntegerMap("collectGoal").containsKey("Wheat"));
    config.updateRule("collectGoal", Map.of("Scythe", "2"));
    assertFalse(config.getRules().getStringMap("collectGoal").containsKey("Wheat"));
  }

  @Test
  void updateEnergy() {
    GameConfiguration config = new GameConfiguration();

    assertEquals(config.getRules().getInteger("energyAmount"),
        config.getInitialState().getEnergy());
    assertEquals(-5, config.getEditableInitialState().restoreEnergy(-5));
    assertEquals(config.getRules().getInteger("energyAmount") - 5,
        config.getInitialState().getEnergy());
    assertEquals(5, config.getEditableInitialState().restoreEnergy(Integer.MAX_VALUE));
    assertEquals(config.getRules().getInteger("energyAmount"),
        config.getInitialState().getEnergy());
  }

  @Test
  void initializeBlock() {

  }
}