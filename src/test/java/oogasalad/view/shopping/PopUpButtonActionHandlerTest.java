package oogasalad.view.shopping;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;
import javafx.scene.layout.StackPane;
import oogasalad.view.popup.PopUpButtonActionHandler;
import org.junit.Test;

public class PopUpButtonActionHandlerTest {

  @Test
  public void testYesAction() {
    Consumer<Boolean> consumer = mock(Consumer.class);
    StackPane parent = new StackPane();
    StackPane popup = new StackPane();
    parent.getChildren().add(popup);

    PopUpButtonActionHandler handler = new PopUpButtonActionHandler(consumer, parent, popup);
    handler.yesAction();

    verify(consumer).accept(true);
    assertFalse(parent.getChildren().contains(popup));
  }

  @Test
  public void testNoAction() {
    Consumer<Boolean> consumer = mock(Consumer.class);
    StackPane parent = new StackPane();
    StackPane popup = new StackPane();
    parent.getChildren().add(popup);

    PopUpButtonActionHandler handler = new PopUpButtonActionHandler(consumer, parent, popup);
    handler.noAction();

    verify(consumer).accept(false);
    assertFalse(parent.getChildren().contains(popup));
  }
}
