package dataobjects;

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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScoreChange other = (ScoreChange) obj;
        if (isCompletionScore == null) {
            if (other.isCompletionScore != null)
                return false;
        } else if (!isCompletionScore.equals(other.isCompletionScore))
            return false;
        if (hasRowIndex == null) {
            if (other.hasRowIndex != null)
                return false;
        } else if (!hasRowIndex.equals(other.hasRowIndex))
            return false;
        if (hasColor == null) {
            if (other.hasColor != null)
                return false;
        } else if (!hasColor.equals(other.hasColor))
            return false;
        if (color != other.color)
            return false;
        if (index != other.index)
            return false;
        if (scoreDifference != other.scoreDifference)
            return false;
        return true;
    }

    
}
