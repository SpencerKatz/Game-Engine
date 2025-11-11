package oogasalad.model.gameobject;

/**
 * Represents an entity that can be visually represented within the game. The Viewable interface
 * provides a mechanism for retrieving the path to an image file associated with the entity,
 * enabling graphical representation in the game's user interface.
 */
public interface Viewable {

  /**
   * Retrieves the path to the image file associated with this entity. This path can be used to load
   * and display the image in the game's user interface.
   *
   * @return A {@code String} that represents the path to the image file.
   */
  String getImagePath();

}

