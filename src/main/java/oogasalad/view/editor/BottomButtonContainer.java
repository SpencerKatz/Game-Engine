package oogasalad.view.editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * The BottomButtonContainer class represents a container for bottom buttons in the editor. It
 * arranges the buttons horizontally and provides spacing and alignment properties.
 */
public class BottomButtonContainer extends HBox {

  /**
   * Constructs a BottomButtonContainer object.
   *
   * @param sab            The SaveAllButton to be included in the container.
   * @param addPhotoButton The AddPhotoButton to be included in the container.
   */
  public BottomButtonContainer(SaveAllButton sab, AddPhotoButton addPhotoButton) {
    super(sab, addPhotoButton);
    super.setAlignment(Pos.CENTER);
    super.setSpacing(10);
    super.setPadding(new Insets(10, 0, 10, 0));
  }
}
