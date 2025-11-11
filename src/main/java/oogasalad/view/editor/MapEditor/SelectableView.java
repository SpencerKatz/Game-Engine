package oogasalad.view.editor.MapEditor;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.view.Tool;

/**
 * Represents a selectable view in the map editor.
 */
public class SelectableView extends ImageView {

  private final String pic;
  private final Label title;

  /**
   * Constructs a SelectableView object with the specified image and title.
   *
   * @param pic   The path to the image file.
   * @param title The title of the selectable view.
   */
  public SelectableView(String pic, String title) {
    super();
    super.setImage(getPic(pic).getImage());
    super.setFitWidth(40);
    super.setFitHeight(30);
    this.pic = pic;
    this.title = new Label(title);
  }

  /**
   * Retrieves the ImageView for the specified image.
   *
   * @param pic The path to the image file.
   * @return An ImageView object representing the image.
   */
  public static ImageView getPic(String pic) {
    String url = Tool.getImagePath(pic);
    return new ImageView(new Image(url));
  }

  /**
   * Gets the label associated with this SelectableView.
   *
   * @return The label associated with this SelectableView.
   */
  public Label getLabel() {
    return title;
  }

  /**
   * Creates a new SelectableView with the same image and title.
   *
   * @return A new SelectableView object.
   */
  public SelectableView getNew() {
    return new SelectableView(pic, title.getText());
  }
}
