package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Originator {

    /**
     * All information in current level
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
     * Get current state
     * @return current state
     */
    public LevelManager getState()  {
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
    /**
     * Set current state
     * @param levelManager current state
     */
    public void setState(LevelManager levelManager, int currentScore, int prevScore) {
        this.levelManager = levelManager;
        this.currentScore = currentScore;
        this.prevScore = prevScore;
    }


    /**
     * Create a Memento object with levelManager
     * @return Memento object
     */
    public Memento createMemento() {
        return new Memento(levelManager, currentScore, prevScore);
    }

    /**
     * Set levelManager from a Memento object
     * @param memento Memento object
     */
    public void setMomento(Memento memento) {
        if (memento == null) {
            System.out.println("Nothing is saved, cannot load, automatically closing...");
            System.exit(0);
        }
        levelManager = memento.getState();
        currentScore = memento.getCurrentScore();
        prevScore = memento.getPrevScore();
    }
}
