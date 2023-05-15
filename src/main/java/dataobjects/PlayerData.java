package dataobjects;

import java.util.Optional;

public class PlayerData implements DataObject {
    private Optional<String> name;
    private int identifier;
    private Optional<PlayerBoardState> playerBoard;

    public PlayerData() {
    }
    public PlayerData(Optional<String> name, int identifier, Optional<PlayerBoardState> playerBoard) {
        this.name = name;
        this.identifier = identifier;
        this.playerBoard = playerBoard;
    }
    public Optional<String> getName() {
        return name;
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }
    public int getIdentifier() {
        return identifier;
    }
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    public Optional<PlayerBoardState> getPlayerBoard() {
        return playerBoard;
    }
    public void setPlayerBoard(Optional<PlayerBoardState> playerBoard) {
        this.playerBoard = playerBoard;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerData other = (PlayerData) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (identifier != other.identifier)
            return false;
        if (playerBoard == null) {
            if (other.playerBoard != null)
                return false;
        } else if (!playerBoard.equals(other.playerBoard))
            return false;
        return true;
    }
    
}
