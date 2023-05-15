package view;

import dataobjects.DataObject;

public interface UI {
    void update(DataObject object);

    DataObject getMove();
}
