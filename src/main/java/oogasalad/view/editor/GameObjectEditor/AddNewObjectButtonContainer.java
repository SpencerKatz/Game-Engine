package oogasalad.view.editor.GameObjectEditor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * Container for AddNewObjectButton and RemoveObjectButton.
 */
public class AddNewObjectButtonContainer extends HBox {

  /**
   * Constructs an AddNewObjectButtonContainer.
   *
   * @param anob               The AddNewObjectButton to be added to the container.
   * @param removeObjectButton The RemoveObjectButton to be added to the container.
   */
  public AddNewObjectButtonContainer(AddNewObjectButton anob,
      RemoveObjectButton removeObjectButton) {
    // Call HBox constructor and pass the buttons to super class
    super(anob, removeObjectButton);

    // Set spacing, alignment, and padding for the container
    super.setSpacing(3);
    super.setAlignment(Pos.CENTER);
    super.setPadding(new Insets(5));
  }

}
