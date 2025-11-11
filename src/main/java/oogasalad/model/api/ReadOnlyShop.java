package oogasalad.model.api;

import java.util.Map;

/**
 * Provides methods to get information about the current shop and items being sold.
 *
 * @author Jason Qiu
 */
public interface ReadOnlyShop {

  /**
   * Returns a map of items being sold, with their values being their prices.
   *
   * @return a map of items being sold, with their values being their prices.
   */
  Map<ReadOnlyItem, Double> getItems();
}
