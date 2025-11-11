package oogasalad.view.editor.MapEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.controller.MapController;
import oogasalad.model.data.GameConfiguration;

/**
 * A container for the map editor components.
 */
public class MapEditor extends VBox {

  private final Stage stage;
  private final Scene backScene;
  private final GameConfiguration gc;

  /**
   * Constructs a MapEditor object.
   *
   * @param stage     The primary stage of the application.
   * @param backScene The scene to return to when exiting the map editor.
   * @param gc        The game configuration.
   */
  public MapEditor(Stage stage, Scene backScene, GameConfiguration gc) {
    super();
    super.setAlignment(Pos.TOP_CENTER);
    this.stage = stage;
    this.backScene = backScene;
    this.gc = gc;
    update();
  }

  /**
   * Updates the map editor components.
   */
  public void update() {
    getChildren().clear();
    CellInfoPane cip = new CellInfoPane();
    BuildableMap bm = new BuildableMap(cip, new MapController(gc));
    TopPanel tp = new TopPanel(stage, backScene, bm);
    BuildableMapWrapper bmw = new BuildableMapWrapper(bm);
    BottomPanel bp =
        new BottomPanel(GameConfiguration.getConfigurablesStore().getAllConfigurables());
    getChildren().addAll(tp, bmw, bp, cip);
  }
}
