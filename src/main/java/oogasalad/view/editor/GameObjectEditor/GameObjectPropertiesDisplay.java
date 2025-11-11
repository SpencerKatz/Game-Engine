package oogasalad.view.editor.GameObjectEditor;

import javafx.scene.layout.VBox;
import oogasalad.controller.GameObjectController;
import oogasalad.view.editor.MapEditor.Selector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Displays the properties of a game object.
 */
public class GameObjectPropertiesDisplay extends VBox {

  private static final Logger LOG = LogManager.getLogger(GameObjectPropertiesDisplay.class);
  private final PropertiesDisplay pd;
  private final GameObjectController gc;

  /**
   * Constructs a GameObjectPropertiesDisplay.
   *
   * @param update The Runnable to be executed for updating.
   * @param gc     The GameObjectController to manage game objects.
   */
  public GameObjectPropertiesDisplay(Runnable update, GameObjectController gc) {
    super();
    this.gc = gc;
    pd = new PropertiesDisplay(update, gc);
    Selector.lastSelectedProperty().addListener(
        (observable, oldValue, newValue) -> setContents(Selector.getLastSelectedSelectable()));
    getChildren().add(pd);
  }

  /**
   * Sets the contents of the display based on the selected game object.
   *
   * @param key The key of the selected game object.
   */
  private void setContents(String key) {
    gc.setKey(key);
    pd.setContents(key);
  }
}
