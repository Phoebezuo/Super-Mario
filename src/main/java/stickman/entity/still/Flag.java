package stickman.entity.still;

import stickman.entity.GameObject;
import stickman.entity.moving.player.Controllable;
import stickman.entity.Interactable;

/**
 * Flag object that the player can interact with to complete the level.
 */
public class Flag extends GameObject implements Interactable {

    /**
     * Creates a new Flag object
     * @param xPos The x-coordinate
     * @param yPos The y-coordinate
     */
    public Flag(double xPos, double yPos) {
        super("flag.png", xPos, yPos, 40, 30, Layer.FOREGROUND);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact(Controllable hero) {
        hero.win();
    }
}
