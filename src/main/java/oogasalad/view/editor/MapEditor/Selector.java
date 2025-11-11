package oogasalad.view.editor.MapEditor;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Selector class for managing the selection of items in the editor.
 */
public class Selector {

  private static final ObjectProperty<VBox> lastSelected = new SimpleObjectProperty<>();

  /**
   * Adds the specified VBox as a selectable item and handles mouse click events.
   *
   * @param selectable The VBox to be added as a selectable item.
   */
  public static void add(VBox selectable) {
    selectable.setOnMouseClicked(event -> {
      if (getLastSelected() != null) {
        getLastSelected().setStyle("-fx-border-color: transparent;");
      }
      lastSelected.set(selectable);
      selectable.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
    });
  }

  /**
   * Retrieves the text of the last selected selectable item.
   *
   * @return The text of the last selected selectable item.
   */
  public static String getLastSelectedSelectable() {
    if (lastSelected.get() == null) {
      return null;
    }
    return ((Label) lastSelected.get().getChildren().get(1)).getText();
  }

  /**
   * Retrieves the last selected VBox.
   *
   * @return The last selected VBox.
   */
  public static VBox getLastSelected() {
    return lastSelected.get();
  }

  /**
   * Retrieves the ObjectProperty representing the last selected VBox.
   *
   * @return The ObjectProperty representing the last selected VBox.
   */
  public static ObjectProperty<VBox> lastSelectedProperty() {
    return lastSelected;
  }
}
