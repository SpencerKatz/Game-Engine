package oogasalad.view.shopping.components.shopblock;

import java.util.List;
import oogasalad.view.shopping.components.PageChangeBorderPane;

/**
 * This class is responsible for creating the page change border pane that is used in the shop block
 * to change pages.
 */
public class BuyPageChangeBorderPane extends PageChangeBorderPane<BuyGridPane> {

  public BuyPageChangeBorderPane(List<BuyGridPane> gridPanes, ShopStackPane itemStackPane) {
    super(gridPanes, itemStackPane);
    getLeftButton().setId("left-page-change-button-buy");
    getRightButton().setId("right-page-change-button-buy");
  }
}