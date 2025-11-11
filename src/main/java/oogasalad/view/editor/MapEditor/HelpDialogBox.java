package oogasalad.view.editor.MapEditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * A dialog box to display help information.
 */
public class HelpDialogBox {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.MapEditor.HelpDialogBox.";
  private final String helpTextPath = "EnglishHelpFilePath";
  private ResourceBundle helpTextPathResource;

  /**
   * Displays the help dialog box.
   *
   * @param title The title of the help dialog box.
   */
  public void show(String title) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    // Load the resource bundle for help text path
    helpTextPathResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + helpTextPath);

    // Set content of the dialog box to the help text
    alert.getDialogPane().setContent(getHelp(helpTextPathResource.getString("file_path")));
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  /**
   * Retrieves the help text from the specified file path and formats it into a node.
   *
   * @param textPath The file path of the help text.
   * @return A Node containing the formatted help text.
   */
  private Node getHelp(String textPath) {
    Label help = new Label();
    List<String> lines;
    try {
      // Read all lines from the file
      lines = Files.readAllLines(Paths.get(textPath));
    } catch (IOException e) {
      // Throw a runtime exception if an error occurs while reading the file
      throw new RuntimeException(e);
    }

    // Join the lines using newline character (\n) to form a single string
    String helpText = String.join("\n", lines);

    // Set the text of the label to the formatted help text
    help.setText(helpText);
    return help;
  }
}
