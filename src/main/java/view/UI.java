package view;

import controller.DataObject;

public interface UI {
    void update(DataObject object);

    DataObject getMove();
}
