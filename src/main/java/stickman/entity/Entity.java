package stickman.entity;

/**
 * Interface describing the behaviours of Entity objects. All objects
 * in the level that are drawn implement this interface.
 */
public interface Entity {

    /**
     * Returns the current sprite path.
     * @return Path to current image
     */
    String getImagePath();

    /**
     * Gets the current x position.
     * @return X coordinate
     */
    double getXPos();

    /**
     * Gets the current y position.
     * @return Y coordinate
     */
    double getYPos();

    /**
     * Gets the height of the entity.
     * @return The height
     */
    double getHeight();

    /**
     * Gets the width of the entity.
     * @return The width
     */
    double getWidth();

    /**
     * Gets the layer the entity is on.
     * @return The entity's layer
     */
    Layer getLayer();

    /**
     * Returns true if other entities cannot pass through.
     * @return Whether entity is solid
     */
    boolean isSolid();

    /**
     * Returns true if the entity is active (and hence shouldn't be deleted).
     * @return Whether the entity is active
     */
    boolean isActive();

    /**
     * Returns true if the two entities are colliding (based on AAB)
     * @param other The other entity
     * @return Whether the two entities are colliding
     */
    default boolean checkCollide(Entity other) {
        boolean a = getXPos() < other.getXPos() + other.getWidth();
        boolean b = getXPos() + getWidth() > other.getXPos();
        boolean c = getYPos() < other.getYPos() + other.getHeight();
        boolean d = getYPos() + getHeight() > other.getYPos();
        return a && b && c && d;
    }

    /**
     * The potential z-coordinates entities can exist on.
     */
    enum Layer{
        BACKGROUND, FOREGROUND, EFFECT
    }
}
