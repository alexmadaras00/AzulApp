package dataobjects;

import java.util.Objects;

public class PlayerData implements DataObject {
    private String name;
    private int identifier;

    public PlayerData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PlayerData))
            return false;
        PlayerData other = (PlayerData) obj;
        return Objects.equals(name, other.name) &&
                identifier == other.identifier;
    }

}
