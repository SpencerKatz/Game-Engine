package oogasalad.model.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.BadValueParseException;
import oogasalad.model.api.exception.KeyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A general class that acts as a simple JSON store. Helper functions convert values into types.
 *
 * @author Jason Qiu
 */
public class Properties implements ReadOnlyProperties {

  /**
   * Creates and returns an instance of {@link Properties} from a JSON file.
   *
   * @param dataFilePath the path to the JSON file starting from inside the data directory.
   * @return the created instance of {@link Properties}.
   * @throws BadGsonLoadException if the filePath is unable to be parsed into an instance of
   *                              {@link Properties}
   * @throws IOException          if the filePath could not be opened.
   */
  public static Properties of(String dataFilePath) throws BadGsonLoadException, IOException {
    return FACTORY.load(dataFilePath);
  }

  /**
   * Serializes the instance to a JSON file.
   *
   * @param dataFilePath the path to the JSON file with the data directory as the root.
   * @throws IOException if there is an issue writing to the given dataFilePath.
   */
  public void save(String dataFilePath) throws IOException {
    FACTORY.save(dataFilePath, this);
  }

  /**
   * Returns the raw string value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string.
   * @throws KeyNotFoundException if the key does not exist.
   */
  @Override
  public String getString(String key) throws KeyNotFoundException {
    throwIfKeyNotFound(properties, key);
    return properties.get(key);
  }

  /**
   * Returns the boolean representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the boolean representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into a boolean.
   */
  @Override
  public boolean getBoolean(String key) throws KeyNotFoundException, BadValueParseException {
    final String rawValue = getString(key);
    return switch (rawValue.toLowerCase()) {
      case "true" -> true;
      case "false" -> false;
      default -> {
        LOG.error("Couldn't parse value '{}' as a boolean (key = {}).", rawValue, key);
        throw new BadValueParseException(rawValue, Boolean.class.getSimpleName());
      }
    };
  }

  /**
   * Returns the double representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the double representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into a double.
   */
  @Override
  public double getDouble(String key) throws KeyNotFoundException, BadValueParseException {
    final String rawValue = getString(key);
    try {
      return Double.parseDouble(rawValue);
    } catch (NumberFormatException e) {
      LOG.error("Couldn't parse value '{}' as a double (key = {}).", rawValue, key);
      throw new BadValueParseException(rawValue, Double.class.getSimpleName(), e);
    }
  }

  /**
   * Returns the raw string list value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string list.
   * @throws KeyNotFoundException if the key does not exist.
   */
  @Override
  public List<String> getStringList(String key) throws KeyNotFoundException {
    throwIfKeyNotFound(listProperties, key);
    return listProperties.get(key);
  }

  /**
   * Returns the raw string map value of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's raw string map.
   * @throws KeyNotFoundException if the key does not exist.
   */
  @Override
  public Map<String, String> getStringMap(String key) throws KeyNotFoundException {
    throwIfKeyNotFound(mapProperties, key);
    return mapProperties.get(key);
  }

  /**
   * Returns the integer representation of the property's value.
   *
   * @param key the key of the property to access.
   * @return the integer representation of the property's value.
   * @throws KeyNotFoundException   if the key does not exist.
   * @throws BadValueParseException if the string value cannot be parsed into an integer.
   */
  @Override
  public int getInteger(String key) throws KeyNotFoundException, BadValueParseException {
    final String rawValue = getString(key);
    try {
      return Integer.parseInt(rawValue);
    } catch (NumberFormatException e) {
      LOG.error("Couldn't parse value '{}' as an integer (key = {}).", rawValue, key);
      throw new BadValueParseException(rawValue, Integer.class.getSimpleName(), e);
    }
  }

  /**
   * Returns the parsed string-integer map of the property if found.
   *
   * @param key the key of the property to access.
   * @return the property's value's string-integer map.
   * @throws KeyNotFoundException if the key does not exist.
   */
  @Override
  public Map<String, Integer> getStringIntegerMap(String key)
      throws KeyNotFoundException, BadValueParseException {
    final Map<String, String> rawValue = getStringMap(key);
    Map<String, Integer> parsed = new HashMap<>();
    try {
      for (Map.Entry<String, String> entry : rawValue.entrySet()) {
        parsed.put(entry.getKey(), Integer.parseInt(entry.getValue()));
      }
    } catch (NumberFormatException e) {
      LOG.error("Couldn't parse some value in the map '{}' as an integer (key = {}).", rawValue,
          key);
      throw new BadValueParseException(rawValue.toString(), Integer.class.getSimpleName(), e);
    }

    return parsed;
  }

  /**
   * Updates a property only if it already exists.
   *
   * @param key   the queried key.
   * @param value the value to set.
   * @throws KeyNotFoundException if the key does not exist.
   */
  public void update(String key, String value) throws KeyNotFoundException {
    throwIfKeyNotFound(properties, key);
    properties.put(key, value);
  }

  /**
   * Updates a property only if it already exists. For list values.
   *
   * @param key  the queried key.
   * @param list the list to set the key to.
   * @throws KeyNotFoundException if the key does not exist.
   */
  public void update(String key, List<String> list) throws KeyNotFoundException {
    throwIfKeyNotFound(listProperties, key);
    listProperties.put(key, List.copyOf(list));
  }

  /**
   * Updates a property only if it already exists. For map values.
   *
   * @param key the queried key.
   * @param map the map to set the key to.
   * @throws KeyNotFoundException if the key does not exist.
   */
  public void update(String key, Map<String, String> map) throws KeyNotFoundException {
    throwIfKeyNotFound(mapProperties, key);
    mapProperties.put(key, Map.copyOf(map));
  }

  @Override
  public Map<String, String> getCopyOfProperties() {
    return Map.copyOf(properties);
  }

  @Override
  public Map<String, List<String>> getCopyOfListProperties() {
    return Map.copyOf(listProperties);
  }

  @Override
  public Map<String, Map<String, String>> getCopyOfMapProperties() {
    return Map.copyOf(mapProperties);
  }

  @Override
  public Map<String, List<String>> getCopyOfPropertyTypes() {
    return Map.copyOf(propertiesType);
  }


  private final Map<String, String> properties;
  private final Map<String, List<String>> listProperties;
  private final Map<String, List<String>> propertiesType;
  private final Map<String, Map<String, String>> mapProperties;
  private static final DataFactory<Properties> FACTORY = new DataFactory<>(Properties.class);
  private static final Logger LOG = LogManager.getLogger(Properties.class);

  /**
   * Initializes with no entries. Should not be used. If initialized with no entry, its usage is to
   * create a property using the following proerties method
   */
  public Properties() {
    properties = new HashMap<>();
    listProperties = new HashMap<>();
    mapProperties = new HashMap<>();
    propertiesType = new HashMap<>();
  }

  private void throwIfKeyNotFound(Map<String, ?> map, String key) throws KeyNotFoundException {
    if (!map.containsKey(key)) {
      LOG.error("Couldn't find key '{}'.", key);
      throw new KeyNotFoundException(key);
    }
  }

  /**
   * Methods for editing properties.
   *
   * @return
   */
  public Map<String, String> getProperties() {
    return properties;
  }

  public Map<String, List<String>> getListProperties() {
    return listProperties;
  }

  public Map<String, Map<String, String>> getMapProperties() {
    return mapProperties;
  }
}
