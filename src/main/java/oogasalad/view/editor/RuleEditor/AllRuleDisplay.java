package oogasalad.view.editor.RuleEditor;

import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import oogasalad.controller.RuleController;
import oogasalad.view.editor.GameObjectEditor.PropertiesDisplay;

/**
 * A VBox that displays all rules for editing.
 */
public class AllRuleDisplay extends VBox {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.RuleEditor.AllRuleDisplay.";
  private final String myLanguage = "EnglishRule";
  private final ResourceBundle RuleResource;

  /**
   * Constructs the AllRuleDisplay.
   *
   * @param rc The RuleController associated with the rules.
   */
  public AllRuleDisplay(RuleController rc) {
    super();
    RuleResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    PropertiesDisplay pd = new PropertiesDisplay(null, rc);
    pd.setContents(RuleResource.getString("rules"));
    getChildren().add(pd);
  }

}
