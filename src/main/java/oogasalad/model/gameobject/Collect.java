package oogasalad.model.gameobject;

import java.util.Map;

/**
 * Provides the necessary contract for objects that can be collected in the game. Implementing this
 * interface allows objects to specify what happens when they are collected, including how many
 * items or what type of items are added to the player's inventory. This can apply to a wide range
 * of game objects, from simple resources to complex items that may trigger additional game
 * mechanics upon collection.
 */
public interface Collect {

  /**
   * Retrieve the items and their quantities stored in the collectable.
   *
   * @return A Map of all items id to their quantities stored in collectable.
   */
  Map<String, Integer> getItemsOnCollection();

  /**
   * Determines whether the object should be collected based on its current state or the current
   * game conditions. This method allows for conditional collection, where objects may only be
   * collectible under certain circumstances or actions.
   *
   * @return {@code true} if the object should be collected; {@code false} otherwise.
   */
  boolean shouldICollect();

}

