package oogasalad.model.gameobject.gameobjectfactory;

import java.util.Map;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.gameobject.Collectable;
import oogasalad.model.gameobject.GameObject;

/**
 * A creator class for constructing {@link Collectable} game objects. Implements the
 * {@link GameObjectCreator} interface to provide a method for instantiating {@link Collectable}
 * objects based on provided properties and parameters.
 */
public class CollectableCreator implements GameObjectCreator {

  /**
   * Creates a {@link Collectable} object with the specified properties, game time, and additional
   * parameters. This method fully initializes a Collectable, setting up its state based on the
   * parameters provided.
   *
   * @param id               The id of the Collectable to be created.
   * @param creationTime     The game time at which the Collectable is being created, which can
   *                         influence its initial state or behaviors, such as start times for
   *                         effects or conditions.
   * @param additionalParams A map containing additional parameters required for creating the
   *                         Collectable. Typically, includes information about which items with
   *                         their corresponding quantity are contained within the Collectable.
   * @return A new {@link Collectable} object, initialized and ready for game logic integration.
   */
  @Override
  public GameObject create(String id, ReadOnlyGameTime creationTime,
      Map<String, Integer> additionalParams) {
    return new Collectable(id, creationTime, additionalParams);
  }
}
