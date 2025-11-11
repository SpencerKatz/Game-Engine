package oogasalad.view.editor;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;
import oogasalad.view.editor.GameObjectEditor.GameObjectEditor;
import oogasalad.view.editor.MapEditor.MapEditor;
import oogasalad.view.editor.RuleEditor.RuleEditor;

/**
 * The EditorWindow class represents the main window for the game editor. It contains methods to
 * initialize and manage the editor window components.
 */
public class EditorWindow extends GridPane {

  private final MapEditor mapEditor;
  private final GameObjectEditor gameObjectEditor;

  /**
   * Constructs an EditorWindow object with a specified stage, back scene, and game configuration.
   *
   * @param stage     The stage for the editor window.
   * @param backScene The scene to return to when exiting the editor.
   * @param gc        The game configuration used in the editor window.
   */
  public EditorWindow(Stage stage, Scene backScene, GameConfiguration gc) {
    super();
    RuleEditor ruleEditor = new RuleEditor(gc);
    mapEditor = new MapEditor(stage, backScene, gc);
    gameObjectEditor = new GameObjectEditor(this::update);
    add(ruleEditor, 0, 0);
    add(gameObjectEditor, 2, 0);
    add(mapEditor, 1, 0);
    add(new BottomButtonContainer(new SaveAllButton(gc, ruleEditor::getName),
        new AddPhotoButton(stage, this::savePhoto)), 1, 1);
    stage.sizeToScene();
  }

  /**
   * Updates the map editor component.
   */
  public void update() {
    mapEditor.update();
  }

  /**
   * Saves a photo specified by the file parameter in the game object editor.
   *
   * @param file The file representing the photo to be saved.
   */
  public void savePhoto(File file) {
    gameObjectEditor.savePhoto(file);
  }
}
