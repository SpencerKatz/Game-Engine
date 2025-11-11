package oogasalad.view.login;

/**
 * Functional interface for setting up event handlers
 */
@FunctionalInterface
public interface EventHandlerSetup {

  void setup(Object component, String eventName) throws Exception;
}
