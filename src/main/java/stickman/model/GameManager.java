package stickman.model;

import stickman.SaveLoad.Caretaker;
import stickman.SaveLoad.Originator;
import stickman.level.*;
import java.util.List;

/**
 * Implementation of GameEngine. Manages the running of the game.
 */
public class GameManager implements GameEngine {

    /**
     * The current level
     */
    private Level level;

    /**
     * List of all level files
     */
    private List<String> levelFileNames;

    /**
     * The current level number
     */
    private int currentLevel = 0;

    /**
     * The current number of lives left
     */
    private double heroLives;

    /**
     * The score of current level
     */
    private int currentScore = 0;

    /**
     * The total score of previous levels
     */
    private int prevScore = 0;

    /**
     * Record the start of level
     */
    private double startTime;

    /**
     * Create memento containing a snapshot of current level
     */
    private Originator originator;

    /**
     * Responsible for memento's safekeeping
     */
    private Caretaker careTaker;

    /**
     * Creates a GameManager object.
     * @param levels The config file containing the names of all the levels
     */
    public GameManager(String levels) {
        ConfigFile configFile = ConfigFile.getConfigFile(levels);
        this.heroLives = configFile.getHeroLives();
        this.levelFileNames = configFile.getLevelFileNames();
        this.startTime = System.currentTimeMillis();
        this.originator = new Originator();
        this.careTaker = new Caretaker();
        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(currentLevel), this);
    }

    public double getStartTime() {
        return startTime;
    }

    @Override
    public Level getCurrentLevel() {
        return this.level;
    }

    @Override
    public boolean jump() {
        return this.level.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.level.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.level.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.level.stopMoving();
    }

    @Override
    public void tick() {
        this.level.tick();
    }

    @Override
    public void shoot() {
        this.level.shoot();
    }

    @Override
    public void save() {
        LevelManager copiedLevel = ((LevelManager) level).deepCopy();
        originator.setState(copiedLevel, currentScore, prevScore);
        careTaker.add(originator.createMemento());
    }

    @Override
    public void load() {
        originator.setMomento(careTaker.get());
        this.level = originator.getState();
        this.currentScore = originator.getCurrentScore();
        this.prevScore = originator.getPrevScore();
    }

    public int getLevel() {
        return currentLevel + 1;
    }

    public void nextLevel() {
        this.currentLevel++;
        if (currentLevel >= levelFileNames.size())  {
            ((LevelManager) level).setWon(true);
            return;
        }
        prevScore += currentScore;
        currentScore = 0;
        startTime = System.currentTimeMillis();
        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(currentLevel), this);
    }

    public void decreaseLives() {
        heroLives--;
    }

    public double getLives() {
        return heroLives;
    }

    public void changeCurrentScore(int value) {
        currentScore += value;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int value) {
        currentScore = value;;
    }

    public int getPrevScore() {
        return prevScore;
    }
}