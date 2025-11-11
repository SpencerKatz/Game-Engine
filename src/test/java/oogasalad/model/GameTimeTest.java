package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.model.gameplay.GameTime;
import oogasalad.model.gameplay.GameTimeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GameTimeTest {

  private GameTime gameTime;

  @BeforeEach
  void setUp() {
    gameTime = new GameTime(1, 0, 0);
  }

  @Test
  void testIfTheGameTimeHandlesDifference() {
    for (int i = 0; i < 100; i++) {
      gameTime.update();

    }
    assertEquals(gameTime.getMinute(), 0);
  }

  @Test
  void testDifferenceGameTime() {
    GameTimeInterface gameTime1 = new GameTime(1, 8, 0);
    GameTimeInterface gameTime2 = new GameTime(2, 10, 30);
    assertEquals(gameTime1.getDifferenceInMinutes(gameTime2), 1590);
    assertEquals(gameTime2.getDifferenceInMinutes(gameTime1), -1590);
  }

  @ParameterizedTest
  @CsvSource({"5, 5, 0, 0, 0, 0, 5, 5, 305", "5, 5, 0, 5, 0, 0, 5, 5, 5", "5, 5, 0, 5, 5, 0, 5, 5, 0", "5, 5, 0, 5, 6, 1, 5, 5, 1439"})
  void testAdvanceTo(int targetHour, int targetMinute, int startDay, int startHour, int startMinute,
      int expectedDay, int expectedHour, int expectedMinute, int expectedMinutesPassed) {
    GameTime actual = new GameTime(startDay, startHour, startMinute);
    int actualMinutesPassed = actual.advanceTo(targetHour, targetMinute);

    GameTime expected = new GameTime(expectedDay, expectedHour, expectedMinute);
    assertEquals(expected, actual);
    assertEquals(expectedMinutesPassed, actualMinutesPassed);
  }
}
