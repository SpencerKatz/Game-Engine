package oogasalad.model.api;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.BadValueParseException;
import oogasalad.model.api.exception.KeyNotFoundException;

/**
 * Provides read-only access to data in properties.
 *
 * @author Jason Qiu
 */
public interface ReadOnlyProperties {

  /**
   * Returns the raw string value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string.
   * @throws KeyNotFoundException if the key does not exist.
   */
  String getString(String key) throws KeyNotFoundException;

  /**
   * Returns the boolean representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the boolean representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into a boolean.
   */
  boolean getBoolean(String key) throws KeyNotFoundException, BadValueParseException;

  /**
   * Returns the double representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the double representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into a double.
   */
  double getDouble(String key) throws KeyNotFoundException, BadValueParseException;

  /**
   * Returns the raw string list value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string list.
   * @throws KeyNotFoundException if the key does not exist.
   */
  List<String> getStringList(String key) throws KeyNotFoundException;

  /**
   * Returns the raw string map value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string map.
   * @throws KeyNotFoundException if the key does not exist.
   */
  Map<String, String> getStringMap(String key) throws KeyNotFoundException;

  /**
   * Returns the parsed string-integer map of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's string-integer map.
   * @throws KeyNotFoundException if the key does not exist.
   */
  Map<String, Integer> getStringIntegerMap(String key) throws KeyNotFoundException;

  /**
   * Returns the integer representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the integer representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into an integer.
   */
  int getInteger(String key) throws KeyNotFoundException, BadValueParseException;

  Map<String, String> getCopyOfProperties();

  Map<String, List<String>> getCopyOfListProperties();

  Map<String, Map<String, String>> getCopyOfMapProperties();

  Map<String, List<String>> getCopyOfPropertyTypes();
}
