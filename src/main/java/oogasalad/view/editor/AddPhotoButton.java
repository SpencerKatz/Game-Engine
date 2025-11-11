package oogasalad.view.editor;

import java.io.File;
import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * The AddPhotoButton class represents a button for adding a photo. It allows users to select an
 * image file and provides a callback to save the selected photo.
 */
public class AddPhotoButton extends Button {

  /**
   * Constructs an AddPhotoButton object.
   *
   * @param window    The parent window where the file chooser dialog will be displayed.
   * @param savePhoto A consumer function that accepts the selected photo file and performs the
   *                  saving operation.
   */
  public AddPhotoButton(Window window, Consumer<File> savePhoto) {
    super("Add Photo"); //TODO: Resource Bundle
    setOnAction(e -> {
      File file = openFileChooser(window);
      if (file != null) {
        savePhoto.accept(file);
      }
    });

  }

  /**
   * Opens a file chooser dialog to select an image file.
   *
   * @param window The parent window where the file chooser dialog will be displayed.
   * @return The selected image file, or null if no file is selected.
   */
  private File openFileChooser(Window window) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Image File");
    // Set filters for file types (optional)
    fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));

    // Show the file chooser dialog
    File selectedFile = fileChooser.showOpenDialog(window);

    // Handle the selected file (e.g., load it into your application)
    return selectedFile;
  }
}
