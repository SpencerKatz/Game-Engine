package oogasalad.view.start;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanguageDialogBoxTest extends ApplicationTest {

  private LanguageDialogBox languageDialogBox;
  private Stage mainStage;

  @Override
  public void start(Stage stage) {
    mainStage = stage;
    String[] languages = {"English", "Spanish", "French"};
    languageDialogBox = new LanguageDialogBox(mainStage, languages);
    languageDialogBox.open();
  }

  @Test
  public void testPrimaryLanguageProperty() {
    ComboBox<String> dropDownMenu = lookup("#language_box #drop_down_menu").queryAs(ComboBox.class);
    clickOn(dropDownMenu);
    clickOn("Spanish");
    assertEquals("Spanish", languageDialogBox.primaryLanguageProperty().get());
  }


}
