package oogasalad.model.gameplay;


import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;
import oogasalad.model.api.ReadOnlyGameTime;

/**
 * Game Time class controlling the game time
 *
 * @author Beilong Tang, Jason Qiu
 */
public class GameTime implements GameTimeInterface {


  // an hour of the game equals 43 seconds in real-time, (This is the default setting in Stardew
  // valley).
  // this field stands for the real-time on behalf of the 10 minutes of game time
  // in other words, (unit) minute game time equals rate (in milliseconds)
  // by default, the unit is 10.
  private static final String DEFAULT_RESOURCE_PACKAGE = "model.gameplay.";
  private static final String myLanguage = "EnglishTimeText";
  private static final ResourceBundle timeTextResource =
      ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
  private static final double rate = 43000.0 / 6.0;
  private static final int unit = 10;

  private Instant previous = Instant.now();

  private long accumulate;

  private int day;

  private int hour;

  private int minute;

  /**
   * @param day    the day
   * @param hour   the hour
   * @param minute the minute
   */
  public GameTime(int day, int hour, int minute) {
    this.day = day;
    this.hour = hour;
    this.minute = minute;
  }

  /**
   * Copy constructor
   *
   * @param other the other gametime to copy.
   */
  public GameTime(ReadOnlyGameTime other) {
    this(other.getDay(), other.getHour(), other.getMinute());
  }

  public void advanceOneUnit() {
    minute += unit;
    if (minute >= 60) {
      minute = 0;
      hour += 1;
    }
    if (hour >= 24) {
      hour = 0;
      day += 1;
    }
  }

  /**
   * Advance the given number of minutes.
   *
   * @param minutes the number of minutes to advance.
   */
  public void advance(int minutes) {
    for (int i = 0; i < minutes / unit; i++) {
      advanceOneUnit();
    }
  }

  /**
   * Advances the time until it reaches given hour and minute.
   * <p>
   * If the current hour and minute are equal to the targets, then do not advance.
   *
   * @param targetHour   the target hour (0-23).
   * @param targetMinute the target minute (0-59).
   * @return the number of minutes advanced.
   */
  public int advanceTo(int targetHour, int targetMinute) {
    int passedHours = 0;
    int passedMinutes = 0;

    if (targetHour < hour) {
      passedHours = 24 - hour + targetHour;
      day++;
    } else {
      passedHours = targetHour - hour;
    }
    hour = targetHour;

    if (targetMinute < minute) {
      passedHours--;
      passedMinutes = 60 - minute + targetMinute;
      if (passedHours == -1) {
        passedHours = 23;
        day++;
      }
    } else {
      passedMinutes = targetMinute - minute;
    }
    minute = targetMinute;

    previous = Instant.now();
    return passedHours * 60 + passedMinutes;
  }

  /**
   * This should be called in the game loop in the UI to update the game time.
   */
  @Override
  public void update() {
    if (previous == null) {
      previous = Instant.now();
    }
    Instant now = Instant.now();
    long timeElapsedMillis = Duration.between(previous, now).toMillis();
    previous = Instant.now();
    accumulate += timeElapsedMillis;
    if (accumulate >= rate) {
      accumulate = 0;
      advanceOneUnit();
    }
  }

  @Override
  public int getHour() {
    return hour;
  }

  @Override
  public int getMinute() {
    return minute;
  }

  @Override
  public int getDay() {
    return day;
  }

  /**
   * @param gameTime@return the difference between two gameTime objects in minutes
   * @return the (gameTime - this time)
   */
  @Override
  public int getDifferenceInMinutes(ReadOnlyGameTime gameTime) {
    int totalMinute2 = gameTime.getMinute() + gameTime.getHour() * 60 + gameTime.getDay() * 24 * 60;
    int totalMinute1 = getMinute() + getHour() * 60 + getDay() * 24 * 60;
    return totalMinute2 - totalMinute1;
  }

  @Override
  public int convertInMinutes() {
    return getMinute() + getHour() * 60 + getDay() * 24 * 60;
  }

  @Override
  public String toString() {
    return String.format("%d: %d: %d", day, hour, minute);
  }

  /**
   * Equality is based off the clock time.
   *
   * @param object the object to compare against.
   * @return true if the time is the same, false otherwise.
   */
  @Override
  public boolean equals(Object object) {
    if (object == null || this.getClass() != object.getClass()) {
      return false;
    }

    final GameTime other = (GameTime) object;
    return day == other.day && hour == other.hour && minute == other.minute;
  }

  @Override
  public int hashCode() {
    return convertInMinutes();
  }

  public GameTime copy() {
    return new GameTime(this.getDay(), this.getHour(), this.getMinute());
  }
}
