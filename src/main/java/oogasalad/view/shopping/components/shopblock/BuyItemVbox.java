package oogasalad.view.shopping.components.shopblock;

import oogasalad.view.shopping.components.ItemVbox;
import oogasalad.view.shopping.components.ItemView;

/**
 * This class is a VBox that contains an item image, a price stack pane, and a sell button. It is
 * used to display a sellable item in the shop block.
 */
public class BuyItemVbox extends ItemVbox {

  /**
   * Constructor of BuyItemVbox.
   *
   * @param buyItemView the ItemView of the item
   */
  public BuyItemVbox(ItemView buyItemView, String myLanguage, String buttonText) {
    super(buyItemView, myLanguage, buttonText);

  }


}

