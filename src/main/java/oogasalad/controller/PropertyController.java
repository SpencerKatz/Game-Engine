package oogasalad.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for managing properties of game objects.
 */
public abstract class PropertyController {

  /**
   * Constructs a PropertyController.
   */
  public PropertyController() {
  }

  /**
   * Retrieves the properties of a game object.
   *
   * @return A map containing the properties of the game object.
   */
  public abstract Map<String, String> getProperties();

  /**
   * Retrieves list properties of a game object.
   *
   * @return A map containing list properties of the game object.
   */
  public abstract Map<String, List<String>> getListProperties();

  /**
   * Retrieves map properties of a game object.
   *
   * @return A map containing map properties of the game object.
   */
  public abstract Map<String, Map<String, String>> getMapProperties();

  /**
   * Updates a property of the game object.
   *
   * @param name  The name of the property to be updated.
   * @param value The new value of the property.
   */
  public abstract void updateProperty(String name, String value);

  /**
   * Updates a map property of the game object.
   *
   * @param name   The name of the map property to be updated.
   * @param newMap The new map of the property.
   */
  public abstract void updateMapProperty(String name, Map<String, String> newMap);

  /**
   * Updates a list property of the game object.
   *
   * @param name  The name of the list property to be updated.
   * @param value The new value to be added to the list property.
   */
  public abstract void updateListProperty(String name, String value);

  /**
   * Converts a string representation of a list into an actual list.
   *
   * @param value The string representation of the list.
   * @return The list converted from the string representation.
   */
  protected List<String> getList(String value) {
    String[] elements = value.substring(1, value.length() - 1).split(", ");
    return new ArrayList<>(Arrays.asList(elements));
  }
}

