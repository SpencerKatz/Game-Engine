package oogasalad.view.editor.GameObjectEditor;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import oogasalad.view.editor.GetDialogFields;

/**
 * Dialog box for adding a new object.
 */
public class AddNewObjectDialogBox implements GetDialogFields {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      "view.editor.GameObjectEditor.AddNewObjectDialogBox.";
  private final String displayTextLanguage = "EnglishDisplayText";
  private ResourceBundle displayTextResource;

  /**
   * Displays a dialog box for adding a new object and retrieves the entered values.
   *
   * @return An array containing the type and name entered by the user, or null if the user cancels
   * the operation.
   */
  @Override
  public String[] getFields() {
    displayTextResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + displayTextLanguage);

    // Create text fields for type and name inputs
    TextField textField1 = new TextField();
    textField1.setId("newType");
    TextField textField2 = new TextField();
    textField2.setId("newName");

    // Create an HBox layout to hold the text fields
    HBox hbox =
        new HBox(new Label(displayTextResource.getString("prompt") + "  "), textField1, textField2);

    // Create an alert dialog
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.getDialogPane().setContent(hbox);
    alert.setTitle(displayTextResource.getString("add_new_object"));
    alert.setHeaderText(null);
    alert.showAndWait();

    // Retrieve user input when OK is clicked
    ButtonType result = alert.getResult();
    if (result == ButtonType.OK) {
      return new String[]{textField1.getText(), textField2.getText()};
    } else {
      return null; // User clicked cancel or X button
    }
  }
}
