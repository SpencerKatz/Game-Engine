package oogasalad.view.editor.ObjectEditor;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.data.GameConfiguration;
import oogasalad.view.editor.EditorScene;
import oogasalad.view.editor.MapEditor.BottomPanel;
import oogasalad.view.editor.MapEditor.SelectableViewBox;
import oogasalad.view.editor.MapEditor.SelectableViewBoxWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectPropertiesChangeTest extends DukeApplicationTest {
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
    @DisplayName("Test one property change")
    public void testOneRuleChange() {
        BottomPanel bp = lookup("#BottomPanel").queryAs(BottomPanel.class);
        Tab tab = bp.getTabs().stream()
                .filter(t -> "Item".equals(t.getId()))
                .findFirst()
                .orElse(null);
        bp.getSelectionModel().select(tab);
        VBox wheat = lookup("#HoeSelectable").queryAs(VBox.class);
        sleep(2000);
        clickOn(wheat);
        TextField tf = lookup("#worth").queryAs(TextField.class);
        tf.setText("6000");
        Button save = lookup("#SavePropertiesHoe").queryButton();
        clickOn(save);
        sleep(1000);
        assertEquals("6000", GameConfiguration.getConfigurablesStore().getAllConfigurables().get("Hoe").getCopyOfProperties().get("worth"));
    }

    @Test
    @DisplayName("Test add property")
    public void testAddProperty(){
        VBox collect = lookup("#COLLECTABLESelectable").queryAs(VBox.class);
        sleep(2000);
        clickOn(collect);
        Button add = lookup("#AddinteractDropMultipliers").queryButton();
        clickOn(add);
        TextField field = lookup("#newField").queryAs(TextField.class);
        TextField value = lookup("#newValue").queryAs(TextField.class);
        field.setText("Robert");
        value.setText("Duvall");
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        Button save = lookup("#SavePropertiesCOLLECTABLE").queryButton();
        clickOn(save);
        sleep(1000);
        assertTrue(GameConfiguration.getConfigurablesStore().getAllConfigurables().get("COLLECTABLE").getCopyOfMapProperties().get("interactDropMultipliers").containsKey("Robert"));
        assertEquals("Duvall", GameConfiguration.getConfigurablesStore().getAllConfigurables().get("COLLECTABLE").getCopyOfMapProperties().get("interactDropMultipliers").get("Robert"));
    }

    @Test
    @DisplayName("Test remove property")
    public void testRemoveProperty(){
        assertTrue(GameConfiguration.getConfigurablesStore().getConfigurableProperties("Wheat").getCopyOfMapProperties().get("dropsOnDestruction").containsKey("Wheat Bundle"));
        BottomPanel bp = lookup("#BottomPanel").queryAs(BottomPanel.class);
        Tab tab = bp.getTabs().stream()
                .filter(t -> "Structure".equals(t.getId()))
                .findFirst()
                .orElse(null);
        bp.getSelectionModel().select(tab);
        VBox collect = lookup("#WheatSelectable").queryAs(VBox.class);
        sleep(2000);
        clickOn(collect);
        Button add = lookup("#RemovedropsOnDestruction").queryButton();
        clickOn(add);
        Button save = lookup("#SavePropertiesWheat").queryButton();
        clickOn(save);
        sleep(3000);
        assertFalse(GameConfiguration.getConfigurablesStore().getAllConfigurables().get("Wheat").getCopyOfMapProperties().get("dropsOnDestruction").containsKey("Wheat Bundle"));
    }

    @Test
    @DisplayName("add image to collectable")
    public void addImageToCollectable() {
        assertEquals(null, GameConfiguration.getConfigurablesStore().getAllConfigurables().get("COLLECTABLE").getCopyOfProperties().get(""));
        VBox collect = lookup("#COLLECTABLESelectable").queryAs(VBox.class);
        sleep(2000);
        clickOn(collect);
        TextField tf = lookup("#image").queryAs(TextField.class);
        tf.setText("hoe.png");
        Button save = lookup("#SavePropertiesCOLLECTABLE").queryButton();
        clickOn(save);
        sleep(1000);
        assertEquals("hoe.png", GameConfiguration.getConfigurablesStore().getAllConfigurables().get("COLLECTABLE").getCopyOfProperties().get("image"));
    }
}
