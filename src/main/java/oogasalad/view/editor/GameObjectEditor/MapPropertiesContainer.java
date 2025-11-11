package oogasalad.view.editor.GameObjectEditor;

import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Container for map properties.
 */
public class MapPropertiesContainer extends HBox {

  /**
   * Constructs a MapPropertiesContainer.
   *
   * @param mapPropertyName   The name of the map property.
   * @param addMapProperty    The Consumer function to add a map property.
   * @param removeMapProperty The Consumer function to remove a map property.
   */
  public MapPropertiesContainer(String mapPropertyName, Consumer<String> addMapProperty,
      Consumer<String> removeMapProperty) {
    super();
    super.setAlignment(Pos.CENTER);
    setId(mapPropertyName);

    // Create label for map properties name
    Label mapPropertiesName = new Label(mapPropertyName);
    mapPropertiesName.getStyleClass().add("map-properties-name");

    // Create add and remove property buttons
    AddPropertyButton addPropertyButton =
        new AddPropertyButton("Add", addMapProperty, mapPropertyName);
    AddPropertyButton removePropertyButton =
        new AddPropertyButton("Remove", removeMapProperty, mapPropertyName);

    // Add components to the container
    super.getChildren().addAll(mapPropertiesName, addPropertyButton, removePropertyButton);
  }
}
