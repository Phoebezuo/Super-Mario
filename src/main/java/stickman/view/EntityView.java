package stickman.view;

import javafx.scene.Node;
import stickman.entity.Entity;

/**
 * The interface for the JavaFX nodes connected to Entity.
 */
public interface EntityView {

    /**
     * Updates the x-coordinate based on the camera offset.
     * @param xViewportOffset The camera offset
     */
    void update(double xViewportOffset, double yViewportOffset);

    /**
     * Checks whether the view is associated with an entity.
     * @param entity The entity to compare to
     * @return Whether the two are related
     */
    boolean matchesEntity(Entity entity);

    /**
     * Marks the entity view for deletion.
     */
    void markForDelete();

    /**
     * Returns the Node of the view.
     * @return The Node of the view
     */
    Node getNode();

    /**
     * Checks whether the view is marked for deletion.
     * @return Whether the view is marked for deletion
     */
    boolean isMarkedForDelete();
}
