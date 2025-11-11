package oogasalad.view.shopping;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.model.api.GameInterface;
import oogasalad.view.branch.BranchBase;
import oogasalad.view.playing.PlayingPageView;


public class ShoppingView extends BranchBase {

  private final ShoppingViewStackPane root;

  public ShoppingView(GameInterface game, Stage stage, Scene previousScene,
      PlayingPageView playingPageView) {
    super(stage, previousScene);

    root = new ShoppingViewStackPane(game, getStage(), getPreviousScene(), playingPageView);

  }

  public Parent getScene() {
    return root;
  }


}
