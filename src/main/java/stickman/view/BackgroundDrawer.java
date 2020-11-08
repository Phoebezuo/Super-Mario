package stickman.view;

import javafx.scene.layout.Pane;
import stickman.model.GameEngine;

/**
 * Interface for background objects.
 */
public interface BackgroundDrawer {

    /**
     * Draws the background to screen.
     * @param model The current GameEngine
     * @param pane The Pane to draw to
     */
    void draw(GameEngine model, Pane pane);

    /**
     * Updates the x-offset of all shapes and sprites.
     * @param xViewportOffset The offset the hero is at
     */
    void update(double xViewportOffset, double yViewportOffset);
}
