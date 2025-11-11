package oogasalad.view.playing.component;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A pile class containing the land and the plants (buildings), it is a stack pane basically.
 */
public class Pile extends StackPane {

  private static final Pane placeholder = new Pane();
  private final List<String> landImagePath = new ArrayList<>();

  private final Logger LOG = LogManager.getLogger(Pile.class);
  private final double landGridPaneWidth;
  private final double landGridPaneHeight;
  private final LandView landview;

  public Pile(int x, int y, LandView landView, double landGridPaneWidth,
      double landGridPaneHeight) {
    super();
    double height = landGridPaneHeight / landView.getRow();
    double width = landGridPaneWidth / landView.getCol();
    this.landview = landView;
    this.landGridPaneWidth = landGridPaneWidth;
    this.landGridPaneHeight = landGridPaneHeight;
    for (int i = 0; i < 3; i++) {
      landImagePath.add(null);
      Rectangle rectangle = new Rectangle(width, height);
      rectangle.setFill(Color.TRANSPARENT);
      this.getChildren().add(rectangle);
    }
    this.setOnMouseClicked(event -> {
      landView.interact(y, x);
    });
  }

  /**
   * Update the pile by the list image path
   *
   * @param listImagePath the list of image that comes from the model
   */
  public void update(List<String> listImagePath) {
    // remove any images that don't exist anymore
    if (landImagePath.size() > listImagePath.size()) {
      this.getChildren().remove(listImagePath.size(), landImagePath.size());
      landImagePath.subList(listImagePath.size(), landImagePath.size()).clear();
    }
    // add placeholders for new images
    for (int i = landImagePath.size(); i < listImagePath.size(); i++) {
      landImagePath.add(null);
      this.getChildren().add(placeholder);
    }
    for (int i = 0; i < listImagePath.size(); i++) {
      if (landImagePath.get(i) == null || !landImagePath.get(i).equals(listImagePath.get(i))) {
        landImagePath.set(i, listImagePath.get(i));
        updateImageView(i, listImagePath.get(i));
      }
    }
  }

  /**
   * Update the image view at the pile given index and the image url
   *
   * @param index 0 - land, 1 - structure, 2 - collectable
   * @param url   the image url
   */
  public void updateImageView(int index, String url) {
    if (url == null) {
      this.getChildren().set(index, placeholder);
      return;
    }
    double height = landGridPaneHeight / landview.getRow();
    double width = landGridPaneWidth / landview.getCol();
    ImageView imageView = new ImageView();
    Image image = new Image("file:data/images/" + url, width, height, false, true);
    imageView.setImage(image);
    this.getChildren().set(index, imageView);
  }
}
