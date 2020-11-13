package stickman.SaveLoad;

import stickman.level.LevelManager;

public class Originator {

    private LevelManager levelManager;

    public LevelManager getState()  {
        return levelManager;
    }

    public void setState(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public Memento createMemento() {
        return new Memento(levelManager);
    }

    public void setMomento(Memento memento) {
        if (memento == null) {
            System.out.println("Nothing is saved, cannot load, automatically closing...");
            System.exit(0);
        }
        levelManager = memento.getState();
    }
}
