package oogasalad.model.gameobject.gameobjectfactory;

import java.util.Map;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.gameobject.GameObject;
import oogasalad.model.gameobject.Structure;


/**
 * A creator class for constructing {@link Structure} game objects. Implements the
 * {@link GameObjectCreator} interface to provide a method for instantiating {@link Structure}
 * objects based on provided properties and parameters.
 */
public class StructureCreator implements GameObjectCreator {

  /**
   * Creates a {@link Structure} object with the specified properties and game time. This method
   * initializes a Structure, setting up its state based on the properties provided.
   *
   * @param id               The id of the gameObject to be created.
   * @param creationTime     The game time at which the Structure is being created, which can
   *                         influence its initial conditions or how it interacts with the game
   *                         environment.
   * @param additionalParams A map containing additional parameters required for creating the
   *                         Structure. This implementation of Structure does not utilize
   *                         additionalParams, but the parameter is included to maintain interface
   *                         consistency.
   * @return A new {@link Structure} object, initialized and ready for game logic integration.
   */
  @Override
  public GameObject create(String id, ReadOnlyGameTime creationTime,
      Map<String, Integer> additionalParams) {
    return new Structure(id, creationTime);
  }
}

