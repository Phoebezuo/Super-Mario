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
import stickman.level.LevelManager;
import stickman.model.GameEngine;
import stickman.model.GameManager;

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

    private Text timeText;
    private Text livesText;
    private Text levelText;
    private Text currentScoreText;
    private Text totalScoreText;

    private Timeline timeline;

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
        initLevelDisplay();
        initScoreDisplay();
    }

    private void initTimeDisplay() {
        timeText = new Text();
        timeText.setFill(Color.BLACK);
        timeText.setX(10.0);
        timeText.setY(20.0);
        timeText.setViewOrder(0.0);
        timeText.setFont(Font.font(15));
        pane.getChildren().add(timeText);
    }

    private void initLivesDisplay() {
        livesText = new Text();
        livesText.setFill(Color.BLACK);
        livesText.setX(10.0);
        livesText.setY(40.0);
        livesText.setViewOrder(0.0);
        livesText.setFont(Font.font(15));
        pane.getChildren().add(livesText);
    }

    private void initLevelDisplay() {
        levelText = new Text();
        levelText.setFill(Color.BLACK);
        levelText.setX(280.0);
        levelText.setY(20.0);
        levelText.setViewOrder(0.0);
        levelText.setFont(Font.font(15));
        pane.getChildren().add(levelText);
    }

    private void initScoreDisplay() {
        currentScoreText = new Text();
        currentScoreText.setFill(Color.BLACK);
        currentScoreText.setX(500.0);
        currentScoreText.setY(20.0);
        currentScoreText.setViewOrder(0.0);
        currentScoreText.setFont(Font.font(15));
        pane.getChildren().add(currentScoreText);

        totalScoreText = new Text();
        totalScoreText.setFill(Color.BLACK);
        totalScoreText.setX(500.0);
        totalScoreText.setY(40.0);
        totalScoreText.setViewOrder(0.0);
        totalScoreText.setFont(Font.font(15));
        pane.getChildren().add(totalScoreText);
    }

    private void updateTimeDisplay() {
        DecimalFormat df = new DecimalFormat("0.00");
        timeText.setText("Elapsed time: " + df.format(tick * 0.017));
    }

    private void updateLivesDisplay() {
        livesText.setText("Lives: " + ((LevelManager) model.getCurrentLevel()).getHeroLives());
    }

    private void updateLevelDisplay() {
        levelText.setText("Level " + ((GameManager) model).getLevel());
    }

    private void updateScoreDisplay() {
        currentScoreText.setText("Current Score:");
        totalScoreText.setText("Total Score: ");
    }

    private void initStatusDisplay(String s) {
        this.pane.getChildren().clear();
        Text text = new Text(s);
        text.setX(170);
        text.setY(200);
        text.setFont(Font.font(50));
        pane.getChildren().add(text);
        timeline.stop();
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
        timeline = new Timeline(new KeyFrame(Duration.millis(17),
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
        updateLevelDisplay();
        updateScoreDisplay();

        if (model.getCurrentLevel().isWon()) {
            initStatusDisplay("You are won!");
        } else if (model.getCurrentLevel().isLose()) {
            initStatusDisplay("You are lose!");
        }

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
