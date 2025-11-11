package oogasalad.view.shopping.components;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyBag;
import oogasalad.model.api.ReadOnlyShop;
import oogasalad.view.shopping.ShoppingViewStackPane;
import oogasalad.view.shopping.Utils;

public abstract class ItemStackPane<T extends GridPane> extends StackPane {

  private final ReadOnlyBag bag;
  private final ReadOnlyShop shop;
  private final GameInterface game;
  private final ShoppingViewStackPane parentStackPane;
  private List<T> gridPanes;
  private List<ItemView> itemViews;
  private ImageView backgroundImageView;
  private PageChangeBorderPane pageChangeBorderPane;

  public ItemStackPane(GameInterface game, ShoppingViewStackPane parentStackPane) {
    super();
    this.game = game;
    this.bag = game.getGameState().getBag();
    this.shop = game.getGameState().getShop();
    this.parentStackPane = parentStackPane;
    initialize();
  }

  protected void initialize() {
    Image backgroundImage = new Image(getBackgroundImagePath());
    backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(Utils.shopStackPaneWidth);
    backgroundImageView.setFitHeight(Utils.shopStackPaneHeight);
    itemViews = createItems();
    createGridPanes(itemViews);
    pageChangeBorderPane = createPageChangeBorderPane(gridPanes, this);
    updateGridPane();
  }

  protected abstract String getBackgroundImagePath();

  protected abstract List<ItemView> createItems();


  protected void createGridPanes(List<ItemView> itemViews) {
    gridPanes = new ArrayList<>();
    int groupCount = (itemViews.size() + 3) / 4;
    for (int i = 0; i < groupCount; i++) {
      int startIndex = i * 4;
      int endIndex = Math.min(startIndex + 4, itemViews.size());
      List<ItemView> sublist = itemViews.subList(startIndex, endIndex);
      T gridPane = createGridPane(game, sublist, parentStackPane);
      gridPanes.add(gridPane);
    }
  }

  protected abstract T createGridPane(GameInterface game, List<ItemView> sublist,
      ShoppingViewStackPane parentStackPane);


  protected abstract PageChangeBorderPane createPageChangeBorderPane(
      List<? extends GridPane> gridPanes, ItemStackPane itemStackPane);

  protected abstract double getLeftMargin();

  protected abstract double getTopMargin();

  public ReadOnlyBag getBag() {
    return bag;
  }

  public ReadOnlyShop getShop() {
    return shop;
  }

  public void updateGridPane() {
    getChildren().clear();
    GridPane currentGridPane = pageChangeBorderPane.getCurrentGridPane();
    setMargin(currentGridPane, new Insets(getTopMargin(), 0, 0, getLeftMargin()));
    setAlignment(currentGridPane, Pos.TOP_LEFT);
    setAlignment(backgroundImageView, Pos.TOP_LEFT);
    setAlignment(pageChangeBorderPane, Pos.TOP_LEFT);
    getChildren().addAll(backgroundImageView, currentGridPane, pageChangeBorderPane);
  }
}
