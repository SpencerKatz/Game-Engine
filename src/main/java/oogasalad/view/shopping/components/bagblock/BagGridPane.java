package oogasalad.view.shopping.components.bagblock;

import java.util.List;
import oogasalad.model.api.GameInterface;
import oogasalad.view.popup.PopUpStackPane;
import oogasalad.view.shopping.ShoppingViewStackPane;
import oogasalad.view.shopping.components.ItemGridPane;
import oogasalad.view.shopping.components.ItemView;

/**
 * This class is responsible for creating the bag grid pane that is used to display the items in the
 * bag.
 */
public class BagGridPane extends ItemGridPane {

  /**
   * Constructor for BagGridPane.
   *
   * @param game            The game interface
   * @param bagItemViews    The list of item views
   * @param parentStackPane The parent stack pane
   */
  public BagGridPane(GameInterface game, List<ItemView> bagItemViews,
      ShoppingViewStackPane parentStackPane) {
    super(game, bagItemViews, parentStackPane);
  }

  @Override
  public BagItemVbox createItemVbox(ItemView itemView) {
    BagItemVbox bagItemVbox = new BagItemVbox(itemView, "SellItemButtonText", "sell");
    bagItemVbox.getButton().setOnAction(event -> {
      PopUpStackPane popUp =
          new PopUpStackPane(getPopUpTextResource(), getParentStackPane(), choice -> {
            if (choice) {
              getGame().sellItem(itemView.getName());
              update(itemView);
            }
          }, "src/main/resources/view/popup/PopUpButtonInfo.csv");
      getParentStackPane().getChildren().add(popUp);
    });
    bagItemVbox.getButton().setId("sell-button-" + itemView.getName());
    return bagItemVbox;
  }

}

