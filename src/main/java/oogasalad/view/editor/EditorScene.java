package oogasalad.view.editor;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;

/**
 * The EditorScene class represents a scene for the game editor. It contains methods to initialize
 * and start the editor scene.
 */
public class EditorScene extends Scene {

  private final Stage stage;
  private final String myPrimaryLanguage;

  /**
   * Constructs an EditorScene object with a specified primary stage, language, back scene, and game
   * configuration.
   *
   * @param primaryStage The primary stage for the editor scene.
   * @param language     The primary language used in the editor scene.
   * @param backScene    The scene to return to when exiting the editor.
   * @param gc           The game configuration used in the editor scene.
   */
  public EditorScene(Stage primaryStage, String language, Scene backScene, GameConfiguration gc) {
    super(new EditorWindow(primaryStage, backScene, gc));
    stage = primaryStage;
    myPrimaryLanguage = language;
    super.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
  }

  /**
   * Constructs an EditorScene object with a specified primary stage, language, and back scene.
   *
   * @param primaryStage The primary stage for the editor scene.
   * @param language     The primary language used in the editor scene.
   * @param backScene    The scene to return to when exiting the editor.
   */
  public EditorScene(Stage primaryStage, String language, Scene backScene) {
    super(new EditorWindow(primaryStage, backScene, new GameConfiguration()));
    stage = primaryStage;
    myPrimaryLanguage = language;
    super.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
  }

  /**
   * Starts the editor scene by setting it on the primary stage and displaying it.
   */
  public void start() {
    stage.setScene(this);
    stage.show();
  }

}
