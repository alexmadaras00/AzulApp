package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatternLine {
    private List<List<Tile>> table = new ArrayList<>();

    public PatternLine() {
        for (int i = 0; i < 5; i++) {
            table.add(new ArrayList<>());
        }
    }

    /**
     * add tiles to pattern line
     *
     * @param row = index of the pattern line, also means the size-1 of the pattern line [0..4]
     * @param t   = the tiles that will be added to the line
     * @return excess tiles if the pattern line is full and will be added to floor line
     */
    public List<Tile> addTiles(int row, List<Tile> t) {
        List<Tile> excessTiles = new ArrayList<>();
        List<Tile> tiles = table.get(row);
        for (int i = 0; i < t.size(); i++) {
            if (tiles.size() <= row) {
                tiles.add(t.get(i));
            } else {
                excessTiles.add(t.get(i));
            }
        }
        table.set(row, tiles);
        return excessTiles;
    }

    /**
     * check if the pattern line can be added
     *
     * @param row  = index of the pattern line, also means the size-1 of the pattern line [0..4]
     * @param type = the color or the tiles
     * @return true if the pattern line is not null and the color matches, else return false
     */
    public Boolean canAddTile(int row, Color type) {
        return ((table.get(row).size() == 0) || table.get(row).contains(type)) && table.get(row).size() <= row;
    }

    /**
     * clear the patternLine[row]
     *
     * @param row = index of the pattern line, also means the size-1 of the pattern line [0..4]
     * @return the tiles removed from the patternLine.
     */
    public List<Tile> clearTiles(int row) {
        List<Tile> clearedTiles = table.get(row);
        table.set(row, new ArrayList<>());
        return clearedTiles;
    }

    /**
     * Check which rows have completed tiles
     *
     * @return list of the rows.
     */
    public List<Integer> completedRows() {
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (table.get(i).size() == i + 1) {
                rows.add(i);
            }
        }
        return rows;
    }

    public List<List<Tile>> getCopyTable() {
        List<List<Tile>> line = Collections.unmodifiableList(table);
        return line;
    }
}
