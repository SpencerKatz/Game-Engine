package oogasalad.model.api;

import java.io.IOException;

/**
 * Provides read-only access to data in a game configuration.
 *
 * @author Jason Qiu
 */
public interface ReadOnlyGameConfiguration {

  /**
   * Serializes the instance to a JSON file.
   * <p>
   * Also saves the configurables store of the same name.
   *
   * @param dataFilePath the path to the JSON file with the data directory as the root.
   * @throws IOException if there is an issue writing to the given dataFilePath.
   */
  void save(String dataFilePath) throws IOException;

  /**
   * Get a read-only instance of rules.
   *
   * @return a read-only instance of rules.
   */
  ReadOnlyProperties getRules();

  /**
   * Get a read-only instance of the initial state.
   *
   * @return a read-only instance of the initial state.
   */
  ReadOnlyGameState getInitialState();

}
