package oogasalad.view.editor.RuleEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;
import oogasalad.view.editor.EditorScene;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.Map;

public class RuleChangeTest extends DukeApplicationTest {
    private Stage stage;
    private EditorScene editorScene;
    private GameConfiguration config;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        config = new GameConfiguration();
        editorScene = new EditorScene(stage, "English", null, config);
        editorScene.start();
    }

    @Test
    @DisplayName("Test one rule change")
    public void testOneRuleChange() {
        TextField rule = lookup("#doEnergy").queryAs(TextField.class);
        assertEquals("true", config.getRules().getCopyOfProperties().get("doEnergy"));
        sleep(5000);
        rule.setText("false");
        Button save = lookup("#SavePropertiesRules").queryButton();
        clickOn(save);
        sleep(1000);
        assertEquals("false", config.getRules().getCopyOfProperties().get("doEnergy"));
    }

    @Test
    @DisplayName("Test all rule change")
    public void testAllRuleChange() {
        for(String key: config.getRules().getCopyOfProperties().keySet()){
            if(config.getRules().getCopyOfPropertyTypes().get("boolean").contains(key)){
                TextField ruleBox = lookup("#" + key).queryAs(TextField.class);
                ruleBox.setText("false");
            }else if(config.getRules().getCopyOfPropertyTypes().get("int").contains(key)){
                TextField ruleBox = lookup("#" + key).queryAs(TextField.class);
                ruleBox.setText("7");
            }else {
                TextField ruleBox = lookup("#" + key).queryAs(TextField.class);
                ruleBox.setText("CompSci 308");
            }
            sleep(1000);
        }
        sleep(2000);
        Button save = lookup("#SavePropertiesRules").queryButton();
        clickOn(save);
        sleep(2000);
        for(String key: config.getRules().getCopyOfProperties().keySet()){
            if(config.getRules().getCopyOfPropertyTypes().get("boolean").contains(key)){
                assertEquals("false", config.getRules().getCopyOfProperties().get(key));
            } else if(config.getRules().getCopyOfPropertyTypes().get("int").contains(key)){
                assertEquals("7", config.getRules().getCopyOfProperties().get(key));
            } else {
                assertEquals("CompSci 308", config.getRules().getCopyOfProperties().get(key));
            }
            sleep(100);
        }
    }
}
