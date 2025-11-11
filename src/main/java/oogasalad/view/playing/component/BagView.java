package oogasalad.view.playing.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyBag;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.view.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The tool view that contains all the items
 */
public class BagView extends StackPane {

  private static final String backGroundImageUrl = "img/playing/box-background.png";

  private final GridPane toolGridPane;

  private final int colNum;

  private final GameInterface game;

  private int page = 0;

  private final List<Item> itemOnShow = new ArrayList<>();

  private final Logger LOG = LogManager.getLogger(BagView.class);
  private final double bottomCellWidth;
  private final double bottomCellHeight;


  /**
   * Constructor for the ToolView class.
   *
   * @param game   the game
   * @param colNum the number of columnsxf to be shown the view
   */

  public BagView(GameInterface game, int colNum, double bottomCellWidth, double bottomCellHeight,
      double bottomBoxWidth, double bottomBoxHeight) {
    super();
    this.toolGridPane = new GridPane();
    this.colNum = colNum;
    this.game = game;
    ImageView backgroundImageView = new ImageView(new Image(backGroundImageUrl));
    backgroundImageView.setFitWidth(bottomBoxWidth);
    backgroundImageView.setFitHeight(bottomBoxHeight);
    this.bottomCellHeight = bottomCellHeight;
    this.bottomCellWidth = bottomCellWidth;
    BorderPane borderPane = new BorderPane();
    StackPane.setMargin(borderPane, new Insets(20, 50, 0, 50));
    Button leftButton = new Button("<");
    borderPane.setLeft(leftButton);
    Button rightButton = new Button(">");
    borderPane.setRight(rightButton);
    borderPane.setCenter(toolGridPane);
    this.getChildren().addAll(backgroundImageView, borderPane);
    leftButton.setOnMouseClicked(e -> switchPage(-1));
    rightButton.setOnMouseClicked(e -> switchPage(1));
    update();
  }

  /**
   * Select the item with the name
   *
   * @param name
   */
  public void select(String name) {
    LOG.info("selected item : %s".formatted(name));
    for (Item i : itemOnShow) {
      BagItem bagItem = i.bagItem();
      bagItem.reset();
      if (bagItem.getName().equals(name)) {
        bagItem.select();
      }
    }
    game.selectItem(name);
  }


  public void update() {
    List<Pair<Pair<String, String>, Integer>> item = getItem(page);
    checkUpdate(item);
  }

  private void switchPage(int interval) {
    LOG.info("switch page %d".formatted(interval));
    if (this.page + interval < 0) {
      return;
    }
    List<Pair<Pair<String, String>, Integer>> item = getItem(this.page + interval);
    if (item.isEmpty()) {
      return;
    }
    this.page = this.page + interval;
    checkUpdate(item);
  }

  /**
   * Get the item in the bag
   *
   * @param page the page number
   * @return the list of items
   */
  private List<Pair<Pair<String, String>, Integer>> getItem(int page) {
    ReadOnlyBag bag = game.getGameState().getBag();
    int idx = 0;
    int begin = page * colNum;
    int end = (page + 1) * colNum;
    List<Pair<Pair<String, String>, Integer>> valueToCheck = new ArrayList<>();
    for (Map.Entry<ReadOnlyItem, Integer> entry : bag.getItems().entrySet()) {
      if (idx >= begin && idx < end) {
        valueToCheck.add(
            new Pair<>(new Pair<>(entry.getKey().getImagePath(), entry.getKey().getName()),
                entry.getValue()));
      }
      idx++;
    }
    return valueToCheck;
  }

  private void checkUpdate(List<Pair<Pair<String, String>, Integer>> newItemList) {
    List<Item> newItemOnShow = new ArrayList<>();
    for (int i = 0; i < newItemList.size(); i++) {
      Pair<Pair<String, String>, Integer> newItem = newItemList.get(i);
      int column = i % colNum;
      int row = i / colNum;
      if (i >= itemOnShow.size()) {
        updateNewItem(newItemList, newItem, newItemOnShow, i, column, row);
      } else {
        updateCurrentItem(i, newItem);
      }
    }
    List<Item> removed = new ArrayList<>();
    if (itemOnShow.size() > newItemList.size()) {
      for (int i = newItemList.size(); i < itemOnShow.size(); i++) {
        toolGridPane.getChildren().remove(itemOnShow.get(i).bagItem());
        removed.add(itemOnShow.get(i));
      }
      itemOnShow.removeAll(removed);
    } else {
      itemOnShow.addAll(newItemOnShow);
    }
  }

  private void updateCurrentItem(int i, Pair<Pair<String, String>, Integer> newItem) {
    Item item = itemOnShow.get(i);
    if (!(item.imageUrl().equals(newItem.getKey().getKey()))) {
      item.bagItem().setImage(Tool.getImagePath(newItem.getKey().getKey()));
    }
    if (item.num() != newItem.getValue()) {
      item.bagItem().setNum(newItem.getValue());
    }
    item.bagItem().setName(newItem.getKey().getValue());
    itemOnShow.set(i, new Item(newItem.getKey().getKey(), newItem.getValue(), item.bagItem()));
  }

  private void updateNewItem(List<Pair<Pair<String, String>, Integer>> newItemList,
      Pair<Pair<String, String>, Integer> newItem, List<Item> newItemOnShow, int i, int column,
      int row) {
    BagItem bagItem =
        new BagItem(newItem.getKey().getKey(), newItem.getKey().getValue(), bottomCellWidth,
            bottomCellHeight, this, newItem.getValue());
    newItemOnShow.add(
        new Item(newItemList.get(i).getKey().getKey(), newItemList.get(i).getValue(), bagItem));
    toolGridPane.add(bagItem, column, row);
  }
}
