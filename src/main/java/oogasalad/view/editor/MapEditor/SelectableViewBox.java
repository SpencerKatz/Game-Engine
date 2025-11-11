package oogasalad.view.editor.MapEditor;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a box containing selectable views.
 */
public class SelectableViewBox extends HBox {

  /**
   * Constructs a SelectableViewBox with the specified list of selectable views.
   *
   * @param selectables The list of selectable views to be added to the box.
   */
  public SelectableViewBox(List<SelectableView> selectables) {
    super();
    selectables.stream().map(node -> {
      VBox vbox = new VBox(node, node.getLabel());
      vbox.setAlignment(Pos.CENTER);
      vbox.setId(node.getLabel().getText() + "Selectable");
      Selector.add(vbox);
      return vbox;
    }).forEach(super.getChildren()::add);
    super.setSpacing(8);
  }
}
