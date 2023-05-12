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
}
