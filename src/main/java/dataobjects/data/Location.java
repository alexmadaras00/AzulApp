package dataobjects.data;

import model.TileColor;

import java.util.Objects;

import dataobjects.DataObject;

public class Location implements DataObject {
    private LocationType type;
    private int index;

    public Location() {
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;

        Location location = (Location) o;

        if (getIndex() != location.getIndex())
            return false;
        return getType() == location.getType();
    }

}
