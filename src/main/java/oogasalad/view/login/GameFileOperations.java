package oogasalad.view.login;


import static oogasalad.view.login.Utils.HEIGHT;
import static oogasalad.view.login.Utils.WIDTH;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oogasalad.database.GameSaveData;
import oogasalad.database.InfoService;
import oogasalad.model.api.GameInterface;
import oogasalad.view.branch.BranchBase;
import oogasalad.view.playing.PlayingPageView;

/**
 * Class to handle game file operations
 */
public class GameFileOperations extends BranchBase {

  private final int userId;
  private final GameInterface game;


  public GameFileOperations(Stage stage, Scene previousScene, int userId, GameInterface game) {
    super(stage, previousScene);
    this.userId = userId;
    this.game = game;
  }

  public Scene createScene() throws Exception {
    return FileUtility.createScene("src/main/resources/view/login/game_file_operations_layout.json",
        this::setupEventHandlers);
  }

  private void setupEventHandlers(Object instance, String eventName) {
    if (instance instanceof Button button) {
      Runnable action = getActionForEvent(eventName);
      if (action != null) {
        button.setOnAction(e -> {
          try {
            action.run();
          } catch (Exception ex) {
            throw new RuntimeException("Error executing action for " + eventName, ex);
          }
        });
      }
    }
  }

  private Runnable getActionForEvent(String eventName) {
    switch (eventName) {
      case "loadGame":
        return this::safeLoadGame;
      case "saveGame":
        return this::safeSaveGame;
      case "center":
        return this::safeCenterView;
      case "back":
        return () -> getStage().setScene(getPreviousScene());
      case "logout":
        return this::logout;
      default:
        return null;
    }
  }

  private void safeLoadGame() {
    try {
      loadGame();
    } catch (IOException ex) {
      handleException(ex);
    }
  }

  private void safeSaveGame() {
    try {
      saveGame();
    } catch (IOException ex) {
      handleException(ex);
    }
  }

  private void safeCenterView() {
    try {
      ThoughtsView thoughtsView = new ThoughtsView(getStage(), getPreviousScene());
      getStage().setScene(thoughtsView.createScene());
    } catch (Exception ex) {
      handleException(ex);
    }
  }

  private void logout() {
    UserSession.clearAllPreferences();
    getStage().close();
  }

  private void handleException(Exception ex) {
    throw new RuntimeException("Operation failed", ex);
  }


  private void loadGame() throws IOException {
    GameSaveData saveData = InfoService.loadLatestGameData(userId);
    FileUtility.saveJsonToFile(userId, saveData.getGameSave(), saveData.getGameConfiguration(),
        saveData.getConfigurableStores());
    PlayingPageView playingPageView =
        new PlayingPageView(getStage(), "English", userId + "", userId + "", WIDTH, HEIGHT);
    playingPageView.start();
  }


  private void saveGame() throws IOException {
    game.save(userId + ".json");
    game.getGameConfiguration().save(userId + ".json");
    String[] saves = FileUtility.readFileAsString(userId);
    InfoService.saveGameData(userId, saves[0], saves[1], saves[2]);
  }
}
