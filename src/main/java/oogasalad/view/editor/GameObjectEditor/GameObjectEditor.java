package oogasalad.view.editor.GameObjectEditor;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import oogasalad.controller.GameObjectController;

/**
 * Editor for game objects.
 */
public class GameObjectEditor extends VBox {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.GameObjectEditor.";
  private final String myLanguage = "EnglishGameObjectEditor";
  private final ResourceBundle EditorResource;
  private final GameObjectController gc;
  private final GameObjectPropertiesDisplay gopd;
  private final Runnable update;

  /**
   * Constructs a GameObjectEditor.
   *
   * @param update The Runnable to be executed for updating.
   */
  public GameObjectEditor(Runnable update) {
    super();
    this.update = update;
    super.setMinWidth(400);
    super.setMaxWidth(500);
    EditorResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    gc = new GameObjectController();
    gopd = new GameObjectPropertiesDisplay(update, gc);
    super.setPadding(new Insets(0, 10, 10, 10));
    super.getChildren().addAll(
        new AddNewObjectButtonContainer(new AddNewObjectButton(this::newObject),
            new RemoveObjectButton(this::removeObject)), gopd);
  }

  /**
   * Creates a new game object.
   *
   * @param type The type of the game object.
   * @param name The name of the game object.
   */
  public void newObject(String type, String name) {
    gc.newObject(type, name);
    update.run();
  }

  /**
   * Removes a game object.
   *
   * @param name The name of the game object to be removed.
   */
  public void removeObject(String name) {
    gc.removeObject(name);
    update.run();
  }

  /**
   * Saves a photo.
   *
   * @param file The file to save the photo to.
   */
  public void savePhoto(File file) {
    gc.savePhoto(file);
  }
}
