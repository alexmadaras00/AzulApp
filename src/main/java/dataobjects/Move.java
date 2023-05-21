package dataobjects;

import java.util.Objects;

import model.Color;

public class Move implements DataObject {
    private Boolean fromFactory;
    private int factoryNumber;
    private Color type;
    private Boolean toPatternLine;
    private int rowIndex;
    private PlayerData player;

    public Move() {
    }

    public Boolean getFromFactory() {
        return fromFactory;
    }

    public void setFromFactory(Boolean fromFactory) {
        this.fromFactory = fromFactory;
    }

    public int getFactoryNumber() {
        return factoryNumber;
    }

    public void setFactoryNumber(int factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public Color getType() {
        return type;
    }

    public void setType(Color type) {
        this.type = type;
    }

    public Boolean getToPatternLine() {
        return toPatternLine;
    }

    public void setToPatternLine(Boolean toPatternLine) {
        this.toPatternLine = toPatternLine;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Move))
            return false;
        Move other = (Move) obj;
        return Objects.equals(fromFactory, other.fromFactory) &&
                factoryNumber == other.factoryNumber &&
                Objects.equals(type, other.type) &&
                Objects.equals(toPatternLine, other.toPatternLine) &&
                rowIndex == other.rowIndex &&
                Objects.equals(player, other.player);
    }
}
