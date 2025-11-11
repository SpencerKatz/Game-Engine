package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.gameobject.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTest {

  Shop shop;
  final List<ReadOnlyItem> possibleItems =
      List.of(new Item("Wheat Bundle"), new Item("Wheat Seeds"), new Item("Hoe"));
  final int rotationTime = 10;
  final int rotationSize = 2;

  @BeforeEach
  void setUp() {
    new GameConfiguration(); // load the default configurablesstore
    shop = new Shop(new GameTime(0, 0, 0), possibleItems, rotationSize, rotationTime);
  }

  @Test
  void getItems() {
    assertEquals(shop.getItemRotationSize(), shop.getItems().size());
    for (ReadOnlyItem item : shop.getItems().keySet()) {
      assertTrue(possibleItems.contains(item));
    }
  }

  @Test
  void update() {
    assertEquals(shop.getItemRotationSize(), shop.getItems().size());
    shop.setItemRotationSize(1);
    assertEquals(rotationSize, shop.getItems().size());
    shop.update(new GameTime(0, 1, rotationTime));
    assertEquals(shop.getItemRotationSize(), shop.getItems().size());
  }
  @Test
  void possibleItems() {
    assertEquals(possibleItems, shop.getCopyOfPossibleItems());
    shop.setPossibleItems(List.of(new Item("Scythe")));
    shop.forceItemRotation(new GameTime(0, 1, rotationTime));
    assertEquals(1, shop.getItems().size());
  }
}