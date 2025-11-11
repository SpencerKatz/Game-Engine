package oogasalad.view.start;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class AbstractSplashScreen {


  public AbstractSplashScreen() {
    // do nothing
  }

  /**
   * Opens SplashScreen
   */
  public abstract void open();

  protected Scene setStage(Stage stage, double widthPortion, double heightPortion,
      ResourceString resourceString, String language, Scene myScene) {

    Scene scene;
    VBox vb = new VBox();
    vb.setAlignment(Pos.CENTER);
    vb.setSpacing(75);
    VBox buttonsBox = new VBox();
    buttonsBox.setSpacing(10);
    buttonsBox.setAlignment(Pos.CENTER);

    //Create the scene, initialized to a reasonable size.
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    int initialStartScreenWidth = (int) (screenBounds.getWidth() * widthPortion);
    int initialStartScreenHeight = (int) (screenBounds.getHeight() * heightPortion);

    Label title = new Label(resourceString.stageTitle());
    title.getStyleClass().add("title-label");
    title.widthProperty().addListener((obs, oldVal, newVal) -> SplashUtils.titleBob(title, newVal));

    vb.getChildren().add(title);
    scene = new Scene(vb, initialStartScreenWidth, initialStartScreenHeight);

    SplashUtils.createButtonsFromFile(resourceString.buttonsPath(), stage, buttonsBox, language,
        scene);
    vb.getChildren().add(buttonsBox);

    scene.getStylesheets().add(getClass().getResource(resourceString.styleCss()).toExternalForm());

    return scene;
  }


}
