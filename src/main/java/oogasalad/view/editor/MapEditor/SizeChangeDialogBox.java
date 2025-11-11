package oogasalad.view.editor.MapEditor;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.view.editor.GetDialogFields;

/**
 * Dialog box for changing the size of a component.
 */
class SizeChangeDialogBox implements GetDialogFields {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      "view.editor.MapEditor.SizeChangeDialogBox.";
  private final String displayTextLanguage = "EnglishDisplayText";
  private ResourceBundle displayTextResource;

  /**
   * Prompts the user to input a new size.
   *
   * @return An array containing the new size inputted by the user [newRows, newColumns], or null if
   * the user cancels.
   */
  @Override
  public String[] getFields() {
    displayTextResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + displayTextLanguage);

    TextField textField1 = new TextField();
    textField1.setId("newRows");
    TextField textField2 = new TextField();
    textField2.setId("newColumns");
    HBox hbox =
        new HBox(new Label(displayTextResource.getString("prompt") + "  "), textField1, textField2);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.getDialogPane().setContent(hbox);
    alert.setTitle(displayTextResource.getString("change_grid_size"));
    alert.setHeaderText(null);
    alert.showAndWait();

    ButtonType result = alert.getResult();
    if (result == ButtonType.OK) {
      return processInput(textField1.getText(), textField2.getText());
    } else {
      return null; // User clicked cancel or X button
    }
  }

  /**
   * Processes user input and validates it.
   *
   * @param text1 The input for new rows.
   * @param text2 The input for new columns.
   * @return An array containing the parsed new size [newRows, newColumns], or null if the input is
   * invalid.
   */
  private String[] processInput(String text1, String text2) {
    try {
      int newI = Integer.parseInt(text1);
      int newJ = Integer.parseInt(text2);
      return new String[]{text1, text2};
    } catch (NumberFormatException e) {
      showErrorPopup(displayTextResource.getString("invalid_type"),
          displayTextResource.getString("error_instruction"));
      return null; // Return null if parsing fails
    }
  }

  /**
   * Shows an error popup with the specified title and message.
   *
   * @param title   The title of the error popup.
   * @param message The error message to be displayed.
   */
  private void showErrorPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
