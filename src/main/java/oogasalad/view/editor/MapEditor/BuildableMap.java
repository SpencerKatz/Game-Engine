package oogasalad.view.editor.MapEditor;

import javafx.scene.layout.GridPane;
import oogasalad.controller.MapController;

/**
 * Represents a buildable map in the map editor.
 */
public class BuildableMap {

  private final GridPaneProperty gridPaneProperty;
  private final CellInfoPane cip;
  private GridPane gp;
  private final MapController gameWorld;

  /**
   * Constructs a BuildableMap object.
   *
   * @param cip       The cell information pane.
   * @param gameWorld The map controller representing the game world.
   */
  public BuildableMap(CellInfoPane cip, MapController gameWorld) {
    gp = new GridPane();
    gp.setId("EditorGridPane");
    this.gridPaneProperty = new GridPaneProperty(gp);
    this.cip = cip;
    gp.setMaxWidth(Cell.getSize()[0] * gameWorld.getWidth());
    gp.setMaxHeight(Cell.getSize()[1] * gameWorld.getHeight());
    this.gameWorld = gameWorld;
    Cell.setGameMap(gameWorld);
    createGrid();
  }

  /**
   * Creates the grid for the buildable map.
   */
  private void createGrid() {
    GridPane temp = new GridPane();
    for (int i = 0; i < gameWorld.getWidth(); i++) {
      for (int j = 0; j < gameWorld.getHeight(); j++) {
        temp.add(new Cell(cip, i, j), i, j);
      }
    }
    setGridPane(temp);
  }

  // Methods for modifying the grid size

  /**
   * Modifies the grid size by changing both width and height.
   *
   * @param newI The new width.
   * @param newJ The new height.
   */
  public void modifyGridSizeBL(int newI, int newJ) {
    gameWorld.setWidth(newI);
    gameWorld.setHeight(newJ);
    createGrid();
  }

  // Methods for adding and removing rows and columns

  /**
   * Adds a row at the top of the grid.
   */
  public void addRowTop() {
    gameWorld.shiftDownAndAddRow();
    createGrid();
  }

  /**
   * Removes a row from the top of the grid.
   */
  public void removeRowTop() {
    gameWorld.shiftUpAndRemoveRow();
    createGrid();
  }

  /**
   * Adds a row at the bottom of the grid.
   */
  public void addRowBottom() {
    modifyGridSizeBL(gameWorld.getWidth(), gameWorld.getHeight() + 1);
  }

  /**
   * Removes a row from the bottom of the grid.
   */
  public void removeRowBottom() {
    modifyGridSizeBL(gameWorld.getWidth(), gameWorld.getHeight() - 1);
  }

  /**
   * Adds a column at the left side of the grid.
   */
  public void addColumnLeft() {
    gameWorld.shiftRightAndAddColumn();
    createGrid();
  }

  /**
   * Removes a column from the left side of the grid.
   */
  public void removeColumnLeft() {
    gameWorld.shiftLeftAndRemoveColumn();
    createGrid();
  }

  /**
   * Adds a column at the right side of the grid.
   */
  public void addColumnRight() {
    modifyGridSizeBL(gameWorld.getWidth() + 1, gameWorld.getHeight());
  }

  /**
   * Removes a column from the right side of the grid.
   */
  public void removeColumnRight() {
    modifyGridSizeBL(gameWorld.getWidth() - 1, gameWorld.getHeight());
  }

  /**
   * Gets the grid pane.
   *
   * @return The grid pane.
   */
  public GridPane getGridPane() {
    return gp;
  }

  /**
   * Sets the grid pane.
   *
   * @param newGridPane The new grid pane.
   */
  public void setGridPane(GridPane newGridPane) {
    gp = newGridPane;
    gridPaneProperty.set(newGridPane);
    gp.setMaxWidth(Cell.getSize()[0] * gameWorld.getWidth());
    gp.setMaxHeight(Cell.getSize()[1] * gameWorld.getHeight());
    gp.setId("EditorGridPane");
  }

  /**
   * Gets the grid pane property.
   *
   * @return The grid pane property.
   */
  public GridPaneProperty getGridPaneProperty() {
    return gridPaneProperty;
  }
}
