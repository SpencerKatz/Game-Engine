package oogasalad.model.gameobject;

import java.util.function.Supplier;
import oogasalad.model.api.ReadOnlyGameTime;
import oogasalad.model.api.ReadOnlyItem;
import oogasalad.model.api.ReadOnlyProperties;
import oogasalad.model.api.exception.IncorrectPropertyFileType;
import oogasalad.model.data.GameConfiguration;

/**
 * Abstract base class for all game objects within the game. This class defines the common behavior
 * and properties of game objects, such as interaction capabilities, expiration, and updating over
 * time.
 *
 * @author Spencer Katz, Jason Qiu
 */
public abstract class GameObject implements Interactable, Expirable, Updatable, Viewable {

  private boolean expired;
  private ReadOnlyGameTime timeSinceExpiringState;
  private ReadOnlyGameTime creationTime;
  private boolean changePropertiesOnNextIteration;
  private String id;
  private String nextId;
  private ReadOnlyGameTime lastUpdateGameTime;

  /**
   * Constructs a GameObject with specified properties and initial state.
   *
   * @param id           The id of the GameObject.
   * @param creationTime The game time at which the object was created.
   */
  public GameObject(String id, ReadOnlyGameTime creationTime) {
    this.id = id;
    this.creationTime = creationTime;
    lastUpdateGameTime = creationTime;
    this.expired = false;
    this.changePropertiesOnNextIteration = false;
  }

  /**
   * Gets the unique identifier of the game object.
   *
   * @return The unique ID of the game object.
   */
  public String getId() {
    return id;
  }

  /**
   * Checks and updates the expiration status of the game object based on the elapsed time.
   *
   * @param gameTime The current time of the game
   * @return Whether this gameObject is expired and thus should be removed from the game.
   */
  @Override
  public boolean checkAndUpdateExpired(ReadOnlyGameTime gameTime) {
    return expired
        && timeSinceExpiringState.getDifferenceInMinutes(gameTime) > getProperties().getInteger(
        "expireTime");
  }

  /**
   * Updates the game object based on the current game time, potentially changing its state.
   *
   * @param gameTime The current game time to base updates on.
   */
  @Override
  public void update(ReadOnlyGameTime gameTime) {
    lastUpdateGameTime = gameTime;
    updateAndInteract(() -> {
      if (getProperties().getBoolean("updatable")
          && creationTime.getDifferenceInMinutes(gameTime) > getProperties().getInteger(
          "updateTime")) {
        return getProperties().getString("updateTransformation");
      }
      return getId();
    });
    updateExpired(gameTime);
  }

  /**
   * Interacts with a given item, potentially changing the game object's state.
   *
   * @param item The item to interact with.
   */
  @Override
  public void interact(ReadOnlyItem item) {
    updateAndInteract(() -> {
      if (interactionValid(item)) {
        return getProperties().getStringMap("interactTransformations").get(item.getName());
      }
      return getId();
    });
  }

  /**
   * Updates the game object's state or interacts based on a provided condition.
   *
   * @param idGenerator A supplier providing the new ID based on the interaction or update.
   */
  private void updateAndInteract(Supplier<String> idGenerator) {
    if (changePropertiesIfApplicable()) {
      return;
    }
    String newId = idGenerator.get();
    shouldIChangeProperties(newId);

  }

  /**
   * Determines if the interaction with a specified item is valid.
   *
   * @param item The item in question.
   * @return true if the interaction is valid, false otherwise.
   */
  @Override
  public boolean interactionValid(ReadOnlyItem item) {
    return getProperties().getStringMap("interactTransformations").containsKey(item.getName());
  }

  /**
   * Updates the expiration status of the game object, marking it as expired if necessary.
   *
   * @param gameTime The current time of the game.
   */
  private void updateExpired(ReadOnlyGameTime gameTime) {
    if (!expired && getProperties().getBoolean("expirable")) {
      expired = true;
      timeSinceExpiringState = gameTime;
    }
  }

  /**
   * Retrieves the image path representing the current state of the game object.
   *
   * @return The path to the game object's image.
   */
  @Override
  public String getImagePath() {
    return getProperties().getString("image");
  }

  /**
   * Decides whether to change the game object's properties based on a new ID.
   *
   * @param newId The new ID to potentially switch to.
   */
  protected void shouldIChangeProperties(String newId) {
    if (newId != null && !newId.equals(getId())) {
      changePropertiesOnNextIteration = true;
      this.nextId = newId;
    }
  }

  /**
   * Changes the properties of the game object if applicable, based on interaction or update
   * conditions.
   *
   * @return true if properties were changed, false otherwise.
   */
  protected boolean changePropertiesIfApplicable() {
    if (changePropertiesOnNextIteration) {
      setProperties(GameConfiguration.getConfigurablesStore().getConfigurableProperties(nextId));
      return true;
    }
    return false;
  }

  /**
   * Method to get the read only properties of the GameObject.
   *
   * @return properties The read only properties of relevant to specific GameObject stored here.
   */
  public ReadOnlyProperties getProperties() {
    return GameConfiguration.getConfigurablesStore().getConfigurableProperties(id);
  }

  /**
   * Sets new id for the game object, updating its behavior and attributes. Ensures that the type of
   * the Properties instance is the same as the subclass of GameObject.
   *
   * @param properties The new properties to set.
   */
  public void setProperties(ReadOnlyProperties properties) {
    if (!properties.getString("type").equals(getClass().getSimpleName())) {
      throw new IncorrectPropertyFileType(
          "Provided properties cannot be cast to correct properties type");
    }
    changePropertiesOnNextIteration = false;
    expired = false;
    creationTime = lastUpdateGameTime;
    id = nextId;
  }
}

