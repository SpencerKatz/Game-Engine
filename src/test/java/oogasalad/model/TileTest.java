package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameobject.Tile;
import oogasalad.model.gameobject.Collectable;
import oogasalad.model.gameobject.Structure;
import oogasalad.model.gameobject.Land;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class TileTest extends BaseGameObjectTest {

  private Land tileLand;
  private Structure tileStructure;
  private Collectable tileCollectable;
  private Properties testingStructureProperties;
  private Properties testingLandProperties;
  private Properties testingCollectableProperties;
  private Tile tileToTest;

  @Override
  protected void initializeGameObjects() throws IOException {
    testingStructureProperties = Properties.of("test/testingTileStructure.json");
    testingLandProperties = Properties.of("test/testingTileLand.json");
    testingCollectableProperties = Properties.of("test/testingTileCollectable.json");
    addPropertiesToStoreWithAlreadyCreatedProperties
        (testingStructureProperties.getString("name"), testingStructureProperties);
    addPropertiesToStoreWithAlreadyCreatedProperties
        (testingLandProperties.getString("name"), testingLandProperties);
    addPropertiesToStoreWithAlreadyCreatedProperties
        (testingCollectableProperties.getString("name"), testingCollectableProperties);
    addPropertiesToStore("axe", "test/testingItem.json");
    tileStructure = (Structure) getFactory()
        .createNewGameObject(testingStructureProperties.getString("name"),
            new GameTime(1,1,1), new HashMap<>());
    tileLand = (Land) getFactory()
        .createNewGameObject(testingLandProperties.getString("name"),
            new GameTime(1,1,1), new HashMap<>());
    Map<String, Integer> collectableItems = new HashMap<>();
    collectableItems.put("axe", 2);
    tileCollectable = (Collectable) getFactory()
        .createNewGameObject(testingCollectableProperties.getString("name"),
            new GameTime(1,1,1), collectableItems);
    tileToTest = new Tile();
    tileToTest.setCollectable(tileCollectable);
    tileToTest.setLand(tileLand);
    tileToTest.setStructure(tileStructure);
  }

  @Test
  public void testCorrectImageRetrieval() {
    List<String> images = new ArrayList<>();
    images.add(tileLand.getImagePath());
    images.add(tileStructure.getImagePath());
    images.add(tileCollectable.getImagePath());
    assertEquals(images.toString(), tileToTest.getImages().toString());
  }

  @Test
  public void testInteractionWillOnlyAffectCollectableIfCollectableIsNotNull() {
    tileToTest.interact(new Item("validItem"));
    tileToTest.update(new GameTime(1,1,1));
    tileToTest.update(new GameTime(1,1,1));
    assertEquals(testingStructureProperties.getString("name"), tileToTest.getStructureId());
    assertEquals(testingLandProperties.getString("name"), tileToTest.getLandId());
  }

  @Test
  public void testInteractionAffectsStructureWhenCollectableIsNull()
      throws IOException {
    tileToTest.setCollectable(null);
    addPropertiesToStore("wheat", "test/testingWheat.json");
    tileToTest.interact(new Item("hoe"));
    tileToTest.update(new GameTime(1,1,1));
    assertEquals("wheat", tileToTest.getStructureId());
    assertEquals(testingLandProperties.getString("name"), tileToTest.getLandId());
  }
  @Test
  public void itemsReturnsShouldReturnNullWhenCollectableNotReadyToCollect() {
    assertNull(tileToTest.itemReturns());
  }

  @Test
  public void itemReturnsShouldBeWhatIsInCollectableAfterValidCollectableInteractionCollectableShouldBecomeNullAfter() {
    tileToTest.interact(new Item("validItem"));
    Map<String, Integer> collectableItems = new HashMap<>();
    collectableItems.put("axe", 2);
    Map<String, Integer> itemReturns = tileToTest.itemReturns();
    assertNotNull(itemReturns);
    assertEquals(collectableItems, itemReturns);
    assertNull(tileToTest.getCollectableId());
  }
  @Test
  public void thereShouldNotBeAnErrorDespiteAllGameObjectsBeingNull() {
    tileToTest.setStructure(null);
    tileToTest.setCollectable(null);
    tileToTest.setLand(null);
    try {
      tileToTest.update(new GameTime(1,1,1));
      tileToTest.interact(new Item("validItem"));
      assertTrue(true, "No exceptions were thrown when all game objects are null.");
    } catch (Exception e) {
      fail("Should not have thrown any exception, but threw " + e.getClass().getSimpleName());
    }
  }

  @Test
  public void invalidItemShouldNotInteractWithGameObjectsDespiteCollectableNull() {
    Item invalid = new Item("invalidItem");
    tileToTest.setCollectable(null);
    tileToTest.interact(invalid);
    tileToTest.update(new GameTime(1,1,1));
    assertEquals(testingLandProperties.getString("name"), tileToTest.getLandId());
    assertEquals(testingStructureProperties.getString("name"), tileToTest.getStructureId());
  }

  @Test
  public void validInteractionValidReturnsTrue() {
    assertTrue(tileToTest.interactionValid(new Item("validItem")));
    tileToTest.setCollectable(null);
    assertTrue(tileToTest.interactionValid(new Item("hoe")));
  }

  @Test
  public void allInteractionsAreValidWithCollectableNonNull() {
    assertTrue(tileToTest.interactionValid(new Item("hoe")));
  }



  @Test
  public void expirationWillLeadToGameObjectsBecomingNullIfExpirable() {
    testingLandProperties.update("expirable", "false");
    testingStructureProperties.update("expirable","true");
    testingStructureProperties.update("expireTime", "1");
    testingCollectableProperties.update("expirable", "false");
    tileToTest.update(new GameTime(1,1,1));
    tileToTest.update(new GameTime(1,1,5));
    assertNotNull(tileToTest.getLandId());
    assertNotNull(tileToTest.getCollectableId());
    assertNull(tileToTest.getStructureId());
  }

  @Test
  public void structureDropsCollectableWhenCollectableIsNullAndStructureIsDestructable() {
    testingStructureProperties.update("destructable","true");
    tileToTest.setCollectable(null);
    tileToTest.interact(new Item("validItem"));
    Map<String, Integer> collectableItems = new HashMap<>();
    collectableItems.put("seed", 2);
    assertNull(tileToTest.getStructureId());
    tileToTest.interact(new Item("validItem"));
    Map<String, Integer> itemReturns = tileToTest.itemReturns();
    assertEquals(collectableItems, itemReturns);
    assertNull(tileToTest.getCollectableId());
  }

  @Test
  public void structurePlantedWhenValidSeedIsPutOnLandAndStructureIsNull() throws IOException {
    addPropertiesToStore("wheat", "test/testingWheat.json");
    tileToTest.setStructure(null);
    tileToTest.setCollectable(null);
    tileToTest.interact(new Item("wheat_seed"));
    assertEquals("wheat", tileToTest.getStructureId());
  }

  @Test
  public void structureNotPlantedWhenValidSeedIsPutOnLandAndStructureIsNotNull() throws IOException {
    addPropertiesToStore("wheat", "test/testingWheat.json");
    tileToTest.interact(new Item("wheat_seed"));
    assertEquals(testingStructureProperties.getString("name"), tileToTest.getStructureId());
  }

  protected Tile getTile() {
    return tileToTest;
  }

  protected Land getLand() {
    return tileLand;
  }

  protected Structure getTileStructure() {
    return tileStructure;
  }

  protected Collectable getTileCollectable() {
    return tileCollectable;
  }

  protected Properties getTestingStructureProperties() {
    return testingStructureProperties;
  }

  protected Properties getTestingLandProperties() {
    return testingLandProperties;
  }

  protected Properties getTestingCollectableProperties() {
    return testingCollectableProperties;
  }
}

