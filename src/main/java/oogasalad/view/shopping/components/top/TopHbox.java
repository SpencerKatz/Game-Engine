package oogasalad.view.shopping.components.top;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.model.api.GameInterface;
import oogasalad.view.shopping.Utils;

/**
 * This class is a HBox that contains a CurrentMoneyHbox, a ProgressBarHbox, and a BackButton. It is
 * used to display the top part of the shop block.
 */
public class TopHbox extends HBox {

  private CurrentMoneyHbox currentMoneyHbox;
  private ProgressBarHbox progressBarHbox;
  private Button backButton;
  private final GameInterface game;

  public TopHbox(GameInterface game) {
    super();
    this.game = game;
    initialize();
  }

  private void initialize() {
    setAlignment(Pos.CENTER);
    setSpacing(Utils.topHBoxSpacing);
    setAlignment(Pos.CENTER_LEFT);
    currentMoneyHbox = new CurrentMoneyHbox(game);
    currentMoneyHbox.update();
    progressBarHbox = new ProgressBarHbox(game.getGameState().getShop());
    backButton = new Button();
    backButton.getStyleClass().add("backButton");
    backButton.setId("shopBackButton");
    getChildren().addAll(progressBarHbox, currentMoneyHbox, backButton);

    setMargin(this, new Insets(10));
  }


  public CurrentMoneyHbox getCurrentMoneyHbox() {
    return currentMoneyHbox;
  }

  public ProgressBarHbox getProgressBarHbox() {
    return progressBarHbox;
  }

  public Button getBackButton() {
    return backButton;
  }

  public CurrentMoneyHbox getMoneyHbox() {
    return currentMoneyHbox;
  }
}
