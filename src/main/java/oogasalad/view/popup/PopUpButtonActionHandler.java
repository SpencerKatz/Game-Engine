package oogasalad.view.popup;

import java.util.function.Consumer;
import javafx.scene.layout.StackPane;


public class PopUpButtonActionHandler {

  private final Consumer<Boolean> choiceCallback;
  private final StackPane parentStackPane;
  private final StackPane popUpStackPane;

  public PopUpButtonActionHandler(Consumer<Boolean> choiceCallback, StackPane parentStackPane,
      StackPane popUpStackPane) {
    this.choiceCallback = choiceCallback;
    this.parentStackPane = parentStackPane;
    this.popUpStackPane = popUpStackPane;
  }

  public void yesAction() {
    choiceCallback.accept(true);
    closePopup();
  }

  public void noAction() {
    choiceCallback.accept(false);
    closePopup();
  }

  public void closePopup() {
    parentStackPane.getChildren().remove(popUpStackPane);
  }
}
