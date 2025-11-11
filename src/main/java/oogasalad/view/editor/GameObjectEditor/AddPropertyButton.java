package oogasalad.view.editor.GameObjectEditor;

import java.util.function.Consumer;
import javafx.scene.control.Button;

/**
 * Button for adding a property.
 */
public class AddPropertyButton extends Button {

  /**
   * Constructs an AddPropertyButton.
   *
   * @param type              The type of the button.
   * @param changeMapProperty The Consumer function to handle the property change.
   * @param mapPropertyName   The name of the map property.
   */
  public AddPropertyButton(String type, Consumer<String> changeMapProperty,
      String mapPropertyName) {
    super(type);
    setId(type + mapPropertyName);

    // Set action event for the button to handle property change
    super.setOnAction(e -> changeMapProperty.accept(mapPropertyName));
  }
}
