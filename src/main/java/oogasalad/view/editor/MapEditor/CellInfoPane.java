package oogasalad.view.editor.MapEditor;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the information panel for a cell in the map grid.
 */
public class CellInfoPane extends HBox {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.MapEditor.CellInfoPane.";
  private final String displayTextLanguage = "EnglishDisplayText";
  private final ResourceBundle displayTextResource;
  private static final Logger LOG = LogManager.getLogger(CellInfoPane.class);
  private final Label displayText;
  private String contentString;
  private int xCor;
  private int yCor;

  /**
   * Constructs a cell information panel.
   */
  public CellInfoPane() {
    super();
    this.setId("CellInfoPane");
    displayTextResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + displayTextLanguage);
    displayText = new Label();
    super.getChildren().add(displayText);
    displayText.setStyle("-fx-font-size: 25");
    clearDisplay();
    super.setMinHeight(displayText.getHeight());
  }

  /**
   * Sets the display content for the cell information panel.
   *
   * @param xpos    The x-coordinate of the cell.
   * @param ypos    The y-coordinate of the cell.
   * @param content The content to be displayed.
   */
  public void setDisplay(int xpos, int ypos, List<String> content) {
    xCor = xpos;
    yCor = ypos;
    contentString = buildContentString(content);
    displayText.setText(displayTextResource.getString("position") + " " + xCor + "," + yCor + "\n"
        + displayTextResource.getString("cell") + " " + contentString);
  }

  private String buildContentString(List<String> content) {
    return String.join(", ", content);
  }

  /**
   * Clears the display content of the cell information panel.
   */
  public void clearDisplay() {
    displayText.setText(
        displayTextResource.getString("position") + " \n" + displayTextResource.getString("cell")
            + " ");
  }

  /**
   * Gets the x-coordinate of the cell.
   *
   * @return The x-coordinate of the cell.
   */
  public int getxCor() {
    return xCor;
  }

  /**
   * Gets the y-coordinate of the cell.
   *
   * @return The y-coordinate of the cell.
   */
  public int getyCor() {
    return yCor;
  }

  /**
   * Gets the content string of the cell.
   *
   * @return The content string of the cell.
   */
  public String getContentString() {
    return contentString;
  }
}
