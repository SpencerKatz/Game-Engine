package oogasalad.view.playing.component;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import oogasalad.model.api.GameInterface;

public class ResultPage {

  private final GameInterface game;

  private final Stage stage;

  public ResultPage(GameInterface game, Stage stage) {
    this.game = game;
    this.stage = stage;
  }

  public void show() {
    new Alert(AlertType.CONFIRMATION, "Your game has ended! \nGame Time: %s\n money:%d".formatted(
        game.getGameState().getGameTime(), game.getGameState().getMoney())).showAndWait();
    stage.close();
  }


}
