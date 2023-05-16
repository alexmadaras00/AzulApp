package dataobjects;

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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (fromFactory == null) {
            if (other.fromFactory != null)
                return false;
        } else if (!fromFactory.equals(other.fromFactory))
            return false;
        if (factoryNumber != other.factoryNumber)
            return false;
        if (type != other.type)
            return false;
        if (toPatternLine == null) {
            if (other.toPatternLine != null)
                return false;
        } else if (!toPatternLine.equals(other.toPatternLine))
            return false;
        if (rowIndex != other.rowIndex)
            return false;
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        return true;
    }   
}
