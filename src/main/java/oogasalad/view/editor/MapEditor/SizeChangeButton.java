package oogasalad.view.editor.MapEditor;

import java.util.function.BiConsumer;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

/**
 * Button used for changing the size of a component in the editor.
 */
public class SizeChangeButton extends Button {

  /**
   * Constructs a SizeChangeButton with the specified name and action to be performed on click.
   *
   * @param name   The name or label of the button.
   * @param action The action to be performed when the button is clicked, accepting two integer
   *               parameters.
   */
  public SizeChangeButton(String name, BiConsumer<Integer, Integer> action) {
    super(name);
    setId("SizeChangeButton");
    super.setTextAlignment(TextAlignment.CENTER);
    super.setPrefWidth(125);
    setOnAction(event -> action.accept(null, null)); // Pass null as placeholders for parameters
  }
}
