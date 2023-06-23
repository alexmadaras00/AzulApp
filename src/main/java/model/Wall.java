package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Wall {
    private Tile[][] wall;
    private Tile[] colors;

    public Wall() {
        colors = TileColor.values();
        int size = colors.length;
        wall = new Tile[size][size];
    }

    public static List<List<TileColor>> wallPattern() {
        List<List<TileColor>> wallTemplate = new ArrayList<>();
        List<TileColor> colors = Arrays.asList(TileColor.values());
        for (int i = 0; i < colors.size(); i++) {
            List<TileColor> wallLine = new ArrayList<>();
            for (int j = 0; j < colors.size(); j++) {
                TileColor color = getTemplateColor(i, j);
                wallLine.add(color);
            }
            wallTemplate.add(wallLine);
        }
        return wallTemplate;
    }

    public static TileColor getTemplateColor(int row, int col) {
        TileColor[] colors = TileColor.values();
        return colors[(col - row + colors.length) % colors.length];
    }

    public int addTile(int row, Tile tile) {
        int index = getIndexOfTile(row, tile);
        int score = getPlacementScore(row, index);
        wall[row][index] = tile;
        return score;
    }

    public boolean canAddTile(int row, Tile type) {
        int index = getIndexOfTile(row, type);
        return wall[row][index] == null;
    }

    public int getCompletedRowCount() {
        int count = 0;
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                count++;
            }
        }
        return count;
    }

    public List<Integer> getCompletionScores() {

        List<Tile> completedColors = getColorCompletions();
        List<Integer> scoreList = new LinkedList<>(generateColorScoreChange(completedColors));
        List<Integer> completedRows = getHorizontalCompletions();
        scoreList.addAll(generateLineScoreChange(completedRows, true));
        List<Integer> completedColums = getVerticalCompletions();
        scoreList.addAll(generateLineScoreChange(completedColums, false));
        return scoreList;
    }

    public List<List<Tile>> getCopyTable() {
        ArrayList<List<Tile>> listCopy = new ArrayList<>();
        for (Tile[] row : wall) {
            listCopy.add(Collections.unmodifiableList(Arrays.asList(row)));
        }
        return Collections.unmodifiableList(listCopy);
    }

    private List<Integer> generateColorScoreChange(List<Tile> completedColor) {
        List<Integer> scoreChanges = new LinkedList<>();
        for (Tile color : completedColor) {
            int completedColorScore = 10;
            scoreChanges.add(completedColorScore);
        }
        return scoreChanges;
    }

    private List<Integer> generateLineScoreChange(List<Integer> completedLines, boolean isRow) {
        List<Integer> scoreChanges = new LinkedList<>();
        for (int line : completedLines) {
            int completedColumnScore = 7;
            int completedRowScore = 2;
            scoreChanges.add(isRow ? completedRowScore : completedColumnScore);
        }
        return scoreChanges;
    }

    private int getIndexOfTile(int row, Tile tile) {
        for (int i = 0; i < colors.length; i++) {
            if (getTemplateColor(row, i) == tile) {
                return i;
            }
        }
        return -1;
    }

    private int getVerticalPlacementScore(int row, int col) {
        int score = 1;
        for (int rowPlus = row + 1; rowPlus < colors.length; rowPlus++) {
            if (wall[rowPlus][col] != null) {
                score++;
            } else {
                break;
            }
        }
        for (int rowMinus = row - 1; rowMinus >= 0; rowMinus--) {
            if (wall[rowMinus][col] != null) {
                score++;
            } else {
                break;
            }
        }
        return score;
    }

    private int getHorizontalPlacementScore(int row, int col) {
        int score = 1;
        for (int colPlus = col + 1; colPlus < colors.length; colPlus++) {
            if (wall[row][colPlus] != null) {
                score++;
            } else {
                break;
            }
        }
        for (int colMinus = col - 1; colMinus >= 0; colMinus--) {
            if (wall[row][colMinus] != null) {
                score++;
            } else {
                break;
            }
        }
        return score;
    }

    private int getPlacementScore(int row, int col) {
        int verScore = getVerticalPlacementScore(row, col);
        int horScore = getHorizontalPlacementScore(row, col);
        horScore = horScore == 1 ? 0 : horScore;
        verScore = verScore == 1 ? 0 : verScore;
        int score = verScore + horScore;
        return (score == 0) ? 1 : score;
    }

    private boolean isCompleteRow(int row) {
        Tile[] TileRow = wall[row];
        for (Tile c : TileRow) {
            if (c == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isCompleteCol(int col) {
        for (int row = 0; row < colors.length; row++) {
            if (wall[row][col] == null) {
                return false;
            }
        }
        return true;
    }

    private List<Tile> getColorCompletions() {
        List<Tile> completedColors = new LinkedList<>();
        Map<Tile, Integer> tileCount = new HashMap<>();
        for (Tile[] row : wall) {
            for (Tile tile : row) {
                if (tile != null) {
                    tileCount.put(tile, tileCount.get(tile) != null ? tileCount.get(tile) + 1 : 1);
                }
            }}
        for (Map.Entry<Tile, Integer> entry : tileCount.entrySet())
            if (entry.getValue() == colors.length) { completedColors.add(entry.getKey());}
        return completedColors;
    }

    private List<Integer> getHorizontalCompletions() {
        List<Integer> completedRows = new LinkedList<>();
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                completedRows.add(row);
            }
        }
        return completedRows;
    }

    private List<Integer> getVerticalCompletions() {
        List<Integer> completedColumns = new LinkedList<>();
        for (int col = 0; col < colors.length; col++) {
            if (isCompleteCol(col)) {
                completedColumns.add(col);
            }
        }
        return completedColumns;
    }

}
