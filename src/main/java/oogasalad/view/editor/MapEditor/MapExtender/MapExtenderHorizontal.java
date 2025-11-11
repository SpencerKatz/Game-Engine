package oogasalad.view.editor.MapEditor.MapExtender;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import oogasalad.view.editor.MapEditor.BuildableMap;

/**
 * Represents a horizontal map extender.
 */
public class MapExtenderHorizontal extends MapExtenderAbstract {

  /**
   * Constructs a MapExtenderHorizontal object.
   *
   * @param bm             The buildable map.
   * @param onActionAdd    The event handler for the add action.
   * @param onActionRemove The event handler for the remove action.
   */
  public MapExtenderHorizontal(BuildableMap bm, EventHandler<MouseEvent> onActionAdd,
      EventHandler<MouseEvent> onActionRemove) {
    super(onActionAdd, onActionRemove);
    getAdder().setHeight(10);
    getRemover().setHeight(10);
    getAdder().setWidth(bm.getGridPane().getWidth());
    getRemover().setWidth(bm.getGridPane().getWidth());

    bm.getGridPaneProperty().addListener((observable, oldValue, newValue) -> {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.02), event -> {
        getAdder().setWidth(newValue.getWidth() / 2);
        getAdder().setX(newValue.getWidth() / 2);
        getRemover().setWidth(newValue.getWidth() / 2);
      }));
      timeline.play();
    });

    bm.getGridPane().widthProperty().addListener((observable, oldValue, newValue) -> {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
        getAdder().setWidth(newValue.doubleValue() / 2);
        getAdder().setX(newValue.doubleValue() / 2);
        getRemover().setWidth(newValue.doubleValue() / 2);
      }));
      timeline.play();
    });

  }
}
