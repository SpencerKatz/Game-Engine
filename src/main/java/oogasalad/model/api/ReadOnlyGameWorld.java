package oogasalad.model.api;

import java.util.List;
import oogasalad.model.gameobject.ItemsToAdd;

/**
 * Defines read-only operations that can be performed on the game world within a game. This
 * interface allows querying and interacting with the game world without modifying its underlying
 * state directly.
 */
public interface ReadOnlyGameWorld {

  /**
   * Retrieves a list of image paths that represent the visual state of the game world at the
   * specified location. This can be used for rendering the game world on a UI.
   *
   * @param width  The width coordinate where the image should be fetched.
   * @param height The height coordinate where the image should be fetched.
   * @param depth  The depth coordinate where the image should be fetched.
   * @return A list of strings, each representing a path to an image resource.
   */
  List<String> getImagePath(int width, int height, int depth);

  /**
   * @return A list of ItemsToAdd, each representing items that are to be added to the player's
   * inventory.
   * @deprecated Should not be used by view. Retrieves a list of items that should be added to the
   * inventory as a result of interactions or events in the game world. This method typically
   * follows updates or interactions that result in item generation or discovery.
   */
  List<ItemsToAdd> itemsToAddToInventory();


  /**
   * Get the number of rows
   *
   * @return number of rows
   */
  int getHeight();

  /**
   * Get the number of columns
   *
   * @return number of columns
   */
  int getWidth();

  /**
   * Get the depth of the model
   *
   * @return depth
   */
  int getDepth();
}

