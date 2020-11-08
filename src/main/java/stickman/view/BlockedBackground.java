package stickman.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import stickman.model.GameEngine;

/**
 * The background of the Game.
 */
public class BlockedBackground implements BackgroundDrawer {

    /**
     * The sky rectangle.
     */
    private Rectangle sky;

    /**
     * The floor rectangle.
     */
    private Rectangle floor;

    /**
     * The window's pane.
     */
    private Pane pane;

    /**
     * The games' GameEngine.
     */
    private GameEngine model;

    /**
     * The height of the floor.
     */
    private double floorHeight = 0;

    @Override
    public void draw(GameEngine model, Pane pane) {
        this.model = model;
        this.pane = pane;

        double width = pane.getWidth();
        double height = pane.getHeight();
        this.floorHeight = model.getCurrentLevel().getFloorHeight();

        this.sky = new Rectangle(0, 0, width, floorHeight);
        sky.setFill(Paint.valueOf("LIGHTBLUE"));
        sky.setViewOrder(1000.0);

        this.floor = new Rectangle(0, floorHeight, width, height);
        floor.setFill(Paint.valueOf("GREEN"));
        floor.setViewOrder(1000.0);

        pane.getChildren().addAll(sky, floor);
    }

    @Override
    public void update(double xViewportOffset, double yViewportOffset) {
        this.floor.setY(floorHeight - yViewportOffset);
    }
}
