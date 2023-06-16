package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import shared.PlayerTile;
import shared.Tile;

public class FloorLine {
    private List<Tile> tiles = new ArrayList<>();
    private List<Integer> scores = Arrays.asList(-1,-1,-2,-2,-2,-3,-3);

    /**
     * method to add tile to floor line
     * @param t = the tiles that will be added to floor line
     * @return excess tiles if the floorline is full
     */
    public List<Tile> addTiles(List<Tile> t){
        List<Tile> excessTiles = new ArrayList<>();
        
        if (t.contains(PlayerTile.getInstance())) {
            // t.remove(PlayerTile.getInstance()); apparently this is not possible
            if (tiles.size() == 7) {
                excessTiles.add(tiles.remove(0));
            }
            tiles.add(0, PlayerTile.getInstance());
        }

        for(int i=0; i<t.size(); i++) {
            if (t.get(i) == PlayerTile.getInstance()) continue;
            if (tiles.size() < 7) {
                tiles.add(t.get(i));
            } else {
                excessTiles.add(t.get(i));
            }
        }
        return excessTiles;
    }

    /**
     * method to get penalty score from the floorline
     * @return penalty score
     */
    public int getScore(){
        Integer penalty = 0;
        for (var i = 0; i < tiles.size(); i++) {
            penalty+=scores.get(i);
        }
        return penalty;
    }

    /**
     *
     * @return immutable list of tiles in floor line
     */
    public List<Tile> getCopyTiles(){
        return Collections.unmodifiableList(tiles);
    }

    /**
     * method to clear the tiles in floor line
     * @return clearedTiles
     */
    public List<Tile> clearTiles(){

        List<Tile> clearedTiles = tiles;
        tiles = (new ArrayList<>());
        return clearedTiles;
    }
}