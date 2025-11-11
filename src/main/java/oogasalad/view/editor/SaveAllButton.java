package oogasalad.view.editor;

import java.io.IOException;
import java.util.function.Supplier;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import oogasalad.model.data.GameConfiguration;

/**
 * The SaveAllButton class represents a button to save all changes in the editor. It triggers a
 * confirmation dialog before saving.
 */
public class SaveAllButton extends Button {

  /**
   * Constructs a SaveAllButton with a specified game configuration and a supplier to get the file
   * name.
   *
   * @param gc          The game configuration to save.
   * @param getFileName A supplier to get the file name.
   */
  public SaveAllButton(GameConfiguration gc, Supplier<String> getFileName) {
    super("Save All"); //TODO: Resource Bundle
    setOnAction(e -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText("Save");
      alert.getDialogPane().setContent(new Label("Are you sure?"));
      alert.showAndWait();
      if (alert.getResult() == ButtonType.OK) {
        try {
          gc.save(getFileName.get());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

  }
}
