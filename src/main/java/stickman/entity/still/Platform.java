package stickman.entity.still;

import stickman.entity.Entity;
import stickman.entity.GameObject;

/**
 * The platforms the player/enemies can walk on.
 */
public class Platform extends GameObject {

    /**
     * Constructs a new Platform object.
     * @param xPos The x-coordinate
     * @param yPos The y-coordinate
     */
    public Platform(double xPos, double yPos) {
        super("foot_tile.png", xPos, yPos, 20, 20, Layer.FOREGROUND);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Entity deepCopy() {
        return new Platform(this.getXPos(), this.getYPos());
    }
}
