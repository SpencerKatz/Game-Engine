package oogasalad.view.editor.RuleEditor;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.controller.RuleController;
import oogasalad.model.data.GameConfiguration;

/**
 * The RuleEditor class represents the editor for rules. It contains the display of all rules and
 * provides methods to interact with them.
 */
public class RuleEditor extends HBox {

  private static final String DEFAULT_RESOURCE_PACKAGE = "view.editor.RuleEditor.";
  private final String myLanguage = "EnglishRule";
  private final ResourceBundle RuleResource;
  private final RuleController rc;

  /**
   * Constructs a RuleEditor object.
   *
   * @param gc The GameConfiguration object associated with the rules.
   */
  public RuleEditor(GameConfiguration gc) {
    super();
    RuleResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    rc = new RuleController(gc);
    AllRuleDisplay rd = new AllRuleDisplay(rc);
    VBox vbox = new VBox();
    vbox.getChildren().addAll(rd);
    vbox.setSpacing(10);
    super.setPadding(new Insets(0, 10, 10, 10));
    super.getChildren().add(rd);
  }

  /**
   * Gets the name associated with the RuleEditor.
   *
   * @return The name associated with the RuleEditor.
   */
  public String getName() {
    return rc.getName();
  }

}
