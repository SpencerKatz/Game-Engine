package oogasalad.model.api;

import java.util.Map;
import oogasalad.model.api.exception.KeyNotFoundException;

/**
 * Provides read-only access to user-created configurables.
 *
 * @author Jason Qiu
 */
public interface ReadOnlyGameConfigurablesStore {

  /**
   * Gets the read-only version of a configurable (GameObject, Item, etc.) that exists in this game
   * configuration.
   *
   * @param id the id/map of the configurable.
   * @return the ReadOnlyProperties of the configurable.
   * @throws KeyNotFoundException if the id does not exist.
   */
  ReadOnlyProperties getConfigurableProperties(String id) throws KeyNotFoundException;

  /**
   * Returns a raw unmodifiable map of the configurables.
   *
   * @return a raw unmodifiable map of the configurables.
   */
  Map<String, ReadOnlyProperties> getAllConfigurables();
}
