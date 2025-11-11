package oogasalad.view.editor.GameObjectEditor;

import javafx.scene.control.Alert;

/**
 * Class to display a validation error alert.
 */
public class ValidationErrorAlert {

  /**
   * Constructs a validation error alert with the specified rule name, rule value, and type.
   *
   * @param ruleName  The name of the rule causing the validation error.
   * @param ruleValue The value of the rule causing the validation error.
   * @param type      The expected type of the rule.
   */
  public ValidationErrorAlert(String ruleName, String ruleValue, String type) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
    alert.setContentText(
        "Rule: " + ruleName + " must be of type " + type + "\nEntered: " + ruleValue);
    alert.showAndWait();
  }
}
