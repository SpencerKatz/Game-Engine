package oogasalad.model.gameplay;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.UnableToSetGameObject;
import oogasalad.model.data.DataFactory;
import oogasalad.model.gameobject.CoordinateOfGameObjectRecord;
import oogasalad.model.gameobject.GameObject;
import oogasalad.model.gameobject.Tile;
import oogasalad.model.gameobject.gameobjectfactory.GameObjectFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Extends the GameWorld to include buildable and manipulable tile features, allowing dynamic
 * changes to the game map such as adding, removing, and modifying tiles at runtime.
 */
public class BuildableTileMap extends GameWorld {

  private static final GameObjectFactory GAME_OBJECT_FACTORY = new GameObjectFactory();

  private static final String TEMPORARY_DIRECTORY_PATH = System.getProperty("java.io.tmpdir");
  private static final DataFactory<GameWorld> GAME_WORLD_FACTORY =
      new DataFactory<>(GameWorld.class);


  private static final DataFactory<BuildableTileMap> FACTORY =
      new DataFactory<>(BuildableTileMap.class);
  private static final Logger LOG = LogManager.getLogger(BuildableTileMap.class);

  /**
   * Create a copy of a GameWorld into a BuildableTileMap, which holds the same data.
   *
   * @param original the original GameWorld.
   * @return the copy of the GameWorld as a BuildableTileMap
   */
  public static BuildableTileMap copyOf(GameWorld original) {
    // It's going to be a really big task to implement deep-copying for everything in GameState,
    // so we can just abuse Gson to save and load a copy instead.
    String copyPath = Paths.get(TEMPORARY_DIRECTORY_PATH, "GAMESTATE_COPY").toString();
    try {
      GAME_WORLD_FACTORY.save(copyPath, original);
    } catch (IOException e) {
      LOG.error("Error saving a copy of a gameworld to '" + copyPath + ".json'");
      throw new RuntimeException(e);
    }

    BuildableTileMap copy;
    try {
      copy = FACTORY.load(copyPath);
    } catch (IOException | BadGsonLoadException e) {
      LOG.error("Error loading a copy of the initial state in the game config to from '" + copyPath
          + ".json'");
      throw new RuntimeException(e);
    }
    return copy;
  }

  /**
   * Constructs a BuildableTileMap with specified dimensions.
   *
   * @param height the height of the game world grid.
   * @param width  the width of the game world grid.
   * @param depth  the depth of the game world grid, used for 3D positioning.
   */
  public BuildableTileMap(int height, int width, int depth) {
    super(height, width, depth);
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
    CoordinateOfGameObjectRecord coord = new CoordinateOfGameObjectRecord(x, y, z);
    Tile tile = getAllTiles().get(coord);
    GameObject gameObject =
        GAME_OBJECT_FACTORY.createNewGameObject(id, new GameTime(0, 0, 0), new HashMap<>());
    reflectTileCreation(tile, gameObject);
  }

  /**
   * Reflects the creation of a tile with a game object using reflection to invoke the appropriate
   * setter method based on the game object's class type.
   *
   * @param tile       The tile where the game object is to be set.
   * @param gameObject The game object to set on the tile.
   * @throws UnableToSetGameObject If reflection fails to find or invoke the setter method.
   */
  private void reflectTileCreation(Tile tile, GameObject gameObject) {
    if (tile != null) {
      Class<?> gameObjectClass = gameObject.getClass();
      String methodName = "set" + gameObjectClass.getSimpleName();
      try {
        Method setMethod = Tile.class.getMethod(methodName, gameObjectClass);
        setMethod.invoke(tile, gameObject);
      } catch (Exception e) {
        throw new UnableToSetGameObject("Error Setting GameObject");
      }
    }
  }

  /**
   * Alters the size of the game world by shifting tile coordinates and resizing the game world
   * dimensions. This method directly manipulates the tile map to achieve the desired effect.
   *
   * @param widthChange  Change in width (number of columns added or removed).
   * @param heightChange Change in height (number of rows added or removed).
   */
  public void alterSizeTL(int widthChange, int heightChange) {
    Map<CoordinateOfGameObjectRecord, Tile> temp = new HashMap<>();
    for (Map.Entry<CoordinateOfGameObjectRecord, Tile> entry : getAllTiles().entrySet()) {
      temp.put(new CoordinateOfGameObjectRecord(entry.getKey().x() + widthChange,
          entry.getKey().y() + heightChange, entry.getKey().z()), entry.getValue());
    }
    setWidth(getWidth() + widthChange);
    setHeight(getHeight() + heightChange);
    setAllTiles(temp);
    initialize();
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
    return getAllTiles().get(new CoordinateOfGameObjectRecord(column, row, depth)).getIds();
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
    getAllTiles().get(new CoordinateOfGameObjectRecord(column, row, depth)).removeTopContents();
  }
}
