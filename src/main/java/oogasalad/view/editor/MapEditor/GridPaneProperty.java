package oogasalad.view.editor.MapEditor;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.GridPane;

/**
 * A custom property class for a GridPane object.
 */
public class GridPaneProperty extends SimpleObjectProperty<GridPane> {

  /**
   * Constructs a GridPaneProperty with the specified initial value.
   *
   * @param initialValue The initial value of the GridPaneProperty.
   */
  public GridPaneProperty(GridPane initialValue) {
    super(initialValue);
  }
}
