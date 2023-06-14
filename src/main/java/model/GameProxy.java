package model;
import java.util.List;

public class GameProxy implements ModelProxy {

    private Model game;


    @Override
    public int getFactoryCount() {
        return game.getFactoryCount();
    }

    @Override
    public Tile[] getFactory(int index) {
        return game.getFactory(index);
    }

    @Override
    public List<Tile> getMiddle() {
        return game.getMiddle();
    }

    @Override
    public List<Integer> getPlayerList() {
        return game.getPlayerList();
    }

    @Override
    public String getName(int identifier) {
        return game.getName(identifier);
    }

    @Override
    public Tile getWall(int identifier, int row, int col) {
        return game.getWall(identifier, row, col);
    }

    @Override
    public Tile[] getPatternLine(int identifier, int row) {
        return game.getPatternLine(identifier, row);
    }

    @Override
    public Tile[] getFloorLine(int identifier) {
        return game.getFloorLine(identifier);
    }

    //TODO test
    @Override
    public int getScore(int identifier) {
        return game.getScore(identifier);
    }

    @Override
    public List<Integer> getWinners() {
        return game.getWinners();
    }

    @Override
    public int getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    @Override
    public int getRound() {
        return game.getRound();
    }

    @Override
    public boolean isPlaying() {
        return game.isPlaying();
    }

    public void setProxy(Model game) {
        this.game = game;
    }
}
