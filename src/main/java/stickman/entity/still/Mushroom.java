package stickman.entity.still;

import stickman.entity.GameObject;
import stickman.entity.moving.player.Controllable;
import stickman.entity.Interactable;

/**
 * Mushroom object that the player can pick up to get the ability to shoot.
 */
public class Mushroom extends GameObject implements Interactable {

    /**
     * Creates a new Mushroom object.
     * @param xPos The x-coordinate
     * @param yPos The y-coordinate
     */
    public Mushroom(double xPos, double yPos) {
        super("mushroom_mario.png", xPos, yPos, 20, 20, Layer.FOREGROUND);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact(Controllable hero) {
        if (this.active) {
            this.active = false;
            hero.upgrade();
        }
    }
}
