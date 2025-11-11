package oogasalad.view.login;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.stage.Stage;
import oogasalad.model.api.GameFactory;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
import util.DukeApplicationTest;

public class LoginTest extends DukeApplicationTest {

  private Stage stage;
  private LoginView loginView;


  @Start
  public void start(Stage stage) {
    this.stage = stage;
    loginView = new LoginView(new GameFactory().createGame());
    loginView.start(stage);
  }

  @Test
  public void testLoginCorrect() {
    correctLogin();
    assertNotNull(lookup("#loadButton").query());
  }

  @Test
  public void testLoginFalse() {
    clickOn("#loginButton");
    clickOn("#usernameField").write("xx");
    clickOn("#passwordField").write("xxx");
    clickOn("#submitButton");
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(".alert", NodeMatchers.isNotNull());
  }

  @Test
  public void testSaveTrue() {
    correctLogin();
    //clickOn("#saveButton");
  }
  @Test
  public void testLoadTrue() {
    correctLogin();
    clickOn("#loadButton");
  }

  @Test
  public void testSendMessage(){
    correctLogin();
    clickOn("#sendThoughtButton");
    clickOn("#sendThoughtButton");
    clickOn("#thoughtTextArea").write("Hello");
    //clickOn("#submitButton");
  }


  private void correctLogin() {
    clickOn("#loginButton");
    clickOn("#usernameField").write("Fake");
    clickOn("#passwordField").write("fake27");
    clickOn("#submitButton");
  }

}
