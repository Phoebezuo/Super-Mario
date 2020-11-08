package stickman.entity.moving.enemy;

import stickman.entity.Entity;
import stickman.entity.Interactable;
import stickman.entity.moving.MovingObject;
import stickman.entity.moving.player.Controllable;

import java.util.List;

/**
 * Standard enemy type that kills the player on impact. Bases its movement
 * on the provided EnemyStrategy object.
 */
public class Slime extends MovingObject implements Interactable {

    /**
     * Whether the Slime is moving left or right.
     */
    private boolean left;

    /**
     * The strategy the slime is using.
     */
    private EnemyStrategy strategy;

    /**
     *
     * @param imagePath
     * @param x
     * @param y
     * @param startLeft
     * @param strategy
     */
    public Slime(String imagePath, double x, double y, boolean startLeft, EnemyStrategy strategy) {
        super(imagePath, x, y, 20, 30, Layer.FOREGROUND);

        this.xVelocity = 0;
        this.yVelocity = 0;

        this.left = startLeft;

        this.strategy = strategy;
    }

    @Override
    public void tick(List<Entity> entities, double heroX, double floorHeight) {

        this.gravity(entities, floorHeight);
        this.yPos += this.yVelocity;

        this.xVelocity = this.strategy.think(this, entities, this.left, heroX);
        this.xPos += this.xVelocity;

        if (Math.abs(this.xVelocity) < 0.05) {
            this.left = !this.left;
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact(Controllable hero) {
        hero.die();
    }
}
