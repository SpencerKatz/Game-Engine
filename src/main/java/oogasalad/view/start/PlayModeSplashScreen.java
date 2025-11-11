package oogasalad.view.start;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import oogasalad.view.playing.PlayingPageView;

/**
 * A splash screen for the play mode.
 */
public class PlayModeSplashScreen extends AbstractSplashScreen {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.start.PlayModeSplashScreen.";
  private static final String DEFAULT_RESOURCE_FOLDER = "data/gameconfigurations";
  private static final String STYLES = "/play_mode_styles.css";
  private static final double DEFAULT_WIDTH_PORTION = 0.65;
  private static final double DEFAULT_HEIGHT_PORTION = 0.9;
  private final Stage stage;
  private final String primaryLanguage;
  private final Scene previousScene;
  private final ResourceBundle buttonResource;
  private final ResourceBundle textResource;

  /**
   * Constructs a PlayModeSplashScreen.
   *
   * @param stageToUse the stage to use
   * @param language   the language
   * @param backScene  the previous scene
   */
  public PlayModeSplashScreen(Stage stageToUse, String language, Scene backScene) {
    super();
    stage = stageToUse;
    primaryLanguage = language;
    previousScene = backScene;

    textResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + primaryLanguage + "Text");
    buttonResource =
        ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + primaryLanguage + "Buttons");
  }

  /**
   * Opens the play mode splash screen.
   */
  @Override
  public void open() {
    String myStageTitle = textResource.getString("title");
    String buttonsPath = buttonResource.getString("buttons_path");

    ResourceString resourceString =
        new ResourceString(DEFAULT_RESOURCE_FOLDER, buttonsPath, myStageTitle, STYLES);

    Scene myScene = setStage(stage, DEFAULT_WIDTH_PORTION, DEFAULT_HEIGHT_PORTION, resourceString,
        primaryLanguage, previousScene);

    stage.setTitle(myStageTitle);
    stage.setScene(myScene);
    stage.show();
  }


  /**
   * Displays the file chooser for selecting game saves and configurations.
   */
  public void makeChooser() {

    LoaderListDisplay loaderListDisplay =
        new LoaderListDisplay(stage, primaryLanguage, textResource.getString("loader"));

    File[] saveFile = loaderListDisplay.open();

    String saveFilePath;
    String configFilePath;
    if (saveFile[0] == null || saveFile[1] == null) {
      return;
    } else {
      saveFilePath = saveFile[0].getName();
      configFilePath = saveFile[1].getName();
    }

    try {

      new PlayingPageView(stage, primaryLanguage, saveFilePath, configFilePath, 800, 600).start();

    } catch (IOException exception) {
      new Alert(AlertType.ERROR, textResource.getString("load_file_error")).showAndWait();
    }
  }

  /**
   * Displays the file chooser for selecting configurations only.
   */
  public void makeConfigChooser() {

    LoaderListDisplay loaderListDisplay = new LoaderListDisplay(stage, primaryLanguage,
        textResource.getString("loader"));

    File configFile = loaderListDisplay.openConfig();

    String configFilePath;
    if (configFile == null) {
      return;
    } else {
      configFilePath = configFile.getName();
    }

    try {

      new PlayingPageView(stage, primaryLanguage, null, configFilePath, 800, 600).start();

    } catch (IOException exception) {
      new Alert(AlertType.ERROR, textResource.getString("load_file_error")).showAndWait();
    }
  }


  /**
   * Returns to the previous scene.
   */
  public void goBackScene() {
    new StartScreen(stage, primaryLanguage, null).open();
  }


}
