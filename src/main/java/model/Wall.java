package model;

public class Wall {
    private Color[][] template;
    private Color[][] wall;

    public Wall() {
    }

    public int addTile(int row, Tile tile) {
        return 0;
    }

    public boolean canAddTile(int row, Color type) {
        return false;
    }

    public boolean hasCompleteRow() {
        return false;
    }

    public int getCompletionScores() {
        return 0;
    }

    public Color[][] getCopyTable() {
        return null;
    }
}
