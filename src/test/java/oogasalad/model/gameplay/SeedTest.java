package oogasalad.model.gameplay;

import java.util.Map;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyGameWorld;
import oogasalad.model.api.ReadOnlyItem;
import org.junit.jupiter.api.Test;

public class SeedTest {

  @Test
  void testSeedNumber() {
    GameInterface game = new GameFactory().createGame();
    ReadOnlyGameWorld gameWorld = game.getGameState().getGameWorld();
    Map<ReadOnlyItem, Integer> items = game.getGameState().getBag().getItems();
    // get the image path for 2,0,0
    System.out.println(gameWorld.getImagePath(2, 0, 0));
    game.selectItem("Hoe");
    game.interact(2, 0, 0);
    game.update();
    // get the image path after hoeing
    System.out.println(gameWorld.getImagePath(2, 0, 0));
    game.selectItem("Wheat Seeds");
    game.interact(2, 0, 0);
    game.update();
    // plant the seed
    System.out.println(gameWorld.getImagePath(2, 0, 0));
    System.out.println(game.getGameState().getBag().getItems());


  }

}
