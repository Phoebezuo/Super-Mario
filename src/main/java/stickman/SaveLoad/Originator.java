package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Originator {

    /**
     * All information in current level
     */
    private LevelManager levelManager;

    /**
     * Get current state
     * @return current state
     */
    public LevelManager getState()  {
        return levelManager;
    }

    /**
     * Set current state
     * @param levelManager current state
     */
    public void setState(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    /**
     * Create a Memento object with levelManager
     * @return Memento object
     */
    public Memento createMemento() {
        return new Memento(levelManager);
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
    }
}
