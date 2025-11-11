package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.gameobject.Collectable;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class CollectableTest extends BaseGameObjectTest {

  private Collectable testingCollectable;

  @Override
  protected void initializeGameObjects() throws IOException {
    addPropertiesToStore("grass_collectable", "test/testingGrassCollectable.json");
    Map<String, Integer> itemsOnCollection = new HashMap<>();
    itemsOnCollection.put("axe", 2);
    testingCollectable = (Collectable) getFactory().createNewGameObject("grass_collectable", new GameTime(1,1,1), itemsOnCollection);
    addPropertiesToStore("axe", "test/testingItem.json");
  }

  @Test
  public void testingGettingItemsOnCollection() {
    assertEquals(2, (int) testingCollectable.getItemsOnCollection().get("axe"));
  }

  @Test
  public void testImagePath() {
    assertEquals("scythe.png", testingCollectable.getImagePath());
  }

  @Test
  public void testingShouldICollectWhenIShould() {
    testingCollectable.interact(new Item("validItem"));
    assertTrue(testingCollectable.shouldICollect());
  }

  @Test
  public void testShouldICollectWhenIShouldNot() {
    assertFalse(testingCollectable.shouldICollect());
  }
}

