package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wall {
    private Color[][] wall;
    private Color[] colors;

    public Wall() {
        colors = Color.values();
        int size = colors.length;
        wall = new Color[size][size];
    }

    public Color getTemplateColor(int row, int col) {
        return colors[(col - row + colors.length) % colors.length];
    }

    private int getIndexOfTile(int row, Color tile) {
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

    public int addTile(int row, Tile tile) {
        Color newTile = (Color) tile;
        int index = getIndexOfTile(row, newTile);
        int score = getPlacementScore(row, index);
        wall[row][index] = newTile;
        return score;
    }

    public boolean canAddTile(int row, Color type) {
        int index = getIndexOfTile(row, type);
        return wall[row][index] == null ? true : false;
    }

    private boolean isCompleteRow(int row) {
        Color[] TileRow = wall[row];
        for (Color c : TileRow) {
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

    public boolean hasCompleteRow() {
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                return true;
            }
        }
        return false;
    }

    private int getColorCompletionScore() {
        int score = 0;
        Map<Color, Integer> tileCount = new HashMap<Color, Integer>();
        for (Color[] row : wall) {
            for (Color tile : row) {
                if (tile != null) {
                    tileCount.put(tile, tileCount.get(tile) != null ? tileCount.get(tile) + 1 : 1);
                }
            }
        }
        for (int count : tileCount.values()) {
            if (count == colors.length) {
                score += 10;
            }
        }
        return score;
    }

    private int getHorizontalCompletionScore() {
        int score = 0;
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                score += 2;
            }
        }
        return score;
    }

    private int getVerticalCompletionScore() {
        int score = 0;
        for (int col = 0; col < colors.length; col++) {
            if (isCompleteCol(col)) {
                score += 7;
            }
        }
        return score;
    }

    public int getCompletionScores() {
        int score = 0;
        score += getColorCompletionScore();
        score += getHorizontalCompletionScore();
        score += getVerticalCompletionScore();
        return score;
    }

    public List<List<Color>> getCopyTable() {
        List<List<Color>> listCopy = new ArrayList<List<Color>>();
        for (Color[] row : wall) {
            listCopy.add(Collections.unmodifiableList(Arrays.asList(row)));
        }
        return Collections.unmodifiableList(listCopy);
    }
}
