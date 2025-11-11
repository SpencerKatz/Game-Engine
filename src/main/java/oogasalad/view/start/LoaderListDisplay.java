package oogasalad.view.start;

import java.io.File;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A dialog box for displaying and selecting save and config files.
 */
public class LoaderListDisplay {

  public static final String DEFAULT_SAVES_FOLDER = "data/gamesaves";
  private static final String DEFAULT_RESOURCE_PACKAGE = "view.start.LoaderListDisplay.";
  private static final String DEFAULT_CONFIG_FOLDER = "data/gameconfigurations";
  private static final String STYLES = "/styles.css";
  private final Stage primaryStage;
  private final ResourceBundle propertiesBundle;
  private final Stage myStage;
  private File selectedSaveFile;
  private File selectedConfigFile;
  private ListView<String> saveView;
  private ListView<String> configView;

  /**
   * Constructs a LoaderListDisplay.
   *
   * @param mainStage the main stage
   * @param language  the language
   * @param title     the title of the dialog box
   */
  public LoaderListDisplay(Stage mainStage, String language, String title) {
    primaryStage = mainStage;
    propertiesBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    myStage = new Stage();
    myStage.setTitle(title);
  }

  /**
   * Opens the dialog box for selecting save and config files.
   *
   * @return an array containing the selected save and config files
   */
  public File[] open() {
    saveView = returnItemListView(DEFAULT_SAVES_FOLDER);
    configView = returnItemListView(DEFAULT_CONFIG_FOLDER);

    VBox saveBox = viewBoxMaker(saveView, propertiesBundle.getString("save_files"));
    VBox configBox = viewBoxMaker(configView, propertiesBundle.getString("config_files"));

    HBox fileLists = new HBox(saveBox, configBox);

    Button selectButton = createButton(propertiesBundle.getString("load"), event -> {
      selectSaveAndConfigFiles(saveView, configView);
      myStage.close();
    });


    setupBottom(fileLists, selectButton);

    File[] files = {selectedSaveFile, selectedConfigFile};
    return Arrays.copyOf(files, files.length);

  }

  /**
   * Opens the dialog box for selecting a config file.
   *
   * @return the selected config file
   */
  public File openConfig() {
    configView = returnItemListView(DEFAULT_CONFIG_FOLDER);
    VBox configBox = viewBoxMaker(configView, propertiesBundle.getString("config_files"));

    HBox fileList = new HBox(configBox);
    HBox.setHgrow(configBox, Priority.ALWAYS);

    Button selectButton = createButton(propertiesBundle.getString("load"), event -> {
      selectOnlyConfigFile(configView);
      myStage.close();
    });

    setupBottom(fileList, selectButton);
    return selectedConfigFile;
  }

  private VBox viewBoxMaker(ListView<String> view, String title) {
    return new VBox(new Label(title), view);
  }

  private void setupBottom(HBox fileLists, Button selectButton) {
    Button exitButton = new Button(propertiesBundle.getString("close"));
    exitButton.setOnAction(event -> myStage.close());

    HBox hBox = new HBox(selectButton, exitButton);
    hBox.setAlignment(Pos.CENTER);

    VBox vBox = new VBox(fileLists, hBox);
    vBox.setId("loader_vBox");

    vBox.setPrefSize(400, 400);
    ScrollPane scrollPane = new ScrollPane(vBox);
    scrollPane.setId("loader_scrollpane");

    Scene scene = new Scene(scrollPane);
    scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

    myStage.initStyle(StageStyle.UNDECORATED);
    myStage.initOwner(primaryStage);

    myStage.setScene(scene);
    myStage.showAndWait();
  }

  private void selectSaveAndConfigFiles(ListView<String> saveList, ListView<String> configList) {
    selectedSaveFile = selectFile(DEFAULT_SAVES_FOLDER, saveList);
    selectedConfigFile = selectFile(DEFAULT_CONFIG_FOLDER, configList);
  }

  private void selectOnlyConfigFile(ListView<String> configList) {
    selectedConfigFile = selectFile(DEFAULT_CONFIG_FOLDER, configList);
  }

  private File selectFile(String defaultDirectoryPath, ListView<String> listView) {
    String selectedFileName = listView.getSelectionModel().getSelectedItem();

    if (selectedFileName != null) {
      return new File(defaultDirectoryPath + "/" + selectedFileName);
    }

    return null;
  }

  private ListView<String> returnItemListView(String directoryPath) {
    ListView<String> listView = new ListView<>();
    listView.getStyleClass().add("list_view");
    listView.setId("list_view");

    File directory = new File(directoryPath);
    File[] files = directory.listFiles();

    if (files != null) {
      for (File file : files) {
        listView.getItems().add(file.getName());
      }
    }

    return listView;
  }

  private Button createButton(String text,
      javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
    Button button = new Button(text);
    button.getStyleClass().add(text);
    button.setOnAction(handler);
    return button;
  }


}
