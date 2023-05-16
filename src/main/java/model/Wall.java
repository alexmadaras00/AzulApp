package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dataobjects.ScoreChange;

public class Wall {
    private Color[][] wall;
    private Color[] colors;
    private int completedColorScore = 10;
    private int completedRowScore = 2;
    private int completedColumnScore = 7;

    public Wall() {
        colors = Color.values();
        int size = colors.length;
        wall = new Color[size][size];
    }

    public Color getTemplateColor(int row, int col) {
        return colors[(col - row + colors.length) % colors.length];
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

    public boolean hasCompleteRow() {
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                return true;
            }
        }
        return false;
    }

    public List<ScoreChange> getCompletionScores() {
        List<ScoreChange> scoreList = new LinkedList<ScoreChange>();

        List<Color> completedColors = getColorCompletions();
        scoreList.addAll(generateColorScoreChange(completedColors));
        List<Integer> completedRows = getHorizontalCompletions();
        scoreList.addAll(generateLineScoreChange(completedRows, true));
        List<Integer> completedColums = getVerticalCompletions();
        scoreList.addAll(generateLineScoreChange(completedColums, false));
        return scoreList;
    }

    public List<List<Color>> getCopyTable() {
        List<List<Color>> listCopy = new ArrayList<List<Color>>();
        for (Color[] row : wall) {
            listCopy.add(Collections.unmodifiableList(Arrays.asList(row)));
        }
        return Collections.unmodifiableList(listCopy);
    }

    private List<ScoreChange> generateColorScoreChange(List<Color> completedColor) {
        List<ScoreChange> scoreChanges = new LinkedList<>();
        for (Color color : completedColor) {
            ScoreChange scoreChange = new ScoreChange();
            scoreChange.setIsCompletionScore(true);
            scoreChange.setHasColor(true);
            scoreChange.setHasRowIndex(false);
            scoreChange.setColor(color);
            scoreChange.setScoreDifference(completedColorScore);
            scoreChanges.add(scoreChange);
        }
        return scoreChanges;
    }

    private List<ScoreChange> generateLineScoreChange(List<Integer> completedLines, boolean isRow) {
        List<ScoreChange> scoreChanges = new LinkedList<>();
        for (int line : completedLines) {
            ScoreChange scoreChange = new ScoreChange();
            scoreChange.setIsCompletionScore(true);
            scoreChange.setHasColor(false);
            scoreChange.setHasRowIndex(isRow);
            scoreChange.setIndex(line);
            scoreChange.setScoreDifference(isRow ? completedRowScore : completedColumnScore);
            scoreChanges.add(scoreChange);
        }
        return scoreChanges;
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

    private List<Color> getColorCompletions() {
        List<Color> completedColors = new LinkedList<Color>();
        Map<Color, Integer> tileCount = new HashMap<Color, Integer>();
        for (Color[] row : wall) {
            for (Color tile : row) {
                if (tile != null) {
                    tileCount.put(tile, tileCount.get(tile) != null ? tileCount.get(tile) + 1 : 1);
                }
            }
        }
        for (Map.Entry<Color, Integer> entry : tileCount.entrySet()) {
            if (entry.getValue() == colors.length) {
                completedColors.add(entry.getKey());
            }
        }
        return completedColors;
    }

    private List<Integer> getHorizontalCompletions() {
        List<Integer> completedRows = new LinkedList<Integer>();
        for (int row = 0; row < colors.length; row++) {
            if (isCompleteRow(row)) {
                completedRows.add(row);
            }
        }
        return completedRows;
    }

    private List<Integer> getVerticalCompletions() {
        List<Integer> completedColumns = new LinkedList<Integer>();
        for (int col = 0; col < colors.length; col++) {
            if (isCompleteCol(col)) {
                completedColumns.add(col);
            }
        }
        return completedColumns;
    }

}
