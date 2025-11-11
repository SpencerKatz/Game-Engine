package oogasalad.view.playing;

import oogasalad.model.api.GameInterface;

/**
 * This class is used to wrap all the size related variables of the playing page. Used to reduce the
 * code density of the view page
 */
public class WindowSizeWrapper {

  public static final int defaultWindowWidth = 800;

  public static final int defaultWindowHeight = 600;

  private final int windowWidth;

  private final int windowHeight;


  private final double landCellWidth;
  private final double landCellHeight;
  private final double bottomCellWidth;
  private final double bottomCellHeight;
  private final double bottomBoxWidth;
  private final double bottomBoxHeight;
  private final int landNumRows;
  private final int landNumCols;
  private final double topHeight;
  private final double topWidth;
  private final double bottomHeight;
  private final double bottomWidth;
  private final double padding;
  private final double leftRightWidth;
  private final double topButtonWidth;
  private final double topButtonHeight;
  private final double topFontSize;
  private final double landGridPaneWidth;
  private final double landGridPaneHeight;
  private final double leftRightHeight;

  public WindowSizeWrapper(int windowWidth, int windowHeight, GameInterface game) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    padding = windowWidth / 100;
    landNumRows = game.getGameState().getGameWorld().getHeight();
    landNumCols = game.getGameState().getGameWorld().getWidth();
    landCellWidth = windowWidth / 20;
    landCellHeight = windowWidth / 20;
    bottomCellWidth = landCellWidth / 1.5;
    bottomCellHeight = landCellHeight / 1.5;
    bottomBoxWidth = windowWidth / 2;
    bottomHeight = (windowHeight - landNumRows * landCellHeight) / 2;
    bottomBoxHeight = bottomHeight - padding;
    topWidth = windowWidth;
    topHeight = windowHeight - bottomHeight - landNumRows * landCellHeight;
    leftRightWidth = (windowWidth - landNumCols * landCellWidth) / 2;
    bottomWidth = windowWidth;
    landGridPaneWidth = landCellWidth * landNumCols;
    landGridPaneHeight = landCellHeight * landNumRows;
    leftRightHeight = windowHeight - bottomHeight - topHeight;
    topButtonWidth = topWidth / 15;
    topButtonHeight = topHeight / 3;
    topFontSize = topButtonHeight / 6;
  }

  public int getWindowWidth() {
    return windowWidth;
  }

  public int getWindowHeight() {
    return windowHeight;
  }

  public double getLandCellWidth() {
    return landCellWidth;
  }

  public double getLandCellHeight() {
    return landCellHeight;
  }

  public double getBottomCellWidth() {
    return bottomCellWidth;
  }

  public double getBottomCellHeight() {
    return bottomCellHeight;
  }

  public double getBottomBoxWidth() {
    return bottomBoxWidth;
  }

  public double getBottomBoxHeight() {
    return bottomBoxHeight;
  }

  public int getLandNumRows() {
    return landNumRows;
  }

  public int getLandNumCols() {
    return landNumCols;
  }

  public double getTopHeight() {
    return topHeight;
  }

  public double getTopWidth() {
    return topWidth;
  }

  public double getBottomHeight() {
    return bottomHeight;
  }

  public double getBottomWidth() {
    return bottomWidth;
  }

  public double getPadding() {
    return padding;
  }

  public double getLeftRightWidth() {
    return leftRightWidth;
  }

  public double getTopButtonWidth() {
    return topButtonWidth;
  }

  public double getTopButtonHeight() {
    return topButtonHeight;
  }

  public double getTopFontSize() {
    return topFontSize;
  }

  public double getLandGridPaneWidth() {
    return landGridPaneWidth;
  }

  public double getLandGridPaneHeight() {
    return landGridPaneHeight;
  }

  public double getLeftRightHeight() {
    return leftRightHeight;
  }
}
