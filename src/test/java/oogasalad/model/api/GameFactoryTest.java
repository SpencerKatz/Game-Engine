package oogasalad.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import oogasalad.model.gameplay.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameFactoryTest {

  GameFactory factory = new GameFactory();

  @BeforeEach
  void setUp() {
  }

  @Test
  void createGameDefault() {
    assertEquals(new Game().getGameConfiguration().getRules().getString("configName"),
        factory.createGame().getGameConfiguration().getRules().getString("configName"));
  }

  @Test
  void createGameConfig() throws IOException {
    Game expected = new Game("testWorld1");
    GameInterface actual = factory.createGame("testWorld1");
    assertEquals(expected.getGameConfiguration().getRules().getString("configName"),
        actual.getGameConfiguration().getRules().getString("configName"));
    assertEquals(expected.getGameState().getGameTime(), actual.getGameState().getGameTime());
  }

  @Test
  void createGameConfigSave() throws IOException {
    Game expected = new Game("testWorld1", "testWorld1");
    GameInterface actual = factory.createGame("testWorld1", "testWorld1");
    assertEquals(expected.getGameConfiguration().getRules().getString("configName"),
        actual.getGameConfiguration().getRules().getString("configName"));
    assertEquals(expected.getGameState().getGameTime(), actual.getGameState().getGameTime());
  }
}