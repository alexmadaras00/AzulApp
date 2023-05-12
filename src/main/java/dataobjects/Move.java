package dataobjects;

import java.util.OptionalInt;

import model.Color;

public class Move implements DataObject {
    private Boolean isFactory;
    private OptionalInt number;
    private Color type;
    private PlayerData player;

    public Move() {
    }
    public Move(Boolean isFactory, OptionalInt number, Color type, PlayerData player) {
        this.isFactory = isFactory;
        this.number = number;
        this.type = type;
        this.player = player;
    }
    public Boolean getIsFactory() {
        return isFactory;
    }
    public void setIsFactory(Boolean isFactory) {
        this.isFactory = isFactory;
    }
    public OptionalInt getNumber() {
        return number;
    }
    public void setNumber(OptionalInt number) {
        this.number = number;
    }
    public Color getType() {
        return type;
    }
    public void setType(Color type) {
        this.type = type;
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
        if (isFactory == null) {
            if (other.isFactory != null)
                return false;
        } else if (!isFactory.equals(other.isFactory))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (type != other.type)
            return false;
        if (player == null) {
            if (other.player != null)
                return false;
        } else if (!player.equals(other.player))
            return false;
        return true;
    }
    
}
