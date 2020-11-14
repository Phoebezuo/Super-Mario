package stickman.entity.moving;

import stickman.entity.Entity;
import stickman.entity.GameObject;

import java.util.List;

/**
 * Abstract implementation of MovingEntity. Extends from GameObject to reduce repetitive code.
 */
public abstract class MovingObject extends GameObject implements MovingEntity {

    /**
     * The x-velocity of the entity.
     */
    protected double xVelocity;

    /**
     * The y-velocity of the entity.
     */
    protected double yVelocity;

    /**
     * Creates a new MovingObject entity.
     * @param imagePath The path to the sprite.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param height The height of the entity.
     * @param width The width of the entity.
     * @param layer The layer on which the entity exists on.
     */
    public MovingObject(String imagePath, double x, double y, double height, double width, Entity.Layer layer) {
        super(imagePath, x, y, height, width, layer);
    }

    @Override
    public void die() {
        this.active = false;
    }

    /**
     * Updates the y-velocity to account for gravity. The list is required to ensure the
     * entity doesn't fall through objects, and the floor height is required to ensure
     * the entity doesn't go below the floor.
     * @param entities Other entities in the level.
     * @param floorHeight The height of the floor.
     */
    protected void gravity(List<Entity> entities, double floorHeight) {
        this.yVelocity += 0.1;
        this.yVelocity = Math.min(this.yVelocity, floorHeight - this.yPos - this.height);

        boolean up = this.yVelocity < 0;

        if (up) {
            this.yVelocity = Math.max(this.yVelocity, -verticalRaycast(true, entities, 0));
        } else {
            this.yVelocity = Math.min(this.yVelocity, verticalRaycast(false, entities, floorHeight));
        }
    }
}
