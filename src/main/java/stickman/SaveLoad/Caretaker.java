package stickman.SaveLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible fo the memento's safekeeping
 */
public class Caretaker {

    /**
     * List of all the saved memento object
     */
    private List<Memento> mementoList = new ArrayList<>();

    /**
     * Add more state into Caretaker object
     * @param state all the information of current state, stored in Memento
     */
    public void add(Memento state) {
        mementoList.add(state);
    }

    /**
     * Retrieve the last state from Caretaker object
     * @return all the information of last state, stored in Memento
     */
    public Memento get() {
        if (mementoList.size() == 0) {
            return null;
        }
        Memento last = mementoList.get(mementoList.size() - 1);
        mementoList.remove(mementoList.size() - 1);
        return last;
    }
}
