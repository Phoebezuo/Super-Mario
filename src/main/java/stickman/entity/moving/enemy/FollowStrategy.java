package stickman.entity.moving.enemy;

import stickman.entity.Entity;
import stickman.entity.moving.MovingEntity;

import java.util.List;

/**
 * Strategy for slimes that follow the player.
 */
public class FollowStrategy implements EnemyStrategy {

    /**
     * The x-velocity for all slimes that follow.
     */
    private static final double FOLLOW_SPEED = 0.5;

    @Override
    public double think(MovingEntity enemy, List<Entity> entities, boolean left, double heroX) {

        double xVelocity = 0;

        left = true;

        if (enemy.getXPos() < heroX) {
            left = false;
        }

        if (left) {
            xVelocity = Math.max(-FOLLOW_SPEED, -enemy.horizontalRaycast(true, entities, 0));
        } else {
            xVelocity = Math.min(FOLLOW_SPEED, enemy.horizontalRaycast(false, entities, Double.MAX_VALUE));
        }

        return xVelocity;
    }
}
