package oogasalad.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import oogasalad.model.data.GameConfigurablesStore;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.gameobjectfactory.GameObjectFactory;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseGameObjectTest {
  private GameConfigurablesStore editableConfigurablesStore;
  private Map<String, Properties> allEditableConfigurables;
  private GameObjectFactory factory;

  @BeforeEach
  public void setUp() throws IOException {
    String configFileName = "TempGameConfiguration.json";
    GameConfiguration gameConfiguration = GameConfiguration.of(configFileName);
    editableConfigurablesStore = GameConfiguration.getEditableConfigurablesStore();
    allEditableConfigurables = new HashMap<>();
    factory = new GameObjectFactory();
    initializeGameObjects();
    gameConfiguration.save("testWorld1.json");
  }

  protected abstract void initializeGameObjects() throws IOException;

  protected void addPropertiesToStore(String id, String propertiesPath) throws IOException {
    Properties properties = Properties.of(propertiesPath);
    editableConfigurablesStore.getAllEditableConfigurables().put(id, properties);
    allEditableConfigurables.put(id, properties);
  }

  protected void addPropertiesToStoreWithAlreadyCreatedProperties(String id, Properties properties) throws
      IOException {
    editableConfigurablesStore.getAllEditableConfigurables().put(id, properties);
    allEditableConfigurables.put(id, properties);
  }

  public GameConfigurablesStore getEditableConfigurablesStore() {
    return editableConfigurablesStore;
  }

  public Map<String, Properties> getAllEditableConfigurables() {
    return allEditableConfigurables;
  }

  public GameObjectFactory getFactory() {
    return factory;
  }
}

