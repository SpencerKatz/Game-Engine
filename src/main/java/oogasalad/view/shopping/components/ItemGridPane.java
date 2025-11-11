package oogasalad.view.shopping.components;

import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import oogasalad.model.api.GameInterface;
import oogasalad.view.shopping.ShoppingViewStackPane;

public abstract class ItemGridPane extends GridPane {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.shopping.components.shopblock.";
  private final List<ItemView> itemViews;
  private final ShoppingViewStackPane parentStackPane;
  private final String myLanguage = "EnglishPopUpText";
  private final GameInterface game;
  private final ResourceBundle popUpTextResource;

  public ItemGridPane(GameInterface game, List<ItemView> bagItemViews,
      ShoppingViewStackPane parentStackPane) {
    super();
    this.game = game;
    this.itemViews = bagItemViews;
    this.parentStackPane = parentStackPane;
    popUpTextResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    initialize();
  }

  protected void initialize() {
    int columnIndex = 0;
    int rowIndex = 0;
    setPadding(new Insets(10));
    setAlignment(Pos.CENTER);
    for (ItemView itemView : itemViews) {
      ItemVbox itemVbox = createItemVbox(itemView);
      add(itemVbox, columnIndex, rowIndex);
      columnIndex++;
      if (columnIndex == 2) {
        columnIndex = 0;
        rowIndex++;
      }
    }
  }

  public void update(ItemView itemView) {
    game.update();
    parentStackPane.getMoneyHbox().update();
    parentStackPane.getBagStackPane().update();
  }

  protected abstract ItemVbox createItemVbox(ItemView itemView);

  public List<ItemView> getItemViews() {
    return itemViews;
  }

  public ShoppingViewStackPane getParentStackPane() {
    return parentStackPane;
  }

  public String getMyLanguage() {
    return myLanguage;
  }

  public GameInterface getGame() {
    return game;
  }

  public ResourceBundle getPopUpTextResource() {
    return popUpTextResource;
  }
}
