package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Memento {

    private LevelManager levelManager;

    public Memento(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public LevelManager getState() {
        return levelManager;
    }
}
