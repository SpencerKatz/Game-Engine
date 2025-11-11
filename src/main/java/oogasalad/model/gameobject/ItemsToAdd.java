package oogasalad.model.gameobject;

/**
 * Constructs an instance of {@code ItemsToAdd}.
 *
 * @param quantity The number of items to be added. This value should be positive to indicate items
 *                 being added. A value of zero or negative may be used according to the
 *                 application's logic, but typically represents no addition.
 * @param id       The unique identifier for the type of items to be added. This ID should
 *                 correspond to a valid item within the game's item system, ensuring that the items
 *                 can be properly created and managed.
 */
public record ItemsToAdd(int quantity, String id) {


}

