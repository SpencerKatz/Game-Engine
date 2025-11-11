package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import oogasalad.model.api.exception.InvalidGameObjectType;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.gameobjectfactory.GameObjectFactory;
import oogasalad.model.gameobject.GameObject;
import oogasalad.model.gameobject.Structure;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class GameObjectFactoryTest extends BaseGameObjectTest {

  private final String[] expectedCreators = {"CollectableCreator", "LandCreator", "StructureCreator"};
  private Structure testingStructure;
  private GameObjectFactory factory;
  private Properties testingStructureProperties;
  @Test
  public void ensureDiscoverCreatorsCorrectlyIdentifiesAllCreators() {
    List<String> actualCreators = factory.getListOfCreators();
    for (String creator : expectedCreators) {
      assertTrue(actualCreators.contains(creator), "Factory should contain: " + creator);
    }
  }

  @Override
  protected void initializeGameObjects() throws IOException {
    factory = new GameObjectFactory();
    testingStructureProperties = Properties.of("test/testingGameObject.json");
    addPropertiesToStoreWithAlreadyCreatedProperties(testingStructureProperties.getString("name"), testingStructureProperties);
  }

  @Test
  public void verifyThatFactoryCreatesCorrectTypeOfGameObject() {
    GameObject gameObject = factory.createNewGameObject(testingStructureProperties.getString("name"),
        new GameTime(1,1,1), new HashMap<>());
    assertEquals("Structure", gameObject.getClass().getSimpleName());
  }

  @Test
  public void invalidGameObjectThrowsInvalidGameObjectType() {
    testingStructureProperties.update("type", "Building");
    assertThrows(InvalidGameObjectType.class, () -> factory.
        createNewGameObject(testingStructureProperties.getString("name"),
            new GameTime(1,1,1), new HashMap<>()));
  }
}
