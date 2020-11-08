package stickman.entity.moving.enemy;

import stickman.entity.Entity;
import stickman.entity.moving.MovingEntity;

import java.util.List;

/**
 * Strategy for slimes that move side to side.
 */
public class DumbStrategy implements EnemyStrategy {

    /**
     * The x-velocity for all slimes that move side to side.
     */
    private static final double DUMB_SPEED = 0.5;

    @Override
    public double think(MovingEntity enemy, List<Entity> entities, boolean left, double heroX) {

        double xVelocity = 0;

        if (left) {
            xVelocity = Math.max(-DUMB_SPEED, -enemy.horizontalRaycast(true, entities, 0));
        } else {
            xVelocity = Math.min(DUMB_SPEED, enemy.horizontalRaycast(false, entities, Double.MAX_VALUE));
        }

        return xVelocity;

    }
}
