package oogasalad.view.login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/**
 * Utility class for reading and writing JSON files
 */
public class FileUtility {

  private static final String GAME_SAVES_DIRECTORY = "data/gamesaves/";
  private static final String GAME_CONFIGURATIONS_DIRECTORY = "data/gameconfigurations/";
  private static final String CONFIGURABLE_STORES_DIRECTORY = "data/configurablestores/";
  private static final String JSON_EXTENSION = ".json";

  public static void saveJsonToFile(int id, String gameSaveJson, String gameConfigJson,
      String storeJson) throws IOException {
    System.out.println("Saving game with id: " + id);
    writeToFile(GAME_SAVES_DIRECTORY, id + JSON_EXTENSION, gameSaveJson);
    writeToFile(GAME_CONFIGURATIONS_DIRECTORY, id + JSON_EXTENSION, gameConfigJson);
    writeToFile(CONFIGURABLE_STORES_DIRECTORY, id + JSON_EXTENSION, storeJson);
  }

  private static void writeToFile(String directoryPath, String fileName, String jsonContent)
      throws IOException {
    File directory = new File(directoryPath);
    File file = new File(directory, fileName);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(jsonContent);
    }
  }

  public static String[] readFileAsString(int id) throws IOException {
    String[] result = new String[3];
    result[0] =
        new String(Files.readAllBytes(Paths.get(GAME_SAVES_DIRECTORY + id + JSON_EXTENSION)));
    result[1] = new String(
        Files.readAllBytes(Paths.get(GAME_CONFIGURATIONS_DIRECTORY + id + JSON_EXTENSION)));
    result[2] = new String(
        Files.readAllBytes(Paths.get(CONFIGURABLE_STORES_DIRECTORY + id + JSON_EXTENSION)));
    return result;
  }

  public static Node createComponent(JSONObject comp, EventHandlerSetup eventHandlerSetup)
      throws Exception {

    String type = comp.getString("type");
    Class<?> clazz = Class.forName("javafx.scene.control." + type);
    Object instance = clazz.getDeclaredConstructor().newInstance();

    JSONObject props = comp.getJSONObject("properties");
    for (String key : props.keySet()) {
      Object value = props.get(key);
      String property = "set" + Character.toUpperCase(key.charAt(0)) + key.substring(1);

      // Determine the type of the value and call the appropriate setter method
      Method method;
      if (value instanceof Boolean) {
        method = clazz.getMethod(property, boolean.class);
        method.invoke(instance, value);
      } else if (value instanceof Integer) {
        method = clazz.getMethod(property, int.class);
        method.invoke(instance, value);
      } else if (value instanceof String) {
        method = clazz.getMethod(property, String.class);
        method.invoke(instance, value);
      } else {
        throw new IllegalArgumentException("Unsupported property type: " + value.getClass());
      }
    }

    if (comp.has("event")) {
      String event = comp.getString("event");
      eventHandlerSetup.setup(instance, event);
    }

    return (Node) instance;
  }

  public static Scene createScene(String path, EventHandlerSetup eventHandlerSetup)
      throws Exception {
    String content = new String(Files.readAllBytes(Path.of(path)));
    JSONObject json = new JSONObject(content);
    JSONArray components = json.getJSONArray("components");

    VBox vbox = Utils.createVbox();

    for (int i = 0; i < components.length(); i++) {
      JSONObject comp = components.getJSONObject(i);
      Node node = FileUtility.createComponent(comp, eventHandlerSetup);
      vbox.getChildren().add(node);
    }

    return new Scene(vbox);
  }
}
