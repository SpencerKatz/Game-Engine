package oogasalad.model.api;

import oogasalad.model.gameobject.Viewable;

/**
 * Provides an interface to get information about an item.
 *
 * @author Jason Qiu, Spencer Katz
 */
public interface ReadOnlyItem extends Viewable {

  /**
   * Get the name/id of the item.
   *
   * @return the name/id of the item.
   */
  String getName();


  /**
   * Get how much money the item is worth.
   *
   * @return how much money the item is worth.
   */
  double getWorth();
}
