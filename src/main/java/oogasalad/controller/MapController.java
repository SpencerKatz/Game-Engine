package oogasalad.controller;

import java.util.List;
import oogasalad.model.api.exception.UnableToSetGameObject;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.gameplay.BuildableTileMap;

/**
 * Controller class for managing the game map and its contents.
 */
public class MapController {

  private final BuildableTileMap btm; // The buildable tile map instance

  /**
   * Constructs a MapController with a given GameConfiguration.
   *
   * @param gc The GameConfiguration from which to initialize the buildable tile map.
   */
  public MapController(GameConfiguration gc) {
    btm = gc.getEditableInitialState().getEditableMap();
  }

  /**
   * Sets a GameObject at the specified coordinates within the game world using the object's ID.
   *
   * @param id The id of the object to set.
   * @param x  The x-coordinate of the tile.
   * @param y  The y-coordinate of the tile.
   * @param z  The z-coordinate of the tile.
   * @throws UnableToSetGameObject If there is an error setting the game object.
   */
  public void setTileGameObject(String id, int x, int y, int z) {
    btm.setTileGameObject(id, x, y, z);
  }

  /**
   * Shifts all tiles to the right and adds a new column to the left.
   */
  public void shiftRightAndAddColumn() {
    btm.alterSizeTL(1, 0);
  }

  /**
   * Shifts all tiles to the left and removes the rightmost column.
   */
  public void shiftLeftAndRemoveColumn() {
    btm.alterSizeTL(-1, 0);
  }

  /**
   * Shifts all tiles up and removes the bottom row.
   */
  public void shiftUpAndRemoveRow() {
    btm.alterSizeTL(0, -1);
  }

  /**
   * Shifts all tiles down and adds a new row at the bottom.
   */
  public void shiftDownAndAddRow() {
    btm.alterSizeTL(0, 1);
  }

  /**
   * Retrieves the identifiers of all game objects contained within a specified tile. This can
   * include identifiers for any collectable, structure, or land elements present at the specified
   * coordinates.
   *
   * @param column The x-coordinate (column) of the tile within the game world grid.
   * @param row    The y-coordinate (row) of the tile within the game world grid.
   * @param depth  The z-coordinate (depth) of the tile within the game world grid.
   * @return A list of strings containing the identifiers of the game objects on the tile.
   */
  public List<String> getTileContents(int column, int row, int depth) {
    return btm.getTileContents(column, row, depth);
  }

  /**
   * Removes the topmost content from a specified tile, prioritizing the removal based on the type
   * of game objects present. This method will remove the topmost object (e.g., a collectable before
   * a structure before the land).
   *
   * @param column The x-coordinate (column) of the tile where the removal will occur.
   * @param row    The y-coordinate (row) of the tile where the removal will occur.
   * @param depth  The z-coordinate (depth) of the tile where the removal will occur.
   */
  public void removeTileTop(int column, int row, int depth) {
    btm.removeTileTop(column, row, depth);
  }

  /**
   * Alters the size of the game world by shifting tile coordinates and resizing the game world
   * dimensions. This method directly manipulates the tile map to achieve the desired effect.
   *
   * @param widthChange  Change in width (number of columns added or removed).
   * @param heightChange Change in height (number of rows added or removed).
   */
  public void alterSizeTL(int widthChange, int heightChange) {
    btm.alterSizeTL(widthChange, heightChange);
  }

  /**
   * Sets the height of the game world and reinitializes the grid.
   *
   * @param height The new height of the game world.
   */
  public void setHeight(int height) {
    btm.setHeight(height);
  }

  /**
   * Sets the width of the game world and reinitializes the grid.
   *
   * @param width The new width of the game world.
   */
  public void setWidth(int width) {
    btm.setWidth(width);
  }

  /**
   * Sets the depth of the game world and reinitializes the grid.
   *
   * @param depth The new depth of the game world.
   */
  public void setDepth(int depth) {
    btm.setDepth(depth);
  }

  /**
   * Retrieves the paths to the images representing the current state of a tile's contents denoted
   * by its width, height, and depth, which can include collectables, structures, and land. This is
   * useful for graphical representation of the tile in the game's user interface.
   *
   * @return A list containing the image paths for the collectable, structure, and land on this
   * tile, if available. The list may be empty if none of the components have an associated image.
   */
  public List<String> getImagePath(int width, int height, int depth) {
    return btm.getImagePath(width, height, depth);
  }

  /**
   * Returns the height of the game world.
   *
   * @return the height of the game world grid as an integer.
   */
  public int getHeight() {
    return btm.getHeight();
  }

  /**
   * Returns the width of the game world.
   *
   * @return the width of the game world grid as an integer.
   */
  public int getWidth() {
    return btm.getWidth();
  }
}
