package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import oogasalad.model.api.exception.IncorrectPropertyFileType;
import oogasalad.model.data.Properties;
import oogasalad.model.gameobject.GameObject;
import oogasalad.model.gameobject.Item;
import oogasalad.model.gameplay.GameTime;
import org.junit.jupiter.api.Test;

public class GameObjectTest extends BaseGameObjectTest {

  private GameObject testingStructure;
  private Properties testingStructureProperties;
  private Properties tilledGrassProperties;
  private Properties differentThingProperties;

  @Override
  protected void initializeGameObjects() throws IOException {
    testingStructureProperties = Properties.of("test/testingGameObject.json");
    tilledGrassProperties = Properties.of("test/testingTilledGrass.json");
    differentThingProperties = Properties.of("test/testingDifferentThing.json");

    addPropertiesToStoreWithAlreadyCreatedProperties("grass_structure", testingStructureProperties);
    addPropertiesToStoreWithAlreadyCreatedProperties("tilled_grass", tilledGrassProperties);
    addPropertiesToStoreWithAlreadyCreatedProperties("differentThing", differentThingProperties);

    testingStructure = getFactory().createNewGameObject("grass_structure", new GameTime(1,1,1), new HashMap<>());
  }

  @Test
  public void testImagePathCorrect() {
    assertEquals("/img/grass.jpg", testingStructure.getImagePath());
  }

  @Test
  public void testStructureInteractionInvalid() {
    testingStructureProperties.update("destructable", "false");
    assertFalse(testingStructure.interactionValid(new Item("thing")));
  }

  @Test
  public void testStructureInteractionValid() {
    assertTrue(testingStructure.interactionValid(new Item("validItem")));
  }

  @Test
  public void verifyId() {
    assertEquals("grass_structure", testingStructure.getId());
  }

  @Test
 public void updateNotEnoughTimeWillNotUpdate() {
    testingStructure.update(new GameTime(1, 1, 1));
    testingStructure.update(new GameTime(1, 1, 1));
    assertEquals("grass_structure", testingStructure.getId());
  }

  @Test
  public void interactShouldLeadToNewGameObject() {
    testingStructure.interact(new Item("validItem"));
    assertEquals("grass_structure", testingStructure.getId());
    testingStructure.update(new GameTime(1,1,1));
    assertEquals("tilled_grass", testingStructure.getId());
  }
  @Test
  public void updateShouldHaveUpdated() {
    testingStructure.update(new GameTime(2, 2, 2));
    testingStructure.update(new GameTime(2, 2, 2));
    assertEquals("tilled_grass", testingStructure.getId());
  }

  @Test
  public void updateShouldNotHaveUpdatedDespiteValidUpdateDueToItUpdatingOnNextIteration() {
    testingStructure.update(new GameTime(2, 2, 2));
    assertEquals("grass_structure", testingStructure.getId());
  }

  @Test
  public void shouldBeExpired() {
    testingStructureProperties.update("expirable", "true");
    testingStructureProperties.update("updatable", "false");
    assertEquals("grass_structure", testingStructure.getId());
    assertFalse(testingStructure.checkAndUpdateExpired(new GameTime(2,2,2)));
    testingStructure.update(new GameTime(2,2,2));
    assertTrue(testingStructure.checkAndUpdateExpired(new GameTime(3,3,3)));
  }

  @Test
  public void shouldThrowIncorrectType() {
    tilledGrassProperties.update("type", "Land");
    testingStructure.update(new GameTime(2,2,2));
    assertThrows(IncorrectPropertyFileType.class, () -> testingStructure.update(new GameTime(2, 2, 2)));
  }

  @Test
  public void testingUpdateAfterUpdate() {
    assertEquals("grass_structure", testingStructure.getId());
    testingStructure.update(new GameTime(2,2,3));
    testingStructure.update(new GameTime(2,2,3));
    assertEquals("tilled_grass", testingStructure.getId());
    testingStructure.update(new GameTime(2,2,10));
    assertEquals("tilled_grass", testingStructure.getId());
    testingStructure.update(new GameTime(2,2,10));
    assertEquals("differentThing", testingStructure.getId());
  }

  @Test
  public void expireBeforeUpdate() {
    testingStructureProperties.update("updateTime", "15");
    testingStructureProperties.update("expirable", "true");
    testingStructureProperties.update("expireTime", "10");
    testingStructure.update(new GameTime(1,1,1));
    assertFalse(testingStructure.checkAndUpdateExpired(new GameTime(1,1,9)));
    assertTrue(testingStructure.checkAndUpdateExpired(new GameTime(1,1,12)));
    testingStructure.update(new GameTime(1,1,14));
    testingStructure.update(new GameTime(1,1,14));
    assertEquals("grass_structure", testingStructure.getId());
  }
}