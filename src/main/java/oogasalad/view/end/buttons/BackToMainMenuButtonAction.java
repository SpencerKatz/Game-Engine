package oogasalad.view.end.buttons;


import javafx.scene.input.MouseEvent;

public class BackToMainMenuButtonAction implements ButtonAction {

  @Override
  public void performAction(MouseEvent e) {
    System.out.println("Back to Main button clicked!");
  }
}