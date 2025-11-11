package oogasalad.controller;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.data.GameConfiguration;
import oogasalad.model.gameplay.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles key events in the game
 */
public class GameKeyHandler implements EventHandler<KeyEvent> {

  private final Game game;
  private static final Logger LOG = LogManager.getLogger(GameKeyHandler.class);

  public GameKeyHandler(GameInterface game) {
    this.game = (Game) game;
  }

  @Override
  public void handle(KeyEvent event) {
    switch (event.getCode()) {
      case M:
        LOG.info("Activated cheat code: Added 1000 monies!");
        game.getEditableGameState().addMoney(1000);
        break;
      case S:
        LOG.info("Activated cheat code: Forced shop rotation!");
        game.getEditableGameState().getEditableShop()
            .forceItemRotation(game.getGameState().getGameTime());
        break;
      case N:
        LOG.info("Activated cheat code: Skip 1 hour ahead!");
        game.getEditableGameState().getEditableGameTime().advance(60);
        break;
      case E:
        LOG.info("Activated cheat code: Restored all energy!");
        game.getEditableGameState().restoreEnergy(Integer.MAX_VALUE);
        break;
      case A:
        LOG.info("Activated cheat code: Gave you one of every item in the config!");
        for (Map.Entry<String, ReadOnlyProperties> entry : GameConfiguration.getConfigurablesStore()
            .getAllConfigurables().entrySet()) {
          if (entry.getValue().getString("type").equalsIgnoreCase("item")) {
            game.getEditableGameState().getEditableBag().addItem(entry.getKey(), 1);
          }
        }
        break;
    }
  }
}
