package oogasalad.view.shopping;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import oogasalad.model.api.GameFactory;
import oogasalad.model.api.GameInterface;
import oogasalad.view.shopping.components.ItemView;
import oogasalad.view.shopping.components.bagblock.BagGridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class BagGridPaneTest extends ApplicationTest {

  private GameInterface gameMock;
  private ItemView itemViewMock;
  private List<ItemView> bagItemViewsMock;
  private ShoppingViewStackPane parentStackPaneMock;
  private BagGridPane bagGridPane;

  @BeforeEach
  public void setup() {
    gameMock = new GameFactory().createGame();
    itemViewMock = new ItemView(100, "url", "name", 1);
    bagItemViewsMock = new ArrayList<>();
    bagItemViewsMock.add(itemViewMock);
    parentStackPaneMock = new ShoppingViewStackPane(gameMock, null, null,
        null);
    bagGridPane = new BagGridPane(gameMock, bagItemViewsMock, parentStackPaneMock);
  }

  @Test
  public void testItemVboxCreationThroughConstructor() {
    assertEquals(bagGridPane.getChildren().size(), 1);
  }


  @Test
  public void testLayoutConsistency() {
    assertEquals(0, GridPane.getColumnIndex(bagGridPane.getChildren().get(0)));
    assertEquals(0, GridPane.getRowIndex(bagGridPane.getChildren().get(0)));
  }

}
