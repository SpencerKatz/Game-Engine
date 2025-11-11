package oogasalad.view.branch;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Branch page (the page that is a branch to the main playing page, like map, shop, etc.)
 */
public abstract class BranchBase {

  private final Stage stage;

  private final Scene previousScene;

  public BranchBase(Stage stage, Scene previousScene) {
    this.stage = stage;
    this.previousScene = previousScene;
  }

  protected Stage getStage() {
    return stage;
  }

  public Scene getPreviousScene() {
    return previousScene;
  }


}
