package oogasalad.view.playing;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import oogasalad.controller.GameKeyHandler;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.view.buttonmenu.ButtonMenu;
import oogasalad.view.gpt.Chat;
import oogasalad.view.login.LoginView;
import oogasalad.view.playing.component.BagView;
import oogasalad.view.playing.component.EnergyProgress;
import oogasalad.view.playing.component.LandView;
import oogasalad.view.playing.component.ResultPage;
import oogasalad.view.shopping.ShoppingView;
import oogasalad.view.shopping.components.top.CurrentMoneyHbox;
import oogasalad.view.start.LoaderListDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is the view for the playing page. It displays the land grid, tools, and items. It also
 * has a timer and a progress bar for energy. It has a timeline that updates the land grid every
 * second.
 */

public class PlayingPageView {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.playing.";
  private static final String DEFAULT_RESOURCE_FOLDER = "src/main/resources/view/playing/";
  private static final Logger LOG = LogManager.getLogger(PlayingPageView.class);
  private ResourceBundle displayTextResource;
  private String menuButtons;
  private final Label timeLabel = new Label();

  private final WindowSizeWrapper windowSize;

  private final EnergyProgress energyProgress;
  private final Stage stage;
  private final String primaryLanguage;

  private final GameFactory gameFactory = new GameFactory();
  private final GameInterface game;
  private Button helpButton;
  private ButtonMenu btm;
  private LandView landView;
  private BagView bagView;
  private Scene previousScene;
  private Timeline timeline;
  private CurrentMoneyHbox moneyBox;

  /**
   * Create a game Play with the default game
   *
   * @param primaryStage the stage
   * @param language     the language of the game view
   * @param backScene    the previous scene that creates the game
   */
  public PlayingPageView(Stage primaryStage, String language, Scene backScene) {
    stage = primaryStage;
    primaryLanguage = language;
    setFileLanguages();
    this.previousScene = backScene;
    game = gameFactory.createGame();
    energyProgress = new EnergyProgress(game);
    windowSize = new WindowSizeWrapper(WindowSizeWrapper.defaultWindowWidth,
        WindowSizeWrapper.defaultWindowHeight, game);
  }

  /**
   * Create a game Play given the config file Path and Game Save file Path
   *
   * @param primaryStage   the stage
   * @param language       the language
   * @param saveFilePath   the path of the game saves
   * @param configFilePath the config path
   * @param windowWidth    the width of the window
   * @param windowHeight   the height of the window
   * @throws IOException
   */

  public PlayingPageView(Stage primaryStage, String language, String saveFilePath,
      String configFilePath, int windowWidth, int windowHeight) throws IOException {
    GameInterface gameTemp;
    stage = primaryStage;
    primaryLanguage = language;
    setFileLanguages();
    try {
      if (saveFilePath == null) {
        gameTemp = gameFactory.createGame(configFilePath);
      } else {
        gameTemp = gameFactory.createGame(configFilePath, saveFilePath);
      }
    } catch (IOException e) {
      LOG.info("cannot find game saves, load from the default game");
      gameTemp = gameFactory.createGame();
    }
    game = gameTemp;
    energyProgress = new EnergyProgress(game);
    windowSize = new WindowSizeWrapper(windowWidth, windowHeight, game);
  }

  /**
   * Start the game
   */
  public void start() {
    LOG.info("initializing game model");
    initModel();
    LOG.info("finish loading game model");

    StackPane root = new StackPane();
    root.getStyleClass().add("playing-root");
    BorderPane borderPane = new BorderPane();
    setupTop(borderPane);
    setupLeftRight(borderPane);
    setupCenter(borderPane);
    setupBottom(borderPane);
    root.getChildren().addAll(borderPane);
    StackPane.setAlignment(borderPane, javafx.geometry.Pos.TOP_LEFT);
    Scene scene = new Scene(root, windowSize.getWindowWidth(), windowSize.getWindowHeight());
    scene.getStylesheets().add("styles.css");
    scene.setOnKeyPressed(new GameKeyHandler(game));
    stage.setTitle(displayTextResource.getString("play_title"));
    stage.setScene(scene);
    setUpdate();
    stage.show();
  }

  /**
   * Save the game to the file
   */
  public void save() {
    FileChooser result = new FileChooser();
    result.setTitle(displayTextResource.getString("save_location"));
    result.setInitialDirectory(new File(LoaderListDisplay.DEFAULT_SAVES_FOLDER));
    result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Files", "*.json"));
    result.setInitialFileName("test.json");
    File file = result.showSaveDialog(stage);
    if (file == null) {
      return;
    }
    try {
      game.save(file.getName());
      game.getGameConfiguration().save(file.getName());
    } catch (IOException | InvalidPathException e) {
      new Alert(AlertType.ERROR, displayTextResource.getString("saving_failed")).showAndWait();
    }
    new Alert(AlertType.CONFIRMATION, displayTextResource.getString("save_done")).showAndWait();
    LOG.info("saving done");
  }


  private void openAndCloseMenu() {
    if (btm == null) {
      LOG.info("Opened Button Menu");
      btm = new ButtonMenu(stage, primaryLanguage, previousScene, menuButtons);
      btm.open();
    } else {
      btm.closeMenu();
      btm = null;
    }
  }

  private void initModel() {
    bagView =
        new BagView(game, 10, windowSize.getBottomCellWidth(), windowSize.getBottomCellHeight(),
            windowSize.getBottomBoxWidth(), windowSize.getBottomBoxHeight());
    bagView.setId("bagView");
    landView =
        new LandView(game, windowSize.getLandGridPaneWidth(), windowSize.getLandGridPaneHeight());
    landView.setId("landView");
  }

