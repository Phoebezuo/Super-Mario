package stickman.model;

import stickman.level.Level;

/**
 * Interface for the GameEngine. Describes the necessary behaviour
 * for running the game.
 */
public interface GameEngine {

    /**
     * Gets the current running level.
     * @return The current level
     */
    Level getCurrentLevel();

    /**
     * Makes the player jump.
     * @return Whether the input had an effect
     */
    boolean jump();

    /**
     * Moves the player left.
     * @return Whether the input had an effect
     */
    boolean moveLeft();

    /**
     * Moves the player right.
     * @return Whether the input had an effect
     */
    boolean moveRight();

    /**
     * Stops player movement.
     * @return Whether the input had an effect
     */
    boolean stopMoving();

    /**
     * Updates the scene every frame.
     */
    void tick();

    /**
     * Makes the player shoot.
     */
    void shoot();

    /**
     * Restarts the level.
     */
    void reset();
}
