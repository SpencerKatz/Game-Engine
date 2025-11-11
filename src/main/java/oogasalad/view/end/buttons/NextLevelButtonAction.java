package oogasalad.view.end.buttons;


import javafx.scene.input.MouseEvent;

public class NextLevelButtonAction implements ButtonAction {

  @Override
  public void performAction(MouseEvent event) {
    System.out.println("Next Level button clicked!");
  }
}
