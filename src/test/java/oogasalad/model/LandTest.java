package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import oogasalad.model.gameobject.Land;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class LandTest extends BaseGameObjectTest {

  private Land testingLand;

  @Override
  protected void initializeGameObjects() throws IOException {
    addPropertiesToStore("grass_land", "test/testingGrassLand.json");
    testingLand = (Land) getFactory().createNewGameObject("grass_land", new GameTime(1,1,1), new HashMap<>());
  }

  @Test
  public void testValidSeed() {
    assertTrue(testingLand.getIfItemCanBePlacedHere(new Item("wheat_seed")));
  }

  @Test
  public void testValidInteractionIfItemCanBePlacedHere() {
    assertTrue(testingLand.interactionValid(new Item("wheat_seed")));
  }

  @Test
  public void testInvalidSeed() {
    assertFalse(testingLand.getIfItemCanBePlacedHere(new Item("invalid")));
  }

  @Test
  public void testGetStructureIdBasedOnPlantableItem() {
    assertEquals("wheat", testingLand.getStructureBasedOnItem(new Item("wheat_seed")));
  }
}

