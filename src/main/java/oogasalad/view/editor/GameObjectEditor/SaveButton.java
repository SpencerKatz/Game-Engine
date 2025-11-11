package oogasalad.view.editor.GameObjectEditor;

import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import oogasalad.model.api.exception.InvalidRuleType;

/**
 * Button for saving properties.
 */
public class SaveButton extends Button {

  /**
   * Constructs a SaveButton with a specified name, action, update, and key.
   *
   * @param name   The name of the button.
   * @param action The action to perform when the button is clicked.
   * @param update The action to update the UI.
   * @param key    The key associated with the button.
   */
  public SaveButton(String name, Consumer<ActionEvent> action, Runnable update, String key) {
    super(name);
    setOnAction(event -> {
      try {
        action.accept(event);
      } catch (InvalidRuleType e) {
        new ValidationErrorAlert(e.getRuleName(), e.getRuleValue(), e.getType());
      }
      if (update != null) {
        update.run();
      }
    });
    super.setId("SaveProperties" + key);
  }
}
