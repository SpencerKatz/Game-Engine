package oogasalad.view.editor.MapEditor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import oogasalad.view.editor.MapEditor.MapExtender.MapExtenderHorizontal;
import oogasalad.view.editor.MapEditor.MapExtender.MapExtenderVertical;

/**
 * Wrapper for the buildable map in the map editor, providing scrolling functionality and additional
 * UI elements.
 */
public class BuildableMapWrapper extends ScrollPane {

  private final BorderPane bp;
  private boolean rendered;

  /**
   * Constructs a BuildableMapWrapper object.
   *
   * @param bm The buildable map to be wrapped.
   */
  public BuildableMapWrapper(BuildableMap bm) {
    super();
    bp = getBorderPane(bm);
    HBox contentContainer = new HBox(bp); // Assuming BuildableMap extends Node
    contentContainer.setPrefWidth(100); // Set initial preferred width
    contentContainer.setPrefHeight(100); // Set initial preferred height
    contentContainer.setAlignment(Pos.CENTER);
    VBox vbox = new VBox(contentContainer);
    vbox.setAlignment(Pos.CENTER);
    super.setContent(vbox); // Set the content container

    super.setOnMouseClicked(e -> rendered = true);

    //Disable further resizing of the content
    super.widthProperty().addListener((observable, oldValue, newValue) -> {
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.3), event -> {
        if (!rendered) {
          super.setPrefSize(400, 400);
        }
      }));
      timeline.play();
    });

    bm.getGridPaneProperty().addListener((observable, oldValue, newValue) -> {
      bp.setCenter(newValue);
      Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.1), e -> {
        if (super.getWidth() > 3 + bp.getWidth()) {
          double padding = (super.getWidth() - bp.getWidth()) / 2;
          super.setPadding(new Insets(super.getPadding().getTop(), super.getPadding().getRight(),
              super.getPadding().getBottom(), padding));
        } else {
          super.setPadding(new Insets(super.getPadding().getTop(), super.getPadding().getRight(),
              super.getPadding().getBottom(), 0));
        }
        if (super.getHeight() > 3 + bp.getHeight()) {
          double padding = (super.getHeight() - bp.getHeight()) / 2;
          super.setPadding(
              new Insets(padding, super.getPadding().getRight(), super.getPadding().getBottom(),
                  super.getPadding().getLeft()));
        } else {
          super.setPadding(
              new Insets(0, super.getPadding().getRight(), super.getPadding().getBottom(),
                  super.getPadding().getLeft()));
        }
      }));
      timeline.play();
    });
  }

  /**
   * Creates a BorderPane with map extender controls.
   *
   * @param bm The buildable map.
   * @return The created BorderPane.
   */
  private BorderPane getBorderPane(BuildableMap bm) {
    BorderPane bp = new BorderPane();
    bp.setCenter(bm.getGridPane());
    setAlignment(bm.getGridPane());

    bp.setRight(new MapExtenderVertical(bm, e -> bm.addColumnRight(), e -> bm.removeColumnRight()));
    setAlignment(bp.getRight());

    bp.setLeft(new MapExtenderVertical(bm, e -> bm.addColumnLeft(), e -> bm.removeColumnLeft()));
    setAlignment(bp.getLeft());

    bp.setTop(new MapExtenderHorizontal(bm, e -> bm.addRowTop(), e -> bm.removeRowTop()));
    setAlignment(bp.getTop());

    bp.setBottom(new MapExtenderHorizontal(bm, e -> bm.addRowBottom(), e -> bm.removeRowBottom()));
    setAlignment(bp.getBottom());

    return bp;
  }

  /**
   * Sets the alignment of a node within the BorderPane to CENTER.
   *
   * @param n The node to set alignment for.
   */
  private void setAlignment(Node n) {
    BorderPane.setAlignment(n, Pos.CENTER);
  }

}
