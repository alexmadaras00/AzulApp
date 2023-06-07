package dataobjects.data;

import dataobjects.DataObject;
import model.TileColor;

public class ScoreChange implements DataObject {
    private ScoreType type;
    private TileColor color;
    private int index;
    private int scoreDifference;

    public ScoreChange() {
    }

    public TileColor getColor() {
        return color;
    }

    public void setColor(TileColor color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getScoreDifference() {
        return scoreDifference;
    }

    public void setScoreDifference(int scoreDifference) {
        this.scoreDifference = scoreDifference;
    }

    public ScoreType getType() {
        return type;
    }

    public void setType(ScoreType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ScoreChange))
            return false;

        ScoreChange that = (ScoreChange) o;

        if (getIndex() != that.getIndex())
            return false;
        if (getScoreDifference() != that.getScoreDifference())
            return false;
        if (getType() != that.getType())
            return false;
        return getColor() == that.getColor();
    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + getIndex();
        result = 31 * result + getScoreDifference();
        return result;
    }
}
