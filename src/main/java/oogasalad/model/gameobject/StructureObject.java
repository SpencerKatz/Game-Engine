package oogasalad.model.gameobject;

import java.util.Map;

/**
 * Provides an interface for game objects that represent structures within the game world.
 * Structures defined by this interface may have specific interactions such as being harvestable or
 * providing items upon destruction.
 *
 * @author Spencer Katz, Jason Qiu
 */
public interface StructureObject {

  /**
   * Retrieves a map of items that this structure yields when it is destroyed. Each entry in the map
   * consists of an item identifier and the quantity of that item.
   *
   * @return A map where each key is an item ID and each value is the quantity of that item to be
   * yielded upon the structure's destruction.
   */

  Map<String, Integer> getItemsOnDestruction();

  /**
   * Returns whether or not the structure is destructable with the given tool.
   *
   * @param id the id of the item.
   * @return true if the structure can be destroyed by the given tool, otherwise false.
   */
  boolean destructableBy(String id);

  /**
   * Determines whether the structure can be harvested. This can involve conditions based on the
   * structure's current state, such as maturity or availability of resources.
   *
   * @return true if the structure is harvestable, false otherwise.
   */
  boolean isHarvestable();

}

