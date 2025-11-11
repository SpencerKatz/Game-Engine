package oogasalad.model.gameobject;

import oogasalad.model.api.ReadOnlyItem;

/**
 * Defines the capability of an object to interact with items within the game. Implementing this
 * interface allows an object to respond to interactions with specific items, potentially changing
 * its state or the state of the item based on the nature of the interaction. This interface is
 * crucial for enabling dynamic and interactive gameplay elements, where the outcome of an
 * interaction can vary based on the items and objects involved.
 */
public interface Interactable {

  /**
   * Handles an interaction between this object and a given item, resulting in a change of state or
   * the execution of an action. The specifics of the interaction depend on both the item's
   * properties and the current state of the object.
   *
   * @param item The item interacting with this object.
   */
  void interact(ReadOnlyItem item);

  /**
   * Determines whether an interaction with the specified item is valid at the current moment. This
   * method allows objects to assess whether they should proceed with an interaction based on the
   * item's properties and the object's current state. It enables conditional interactions, where
   * only certain items or states allow for the interaction to occur.
   *
   * @param item The item proposed to interact with this object.
   * @return {@code true} if the interaction with the item is valid; {@code false} otherwise.
   */
  boolean interactionValid(ReadOnlyItem item);
}

