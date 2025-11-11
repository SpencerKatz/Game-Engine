package oogasalad.view.shopping.components;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import oogasalad.view.shopping.Utils;
import oogasalad.view.shopping.components.shopblock.PriceStackPane;

public abstract class ItemVbox extends VBox {

  private final String DEFAULT_RESOURCE_PACKAGE = "view.shopping.components.shopblock.";
  private final ItemView itemView;
  private ResourceBundle buttonTextResource;
  private Button button;

  public ItemVbox(ItemView itemView, String myLanguage, String buttonText) {
    super();
    this.itemView = itemView;
    initialize(myLanguage, buttonText);
  }

  protected void initialize(String myLanguage, String buttonText) {
    buttonTextResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    ImageView itemImage = new ImageView(new Image("file:data/images/" + itemView.getUrl()));
    itemImage.setFitWidth(Utils.sellItemImageWidth);
    itemImage.setFitHeight(Utils.sellItemImageHeight);
    PriceStackPane priceStackPane = new PriceStackPane(itemView.getWorth());
    priceStackPane.setPrefSize(Utils.priceWidth, Utils.priceHeight);
    setMargin(priceStackPane, new Insets(-30, 0, -20, 0));
    button = new Button(buttonTextResource.getString(buttonText));
    button.getStyleClass().add("sellButton");
    button.setPrefSize(Utils.sellButtonWidth, Utils.sellButtonHeight);
    setAlignment(Pos.CENTER);
    getChildren().addAll(itemImage, priceStackPane, button);
  }

  public Button getButton() {
    return button;
  }

  public void setButtonId(String id) {
    button.setId(id);
  }
}
