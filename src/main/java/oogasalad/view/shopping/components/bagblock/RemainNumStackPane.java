package oogasalad.view.shopping.components.bagblock;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.view.shopping.Utils;

/**
 * This class is a StackPane that contains a background image and a Label. It is used to display the
 * number of items remaining in the bag block.
 */
public class RemainNumStackPane extends StackPane {

  /**
   * Constructor for the RemainNumStackPane
   *
   * @param num the number of items remaining
   */
  public RemainNumStackPane(int num) {
    super();
    setPrefSize(Utils.itemRemainWidth, Utils.itemRemainHeight);
    initialize(num);
  }

  private void initialize(int num) {
    Label priceLabel = new Label("" + num);
    priceLabel.getStyleClass().add("shop-price-label");
    Image backgroundImage = new Image("img/shop/ItemRemain.png");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(Utils.itemRemainWidth);
    backgroundImageView.setFitHeight(Utils.itemRemainHeight);
    getChildren().addAll(backgroundImageView, priceLabel);
  }
}
