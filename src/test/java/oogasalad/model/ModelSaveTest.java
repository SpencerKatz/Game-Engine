package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import org.junit.jupiter.api.Test;

public class ModelSaveTest {

  //TODO: model team, fix the test
  @Test
  void testInteractDidNotWorkOnce() {
    GameInterface game = new GameFactory().createGame();
    String imagePath = game.getGameState().getGameWorld().getImagePath(2, 0, 0).toString();
    System.out.println(imagePath);
    game.selectItem("Hoe");
    game.interact(2, 0, 0);
    game.update();
    String imageAfter = game.getGameState().getGameWorld().getImagePath(2, 0, 0).toString();
    System.out.println(game.getGameState().getGameWorld().getImagePath(2, 0, 0));
    assertNotEquals(imageAfter, imagePath);
  }

  @Test
  void testInteractError() throws IOException {
    GameInterface game = new GameFactory().createGame();
    String imagePath = game.getGameState().getGameWorld().getImagePath(2, 0, 0).toString();
    System.out.println(imagePath);
    game.selectItem("Hoe");
    game.interact(2, 0, 0);
    game.interact(2, 0, 0);
    String imageAfter = game.getGameState().getGameWorld().getImagePath(2, 0, 0).toString();
    System.out.println(game.getGameState().getGameWorld().getImagePath(2, 0, 0));
    assertNotEquals(imageAfter, imagePath);
  }

  @Test
  void testSave() throws IOException {
    GameInterface game = new GameFactory().createGame();
    game.save("x.json");
    game.getGameConfiguration().save("x.json");
    System.out.println(game.getGameState().getGameWorld().getImagePath(2, 0, 0));
    game.selectItem("Hoe");
    game.interact(2, 0, 0);
    game.interact(2, 0, 0);
    game.update();
    System.out.println(game.getGameState().getGameWorld().getImagePath(2, 0, 0));
    game.save("x2.json");
    game.getGameConfiguration().save("x2.json");
    GameInterface game1 = new GameFactory().createGame("x.json", "x.json");
    GameInterface game2 = new GameFactory().createGame("x2.json", "x2.json");
    assertNotEquals(game1.getGameState().getGameWorld().getImagePath(2, 0, 0),
        game2.getGameState().getGameWorld().getImagePath(2, 0, 0));
  }
}
