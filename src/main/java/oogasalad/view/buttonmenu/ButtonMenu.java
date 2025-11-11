package oogasalad.view.buttonmenu;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import oogasalad.view.start.SplashUtils;

/**
 * A menu for displaying buttons.
 */
public class ButtonMenu {

  static final String DEFAULT_RESOURCE_PACKAGE = "view.buttonmenu.";
  private final Stage primaryStage;
  private final Scene previousScene;
  private final String primaryLanguage;
  private final String buttonsFilePath;
  private final String STYLES = "/view/buttonmenu/menu_style.css";
  private Scene menuScene;
  private Stage menuStage;
  private final ResourceBundle propertiesBundle;

  /**
   * Constructs a ButtonMenu.
   *
   * @param mainStage the main stage
   * @param language  the language
   * @param backScene the previous scene
   * @param filePath  the file path of the buttons
   */
  public ButtonMenu(Stage mainStage, String language, Scene backScene, String filePath) {
    primaryStage = mainStage;
    primaryLanguage = language;
    propertiesBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + primaryLanguage);
    previousScene = backScene;
    buttonsFilePath = filePath;

    primaryStage.sceneProperty().addListener(new ChangeListener<>() {
      @Override
      public void changed(ObservableValue<? extends Scene> observable, Scene oldValue,
          Scene newValue) {
        menuStage.close();
      }
    });
  }

  /**
   * Opens the button menu.
   */
  public void open() {
    menuStage = new Stage();
    menuStage.initStyle(StageStyle.UNDECORATED);
    VBox root = new VBox();
    root.setSpacing(10);
    root.getStyleClass().add("root_VBox");

    VBox topBox = new VBox();
    topBox.getStyleClass().add("top_box");

    Label title = new Label(propertiesBundle.getString("menu"));
    topBox.getChildren().add(title);
    topBox.setAlignment(Pos.CENTER);

    root.getChildren().add(topBox);

    SplashUtils.createButtonsFromFile(buttonsFilePath, primaryStage, root, primaryLanguage,
        previousScene);

    Group group = new Group(root);

    ScrollPane scrollPane = new ScrollPane(group);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    scrollPane.setPrefHeight(400);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(false);

    root.setAlignment(Pos.CENTER);
    root.minHeightProperty().bind(scrollPane.heightProperty());
    root.prefHeightProperty().bind(scrollPane.heightProperty());

    menuScene = new Scene(scrollPane);
    menuScene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

    if (primaryStage != null) {
      menuStage.initOwner(primaryStage);
      Window owner = primaryStage.getScene().getWindow();
      if (owner != null) {
        Bounds bounds = primaryStage.getScene().getRoot().getLayoutBounds();
        Point2D topLeft = new Point2D(bounds.getMaxX() / 2 - 50, bounds.getMaxY() / 2 - 200);
        Point2D screenTopLeft = primaryStage.getScene().getRoot().localToScreen(topLeft);
        menuStage.setX(screenTopLeft.getX());
        menuStage.setY(screenTopLeft.getY());
      }
    }
    menuStage.setScene(menuScene);
    menuStage.show();
  }

  /**
   * Closes the button menu.
   */
  public void closeMenu() {
    menuStage.close();
  }

}
