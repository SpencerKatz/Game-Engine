package oogasalad.model.gameplay;

import oogasalad.model.api.ReadOnlyGameTime;

/**
 * Interfaces for Game Time object
 */
public interface GameTimeInterface extends ReadOnlyGameTime {

  /**
   * This should be called in the game loop in the UI to update the game time.
   */
  void update();
}
