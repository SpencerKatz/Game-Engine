package oogasalad.model.gameobject;

/**
 * A record that defines a three-dimensional coordinate within the game world. This record is
 * typically used to identify specific locations on a game map, such as the position of tiles or
 * game objects.
 *
 * @param x The x-coordinate, representing the horizontal position.
 * @param y The y-coordinate, representing the vertical position.
 * @param z The z-coordinate, representing the depth position, which can be used in 3D game
 *          environments.
 */
public record CoordinateOfGameObjectRecord(int x, int y, int z) {

}

