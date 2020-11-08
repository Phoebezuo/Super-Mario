package stickman.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stickman.entity.Entity;

/**
 * The standard implementation of the EntityView interface.
 */
public class EntityViewImpl implements EntityView {

    /**
     * The Entity this view is associated with.
     */
    private Entity entity;

    /**
     * Whether the view should be deleted.
     */
    private boolean delete = false;

    /**
     * The ImageView node this view is associated with.
     */
    private ImageView node;

    /**
     * The path to this view's sprite.
     */
    private String imagePath;

    /**
     * Constructs an EntityViewImpl object.
     * @param entity The corresponding Entity
     */
    EntityViewImpl(Entity entity) {
        this.entity = entity;
        this.imagePath = entity.getImagePath();
        this.node = new ImageView(imagePath);
        this.node.setViewOrder(getViewOrder(entity.getLayer()));
        update(0, 0);
    }

    /**
     * Converts the layer into a z-coordinate.
     * @param layer The layer the view is on
     * @return The z-coordinate of the view as a double
     */
    private double getViewOrder(Entity.Layer layer) {
        switch (layer) {
            case BACKGROUND: return 100.0;
            case FOREGROUND: return 50.0;
            case EFFECT: return 25.0;
            default: throw new IllegalStateException("Javac doesn't understand how enums work so now I have to exist");
        }
    }

    @Override
    public void update(double xViewportOffset, double yViewportOffset) {
        String newPath = entity.getImagePath();
        if (!imagePath.equals(newPath)) {
            imagePath = newPath;
            node.setImage(new Image(imagePath));
        }
        node.setX(entity.getXPos() - xViewportOffset);
        node.setY(entity.getYPos() - yViewportOffset);
        node.setFitHeight(entity.getHeight());
        node.setFitWidth(entity.getWidth());
        node.setPreserveRatio(true);
        delete = false;
    }

    @Override
    public boolean matchesEntity(Entity entity) {
        return this.entity.equals(entity);
    }

    @Override
    public void markForDelete() {
        this.delete = true;
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @Override
    public boolean isMarkedForDelete() {
        return this.delete;
    }
}
