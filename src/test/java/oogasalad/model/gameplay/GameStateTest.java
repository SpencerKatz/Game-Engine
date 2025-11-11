package oogasalad.model.gameplay;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Paths;
import oogasalad.model.data.DataFactory;
import oogasalad.model.data.GameConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateTest {

  private static final String TEST_DATA_DIRECTORY = "test";
  public DataFactory<GameState> gameStateFactory = new DataFactory<>(GameState.class);


  public GameConfiguration defaultConfig = new GameConfiguration();
  public GameState defaultState;

  @BeforeEach
  void setup() {
    defaultState = new GameState(defaultConfig.getRules());
  }

  @Test
  void saveDefaultGameState() {
    defaultState.getEditableBag().addItems(defaultConfig.getRules().getStringIntegerMap("startingItems"));
    assertDoesNotThrow(() -> save("defaultState", defaultState));
  }

  private void save(String dataFilePath, GameState state) throws IOException {
    gameStateFactory.save(Paths.get(TEST_DATA_DIRECTORY, dataFilePath).toString(), state);
  }
}