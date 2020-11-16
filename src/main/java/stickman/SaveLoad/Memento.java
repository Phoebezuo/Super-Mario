package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Memento {

    /**
     * Store internal state of Originator object
     */
    private LevelManager levelManager;

    /**
     * Current score in saved version
     */
    private int currentScore;

    /**
     * Previous score in saved version
     */
    private int prevScore;

    /**
     * Create a Memento object with level information
     * @param levelManager store all the level information
     */
    public Memento(LevelManager levelManager, int currentScore, int prevScore) {
        this.levelManager = levelManager;
        this.currentScore = currentScore;
        this.prevScore = prevScore;
    }

    /**
     * Get the level information stored in this Memento object
     * @return level information
     */
    public LevelManager getState() {
        return levelManager;
    }

    /**
     * Get current score
     * @return current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Get previous score
     * @return previous score
     */
    public int getPrevScore() {
        return prevScore;
    }
}
