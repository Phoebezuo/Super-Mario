package stickman.entity.moving.player;

import stickman.entity.moving.MovingEntity;

/**
 * Interface describing the behaviours of a Player controlled character.
 */
public interface Controllable extends MovingEntity {

    /**
     * Moves the entity left.
     * @return Whether entity moves left
     */
    boolean moveLeft();

    /**
     * Moves the entity right.
     * @return Whether entity moves right
     */
    boolean moveRight();

    /**
     * Makes the entity jump.
     * @return Whether entity jumps
     */
    boolean jump();

    /**
     * Stops all horizontal movement.
     * @return Whether entity stops
     */
    boolean stop();

    /**
     * Upgrades the hero's ability (e.g. with a mushroom).
     */
    void upgrade();

    /**
     * Returns true if the player is facing left.
     * @return Whether player is left facing
     */
    boolean isLeftFacing();

    /**
     * Returns true if the player has collected a mushroom.
     * @return Whether player has upgraded
     */
    boolean upgraded();

    /**
     * Called when the player reaches the flag.
     */
    void win();
}
