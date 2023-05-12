package dataobjects;

import java.util.Optional;
import java.util.OptionalInt;

import model.Color;

public class ScoreChange implements DataObject {
    private Boolean isRoundScore;
    private Boolean isPatternLine;
    private Optional<Color> color;
    private OptionalInt row;
    private OptionalInt column;
    private int scoreDifference;

    public ScoreChange() {
    }
    public ScoreChange(Boolean isRoundScore, Boolean isPatternLine, Optional<Color> color, OptionalInt row,
            OptionalInt column, int scoreDifference) {
        this.isRoundScore = isRoundScore;
        this.isPatternLine = isPatternLine;
        this.color = color;
        this.row = row;
        this.column = column;
        this.scoreDifference = scoreDifference;
    }
    public Boolean getIsRoundScore() {
        return isRoundScore;
    }
    public void setIsRoundScore(Boolean isRoundScore) {
        this.isRoundScore = isRoundScore;
    }
    public Boolean getIsPatternLine() {
        return isPatternLine;
    }
    public void setIsPatternLine(Boolean isPatternLine) {
        this.isPatternLine = isPatternLine;
    }
    public Optional<Color> getColor() {
        return color;
    }
    public void setColor(Optional<Color> color) {
        this.color = color;
    }
    public OptionalInt getRow() {
        return row;
    }
    public void setRow(OptionalInt row) {
        this.row = row;
    }
    public OptionalInt getColumn() {
        return column;
    }
    public void setColumn(OptionalInt column) {
        this.column = column;
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
        if (isRoundScore == null) {
            if (other.isRoundScore != null)
                return false;
        } else if (!isRoundScore.equals(other.isRoundScore))
            return false;
        if (isPatternLine == null) {
            if (other.isPatternLine != null)
                return false;
        } else if (!isPatternLine.equals(other.isPatternLine))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (row == null) {
            if (other.row != null)
                return false;
        } else if (!row.equals(other.row))
            return false;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        if (scoreDifference != other.scoreDifference)
            return false;
        return true;
    }
    
}
