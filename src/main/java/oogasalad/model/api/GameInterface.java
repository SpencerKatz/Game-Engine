package oogasalad.model.api;

import java.io.IOException;
import oogasalad.model.api.exception.KeyNotFoundException;

/**
 * Main API for the view to interact with.
 * <p>
 * Provides functions to interact with the world and get data about the current state,
 * configuration, and configurables.
 *
 * @author Jason Qiu
 */
public interface GameInterface {

  /**
   * Returns the currently loaded GameConfiguration, which provides methods to get the currently
   * configured rules, store of configurables, initial GameState, etc.
   *
   * @return the currently loaded GameConfiguration.
   */
  ReadOnlyGameConfiguration getGameConfiguration();

  /**
   * Returns the current GameState, from which GameTime, GameWorld, and other stateful information
   * can be retrieved.
   *
   * @return the current GameState instance.
   */
  ReadOnlyGameState getGameState();

  /**
   * Updates the game model since the last time it was updated in realtime.
   * <p>
   * The implementation will independently keep track of time passed, so no delta time parameter is
   * needed.
   */
  void update();

  /**
   * Saves the current GameState to 'data/gamesaves' with the given filename.
   *
   * @param fileName the name of the file to save to.
   * @throws IOException if writing to the file fails.
   */
  void save(String fileName) throws IOException;

  /**
   * Selects an item in the bag.
   *
   * @param name the name of the item in the bag to select.
   * @throws KeyNotFoundException if the item is not in the bag.
   */
  void selectItem(String name) throws KeyNotFoundException;

  /**
   * Interacts with the given coordinate at the world using the selected item.
   *
   * @param x     the interacted x-coordinate.
   * @param y     the interacted y-coordinate.
   * @param depth the interacted depth coordinate.
   */
  void interact(int x, int y, int depth);

  /**
   * Restores all energy and passes game time.
   */
  void sleep();

  /**
   * Tries to buy an item from the shop.
   *
   * @param name the name of the item to buy from the shop.
   * @return true if successfully bought, false otherwise.
   * @throws KeyNotFoundException if the item is not in the shop.
   */
  boolean buyItem(String name) throws KeyNotFoundException;

  /**
   * Tries to sell an item from the bag.
   *
   * @param name the name of the item to sell from the bag.
   * @throws KeyNotFoundException if the item is not in the bag.
   */
  void sellItem(String name) throws KeyNotFoundException;

  /**
   * Returns true if the game is over, false otherwise.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();
}
