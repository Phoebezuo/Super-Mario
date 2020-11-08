package stickman.level;

import stickman.entity.Entity;

import java.util.List;

/**
 * The interface describing the behaviours of a Level.
 */
public interface Level {

    /**
     * Gets all the entities within the Level.
     * @return All the entities within the level
     */
    List<Entity> getEntities();

    /**
     * Gets the height of the level.
     * @return The height of the level
     */
    double getHeight();

    /**
     * Gets the width of the level.
     * @return The width of the level
     */
    double getWidth();

    /**
     * Updates the level every frame.
     */
    void tick();

    /**
     * Gets the height of the floor.
     * @return The height of the floor
     */
    double getFloorHeight();

    /**
     * Gets the x-coordinate of the player character.
     * @return The x-coordinate of the player
     */
    double getHeroX();

    /**
     * Gets the y-coordinate of the player character.
     * @return The y-coordinate of the player
     */
    double getHeroY();

    /**
     * Makes the player jump.
     * @return Whether the player jumped
     */
    boolean jump();

    /**
     * Makes the player move left.
     * @return Whether the player moved left
     */
    boolean moveLeft();

    /**
     * Makes the player move right.
     * @return Whether the player moved right
     */
    boolean moveRight();

    /**
     * Stops all horizontal movement of the player.
     * @return Whether the player stopped moving
     */
    boolean stopMoving();

    /**
     * Resets the level.
     */
    void reset();

    /**
     * Makes the player shoot.
     */
    void shoot();

    /**
     * Returns the source file of the level.
     * @return The file the level is based off of
     */
    String getSource();

    /**
     * Stops level and shows victory message.
     */
    void win();
}
