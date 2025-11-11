package oogasalad.view.editor.MapEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import oogasalad.model.api.ReadOnlyProperties;

/**
 * Represents the bottom panel of the map editor.
 */
public class BottomPanel extends TabPane {

  /**
   * Constructs a BottomPanel object.
   *
   * @param allConfigurables A map containing all the configurable properties.
   */
  public BottomPanel(Map<String, ReadOnlyProperties> allConfigurables) {
    super();
    this.setId("BottomPanel");
    Map<String, List<SelectableView>> mapOfSelectables = getMapOfSelectables(allConfigurables);
    createTabs(mapOfSelectables);
  }

  /**
   * Creates tabs based on the map of selectables.
   *
   * @param mapOfSelectables The map containing the selectables.
   */
  private void createTabs(Map<String, List<SelectableView>> mapOfSelectables) {
    for (Map.Entry<String, List<SelectableView>> entry : mapOfSelectables.entrySet()) {
      Tab tab = new Tab(entry.getKey());
      tab.setId(entry.getKey());
      tab.setClosable(false);
      tab.setContent(
          new SelectableViewBoxWrapper(new SelectableViewBox(entry.getValue()), (entry.getKey())));
      super.getTabs().add(tab);
    }
  }

  /**
   * Constructs a map of selectables from the given map of configurable properties.
   *
   * @param allConfigurables A map containing all the configurable properties.
   * @return A map containing the selectables.
   */
  private Map<String, List<SelectableView>> getMapOfSelectables(
      Map<String, ReadOnlyProperties> allConfigurables) {
    Map<String, List<SelectableView>> mapOfSelectables = new TreeMap<>();
    for (ReadOnlyProperties properties : allConfigurables.values()) {
      String type = properties.getString("type");
      if (!mapOfSelectables.containsKey(type)) {
        mapOfSelectables.put(type, new ArrayList<>());
      }
      mapOfSelectables.get(type)
          .add(new SelectableView(properties.getString("image"), properties.getString("name")));
    }
    return mapOfSelectables;
  }
}
