package dataobjects;

import java.util.Objects;

import model.Color;

public class ScoreChange implements DataObject {
    private Boolean isCompletionScore;
    private Boolean hasRowIndex;
    private Boolean hasColor;
    private Color color;
    private int index;
    private int scoreDifference;

    public ScoreChange() {
    }

    public Boolean getIsCompletionScore() {
        return isCompletionScore;
    }

    public void setIsCompletionScore(Boolean isCompletionScore) {
        this.isCompletionScore = isCompletionScore;
    }

    public Boolean getHasRowIndex() {
        return hasRowIndex;
    }

    public void setHasRowIndex(Boolean hasRowIndex) {
        this.hasRowIndex = hasRowIndex;
    }

    public Boolean getHasColor() {
        return hasColor;
    }

    public void setHasColor(Boolean hasColor) {
        this.hasColor = hasColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof ScoreChange))
            return false;
        ScoreChange other = (ScoreChange) obj;
        return Objects.equals(isCompletionScore, other.isCompletionScore) &&
                Objects.equals(hasRowIndex, other.hasRowIndex) &&
                Objects.equals(hasColor, other.hasColor) &&
                Objects.equals(color, other.color) &&
                index == other.index &&
                scoreDifference == other.scoreDifference;
    }

}
