package oogasalad.view.editor.GameObjectEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import oogasalad.controller.PropertyController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Display for object properties.
 */
public class PropertiesDisplay extends VBox {

  private static final Logger LOG = LogManager.getLogger(GameObjectPropertiesDisplay.class);
  private final PropertyController goc;

  private final List<ObjectPropertyDisplay> objectPropertyDisplays;
  private final Runnable update;
  private final List<ObjectPropertyDisplay> objectPropertyListDisplays;
  private final Map<String, List<ObjectPropertyDisplay>> objectPropertyMapDisplays;

  /**
   * Constructs a PropertiesDisplay with specified update Runnable and PropertyController.
   *
   * @param update The Runnable to update.
   * @param pc     The PropertyController managing properties.
   */
  public PropertiesDisplay(Runnable update, PropertyController pc) {
    super();
    this.update = update;
    this.objectPropertyDisplays = new ArrayList<>();
    this.objectPropertyMapDisplays = new TreeMap<>();
    this.objectPropertyListDisplays = new ArrayList<>();
    super.setAlignment(Pos.CENTER);
    goc = pc;
  }

  /**
   * Sets the contents of the display based on the selected key.
   *
   * @param key The key of the selected object.
   */
  public void setContents(String key) {
    super.getChildren().clear();
    objectPropertyDisplays.clear();
    objectPropertyMapDisplays.clear();
    Label name = new Label(key);
    name.getStyleClass().add("object-name");
    super.getChildren().add(name);

    // Display properties
    if (goc.getProperties() != null) {
      displayProperties(goc.getProperties());
      displayListProperties(goc.getListProperties());
      displayMapProperties(goc.getMapProperties());
      super.getChildren().add(new SaveButton("save", e -> save(), update, key));
    }
  }

  /**
   * Displays properties.
   *
   * @param properties The properties to display.
   */
  private void displayProperties(Map<String, String> properties) {
    properties.forEach((propertyName, propertyValue) -> {
      objectPropertyDisplays.add(
          new ObjectPropertyDisplay(propertyName, propertyValue, super.getChildren()));
    });
  }

  /**
   * Displays list properties.
   *
   * @param listProperties The list properties to display.
   */
  private void displayListProperties(Map<String, List<String>> listProperties) {
    listProperties.forEach((propertyName, propertyValues) -> {
      objectPropertyListDisplays.add(
          new ObjectPropertyDisplay(propertyName, propertyValues.toString(), super.getChildren()));
    });
  }

  /**
   * Displays map properties.
   *
   * @param mapProperties The map properties to display.
   */
  private void displayMapProperties(Map<String, Map<String, String>> mapProperties) {
    mapProperties.forEach((mapPropertyName, mapPropertyValues) -> {
      super.getChildren().add(new MapPropertiesContainer(mapPropertyName, this::addMapProperty,
          this::removeMapProperty));
      List<ObjectPropertyDisplay> listOfOPDS = new ArrayList<>();
      mapPropertyValues.forEach((propertyName, propertyValue) -> {
        listOfOPDS.add(new ObjectPropertyDisplay(propertyName, propertyValue, super.getChildren()));
      });
      objectPropertyMapDisplays.put(mapPropertyName, listOfOPDS);
    });
  }

  /**
   * Saves the properties.
   */
  public void save() {
    for (ObjectPropertyDisplay opd : objectPropertyDisplays) {
      goc.updateProperty(opd.getName(), opd.getValue());
    }
    for (Map.Entry<String, List<ObjectPropertyDisplay>> entry :
        objectPropertyMapDisplays.entrySet()) {
      goc.updateMapProperty(entry.getKey(), createMap(entry.getValue()));
    }
    for (ObjectPropertyDisplay opd : objectPropertyListDisplays) {
      goc.updateListProperty(opd.getName(), opd.getValue());
    }
  }

  /**
   * Creates a map from a list of object property displays.
   *
   * @param value The list of object property displays.
   * @return The created map.
   */
  private Map<String, String> createMap(List<ObjectPropertyDisplay> value) {
    Map<String, String> mapOPD = new TreeMap<>();
    for (ObjectPropertyDisplay opd : value) {
      mapOPD.put(opd.getName(), opd.getValue());
    }
    return mapOPD;
  }

  /**
   * Adds a map property.
   *
   * @param key The key of the map property.
   */
  private void addMapProperty(String key) {
    AddNewMapPropertyDialogBox popup = new AddNewMapPropertyDialogBox();
    String[] newFieldAndValue = popup.getFields();
    if (newFieldAndValue != null) {
      objectPropertyMapDisplays.get(key).add(
          new ObjectPropertyDisplay(newFieldAndValue[0], newFieldAndValue[1], getChildren(),
              findIndex(key) + 1));
    }
  }

  /**
   * Removes a map property.
   *
   * @param key The key of the map property.
   */
  private void removeMapProperty(String key) {
    getChildren().remove(findIndex(key));
    objectPropertyMapDisplays.get(key).remove(objectPropertyMapDisplays.get(key).size() - 1);
  }

  /**
   * Finds the index of a property.
   *
   * @param loc The location of the property.
   * @return The index of the property.
   */
  private int findIndex(String loc) {
    int counter = 0;
    for (Node n : getChildren()) {
      if (n.getId() != null && n.getId().equals(loc)) {
        return counter + objectPropertyMapDisplays.get(loc).size();
      }
      counter++;
    }
    return -1;
  }
}
