package oogasalad.model.gameobject;

import oogasalad.model.api.ReadOnlyItem;

/**
 * Represents entities within the game that are capable of supporting planting operations.
 * Implementing this interface indicates that the game object can be interacted with in a manner
 * that involves planting, such as seeds or other growable entities. This could apply to various
 * types of terrain, objects, or custom game elements designed to support planting behavior.
 */
public interface Plantable {

  /**
   * Determines if the entity is currently in a state that allows planting operations to be
   * performed with a specific item. This method checks if a planting action can be performed on the
   * entity based on its current state, the properties of the item, or environmental conditions.
   *
   * @param item The item intended to be planted or placed on this entity.
   * @return {@code true} if the entity can accept the item for planting; {@code false} otherwise.
   */
  boolean getIfItemCanBePlacedHere(ReadOnlyItem item);

  /**
   * Retrieves the identifier or name of the structure that will be based on the given item when
   * planted or placed on this entity. This method should return a string that represents the
   * specific type of structure or object that results from using the given item on the plantable
   * entity.
   *
   * @param item The item that influences the type of structure to be created.
   * @return A string representing the type of structure created based on the item, or {@code null}
   * if the item does not result in a structure.
   */
  String getStructureBasedOnItem(ReadOnlyItem item);

}
