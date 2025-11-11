package oogasalad.view.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import javafx.scene.layout.GridPane;
import oogasalad.Main;
import oogasalad.view.shopping.components.PageChangeBorderPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class PageChangeBorderPaneTest extends ApplicationTest {

  private PageChangeBorderPane<GridPane> pageChangeBorderPane;
  private GridPane gridPane1;
  private GridPane gridPane2;

  @BeforeAll
  public static void setUpClass() throws Exception {
    ApplicationTest.launch(Main.class);
  }


  @BeforeEach
  public void setUp() {
    gridPane1 = new GridPane();
    gridPane2 = new GridPane();
    pageChangeBorderPane = new PageChangeBorderPane<>(Arrays.asList(gridPane1, gridPane2), null);
  }

  @Test
  public void initialPageIsFirstGridPane() {
    assertEquals(gridPane1, pageChangeBorderPane.getCurrentGridPane());
  }


  @Test
  public void cannotNavigateBeforeFirstPage() {
    pageChangeBorderPane.getLeftButton().fire();
    assertEquals(gridPane1, pageChangeBorderPane.getCurrentGridPane());
  }

  @Test
  public void leftButtonIsDisabledOnFirstPage() {
    assertTrue(pageChangeBorderPane.getLeftButton().isDisabled());
  }


  @Test
  public void buttonsAreDisabledWhenOnlyOnePage() {
    pageChangeBorderPane = new PageChangeBorderPane<>(Collections.singletonList(gridPane1), null);
    assertTrue(pageChangeBorderPane.getLeftButton().isDisabled());
    assertTrue(pageChangeBorderPane.getRightButton().isDisabled());
  }
}
