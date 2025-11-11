package oogasalad.view.start;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog box for selecting the application language.
 */
public class LanguageDialogBox {

  private final StringProperty primaryLanguage = new SimpleStringProperty();
  private final String STYLES = "/language_dialog_box_styles.css";
  private final ComboBox<String> dropDownMenu;
  private Stage myStage;
  private final Stage primaryStage;

  /**
   * Constructs a LanguageDialogBox.
   *
   * @param mainStage the main stage
   * @param languages an array of available languages
   */
  public LanguageDialogBox(Stage mainStage, String[] languages) {
    primaryStage = mainStage;

    dropDownMenu = new ComboBox<>();
    dropDownMenu.getStyleClass().add("drop_down_menu");
    dropDownMenu.setId("drop_down_menu");

    for (String aLanguage : languages) {
      dropDownMenu.getItems().add(aLanguage);
    }

    dropDownMenu.setOnAction(e -> setPrimaryLanguage(dropDownMenu.getValue()));
  }

  /**
   * Opens the language dialog box.
   */
  public void open() {
    myStage = new Stage();
    VBox root = new VBox();
    root.getStyleClass().add("language_box");
    root.setId("language_box");

    root.setAlignment(Pos.TOP_CENTER);
    root.getChildren().add(dropDownMenu);
    Scene newScene = new Scene(root);
    newScene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

    myStage.initOwner(primaryStage);

    myStage.setScene(newScene);
    myStage.show();
  }

  /**
   * Gets the primary language property.
   *
   * @return the primary language property
   */
  public StringProperty primaryLanguageProperty() {
    return primaryLanguage;
  }

  private void setPrimaryLanguage(String language) {
    primaryLanguage.set(language);
    myStage.close();
  }
}
