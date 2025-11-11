package oogasalad.model.api;

import java.io.IOException;
import oogasalad.model.gameplay.Game;

/**
 * Factory for creating an instance of a GameInterface.
 *
 * @author Jason Qiu
 */
public class GameFactory {

  /**
   * Creates a default instance of Game.
   */
  public GameInterface createGame() {
    return new Game();
  }

  /**
   * Creates a new instance of Game from a config, automatically loading the default state as the
   * current state.
   *
   * @param configName the name of the configuration file found in 'data/gameconfigurations'
   * @return a new instance of Game from a config, automatically loading the default state as the *
   * current state.
   * @throws IOException if there was an issue loading the game configuration.
   */
  public GameInterface createGame(String configName) throws IOException {
    return new Game(configName);
  }

  /**
   * Creates a new instance of Game from a config and save.
   *
   * @param configName the name of the configuration file found in 'data/gameconfigurations' *
   * @param saveName   the name of the save file found in 'data/gamesaves' * *.
   * @return a new instance of Game from a config and save.
   * @throws IOException if there was an issue loading the configuration or save
   */
  public GameInterface createGame(String configName, String saveName) throws IOException {
    return new Game(configName, saveName);
  }
}
