package shared;

public class PlayerTile implements Tile {
    private static PlayerTile instance;

    private PlayerTile() {
    }
    public static synchronized PlayerTile getInstance() {
        if (instance == null) {
            instance = new PlayerTile();
        }
        return instance;
    }
}