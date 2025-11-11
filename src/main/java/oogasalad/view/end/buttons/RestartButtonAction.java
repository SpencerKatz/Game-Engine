package oogasalad.view.end.buttons;


import javafx.scene.input.MouseEvent;

public class RestartButtonAction implements ButtonAction {

  @Override
  public void performAction(MouseEvent e) {
    System.out.println("Restart button clicked!");
  }
}