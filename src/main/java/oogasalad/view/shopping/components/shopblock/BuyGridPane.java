package oogasalad.view.shopping.components.shopblock;

import java.util.List;
import oogasalad.model.api.GameInterface;
import oogasalad.view.popup.PopUpStackPane;
import oogasalad.view.shopping.ShoppingViewStackPane;
import oogasalad.view.shopping.components.ItemGridPane;
import oogasalad.view.shopping.components.ItemView;

/**
 * This class is responsible for creating the sell grid pane that is used to display the items in
 * the shop.
 */
public class BuyGridPane extends ItemGridPane {

  /**
   * Constructor for SellGridPane.
   *
   * @param game            The game interface
   * @param sellItemViews   The list of item views
   * @param parentStackPane The parent stack pane
   */
  public BuyGridPane(GameInterface game, List<ItemView> sellItemViews,
      ShoppingViewStackPane parentStackPane) {
    super(game, sellItemViews, parentStackPane);
  }

  @Override
  protected BuyItemVbox createItemVbox(ItemView itemView) {
    BuyItemVbox buyItemVbox = new BuyItemVbox(itemView, "BuyItemButtonText", "buy");
    buyItemVbox.getButton().setOnAction(event -> {
      PopUpStackPane popUp =
          new PopUpStackPane(getPopUpTextResource(), getParentStackPane(), choice -> {
            if (choice) {
              getGame().buyItem(itemView.getName());
              update(itemView);
            }
          }, "src/main/resources/view/popup/PopUpButtonInfo.csv");
      getParentStackPane().getChildren().add(popUp);
    });
    buyItemVbox.getButton().setId("buy-button-" + itemView.getName());
    return buyItemVbox;
  }

}
