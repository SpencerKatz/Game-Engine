package oogasalad.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.data.Properties;

/**
 * Controller class for managing game objects and their properties.
 */
public class GameObjectController extends PropertyController {

  private final Map<String, Properties> allGameObjects; // Map to store all game objects
  private Properties properties; // Current properties of the selected game object

  /**
   * Constructor for GameObjectController. Initializes the map of all game objects.
   */
  public GameObjectController() {
    super();
    allGameObjects =
        GameConfiguration.getEditableConfigurablesStore().getAllEditableConfigurables();
  }

  /**
   * Sets the properties of the selected game object based on the key.
   *
   * @param key The key identifying the game object.
   */
  public void setKey(String key) {
    for (Map.Entry<String, Properties> entry : allGameObjects.entrySet()) {
      if (entry.getValue().getCopyOfProperties().get("name").equals(key)) {
        properties = allGameObjects.get(entry.getKey());
      }
    }
  }

  @Override
  public Map<String, String> getProperties() {
    try {
      return removeType(properties.getCopyOfProperties());
    } catch (NullPointerException e) {
      return null;
    }
  }

  private Map<String, String> removeType(Map<String, String> copyOfProperties) {
    Map<String, String> noType = new TreeMap<>(copyOfProperties);
    noType.remove("type");
    return noType;
  }

  @Override
  public Map<String, List<String>> getListProperties() {
    return properties.getListProperties();
  }

  @Override
  public Map<String, Map<String, String>> getMapProperties() {
    return properties.getCopyOfMapProperties();
  }

  @Override
  public void updateProperty(String name, String value) {
    properties.update(name, value);
  }

  @Override
  public void updateMapProperty(String name, Map<String, String> newMap) {
    properties.update(name, newMap);
  }

  @Override
  public void updateListProperty(String name, String value) {
    properties.update(name, getList(value));
  }

  /**
   * Creates a new game object with the specified type and name.
   *
   * @param type The type of the game object.
   * @param name The name of the game object.
   */
  public void newObject(String type, String name) {
    properties = GameConfiguration.templateProperties(type);
    properties.update("name", name);
    allGameObjects.put(name, properties);
  }

  /**
   * Removes the specified game object.
   *
   * @param name The name of the game object to be removed.
   */
  public void removeObject(String name) {
    allGameObjects.remove(name);
  }

  /**
   * Saves the photo file to the data/images directory.
   *
   * @param file The photo file to be saved.
   */
  public void savePhoto(File file) {
    try {
      // Define the directory where the file will be saved
      File targetDirectory = new File("data/images");

      // Construct the path to the target file
      Path targetPath = new File(targetDirectory, file.getName()).toPath();

      // Copy the selected file to the target directory
      Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException e) {
      // Display an error message if an exception occurs
      e.printStackTrace();
    }
  }
}
