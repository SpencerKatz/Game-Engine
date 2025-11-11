package oogasalad.view.shopping.components.top;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import oogasalad.model.api.GameInterface;
import oogasalad.view.shopping.Utils;

/**
 * This class is a HBox that contains a button, a label, and an image view. It is used to display
 * the current money in the shop block.
 */
public class CurrentMoneyHbox extends HBox {

  private final Label moneyLabel = new Label();
  private final GameInterface game;

  /**
   * Constructor for the CurrentMoneyHbox
   */
  public CurrentMoneyHbox(GameInterface game) {
    super();
    this.game = game;
    initialize();
  }

  private void initialize() {
    Button addButton = new Button();
    addButton.setId("shopAddButton");
    moneyLabel.setPadding(new Insets(10, 0, 10, 20));
    moneyLabel.getStyleClass().add("shop-money-label");
    moneyLabel.setId("shopMoneyLabel");
    Image coinImage = new Image("img/shop/coin.png");
    ImageView coinImageView = new ImageView(coinImage);
    coinImageView.setFitHeight(Utils.coinImageHeight);
    coinImageView.setFitWidth(Utils.coinImageWidth);
    getChildren().addAll(addButton, moneyLabel, coinImageView);
  }

  public void update() {
    moneyLabel.setText("" + game.getGameState().getMoney());
  }
}
