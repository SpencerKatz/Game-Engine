package oogasalad.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameobject.gameobjectfactory.GameObjectFactory;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameobject.ItemsToAdd;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.gameplay.BuildableTileMap;

public class GameWorldTest extends TileTest {

  private BuildableTileMap gameWorld;
  private static final GameObjectFactory factory = new GameObjectFactory();

 @Override
 protected void initializeGameObjects() throws IOException {
   super.initializeGameObjects();
   gameWorld = new BuildableTileMap(5,5,1);
   gameWorld.setTileGameObject(getTestingLandProperties().getString("name"),
       1,1,0);
   gameWorld.setTileGameObject(getTestingStructureProperties().getString("name"),
       1,1,0);
   getTestingStructureProperties().update("updatable", "true");
   getTestingStructureProperties().update("updateTime", "1");
   addPropertiesToStore("wheat", "test/testingWheat.json");
 }



  @Test
  public void testUpdateGameWorld() {
    ReadOnlyGameTime gameTime = new GameTime(1, 30, 0);
    gameWorld.update(gameTime);
    gameWorld.update(gameTime);
    List<String> ids = gameWorld.getTileContents(1,1,0);
    List<String> expectedList = new ArrayList<>();
    expectedList.add(getTestingLandProperties().getString("name"));
    expectedList.add(getTestingStructureProperties().getString("updateTransformation"));
    assertTrue(gameWorld.getTileContents(2,2,0).isEmpty());
    assertEquals(expectedList, ids);
  }

  @Test
  public void testDimensionModification() {
    gameWorld.setWidth(10);
    assertEquals(10, gameWorld.getWidth());

    gameWorld.setHeight(10);
    assertEquals(10, gameWorld.getHeight());

    gameWorld.setDepth(2);
    assertEquals(2, gameWorld.getDepth());
  }

  @Test
  public void testGetImagePathOnEmptyTile() {
   assertTrue(gameWorld.getImagePath(2,2,0).isEmpty());
  }

  @Test
  public void testGetImagePathOnFullTile() {
    gameWorld.setTileGameObject(getTestingCollectableProperties()
        .getString("name"), 1,1,0);
    List<String> images = gameWorld.getImagePath(1,1,0);
    List<String> expectedList = new ArrayList<>();
    expectedList.add(getTestingLandProperties().getString("image"));
    expectedList.add(getTestingStructureProperties().getString("image"));
    expectedList.add(getTestingCollectableProperties().getString("image"));
    assertEquals(expectedList, images);
  }

  @Test
  public void validInteractionReturnsTrueInInteract() {
   assertTrue(gameWorld.interact(new Item("validItem"), 1,1,0));
  }

  @Test
  public void testInteractOnTileWhereInteractWillHaveAnEffect() {
    gameWorld.interact(new Item("validItem"), 1,1,0);
    gameWorld.update(new GameTime(1,1,1));
    List<String> expectedList = new ArrayList<>();
    List<String> ids = gameWorld.getTileContents(1,1,0);
    expectedList.add(getTestingLandProperties().getString("name"));
    expectedList.add(getTestingStructureProperties().getStringMap
        ("interactTransformations").get("validItem"));
    assertEquals(expectedList, ids);
  }

  @Test
  public void itemsToAddToInventoryShouldBeEmptyWhenNoCollectableAreCollected() {
    assertTrue(gameWorld.itemsToAddToInventory().isEmpty());
  }

  @Test
  public void itemsToAddToInventoryShouldContainsAllCollectableItemsThatWereCollected() {
    getTestingStructureProperties().update("destructable", "true");
    gameWorld.interact(new Item("validItem"), 1, 1,0);
    gameWorld.update(new GameTime(1,1,1));
    assertTrue(gameWorld.itemsToAddToInventory().isEmpty());
    gameWorld.interact(new Item("validItem"), 1, 1, 0);
    List<ItemsToAdd> itemsToAddToInventory = new ArrayList<>();
    itemsToAddToInventory.add(new ItemsToAdd(2, "seed"));
    assertEquals(itemsToAddToInventory, gameWorld.itemsToAddToInventory());
    assertTrue(gameWorld.itemsToAddToInventory().isEmpty());
  }
}



