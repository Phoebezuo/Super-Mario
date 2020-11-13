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

    private Text elapsedTimeText;
    private Text targetTimeText;
    private Text livesText;
    private Text levelText;
    private Text currentScoreText;
    private Text prevScoreText;
    private Timeline timeline;

    private int counter = 0;
    private double displayTime = 0.0;

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
        elapsedTimeText = new Text();
        elapsedTimeText.setFill(Color.BLACK);
        elapsedTimeText.setX(10.0);
        elapsedTimeText.setY(20.0);
        elapsedTimeText.setViewOrder(0.0);
        elapsedTimeText.setFont(Font.font(15));

        targetTimeText = new Text();
        targetTimeText.setFill(Color.BLACK);
        targetTimeText.setX(10.0);
        targetTimeText.setY(40.0);
        targetTimeText.setViewOrder(0.0);
        targetTimeText.setFont(Font.font(15));
        pane.getChildren().addAll(elapsedTimeText, targetTimeText);
    }

    private void initLivesDisplay() {
        livesText = new Text();
        livesText.setFill(Color.BLACK);
        livesText.setX(10.0);
        livesText.setY(60.0);
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

        prevScoreText = new Text();
        prevScoreText.setFill(Color.BLACK);
        prevScoreText.setX(500.0);
        prevScoreText.setY(40.0);
        prevScoreText.setViewOrder(0.0);
        prevScoreText.setFont(Font.font(15));
        pane.getChildren().add(prevScoreText);
    }

    private void updateDisplay() {
        double elapsedTime = Math.round(((GameManager) model).getTick() * 0.009 * 10) / 10.0;
        double targetTime =  ((LevelManager) model.getCurrentLevel()).getTargetTime();

        if (elapsedTime < targetTime) {
            counter++;
            if (counter == 100) {
                displayTime++;
                ((GameManager) model).changeCurrentScore(1);
                counter = 0;
            }
        } else {
            counter++;
            if (counter == 100) {
                displayTime++;
                if (((GameManager) model).getCurrentScore() > 0) {
                    ((GameManager) model).changeCurrentScore(-1);
                }
                counter = 0;
            }
        }

        elapsedTimeText.setText("Elapsed Time: " + elapsedTime);
        targetTimeText.setText("Target Time: " + targetTime);
        currentScoreText.setText("Current Score: " + ((GameManager) model).getCurrentScore());
        prevScoreText.setText("Previous Score: " + ((GameManager) model).getPrevScore());
        livesText.setText("Lives: " + ((GameManager) model).getLives());
        levelText.setText("Level " + ((GameManager) model).getLevel());
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

        ((GameManager) model).updateTick();
        updateDisplay();

        if (((LevelManager) model.getCurrentLevel()).isWon()) {
            initStatusDisplay("Winner");
        } else if (((LevelManager) model.getCurrentLevel()).isLose()) {
            initStatusDisplay("Game Over");
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
