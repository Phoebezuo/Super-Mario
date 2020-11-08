package stickman.level;

import stickman.entity.Entity;
import stickman.entity.Interactable;
import stickman.entity.moving.MovingEntity;

/**
 * The interface describing the behaviours found in the Level Builder Pattern.
 */
public interface LevelBuilder {

    /**
     * Adds an enemy object to the builder.
     * @param enemy The enemy object to add.
     * @return This instance of LevelBuilder
     */
    LevelBuilder addEnemy(MovingEntity enemy);

    /**
     * Adds a static object to the builder (such as Platform).
     * @param entity The StaticEntity to add
     * @return This instance of LevelBuilder
     */
    LevelBuilder addStaticEntity(Entity entity);

    /**
     * Adds an interactable object to the builder.
     * @param collectable The Interactable to add
     * @return This instance of LevelBuilder
     */
    LevelBuilder addInteractable(Interactable collectable);

    /**
     * Sets the position and size of the hero in the level.
     * @param x The starting x-coordinate
     * @param size The size of the hero
     * @return This instance of LevelBuilder
     */
    LevelBuilder setHero(double x, String size);

    /**
     * Sets the height of the floor in the level.
     * @param height The floor height
     * @return This instance of LevelBuilder
     */
    LevelBuilder setFloorHeight(double height);

    /**
     * Sets the dimensions of the level.
     * @param width The width of the level
     * @param height The height of the level
     * @return This instance of LevelBuilder
     */
    LevelBuilder setDimensions(double width, double height);

    /**
     * Builds a Level object based on the previously set parameters.
     * @return The corresponding Level object
     */
    Level build();
}
