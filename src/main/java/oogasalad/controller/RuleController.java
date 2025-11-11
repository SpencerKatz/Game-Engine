package oogasalad.controller;

import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.InvalidRuleType;
import oogasalad.model.api.exception.KeyNotFoundException;
import oogasalad.model.data.GameConfiguration;

/**
 * Controller class for managing rules.
 */
public class RuleController extends PropertyController {

  private final GameConfiguration config; // The game configuration

  /**
   * Constructs a RuleController with the given GameConfiguration.
   *
   * @param gc The GameConfiguration used by the RuleController.
   */
  public RuleController(GameConfiguration gc) {
    super();
    config = gc;
  }

  /**
   * Updates a rule if it already exists.
   *
   * @param rule     The rule to be updated.
   * @param newValue The new value of the rule.
   * @throws InvalidRuleType      If the rule is invalid.
   * @throws KeyNotFoundException If the rule does not exist.
   */
  public void updateRule(String rule, String newValue) throws InvalidRuleType {
    config.updateRule(rule, newValue);
  }

  @Override
  public Map<String, String> getProperties() {
    return config.getRules().getCopyOfProperties();
  }

  @Override
  public Map<String, List<String>> getListProperties() {
    return config.getRules().getCopyOfListProperties();
  }

  @Override
  public Map<String, Map<String, String>> getMapProperties() {
    return config.getRules().getCopyOfMapProperties();
  }

  @Override
  public void updateProperty(String name, String value) throws InvalidRuleType {
    updateRule(name, value);
  }

  @Override
  public void updateMapProperty(String name, Map<String, String> newMap) {
    config.updateRule(name, newMap);
  }

  @Override
  public void updateListProperty(String name, String value) {
    config.updateRule(name, getList(value));
  }

  /**
   * Gets the name of the configuration.
   *
   * @return The name of the configuration.
   */
  public String getName() {
    return config.getRules().getString("configName");
  }
}
