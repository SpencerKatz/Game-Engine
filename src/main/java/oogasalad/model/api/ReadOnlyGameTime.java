package oogasalad.model.api;

import oogasalad.model.gameplay.GameTime;

/**
 * Provides methods for accessing parts of the GameTime and perform arithmetic without being able to
 * modify it.
 *
 * @author Beilong Tang, Jason Qiu
 */
public interface ReadOnlyGameTime {

  int getHour();

  int getMinute();

  int getDay();

  /**
   * @param gameTime, another game time object
   * @return the difference between two gameTime objects in minutes
   */
  int getDifferenceInMinutes(ReadOnlyGameTime gameTime);

  int convertInMinutes();


  /**
   * Read-only copy constructor.
   *
   * @param other the other GameTime to copy.
   */
  static ReadOnlyGameTime copyOf(ReadOnlyGameTime other) {
    return new GameTime(other.getDay(), other.getMinute(), other.getHour());
  }
}
