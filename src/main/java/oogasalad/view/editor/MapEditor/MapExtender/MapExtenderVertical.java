package oogasalad.view.editor.MapEditor.MapExtender;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import oogasalad.view.editor.MapEditor.BuildableMap;

/**
 * Represents a vertical map extender.
 */
public class MapExtenderVertical extends MapExtenderAbstract {

  /**
   * Constructs a MapExtenderVertical object.
   *
   * @param bm             The buildable map.
   * @param onActionAdd    The event handler for the add action.
   * @param onActionRemove The event handler for the remove action.
   */
  public MapExtenderVertical(BuildableMap bm, EventHandler<MouseEvent> onActionAdd,
      EventHandler<MouseEvent> onActionRemove) {
    super(onActionAdd, onActionRemove);
    getAdder().setHeight(bm.getGridPane().getHeight());
    getRemover().setHeight(bm.getGridPane().getHeight());
    getAdder().setWidth(10);
    getRemover().setWidth(10);

    // Listen for property changes
    bm.getGridPane().heightProperty().addListener((observable, oldValue, newValue) -> {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
        getAdder().setHeight(newValue.doubleValue() / 2);
        getRemover().setHeight(newValue.doubleValue() / 2);
        getRemover().setY(newValue.doubleValue() / 2);
      }));
      timeline.play();
    });

    bm.getGridPaneProperty().addListener((observable, oldValue, newValue) -> {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.02), event -> {
        getAdder().setHeight(newValue.getHeight() / 2);
        getRemover().setHeight(newValue.getHeight() / 2);
        getRemover().setY(newValue.getHeight() / 2);
      }));
      timeline.play();
    });
  }
}
