package oogasalad.view.start;

import javafx.scene.control.Button;

/**
 * A custom button used for changing pages.
 */
public class ChangePageButton extends Button {

  /**
   * Constructs a ChangePageButton with the specified name and color.
   *
   * @param name  the name of the button
   * @param color the background color of the button
   */
  public ChangePageButton(String name, String color) {
    super(name);
    this.setStyle("-fx-background-color: " + color + ";");
    this.setMinSize(100, 50);
    this.setOnMouseEntered(event -> this.setStyle("-fx-background-color: gray;"));
    this.setOnMouseExited(event -> this.setStyle("-fx-background-color: " + color + ";"));

    this.setId(name);
  }
}