  private void setUpdate() {
    timeline = new Timeline();
    KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0 / 30), event -> {
      if (!game.isGameOver()) {
        game.update();
        landView.update();
        bagView.update();
        updateTimeLabel();
        energyProgress.update();
        moneyBox.update();
      } else {
        timeline.stop();
        Platform.runLater(this::endGame);
      }
    });
    timeline.getKeyFrames().add(keyFrame);
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void endGame() {
    ResultPage resultPage = new ResultPage(game, stage);
    resultPage.show();
  }

  private void updateTimeLabel() {
    timeLabel.setText("" + game.getGameState().getGameTime());
  }

  private void setupTop(BorderPane root) {
    HBox topBox = new HBox();
    topBox.setPrefSize(windowSize.getTopWidth(), windowSize.getTopHeight());
    topBox.getStyleClass().add("top-box");
    createHelpButton();
    Button menu = setUpMenuButton();
    Button btnOpenShop = setShopButton();
    timeLabel.getStyleClass().add("play-top-label");
    timeLabel.setId("time-label");
    moneyBox = setMoneyBox();
    Button sleepButton = setSleepButton();
    Button saveButton = setSaveButton();
    Button loginButton = new Button(displayTextResource.getString("web"));
    loginButton.setId("login-button");
    setButtonSize(loginButton, windowSize.getTopButtonWidth(), windowSize.getTopButtonHeight(),
        windowSize.getTopFontSize());
    loginButton.setOnAction(e -> openLogin());
    topBox.getChildren()
        .addAll(menu, helpButton, sleepButton, saveButton, timeLabel, energyProgress, btnOpenShop,
            moneyBox, loginButton);
    root.setTop(topBox);
  }

  private Button setUpMenuButton() {
    Button menu = new Button(displayTextResource.getString("menu"));
    menu.setId("menu_button");
    setButtonSize(menu, windowSize.getTopButtonWidth(), windowSize.getTopButtonHeight(),
        windowSize.getTopFontSize());
    menu.setOnAction(event -> openAndCloseMenu());
    menu.getStyleClass().add("menu_button");
    menu.setAlignment(Pos.CENTER);
    return menu;
  }

  private Button setShopButton() {
    Button btnOpenShop = new Button();
    btnOpenShop.setId("shopButton");
    btnOpenShop.setOnAction(e -> openShop());
    return btnOpenShop;
  }

  private Button setSleepButton() {
    Button sleepButton = new Button(displayTextResource.getString("sleep"));
    setButtonSize(sleepButton, windowSize.getTopButtonWidth(), windowSize.getTopButtonHeight(),
        windowSize.getTopFontSize());
    sleepButton.setId("sleep-button");
    sleepButton.setOnAction(event -> {
      LOG.info("slept");
      game.sleep();
    });
    return sleepButton;
  }

  private Button setSaveButton() {
    Button saveButton = new Button(displayTextResource.getString("save"));
    saveButton.setId("save-button");
    saveButton.setOnAction(event -> save());
    setButtonSize(saveButton, windowSize.getTopButtonWidth(), windowSize.getTopButtonHeight(),
        windowSize.getTopFontSize());
    return saveButton;
  }

  private CurrentMoneyHbox setMoneyBox() {
    CurrentMoneyHbox moneyBox = new CurrentMoneyHbox(game);
    moneyBox.update();
    moneyBox.setAlignment(Pos.CENTER);
    return moneyBox;
  }

  private void setupCenter(BorderPane root) {
    root.setCenter(landView);
  }

  private void setupBottom(BorderPane root) {
    HBox bottomBox = new HBox();
    bottomBox.setPadding(new Insets(windowSize.getPadding()));
    bottomBox.setPrefSize(windowSize.getBottomWidth(), windowSize.getBottomHeight());
    bottomBox.getStyleClass().add("bottom-box");
    StackPane toolStackPane = bagView;
    bottomBox.getChildren().addAll(toolStackPane);
    root.setBottom(bottomBox);
  }

  private void setupLeftRight(BorderPane root) {
    VBox leftBox = new VBox();
    leftBox.setPrefSize(windowSize.getLeftRightWidth(), windowSize.getLeftRightHeight());
    VBox rightBox = new VBox();
    rightBox.setPrefSize(windowSize.getLeftRightWidth(), windowSize.getLeftRightHeight());
    root.setLeft(leftBox);
    root.setRight(rightBox);
  }

  private void openShop() {
    Scene scene = stage.getScene();
    ShoppingView shoppingPageView = new ShoppingView(game, stage, scene, this);
    Scene shoppingScene = new Scene(shoppingPageView.getScene());
    shoppingScene.getStylesheets().add("styles.css");
    stage.setScene(shoppingScene);
    stage.show();
  }

  private void createHelpButton() {
    helpButton = new Button();
    helpButton.setId("help-button");
    helpButton.setOnAction(e -> {
      Stage chatStage = new Stage();
      Chat chatApp = new Chat(chatStage);
      chatApp.start();
    });
  }

  private void setFileLanguages() {
    displayTextResource =
        ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + primaryLanguage + "DisplayText");
    menuButtons = DEFAULT_RESOURCE_FOLDER + primaryLanguage + "MenuButtons.csv";
  }

  private void openLogin() {
    LoginView loginView = new LoginView(game);
    loginView.start(new Stage());
  }

  private void setButtonSize(Button button, double width, double height, double fontSize) {
    button.setPrefWidth(width);
    button.setPrefHeight(height);
    button.setStyle(String.format("-fx-font-size: %.1fpx;", fontSize));
  }
}
