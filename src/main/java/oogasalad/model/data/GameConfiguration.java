package oogasalad.model.data;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.ReadOnlyGameConfigurablesStore;
import oogasalad.model.api.ReadOnlyGameConfiguration;
import oogasalad.model.api.ReadOnlyGameState;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.api.exception.BadGsonLoadException;
import oogasalad.model.api.exception.InvalidRuleType;
import oogasalad.model.api.exception.KeyNotFoundException;
import oogasalad.model.gameplay.GameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that represents the set of all configurations for a game in the framework.
 * <p>
 * It includes the configured game rules and default initial state of the game.
 *
 * @author Jason Qiu
 */
public class GameConfiguration implements ReadOnlyGameConfiguration {

  // TODO: Externalize this to a configuration file.
  // The path to the game configurations directory from the data directory.
  public static final String GAMECONFIGURATION_DIRECTORY_PATH = "gameconfigurations";

  public static final String TEMPLATES_DIRECTORY_PATH = "templates";


  /**
   * Initializes the game configuration to a set of default rules and initial state.
   */
  public GameConfiguration() {
    this(getDefaultRules(), getDefaultConfigurablesStore(), getDefaultInitialState());
  }

  /**
   * Initializes the game configuration with the given rules.
   *
   * @param rules the loaded rules to use.
   */
  public GameConfiguration(Properties rules) {
    this(rules, getDefaultConfigurablesStore(), getDefaultInitialState());
  }

  /**
   * Initializes the game configuration with the given rules, configurablesstore, and initial
   * state.
   *
   * @param rules        the loaded rules to use.
   * @param store        the loaded configurables store to use.
   * @param initialState the initial state to use.
   */
  public GameConfiguration(Properties rules, GameConfigurablesStore store, GameState initialState) {
    this.rules = rules;
    DataValidation.validateProperties(rules);
    configurablesStore = store;
    this.initialState = initialState;
  }

  /**
   * Creates and returns an instance of {@link GameConfiguration} from a JSON file. Also loads the
   * configurables store with the same name.
   *
   * @param dataFilePath the path to the JSON file.
   * @return the created instance of {@link GameConfiguration}.
   * @throws BadGsonLoadException if the filePath is unable to be parsed into an instance of
   *                              {@link GameConfiguration}
   * @throws IOException          if the filePath could not be opened.
   */
  public static GameConfiguration of(String dataFilePath) throws BadGsonLoadException, IOException {
    GameConfiguration load = GAME_CONFIGURATION_DATA_FACTORY.load(
        Paths.get(GAMECONFIGURATION_DIRECTORY_PATH, dataFilePath).toString());
    configurablesStore = GameConfigurablesStore.of(dataFilePath);
    return load;
  }

  /**
   * Returns the read-only ConfigurablesStore for the game configuration.
   *
   * @return the read-only ConfigurablesStore for the game configuration.
   */
  public static ReadOnlyGameConfigurablesStore getConfigurablesStore() {
    return configurablesStore;
  }


  /**
   * Serializes the instance to a JSON file.
   * <p>
   * Also saves the configurables store of the same name.
   *
   * @param dataFilePath the path to the JSON file with the data directory as the root.
   * @throws IOException if there is an issue writing to the given dataFilePath.
   */
  @Override
  public void save(String dataFilePath) throws IOException {
    configurablesStore.save(dataFilePath);
    GAME_CONFIGURATION_DATA_FACTORY.save(
        Paths.get(GAMECONFIGURATION_DIRECTORY_PATH, dataFilePath).toString(), this);
  }

  @Override
  public ReadOnlyProperties getRules() {
    return rules;
  }

  @Override
  public ReadOnlyGameState getInitialState() {
    return initialState;
  }

  /**
   * Updates a rule only if it already exists.
   *
   * @param rule     queried rule.
   * @param newValue the value to set.
   * @throws KeyNotFoundException if the rule does not exist.
   */
  public void updateRule(String rule, String newValue) throws InvalidRuleType {
    DataValidation.validate(rule, newValue);
    rules.update(rule, newValue);
  }

  public void updateRule(String rule, List<String> newValue) {
    rules.update(rule, newValue);
  }

  public void updateRule(String rule, Map<String, String> newValue) {
    rules.update(rule, newValue);
  }


  public GameState getEditableInitialState() {
    return initialState;
  }


  /**
   * Returns the editable ConfigurablesStore for the game configuration.
   *
   * @return the editable ConfigurablesStore for the game configuration.
   */
  public static GameConfigurablesStore getEditableConfigurablesStore() {
    return configurablesStore;
  }

  private final GameState initialState;
  private final Properties rules;
  private static GameConfigurablesStore configurablesStore;
  private static final DataFactory<GameConfiguration> GAME_CONFIGURATION_DATA_FACTORY =
      new DataFactory<>(GameConfiguration.class);
  private static final DataFactory<GameState> GAMESTATE_DATA_FACTORY =
      new DataFactory<>(GameState.class);
  private static final DataFactory<GameConfigurablesStore> CONFIGURABLES_DATA_FACTORY =
      new DataFactory<>(GameConfigurablesStore.class);
  private static final Logger LOG = LogManager.getLogger(GameConfiguration.class);


  private static Properties getDefaultRules() {
    try {
      return Properties.of(Paths.get(TEMPLATES_DIRECTORY_PATH, "GameRulesGrouped").toString());
    } catch (IOException e) {
      LOG.error("Couldn't load default GameRules 'templates/GameRulesGrouped.json'.");
      throw new RuntimeException(e);
    }
  }

  private static GameConfigurablesStore getDefaultConfigurablesStore() {
    try {
      return CONFIGURABLES_DATA_FACTORY.load(
          Paths.get(TEMPLATES_DIRECTORY_PATH, "ConfigurablesStore").toString());
    } catch (IOException e) {
      LOG.error("Couldn't load default ConfigurablesStore 'templates/ConfigurablesStore.json'.");
      throw new RuntimeException(e);
    }
  }

  public static Properties templateProperties(String type) {
    try {
      return Properties.of(Paths.get(TEMPLATES_DIRECTORY_PATH, type).toString());
    } catch (IOException e) {
      LOG.error("Couldn't load default ConfigurablesStore 'templates/ConfigurablesStore.json'.");
      throw new RuntimeException(e);
    }
  }

  private static GameState getDefaultInitialState() {
    try {
      return GAMESTATE_DATA_FACTORY.load(
          Paths.get(TEMPLATES_DIRECTORY_PATH, "GameState").toString());
    } catch (IOException e) {
      LOG.error("Couldn't load default GameState 'templates/GameState.json'.");
      throw new RuntimeException(e);
    }
  }
}
