package oogasalad.view.shopping.components.shopblock;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import oogasalad.view.shopping.Utils;

/**
 * This class is a StackPane that contains a price label and a background image. It is used to
 * display the price of a shop item in the shop block.
 */
public class PriceStackPane extends StackPane {

  /**
   * Constructor for the PriceStackPane
   *
   * @param price the price of the item
   */
  public PriceStackPane(double price) {
    super();
    setPrefSize(Utils.priceStackPaneWidth, Utils.priceStackPaneHeight);
    initialize(price);
  }

  private void initialize(double price) {
    Label priceLabel = new Label("" + price);
    priceLabel.getStyleClass().add("shop-price-label");
    priceLabel.setRotate(-9);
    Image backgroundImage = new Image("img/shop/price-label.png");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(Utils.priceStackPaneWidth);
    backgroundImageView.setFitHeight(Utils.priceStackPaneHeight);
    setMargin(priceLabel, new Insets(0, 0, 0, 20));
    getChildren().addAll(backgroundImageView, priceLabel);
  }
}
