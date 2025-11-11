package oogasalad.model.data;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import oogasalad.model.api.ReadOnlyGameConfigurablesStore;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.KeyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A store for all configurable (user-created) game objects in a particular game configuration.
 *
 * @author Jason Qiu
 */
public class GameConfigurablesStore implements ReadOnlyGameConfigurablesStore {

  // TODO: Externalize this to a configuration file.
  // The path to the game configurations directory from the data directory.
  public static final String GAMECONFIGURABLESSTORE_DIRECTORY_PATH = "configurablestores";

  /**
   * Creates and returns an instance of {@link GameConfiguration} from a JSON file.
   *
   * @param dataFilePath the path to the JSON file.
   * @return the created instance of {@link GameConfiguration}.
   * @throws BadGsonLoadException if the filePath is unable to be parsed into an instance of
   *                              {@link GameConfiguration}
   * @throws IOException          if the filePath could not be opened.
   */
  public static GameConfigurablesStore of(String dataFilePath)
      throws BadGsonLoadException, IOException {
    return CONFIGURABLES_DATA_FACTORY.load(
        Paths.get(GAMECONFIGURABLESSTORE_DIRECTORY_PATH, dataFilePath).toString());
  }

  /**
   * Serializes the instance to a JSON file.
   *
   * @param dataFilePath the path to the JSON file with the data directory as the root.
   * @throws IOException if there is an issue writing to the given dataFilePath.
   */
  public void save(String dataFilePath) throws IOException {
    CONFIGURABLES_DATA_FACTORY.save(
        Paths.get(GAMECONFIGURABLESSTORE_DIRECTORY_PATH, dataFilePath).toString(), this);
  }

  public GameConfigurablesStore() {
    configurables = new HashMap<>();
  }

  /**
   * Gets the read-only version of a configurable (GameObject, Item, etc.) that exists in this game
   * configuration.
   *
   * @param id the id of the configurable.
   * @return the ReadOnlyProperties of the configurable.
   * @throws KeyNotFoundException if the id does not exist.
   */
  @Override
  public ReadOnlyProperties getConfigurableProperties(String id) throws KeyNotFoundException {
    if (!configurables.containsKey(id)) {
      LOG.error("Could not find properties for id '{}'.", id);
      throw new KeyNotFoundException(id);
    }
    return configurables.get(id);
  }

  /**
   * Returns a raw unmodifiable map of the configurables.
   *
   * @return a raw unmodifiable map of the configurables.
   */
  @Override
  public Map<String, ReadOnlyProperties> getAllConfigurables() {
    return Collections.unmodifiableMap(configurables);
  }

  /**
   * Returns a reference to the internal map of configurables to be modified.
   *
   * @return a reference to the internal map of configurables to be modified.
   */
  public Map<String, Properties> getAllEditableConfigurables() {
    return configurables;
  }

  private final Map<String, Properties> configurables;


  private static final DataFactory<GameConfigurablesStore> CONFIGURABLES_DATA_FACTORY =
      new DataFactory<>(GameConfigurablesStore.class);
  private static final Logger LOG = LogManager.getLogger(GameConfigurablesStore.class);
}
