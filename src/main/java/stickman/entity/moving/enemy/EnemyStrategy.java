package stickman.entity.moving.enemy;

import stickman.entity.Entity;
import stickman.entity.moving.MovingEntity;

import java.util.List;

/**
 * Interface for the Strategy that Slimes use to determine where to move.
 */
public interface EnemyStrategy {

    /**
     * Determines the x-velocity of the slime.
     * @param enemy The slime to move
     * @param entities The other entities in the level
     * @param left Whether the slime was moving left
     * @param heroX The x-coordinate of the hero
     * @return The new x-velocity the slime should use
     */
    double think(MovingEntity enemy, List<Entity> entities, boolean left, double heroX);
}
