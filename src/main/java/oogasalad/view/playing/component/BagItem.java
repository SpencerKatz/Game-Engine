package oogasalad.view.playing.component;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Tool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a tool in the game.
 */
public class BagItem extends StackPane {

  private final Rectangle selectedRectangle;
  private final String url;
  private final Label numLabel;
  private final ImageView imageView;

  private String name;

  private final Logger LOG = LogManager.getLogger(BagItem.class);
  private final double width;
  private final double height;


  /**
   * Constructor for the Tool class.
   *
   * @param url     the url of the tool
   * @param width   the width of the tool
   * @param height  the height of the tool
   * @param bagView the bagView that holds this bagItem
   */
  public BagItem(String url, String name, double width, double height, BagView bagView, int num) {
    super();
    this.url = Tool.getImagePath(url);
    this.name = name;
    this.width = width;
    this.height = height;
    imageView = new ImageView(new Image(this.url, width, height, false, true));
    StackPane imageContainer = new StackPane();
    VBox vBox = new VBox();
    vBox.setAlignment(javafx.geometry.Pos.CENTER);
    numLabel = new Label("" + num);
    numLabel.getStyleClass().add("item-num-label");
    imageContainer.getChildren().add(imageView);
    selectedRectangle = setUpSelected();
    imageContainer.getChildren().add(selectedRectangle);
    vBox.getChildren().addAll(imageContainer, numLabel);
    this.getChildren().add(vBox);
    LOG.info("bag item added: %s %d".formatted(this.url, num));
    setOnMouseClicked(event -> {
      bagView.select(this.name);
    });
    setId(name);
  }

  private Rectangle setUpSelected() {
    Rectangle selectedRectangle = new Rectangle(width, height, Color.BLUE);
    selectedRectangle.setOpacity(0);
    return selectedRectangle;
  }

  public void reset() {
    selectedRectangle.setOpacity(0);
  }

  public void setNum(int num) {
    numLabel.setText(num + "");
  }

  public void setImage(String url) {
    imageView.setImage(new Image(url, width, height, false, true));
  }

  public ImageView getImageView() {
    return imageView;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void select() {
    this.selectedRectangle.setOpacity(0.5);
  }

}
