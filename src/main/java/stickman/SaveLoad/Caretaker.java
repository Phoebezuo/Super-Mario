package stickman.SaveLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible fo the memento's safekeeping
 */
public class Caretaker {

    /**
     * Saved memento object
     */
    private Memento memento;

    /**
     * Set state into Caretaker object
     * @param state all the information of current state, stored in Memento
     */
    public void setState(Memento state) {
        this.memento = state;
    }

    /**
     * Retrieve the last saved state from Caretaker object
     * @return all the information of last state, stored in Memento
     */
    public Memento getState() {
        return memento;
    }
}
