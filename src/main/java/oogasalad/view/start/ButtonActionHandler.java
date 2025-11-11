/**
 * This class represents an EventHandler for JavaFX buttons. It dynamically invokes a method of a
 * specified class with the provided parameters when the associated button is clicked.
 */
package oogasalad.view.start;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ButtonActionHandler implements EventHandler<ActionEvent> {

  private final String className;
  private final String methodName;
  private final Stage stage;
  private final String language;
  private final String[] parameters;
  private final Scene previousScene;

  /**
   * Constructs a ButtonActionHandler with the specified parameters.
   *
   * @param className  the name of the class containing the method to be invoked
   * @param methodName the name of the method to be invoked
   * @param stage      the primary stage
   * @param language   the language
   * @param backScene  the previous scene
   * @param parameters the parameters to be passed to the method
   */
  public ButtonActionHandler(String className, String methodName, Stage stage, String language,
      Scene backScene, String... parameters) {
    this.className = className;
    this.methodName = methodName;
    this.stage = stage;
    this.language = language;
    this.previousScene = backScene;
    this.parameters = parameters.clone();
  }

  /**
   * Invoked when a specific button is clicked.
   *
   * @param event the event triggered by the button click
   */
  @Override
  public void handle(ActionEvent event) {
    try {
      Class<?> clazz = Class.forName(className);
      Class<?>[] parameterTypes = new Class[parameters.length];
      for (int i = 1; i < parameters.length; i++) {
        parameterTypes[i] = String.class; // Assume all parameters are strings
      }
      Method method = clazz.getMethod(methodName, parameterTypes);
      System.out.println(clazz.getSimpleName());
      Constructor<?> constructor = clazz.getConstructor(Stage.class, String.class, Scene.class);
      Object instance = constructor.newInstance(stage, language, previousScene);

      Object[] args = new Object[parameters.length]; // +1 for the stage
      System.arraycopy(parameters, 0, args, 0, parameters.length); // copy remaining parameters

      method.invoke(instance, args); // invoke the method with all parameters

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
