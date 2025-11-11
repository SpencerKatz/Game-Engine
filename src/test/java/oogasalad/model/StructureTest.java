package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameobject.Structure;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class StructureTest extends BaseGameObjectTest {

  private Structure testingStructure;

  @Override
  protected void initializeGameObjects() throws IOException {
    addPropertiesToStore("grass_structure", "test/testingGameObject.json");
    testingStructure = (Structure) getFactory().createNewGameObject("grass_structure", new GameTime(1,1,1), new HashMap<>());
  }

  @Test
  public void interactionValidIfDestructable() {
    assertTrue(testingStructure.interactionValid(new Item("item")));
  }

  @Test
  public void retrieveStructureDropsCorrectly() {
    assertTrue(testingStructure.getItemsOnDestruction().containsKey("seed"));
  }

  @Test
  public void retrieveStructureDropsAmountCorrectly() {
    assertEquals(2, testingStructure.getItemsOnDestruction().get("seed"));
  }
}

