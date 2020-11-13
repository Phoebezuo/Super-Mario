package stickman.SaveLoad;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

    private List<Memento> mementoList = new ArrayList<>();

    public void add(Memento state) {
        mementoList.add(state);
    }

    public Memento get() {
        if (mementoList.size() == 0) {
            return null;
        }
        Memento last = mementoList.get(mementoList.size() - 1);
        mementoList.remove(mementoList.size() - 1);
        return last;
    }
}
