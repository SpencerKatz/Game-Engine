package oogasalad.model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.api.ReadOnlyGameWorld;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.gameobject.CoordinateOfGameObjectRecord;
import oogasalad.model.gameobject.ItemsToAdd;
import oogasalad.model.gameobject.Tile;
import oogasalad.model.gameobject.Updatable;

/**
 * Represents the game world containing a grid of tiles, each potentially holding different game
 * objects. The game world is defined by its dimensions (height, width, depth) and is responsible
 * for initializing tiles, handling interactions, and updating game states based on game time or
 * interactions.
 */
public class GameWorld implements ReadOnlyGameWorld, Updatable {

  private Map<CoordinateOfGameObjectRecord, Tile> allTiles;
  private int height;
  private int width;
  private int depth;


  /**
   * Constructs a new GameWorld with specified dimensions.
   *
   * @param height the height of the game world grid.
   * @param width  the width of the game world grid.
   * @param depth  the depth of the game world grid, used for 3D positioning.
   */
  public GameWorld(int height, int width, int depth) {
    this.height = height;
    this.width = width;
    this.depth = depth;
    allTiles = new HashMap<>();
    initialize();
  }

  /**
   * Initializes the game world by creating tiles at each coordinate of the grid based on the
   * specified dimensions.
   */
  protected void initialize() {
    Map<CoordinateOfGameObjectRecord, Tile> newTiles = new HashMap<>();
    for (int z = 0; z < depth; z++) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          CoordinateOfGameObjectRecord coord = new CoordinateOfGameObjectRecord(x, y, z);
          Tile tile = allTiles.getOrDefault(coord, new Tile());
          newTiles.putIfAbsent(coord, tile);
        }
      }
    }
    allTiles = newTiles;
  }

  /**
   * Updates the state of each tile in the game world based on the current game time.
   *
   * @param gameTime The current game time context.
   */
  @Override
  public void update(ReadOnlyGameTime gameTime) {
    for (Map.Entry<CoordinateOfGameObjectRecord, Tile> entry : allTiles.entrySet()) {
      entry.getValue().update(gameTime);
    }
  }

  /**
   * Handles interaction with a specific tile at the given coordinates using an item.
   *
   * @param item   The item used for interaction.
   * @param width  The width coordinate of the interaction.
   * @param height The height coordinate of the interaction.
   * @param depth  The depth coordinate of the interaction.
   * @return A boolean representing whether a valid interaction took place.
   */
  public boolean interact(ReadOnlyItem item, int width, int height, int depth) {
    Tile interactingTile = allTiles.get(new CoordinateOfGameObjectRecord(width, height, depth));
    if (interactingTile.interactionValid(item)) {
      allTiles.get(new CoordinateOfGameObjectRecord(width, height, depth)).interact(item);
      return true;
    }
    return false;
  }

  /**
   * Retrieves the paths to the images representing the current state of a tile's contents denoted
   * by its width, height, and depth, which can include collectables, structures, and land. This is
   * useful for graphical representation of the tile in the game's user interface.
   *
   * @return A list containing the image paths for the collectable, structure, and land on this
   * tile, if available. The list may be empty if none of the components have an associated image.
   */
  @Override
  public List<String> getImagePath(int width, int height, int depth) {
    return allTiles.get(new CoordinateOfGameObjectRecord(width, height, depth)).getImages();
  }

  /**
   * Retrieves a list of all items from each tile that need to be added to the inventory. This
   * method processes each tile, extracts items to be added, and returns them as a list of
   * ItemsToAdd.
   *
   * @return A List of ItemsToAdd, each object representing a set of items to be added to the
   * inventory, characterized by their ID and quantity.
   */
  @Override
  public List<ItemsToAdd> itemsToAddToInventory() {
    List<ItemsToAdd> itemsToAddList = new ArrayList<>();
    for (Entry<CoordinateOfGameObjectRecord, Tile> entry : allTiles.entrySet()) {
      Map<String, Integer> tileItems = entry.getValue().itemReturns();
      if (tileItems != null && !tileItems.isEmpty()) {
        tileItems.forEach((id, quantity) -> {
          if (quantity > 0) {
            itemsToAddList.add(new ItemsToAdd(quantity, id));
          }
        });
      }
    }
    return itemsToAddList;
  }

  /**
   * Sets the height of the game world and reinitializes the grid.
   *
   * @param height The new height of the game world.
   */
  public void setHeight(int height) {
    this.height = height;
    initialize();
  }

  /**
   * Sets the width of the game world and reinitializes the grid.
   *
   * @param width The new width of the game world.
   */
  public void setWidth(int width) {
    this.width = width;
    initialize();
  }

  /**
   * Sets the depth of the game world and reinitializes the grid.
   *
   * @param depth The new depth of the game world.
   */
  public void setDepth(int depth) {
    this.depth = depth;
    initialize();
  }

  /**
   * Returns the height of the game world.
   *
   * @return the height of the game world grid as an integer.
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Returns the width of the game world.
   *
   * @return the width of the game world grid as an integer.
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Returns the depth of the game world, which is relevant for 3D positioning.
   *
   * @return the depth of the game world grid as an integer.
   */
  @Override
  public int getDepth() {
    return depth;
  }

  /**
   * Retrieves a map of all tiles in the game world, indexed by their coordinates. This is a
   * protected method intended for internal use.
   *
   * @return a map linking {@link CoordinateOfGameObjectRecord} objects to {@link Tile} objects.
   */
  protected Map<CoordinateOfGameObjectRecord, Tile> getAllTiles() {
    return allTiles;
  }

  /**
   * Sets the map of all tiles in the game world. This method replaces the current map of tiles with
   * the provided map. This is a protected method intended for internal use, particularly useful
   * during reinitializations or updates where a completely new set of tiles needs to be
   * established.
   *
   * @param temp The new map of {@link CoordinateOfGameObjectRecord} to {@link Tile} to be set as
   *             all tiles.
   */
  protected void setAllTiles(Map<CoordinateOfGameObjectRecord, Tile> temp) {
    allTiles = temp;
  }
}



