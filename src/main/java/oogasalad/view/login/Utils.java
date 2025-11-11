package oogasalad.view.login;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

/**
 * This class is used to store utils functions.
 */
public class Utils {

  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;
  public static final double PADDING = 10;
  public static final double VWIDTH = 300;
  public static final double VHEIGHT = 400;

  public static void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static VBox createVbox() {
    VBox vbox = new VBox(PADDING);
    vbox.setPadding(new Insets(PADDING));
    vbox.setPrefWidth(VWIDTH);
    vbox.setPrefHeight(VHEIGHT);
    return vbox;
  }

}
