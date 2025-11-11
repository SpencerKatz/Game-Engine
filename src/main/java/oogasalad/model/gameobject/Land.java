package oogasalad.model.gameobject;

import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.api.ReadOnlyItem;

/**
 * Represents a piece of land within the game world, extending the {@link GameObject} class. This
 * class encapsulates land-specific behaviors and properties, such as the ability to support
 * planting operations. By implementing the {@link Plantable} interface, this class indicates that
 * certain objects, depending on their properties and environmental conditions, can be planted on
 * this land. Land-specific properties might include soil type, fertility, and other factors
 * affecting plantability.
 *
 * @author Spencer Katz, Jason Qiu
 */
public class Land extends GameObject implements Plantable {

  /**
   * Constructs a new piece of Land with specified properties and the creation time. The properties
   * determine the land's behavior within the game, such as its suitability for planting and other
   * interactions.
   *
   * @param id           The id of the GameObject.
   * @param creationTime The game time at which this object was created, used to track age or other
   *                     time-sensitive characteristics.
   */
  public Land(String id, ReadOnlyGameTime creationTime) {
    super(id, creationTime);
  }

  /**
   * Determines if a specific item can be placed or planted on this piece of land. This method
   * assesses the item's characteristics against the land's properties to decide if the planting
   * action is valid.
   *
   * @param item The item proposed to be planted on this land.
   * @return {@code true} if the item can be successfully planted; {@code false} otherwise,
   * indicating that the conditions are not suitable for planting.
   */
  @Override
  public boolean getIfItemCanBePlacedHere(ReadOnlyItem item) {
    return getProperties().getStringMap("plantableSeeds").containsKey(item.getName());
  }

  /**
   * Retrieves the type of structure or growth that results from planting a specific item on this
   * land. The output depends on both the item's properties and the land's characteristics.
   *
   * @param item The item used, influencing the type of structure or growth resulting.
   * @return A string identifier of the structure or growth, or {@code null} if no structure results
   * from the item.
   */
  @Override
  public String getStructureBasedOnItem(ReadOnlyItem item) {
    return getProperties().getStringMap("plantableSeeds").get(item.getName());
  }

  /**
   * Determines if the interaction with a specified item is valid.
   *
   * @param item The item in question.
   * @return true if the interaction is valid, false otherwise.
   */
  @Override
  public boolean interactionValid(ReadOnlyItem item) {
    return super.interactionValid(item) || getIfItemCanBePlacedHere(item);
  }
}

