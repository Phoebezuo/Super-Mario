package stickman.entity.moving;

import stickman.entity.Entity;

import java.util.List;

/**
 * Interface describing the behaviours of a Moving Entity. A Moving Entity
 * is an Entity that needs to be updated every frame (with a tick() method).
 */
public interface MovingEntity extends Entity {

    /**
     * Updates the Entity every frame.
     */
    void tick(List<Entity> entities, double heroX, double floorHeight);

    /**
     * Causes the Entity to no longer be active.
     */
    void die();

    /**
     * Returns the distance (in pixels) to the nearest entity with same y-coordinates.
     * @param left Whether to check to the left
     * @param entities The other entities in the level
     * @param bound The furthest the entity can move in the direction
     * @return The distance to the nearest entity with same y-coordinate
     */
    default double horizontalRaycast(boolean left, List<Entity> entities, double bound) {

        double res = bound - (getXPos() + getWidth());

        if (left) {
            res = getXPos();
        }

        for (Entity entity : entities) {
            if (entity.isSolid() && entity != this) {
                if (getYPos() < entity.getYPos() + entity.getHeight() && getYPos() + getHeight() > entity.getYPos()) {
                    double horizontalDistance = entity.getXPos() - (getXPos() + getWidth());

                    if (left) {
                        horizontalDistance = getXPos() - (entity.getXPos() + entity.getWidth());
                    }

                    if (horizontalDistance >= 0) {
                        res = Math.min(res, horizontalDistance);
                    }
                }
            }
        }

        return res;
    }

    /**
     * Returns the distance (in pixels) to the nearest entity with same y-coordinates.
     * @param up Whether to check upwards
     * @param entities The other entities in the level
     * @param bound The furthest the entity can move in the direction
     * @return The distance to the nearest entity with same y-coordinate
     */
    default double verticalRaycast(boolean up, List<Entity> entities, double bound) {

        double res = bound - (getYPos() + getHeight());

        if (up) {
            res = getYPos();
        }

        for (Entity entity : entities) {
            if (entity.isSolid() && entity != this) {
                if (getXPos() < entity.getXPos() + entity.getWidth() && getXPos() + getWidth() > entity.getXPos()) {
                    double verticalDistance = entity.getYPos() - (getYPos() + getHeight());

                    if (up) {
                        verticalDistance = getYPos() - (entity.getYPos() + entity.getHeight());
                    }

                    if (verticalDistance >= 0) {
                        res = Math.min(res, verticalDistance);
                    }
                }
            }
        }

        return res;
    }
}
