package oogasalad.view.playing.component;

import javafx.scene.control.ProgressBar;
import oogasalad.model.api.GameInterface;

public class EnergyProgress extends ProgressBar {


  private final GameInterface game;

  private final double maximumEnergy;
  private double progress;

  public EnergyProgress(GameInterface game) {
    super(1.0);
    maximumEnergy =
        Double.parseDouble(game.getGameConfiguration().getRules().getString("energyAmount"));
    this.game = game;
  }

  public void update() {
    if (game.getGameState().getEnergy() / maximumEnergy == progress) {
      return;
    }
    progress = game.getGameState().getEnergy() / maximumEnergy;
    setProgress(progress);
  }


}
