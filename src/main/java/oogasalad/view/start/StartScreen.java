package oogasalad.view.start;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the start screen of the application.
 */
public class StartScreen extends AbstractSplashScreen {

  public static final String DEFAULT_RESOURCE_FOLDER = "src/main/resources/view/start/StartScreen/";
  public static final double DEFAULT_WIDTH_PORTION = 0.65;
  public static final double DEFAULT_HEIGHT_PORTION = 0.9;
  private static final String DEFAULT_RESOURCE_PACKAGE = "view.start.StartScreen.";
  private static final String STYLES = "/styles.css";

  private final Stage stage;
  private final Scene previousScene;
  private String buttonLanguage;
  private String titleLanguage;
  private ResourceBundle buttonResource;
  private ResourceBundle titleResource;
  private String buttonsPath;
  private String myStageTitle;
  private String[] myLanguages;
  private String myPrimaryLanguage;
  private LanguageDialogBox languageDialogBox;
  private Scene startScreen;

  /**
   * Creates a new StartScreen instance.
   *
   * @param stageToUse the primary stage
   * @param language   the language
   * @param backScene  the previous scene
   */
  public StartScreen(Stage stageToUse, String language, Scene backScene) {
    super();
    stage = stageToUse;
    myPrimaryLanguage = language;
    setLanguages();
    previousScene = backScene;
  }

  /**
   * Opens the start screen.
   */
  @Override
  public void open() {
    titleResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + titleLanguage);
    buttonResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + buttonLanguage);

    myStageTitle = titleResource.getString("title");
    buttonsPath = buttonResource.getString("buttons_path");

    ResourceString resourceString =
        new ResourceString(DEFAULT_RESOURCE_FOLDER, buttonsPath, myStageTitle, STYLES);
    startScreen = setStage(stage, DEFAULT_WIDTH_PORTION, DEFAULT_HEIGHT_PORTION, resourceString,
        myPrimaryLanguage, startScreen);

    stage.setTitle(myStageTitle);
    stage.setScene(startScreen);
    stage.show();
  }

  /**
   * Retrieves the start screen scene.
   *
   * @return the start screen scene
   */
  public Scene getStartScreen() {
    return startScreen;
  }

  private void setLanguages() {
    setFilesLanguage();
    myLanguages = SplashUtils.readCommaSeparatedCSV(DEFAULT_RESOURCE_FOLDER + "LanguagesList.csv");

    languageDialogBox = new LanguageDialogBox(stage, myLanguages);
    languageDialogBox.primaryLanguageProperty().addListener(new ChangeListener<>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        myPrimaryLanguage = newValue;
        new StartScreen(stage, myPrimaryLanguage, null).open();
      }
    });
  }

  private void setFilesLanguage() {
    titleLanguage = myPrimaryLanguage + "Text";
    buttonLanguage = myPrimaryLanguage + "Buttons";
  }

  /**
   * Opens the language dialog box.
   */
  public void openLanguageDialogBox() {
    languageDialogBox.open();
  }
}
