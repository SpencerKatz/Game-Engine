package oogasalad.view.shopping.components;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.view.shopping.Utils;

/**
 * This class is responsible for creating the page change border pane that is used to change pages.
 *
 * @param <T> The type of GridPane
 */
public class PageChangeBorderPane<T extends GridPane> extends BorderPane {

  private Button leftButton;
  private Button rightButton;
  private final List<T> gridPanes;

  private int currentPageIndex = 0;
  private GridPane currentGridPane;
  private final ItemStackPane<T> itemStackPane;

  public PageChangeBorderPane(List<T> gridPanes, ItemStackPane<T> itemStackPane) {
    super();
    this.gridPanes = gridPanes;
    this.itemStackPane = itemStackPane;
    this.setPickOnBounds(false);
    initialize();
  }

  private void initialize() {
    leftButton = new Button();
    leftButton.getStyleClass().add("left-page-change-button");
    rightButton = new Button();
    rightButton.getStyleClass().add("right-page-change-button");
    setAlignment(leftButton, Pos.CENTER_LEFT);
    setAlignment(rightButton, Pos.CENTER_RIGHT);
    setMargin(leftButton, new Insets(0, 0, 0, 10));
    setMargin(rightButton, new Insets(0, 10, 0, 0));
    setLeft(leftButton);
    setRight(rightButton);
    setPageChangeButton();
    currentGridPane = gridPanes.get(0);
    setPrefSize(Utils.pageChangeBorderPaneWidth, Utils.pageChangeBorderPaneHeight);
  }

  private void setPageChangeButton() {
    enableButtons();
    leftButton.setOnAction(event -> {
      if (currentPageIndex == 0) {
        return;
      }
      currentPageIndex--;
      changePage();
      enableButtons();
    });
    rightButton.setOnAction(event -> {
      if (currentPageIndex == gridPanes.size() - 1) {
        return;
      }
      currentPageIndex++;
      changePage();
      enableButtons();
    });
  }

  private void changePage() {
    if (currentPageIndex < 0 || currentPageIndex >= gridPanes.size()) {
      return;
    }
    currentGridPane = gridPanes.get(currentPageIndex);
    itemStackPane.updateGridPane();
  }

  private void enableButtons() {
    leftButton.setDisable(currentPageIndex == 0);
    rightButton.setDisable(currentPageIndex == gridPanes.size() - 1 || gridPanes.size() == 0);
  }

  public GridPane getCurrentGridPane() {
    return currentGridPane;
  }

  public Button getLeftButton() {
    return leftButton;
  }

  public Button getRightButton() {
    return rightButton;
  }
}
