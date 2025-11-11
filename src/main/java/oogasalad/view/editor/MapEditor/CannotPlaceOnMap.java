package oogasalad.view.editor.MapEditor;

import javafx.scene.control.Alert;

/**
 * Alert dialog indicating that a certain type cannot be placed on the map.
 */
public class CannotPlaceOnMap {

  /**
   * Constructs a CannotPlaceOnMap alert dialog.
   *
   * @param type The type that cannot be placed on the map.
   */
  public CannotPlaceOnMap(String type) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Cannot Place On Map");
    alert.setHeaderText(null);
    alert.setContentText("Cannot Place Type " + type + " On Map");
    alert.showAndWait();
  }
}
