package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import stickman.entity.Entity;
import stickman.model.GameEngine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The window the Game exists within.
 */
public class GameWindow {

    /**
     * The distance from the top/bottom the player can be before the camera follows.
     */
    private static final double VIEWPORT_MARGIN_VERTICAL = 130.0;

    /**
     * The distance from the left/right side the player can be before the camera follows.
     */
    private static final double VIEWPORT_MARGIN = 280.0;

    /**
     * The width of the screen.
     */
    private final int width;

    /**
     * The height of the screen.
     */
    private final int height;

    /**
     * The current running scene.
     */
    private Scene scene;

    /**
     * The pane of the window on which sprites are projected.
     */
    private Pane pane;

    /**
     * The GameEngine of the game.
     */
    private GameEngine model;

    /**
     * A list of all the entities' views in the Game.
     */
    private List<EntityView> entityViews;

    /**
     * The background of the scene.
     */
    private BackgroundDrawer backgroundDrawer;

    /**
     * The x-offset of the camera.
     */
    private double xViewportOffset = 0.0;

    /**
     * The y-offset of the camera.
     */
    private double yViewportOffset = 0.0;

    private Text time;
    private Text lives;
    private Text currentScore;
    private Text totalScore;

    private int tick = 0;

    /**
     * Creates a new GameWindow object.
     * @param model The GameEngine of the game
     * @param width The width of the screen
     * @param height The height of the screen
     */
    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        this.pane = new Pane();
        this.width = width;
        this.height = height;
        this.scene = new Scene(pane, width, height);

        this.entityViews = new ArrayList<>();

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        this.backgroundDrawer = new BlockedBackground();

        backgroundDrawer.draw(model, pane);

        initTimeDisplay();
        initLivesDisplay();
        initScoreDisplay();
    }

    private void initTimeDisplay() {
        time = new Text();
        time.setFill(Color.BLACK);
        time.setX(10.0);
        time.setY(20.0);
        time.setViewOrder(0.0);
        time.setFont(Font.font(15));
        pane.getChildren().add(time);
    }

    private void initLivesDisplay() {
        lives = new Text();
        lives.setFill(Color.BLACK);
        lives.setX(10.0);
        lives.setY(40.0);
        lives.setViewOrder(0.0);
        lives.setFont(Font.font(15));
        pane.getChildren().add(lives);
    }

    private void initScoreDisplay() {
        currentScore = new Text();
        currentScore.setFill(Color.BLACK);
        currentScore.setX(500.0);
        currentScore.setY(20.0);
        currentScore.setViewOrder(0.0);
        currentScore.setFont(Font.font(15));
        pane.getChildren().add(currentScore);

        totalScore = new Text();
        totalScore.setFill(Color.BLACK);
        totalScore.setX(500.0);
        totalScore.setY(40.0);
        totalScore.setViewOrder(0.0);
        totalScore.setFont(Font.font(15));
        pane.getChildren().add(totalScore);
    }

    private void updateTimeDisplay() {
        DecimalFormat df = new DecimalFormat("0.00");
        time.setText("Elapsed time: " + df.format(tick * 0.017));
    }

    private void updateLivesDisplay() {
        lives.setText("Lives: 5");
    }

    private void updateScoreDisplay() {
        currentScore.setText("Current Score:");
        totalScore.setText("Total Score: ");
    }


    /**
     * Returns the scene.
     * @return The current scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Starts the game.
     */
    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Draws the game (and updates it).
     */
    private void draw() {
        model.tick();

        tick++;
        updateTimeDisplay();
        updateLivesDisplay();
        updateScoreDisplay();

        List<Entity> entities = model.getCurrentLevel().getEntities();

        for (EntityView entityView: entityViews) {
            entityView.markForDelete();
        }

        double heroXPos = model.getCurrentLevel().getHeroX();
        heroXPos -= xViewportOffset;

        if (heroXPos < VIEWPORT_MARGIN) {
            if (xViewportOffset >= 0) { // Don't go further left than the start of the level
                xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
                if (xViewportOffset < 0) {
                    xViewportOffset = 0;
                }
            }
        } else if (heroXPos > width - VIEWPORT_MARGIN) {
            xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
        }

        double heroYPos = model.getCurrentLevel().getHeroY();
        heroYPos -= yViewportOffset;

        if (heroYPos < VIEWPORT_MARGIN_VERTICAL) {
            if (yViewportOffset >= 0) { // Don't go further up than the top of the level
                yViewportOffset -= VIEWPORT_MARGIN_VERTICAL - heroYPos;
            }
        } else if (heroYPos > height - VIEWPORT_MARGIN_VERTICAL) {
            yViewportOffset += heroYPos - (height - VIEWPORT_MARGIN_VERTICAL);
        }

        backgroundDrawer.update(xViewportOffset, yViewportOffset);

        for (Entity entity: entities) {
            boolean notFound = true;
            for (EntityView view: entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (EntityView entityView: entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
    }
}
