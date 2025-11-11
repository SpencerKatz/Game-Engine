package oogasalad.view.editor.GameObjectEditor;

import java.util.function.Consumer;
import javafx.scene.control.Button;

/**
 * Button to remove an object.
 */
public class RemoveObjectButton extends Button {

  /**
   * Constructs a RemoveObjectButton with a specified remove Consumer.
   *
   * @param remove The Consumer to remove an object.
   */
  public RemoveObjectButton(Consumer<String> remove) {
    super("Remove"); //TODO: Resource bundle
    setId("RemoveObject");
    RemoveObjectDialogBox rodb = new RemoveObjectDialogBox();
    super.setOnAction(e -> {
      String[] toBeRemoved = rodb.getFields();
      if (toBeRemoved != null) {
        remove.accept(toBeRemoved[0]);
      }
    });
  }

}
