package oogasalad.view.shopping;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyBag;
import oogasalad.model.api.ReadOnlyShop;
import oogasalad.view.playing.PlayingPageView;
import oogasalad.view.shopping.components.bagblock.BagStackPane;
import oogasalad.view.shopping.components.shopblock.ShopStackPane;
import oogasalad.view.shopping.components.top.CurrentMoneyHbox;
import oogasalad.view.shopping.components.top.TopHbox;

public class ShoppingViewStackPane extends StackPane {

  private final GameInterface game;
  private final Stage stage;
  private final Scene previousScene;
  private final PlayingPageView playingPageView;
  private TopHbox topHBox;
  private BagStackPane bagStackPane;

  public ShoppingViewStackPane(GameInterface game, Stage stage, Scene previousScene,
      PlayingPageView playingPageView) {
    super();
    this.getStyleClass().add("shop-boarder-pane");
    this.game = game;
    ReadOnlyShop shop = game.getGameState().getShop();
    ReadOnlyBag bag = game.getGameState().getBag();
    this.stage = stage;
    this.previousScene = previousScene;
    this.playingPageView = playingPageView;
    initialize();
  }


  private void initialize() {
    BorderPane borderPane = new BorderPane();
    topHBox = new TopHbox(game);
    borderPane.setTop(topHBox);
    HBox centerHBox = new HBox();
    ShopStackPane sellItemStackPane = new ShopStackPane(game, this);
    bagStackPane = new BagStackPane(game, this);
    centerHBox.getChildren().addAll(sellItemStackPane, bagStackPane);
    borderPane.setCenter(centerHBox);
    topHBox.getBackButton().setOnMouseClicked(event -> {
      playingPageView.start();
    });
    getChildren().add(borderPane);
  }

  public CurrentMoneyHbox getMoneyHbox() {
    return this.topHBox.getMoneyHbox();
  }

  public BagStackPane getBagStackPane() {
    return bagStackPane;
  }

}
