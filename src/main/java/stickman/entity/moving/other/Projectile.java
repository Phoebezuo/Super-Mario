package stickman.entity.moving.other;

import stickman.entity.Entity;
import stickman.entity.moving.MovingEntity;

import java.util.List;

/**
 * Interface describing the behaviours of a projectile in the game.
 */
public interface Projectile extends MovingEntity {

    /**
     * Stops the projectile (by making it inactive).
     */
    void stop();

    /**
     * Checks for collisions with other moving entities. If there is a collision,
     * both entities become inactive.
     * @param movingEntities List of moving entities in the level
     */
    default void movingCollision(List<MovingEntity> movingEntities) {
        for (MovingEntity movingEntity : movingEntities) {
            if (movingEntity != this) {
                if (this.checkCollide(movingEntity) && movingEntity.isActive()) {
                    movingEntity.die();
                    this.stop();
                    return;
                }
            }
        }
    }

    /**
     * Checks for collisions with static entities. If there is a collision, the
     * projectile becomes inactive.
     * @param entities The static entities in the level
     */
    default void staticCollision(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity != this) {
                if (this.checkCollide(entity) && entity.isSolid()) {
                    this.stop();
                    return;
                }
            }
        }
    }
}
