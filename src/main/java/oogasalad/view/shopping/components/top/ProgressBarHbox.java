package oogasalad.view.shopping.components.top;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import oogasalad.model.api.ReadOnlyShop;
import oogasalad.view.shopping.Utils;

/**
 * This class is a HBox that contains a button and a progress bar. It is used to display the current
 * energy in the shop block.
 */
public class ProgressBarHbox extends HBox {

  private final ReadOnlyShop shop;
  private ProgressBar progressBar;

  /**
   * Constructor for the ProgressBarHbox
   *
   * @param shop the shop to be displayed
   */
  public ProgressBarHbox(ReadOnlyShop shop) {
    super();
    this.shop = shop;
    initialize();
  }

  private void initialize() {
    Button button = new Button();
    button.setId("shopAddButton");
    //TODO: energy
    progressBar = new ProgressBar();
    progressBar.setPrefSize(Utils.progressBarWidth, Utils.progressBarHeight);
    getChildren().addAll(button, progressBar);
  }

  public void update(double progress) {
    progressBar.setProgress(progress);
  }

}
