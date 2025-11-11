package oogasalad.model.api;

import java.util.Map;

/**
 * Provides an interface to get information about the player's bag and items within.
 *
 * @author Jason Qiu
 */
public interface ReadOnlyBag {

  /**
   * Returns the items currently held in the bag.
   *
   * @return the items currently held in the bag.
   */
  Map<ReadOnlyItem, Integer> getItems();
}
