package oogasalad.view.editor.GameObjectEditor;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Display for an object property.
 */
public class ObjectPropertyDisplay {

  private final TextField tf;
  private final String name;

  /**
   * Constructs an ObjectPropertyDisplay with specified key, value, elements, and index.
   *
   * @param key      The key of the property.
   * @param value    The value of the property.
   * @param elements The list of elements.
   * @param index    The index to insert at.
   */
  public ObjectPropertyDisplay(String key, String value, List<Node> elements, int index) {
    Label nameLabel = new Label(key);
    nameLabel.getStyleClass().add("object-property");
    BorderPane bp = new BorderPane();
    TextField tf = new TextField(value);
    tf.setId(key);
    bp.setLeft(nameLabel);
    bp.setRight(tf);
    elements.add(index, bp);
    this.tf = tf;
    name = key;
  }

  /**
   * Constructs an ObjectPropertyDisplay with specified key, value, and elements.
   *
   * @param key      The key of the property.
   * @param value    The value of the property.
   * @param elements The list of elements.
   */
  public ObjectPropertyDisplay(String key, String value, List<Node> elements) {
    Label nameLabel = new Label(key);
    nameLabel.getStyleClass().add("object-property");
    BorderPane bp = new BorderPane();
    TextField tf = new TextField(value);
    tf.setId(key);
    bp.setLeft(nameLabel);
    bp.setRight(tf);
    elements.add(bp);
    this.tf = tf;
    name = key;
  }

  /**
   * Retrieves the name of the property.
   *
   * @return The name of the property.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the value of the property.
   *
   * @return The value of the property.
   */
  public String getValue() {
    return String.valueOf(tf.getText());
  }
}
