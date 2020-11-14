package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Memento {

    /**
     * Store internal state of Originator object
     */
    private LevelManager levelManager;

    /**
     * Create a Memento object with level information
     * @param levelManager store all the level information
     */
    public Memento(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    /**
     * Get the level information stored in this Memento object
     * @return level information
     */
    public LevelManager getState() {
        return levelManager;
    }
}
