package stickman.entity.moving.other;

import stickman.entity.Entity;
import stickman.entity.moving.MovingObject;

import java.util.List;

/**
 * Bullet object that the player can shoot to kill slimes.
 */
public class Bullet extends MovingObject implements Projectile {

    /**
     * The x-velocity of all bullets.
     */
    public static final double BULLET_SPEED = 2;

    /**
     * The width of all bullets. (Static to help testing)
     */
    public static final double BULLET_WIDTH = 10;

    /**
     * The height of all bullets. (Static to help testing)
     */
    public static final double BULLET_HEIGHT = 10;

    /**
     * Constructs a bullet object.
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param left Whether the bullet is moving left or right
     */
    public Bullet(double x, double y, boolean left) {
        super("bullet.png", x, y, BULLET_HEIGHT, BULLET_WIDTH, Layer.FOREGROUND);

        this.xVelocity = left ? -BULLET_SPEED : BULLET_SPEED;
        this.yVelocity = 0;
    }

    @Override
    public void tick(List<Entity> entities, double heroX, double floorHeight) {
        this.xPos += this.xVelocity;
        this.yPos += this.yVelocity;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void stop() {
        this.active = false;
    }
}
