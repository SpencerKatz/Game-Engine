package oogasalad.view.editor.MapEditor;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

/**
 * A custom button class for help functionality.
 */
public class HelpButton extends Button {

  /**
   * Constructs a HelpButton with the specified name and action to be performed when clicked.
   *
   * @param name    The text to display on the button.
   * @param onClick The action to be performed when the button is clicked.
   */
  public HelpButton(String name, Consumer<ActionEvent> onClick) {
    super(name);
    super.setTextAlignment(TextAlignment.CENTER);
    this.setOnAction(onClick::accept);
  }
}
