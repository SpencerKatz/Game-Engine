package oogasalad.view.editor.MapEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;
import oogasalad.view.editor.EditorScene;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class EditorSceneTest extends DukeApplicationTest {
  private Stage stage;
  private EditorScene editorScene;
  private Cell cell;
  private CellInfoPane cellInfoPane;
  private SelectableView lava;
  @Override
  public void start(Stage stage) {
    this.stage = stage;
    editorScene = new EditorScene(stage, "English", null, new GameConfiguration());
    editorScene.start();
//    this.cell = (Cell) lookup("#0 0").query();
    sleep(1000);
    // When trying to find the Cell with id "0 0"
    cell = lookup("#EditorGridPane #0_0").queryAs(Cell.class);
    cellInfoPane = lookup("#CellInfoPane").queryAs(CellInfoPane.class);
  }

  @Test
  @DisplayName("Test CellInfoPane Coordinates")
  public void testCellInfoPaneCoordinates() {
    int xCor = 0;
    int yCor = 0;
    sleep(500);
    clickOn(cell);
    sleep(500);
    assertEquals(cellInfoPane.getxCor(), xCor);
    assertEquals(cellInfoPane.getyCor(), yCor);
  }

  @Test
  @DisplayName("Test CellInfoPane Info")
  public void testCellInfoPaneInfo() {
    String contents = "Dirt, Wheat";
    sleep(100);
    clickOn(cell);
    sleep(500);
    assertTrue(cellInfoPane.getContentString().equals(contents));

  }


}
