package oogasalad.model.gameobject;

import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.data.GameConfiguration;

/**
 * Represents an Item to be used during the game.
 */
public class Item implements ReadOnlyItem {

  private final String itemId;

  public Item(String itemId) {
    this.itemId = itemId;
  }

  /**
   * Get the name/id of the item.
   *
   * @return the name/id of the item.
   */
  @Override
  public String getName() {
    return itemId;
  }

  /**
   * Get how much money the item is worth.
   *
   * @return how much money the item is worth.
   */
  @Override
  public double getWorth() {
    return getItemProperties().getDouble("worth");
  }

  /**
   * Get the image file name that represents the item view. This file lives in `data/images`
   *
   * @return the image file name that represents the item view.
   */
  @Override
  public String getImagePath() {
    return getItemProperties().getString("image");
  }

  public double getEnergyChange() {
    return getItemProperties().getDouble("energyChange");
  }

  public boolean isEdible() {
    return getItemProperties().getBoolean("edible");
  }

  public boolean isConsumable() {
    return getItemProperties().getBoolean("consumable");
  }

  /**
   * Method to get the read only properties of the Item.
   *
   * @return properties The read only properties of relevant to specific Item stored here.
   */
  public ReadOnlyProperties getItemProperties() {
    return GameConfiguration.getConfigurablesStore().getConfigurableProperties(itemId);
  }

  /**
   * Generates a hash code for this item based solely on its ItemId.
   *
   * @return A hash code value for this object
   */
  @Override
  public int hashCode() {
    return itemId.hashCode();
  }

  /**
   * Equals is based on the id.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    return itemId.equals(((Item) obj).itemId);
  }
}
