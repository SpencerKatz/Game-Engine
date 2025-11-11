package oogasalad.view.playing.component;

import javafx.scene.layout.GridPane;
import oogasalad.model.api.GameInterface;
import oogasalad.model.api.ReadOnlyGameWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A 2d grid representing the land and the plants (buildings).
 */
public class LandView extends GridPane {

  private final Pile[][] piles;

  private final ReadOnlyGameWorld readOnlyGameWorld;

  private final GameInterface game;

  private static final Logger LOG = LogManager.getLogger(LandView.class);


  public LandView(GameInterface game, double landGridPaneWidth, double landGridPaneHeight) {
    super();
    this.readOnlyGameWorld = game.getGameState().getGameWorld();
    this.game = game;
    piles = new Pile[readOnlyGameWorld.getHeight()][readOnlyGameWorld.getWidth()];
    for (int i = 0; i < piles.length; i++) {
      for (int j = 0; j < piles[0].length; j++) {
        piles[i][j] = new Pile(i, j, this, landGridPaneWidth, landGridPaneHeight);
        this.add(piles[i][j], j, i);
      }
    }
  }

  /**
   * Update the landView
   */
  public void update() {
    for (int i = 0; i < readOnlyGameWorld.getHeight(); i++) {
      for (int j = 0; j < readOnlyGameWorld.getWidth(); j++) {
        piles[i][j].update(readOnlyGameWorld.getImagePath(j, i, 0));
      }
    }
  }

  /**
   * Get the grid view to be displayed by javafx.
   *
   * @return the grid view to be displayed by javafx
   */
  public void interact(int x, int y) {
    LOG.info("interact with the gird at %d, %d".formatted(x, y));
    game.interact(x, y, 0);
  }

  public int getRow() {
    return piles.length;
  }

  public int getCol() {
    return piles[0].length;
  }
}
