package stickman.entity;

import stickman.entity.moving.player.Controllable;

/**
 * Interface describing the behaviours for an interactable object.
 * An entity is interactable if colliding with the player character
 * results in some event (e.g. loss of life, upgrade, etc).
 */
public interface Interactable extends Entity {

    /**
     * Called when the player collides with the entity.
     * @param hero The hero object
     */
    void interact(Controllable hero);
}
