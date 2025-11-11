package oogasalad.view.shopping.components.bagblock;

import oogasalad.view.shopping.components.ItemVbox;
import oogasalad.view.shopping.components.ItemView;

/**
 * This class is a VBox that contains an item image and a RemainNumStackPane. It is used to display
 * an item in the bag block.
 */
public class BagItemVbox extends ItemVbox {


  /**
   * Constructor of BagItemVbox.
   *
   * @param buyItemView the ItemView of the item
   */
  public BagItemVbox(ItemView buyItemView, String myLanguage, String buttonText) {
    super(buyItemView, myLanguage, buttonText);
    RemainNumStackPane remainNumStackPane = new RemainNumStackPane(buyItemView.getNum());
    getChildren().add(remainNumStackPane);
  }


}
