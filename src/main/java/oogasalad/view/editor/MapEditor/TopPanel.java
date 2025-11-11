package oogasalad.view.editor.MapEditor;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Panel at the top of the map editor containing buttons and title.
 */
public class TopPanel extends StackPane {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.MapEditor.TopPanel.";
  private final String buttonLanguage = "EnglishButtons";
  private final String titleLanguage = "EnglishTitle";
  private final ResourceBundle buttonResource;
  private final ResourceBundle titleResource;
  private final Stage primaryStage;
  private final Scene previousScene;

  /**
   * Constructs the top panel of the map editor.
   *
   * @param stage     The primary stage.
   * @param backScene The scene to go back to.
   * @param bm        The buildable map associated with the editor.
   */
  public TopPanel(Stage stage, Scene backScene, BuildableMap bm) {
    super();
    this.primaryStage = stage;
    this.previousScene = backScene;

    titleResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + titleLanguage);
    buttonResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + buttonLanguage);

    Label l = new Label(titleResource.getString("map_editor"));
    HBox hbox = new HBox(l);
    hbox.setMinWidth(400);
    hbox.setMaxWidth(400);
    hbox.setAlignment(Pos.CENTER);
    l.getStyleClass().add("map-label");

    SizeChangeButton scb =
        new SizeChangeButton(buttonResource.getString("size_change"), (newI, newJ) -> {
          SizeChangeDialogBox dialog = new SizeChangeDialogBox();
          String[] newSize = dialog.getFields();
          if (newSize != null) {
            bm.modifyGridSizeBL(Integer.parseInt(newSize[1]), Integer.parseInt(newSize[0]));
          }
        });

    Button backButton = new Button("Back");
    backButton.setOnAction(event -> goBack());

    HelpButton hb = new HelpButton(buttonResource.getString("help"), e -> {
      HelpDialogBox dialog = new HelpDialogBox();
      dialog.show(buttonResource.getString("help"));
    });

    BorderPane bp = new BorderPane();
    bp.setLeft(hb);
    bp.setCenter(backButton);
    bp.setRight(scb);

    BorderPane.setAlignment(hb, Pos.CENTER_LEFT);
    BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
    BorderPane.setAlignment(scb, Pos.CENTER_RIGHT);
    super.getChildren().addAll(hbox, bp);
  }

  /**
   * Returns to the previous scene.
   */
  private void goBack() {
    primaryStage.setScene(previousScene);
  }

}
