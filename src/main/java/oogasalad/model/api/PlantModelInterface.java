package oogasalad.model.api;


import oogasalad.model.gameplay.GameTimeInterface;

/**
 * The structures on the land, like plants,
 *
 * @deprecated SLATED TO GET DESTROYED
 */
public interface PlantModelInterface {

  /**
   * Get the percentage of the growing till it matures
   *
   * @param gameTime
   * @return
   */
  double getProgress(GameTimeInterface gameTime);

  /**
   * @return a list of strings standing for a list of images to be shown as the stage as the plant
   * grows
   */
  String getStatusImagePath(GameTimeInterface gameTime);

}
