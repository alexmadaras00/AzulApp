package model;

import model.factory.Factory;
import utils.ExceptionGameStart;

import java.util.*;
import java.util.function.Function;

public class Game implements Model {
    private List<Player> players;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<Factory> factories;
    private List<Integer> winners;
    private GamePhase gamePhase;
    private List<Player> turnOrder;
    private Bag bag;
    private Middle middle;

    public Game() {
        this.players = new ArrayList<>();
        this.factories = new ArrayList<>();
        this.gamePhase = GamePhase.INITIALIZED;
        this.middle = new Middle();
        this.bag = new Bag();
        this.isPlaying = false;
        this.box = new ArrayList<>();
        this.round = 0;
        this.turnOrder = new ArrayList<>();
        this.winners = new ArrayList<>();
    }

    // --- Getters for testing ---
    public List<Tile> getBox() {
        return box;
    }

    public List<Player> getTurnOrder() {
        return turnOrder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Middle getCenter() {
        return middle;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public Bag getBag() {
        return bag;
    }
    // ------------------------

    @Override
    public List<Tile> getMiddle() {
        return Collections.unmodifiableList(middle.getAllTiles());
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public Tile[] getFactory(int index) {
        Tile[] result = new Tile[4];
        List<TileColor> factoryTiles = factories.get(index).getAllTiles();
        for (int i = 0; i < Math.min(4,factoryTiles.size()); i++) {
            result[i] = factoryTiles.get(i);
        }
        return result;
    }

    @Override
    public int getFactoryCount() {
        return factories.size();
    }

    @Override
    public Tile[] getFloorLine(int identifier) {
        Tile[] result = new Tile[7];
        List<Tile> floorLineTiles = getPlayerByIdentifier(identifier).getBoard().getFloorLineTiles();
        for (int i = 0; i < Math.min(7,floorLineTiles.size()); i++) {
            result[i] = floorLineTiles.get(i);
        }
        return result;
    }

    private Player getPlayerByIdentifier(int identifier) {
        for (Player player: players) {
            if (player.getIdentifier() == identifier) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String getName(int identifier) {
        return getPlayerByIdentifier(identifier).getName();
    }

    @Override
    public Tile[] getPatternLine(int identifier, int row) {
        Tile[] result = new Tile[row+1];
        List<Tile> patternLineTiles = getPlayerByIdentifier(identifier).getBoard().getPatternLineTiles().get(row);
        for (int i = 0; i < Math.min(row+1,patternLineTiles.size()); i++) {
            result[row-i] = patternLineTiles.get(i);
        }
        return result;
    }

    @Override
    public List<Player> getPlayerList() {
        return new ArrayList<>(players);
    }

    @Override
    public int getScore(int identifier) {
        return getPlayerByIdentifier(identifier).getBoard().getScore();
    }

    @Override
    public Tile getWall(int identifier, int row, int col) {
        return getPlayerByIdentifier(identifier).getBoard().getWallTiles().get(row).get(col);
    }

    @Override
    public List<Integer> getWinners() {
        return Collections.unmodifiableList(winners);
    }

    @Override
    public void startGame() {
        if (canStartGame()) {
            fillBag();
            initFactories();
            isPlaying = true;
            gamePhase = GamePhase.PREPARING_ROUND;
            startRound();
        } else
            throw new ExceptionGameStart("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.");
    }

    private void fillBag() {
        for (int i = 0; i < 20; i++) {
            List<TileColor> colorTiles = new ArrayList<>();
            colorTiles.add(TileColor.RED);
            colorTiles.add(TileColor.BLACK);
            colorTiles.add(TileColor.BLUE);
            colorTiles.add(TileColor.CYAN);
            colorTiles.add(TileColor.YELLOW);
            bag.addTiles(colorTiles);
        }
    }

    private void initFactories() {
        if (players.size() == 2) {
            for (int i = 0; i < 5; i++)
                factories.add(new Factory());
        } else if (players.size() == 3) {
            for (int i = 0; i < 7; i++)
                factories.add(new Factory());
        } else {
            for (int i = 0; i < 9; i++)
                factories.add(new Factory());
        }
    }

    private void startRound() {
        gamePhase = GamePhase.PREPARING_ROUND;
        fillFactories();
        middle.addTiles(List.of(PlayerTile.getInstance()));
        gamePhase = GamePhase.FACTORY_OFFER;
        round += 1;
    }

    private void fillFactories() {
        for (int i = 0; i < factories.size(); i++) {
            if (bag.getTiles().size() < 4) {
                bag.addTiles((List<TileColor>) (Object) box);
                box = new ArrayList<>();
            }
            List<TileColor> tiles = bag.popTiles(4);
            factories.get(i).addTiles(tiles);
        }
    }

    private void endRound() {
        gamePhase = GamePhase.WALL_TILLING;
        wallTilling();
        players.forEach(player -> {
            if (isEndOfGame()) {
                player.getBoard().addFinalScores();
            }
        });
        if (isEndOfGame()) {
            endGame();
        } else {
            startRound();
        }
    }

    private void wallTilling() {
        this.players.forEach(player -> {
            List<Tile> remainingTiles = player.getBoard().wallTilling();
            if (remainingTiles.contains(PlayerTile.getInstance())) {
                setStartingPlayer(player);
                remainingTiles.remove(PlayerTile.getInstance());
            }
            box.addAll(remainingTiles);
        });
    }

    private void setStartingPlayer(Player player) {
        for (int i = 0; i < turnOrder.size(); i++) {
            if (turnOrder.get(0).equals(player)) {
                return;
            } else {
                turnOrder.add(turnOrder.remove(0));
            }
        }
    }

    @Override
    public void terminateGame() {
        gamePhase = GamePhase.TERMINATED;
        isPlaying = false;
    }

    private void endGame() {
        winners = determineWinners();
        gamePhase = GamePhase.FINISHED;
        isPlaying = false;
    }

    private List<Integer> determineWinners() {
        List<Integer> winners = new ArrayList<>();
        List<Player> possibleWinners = new ArrayList<>();
        int maxScore = 0;
        for (Player p : players) {
            int playerScore = p.getBoard().getScore();
            if (playerScore > maxScore) {
                maxScore = playerScore;
                possibleWinners = new ArrayList<>();
                possibleWinners.add(p);
            } else if (playerScore == maxScore) {
                possibleWinners.add(p);
            }
        }
        int maxCompletedRows = 0;
        for (Player p : possibleWinners) {
            int completedRowCount = p.getBoard().getCompletedRowCount();
            if (completedRowCount > maxCompletedRows) {
                maxScore = completedRowCount;
                winners = new ArrayList<>();
                winners.add(p.getIdentifier());
            } else if (completedRowCount == maxCompletedRows) {
                winners.add(p.getIdentifier());
            }
        }
        return winners;
    }

    @Override
    public int getCurrentPlayer() {
        return turnOrder.get(0).getIdentifier();
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        turnOrder.add(player);
    }

    @Override
    public boolean canStartGame() {
        return players.size() >= 2 && players.size() <= 4 && !isPlaying && round == 0
                && middle.getAllTiles().size() == 0 && turnOrder.size() == players.size()
                && box.size() == 0 && bag.getTiles().size() == 0
                && factories.stream().allMatch(factory -> factory.getAllTiles().size() == 0);
    }

    private boolean isEndOfRound() {
        for (Factory factory : factories) {
            if (factory.getAllTiles().size() > 0) {
                return false;
            }
        }
        return middle.getAllTiles().size() == 0;
    }

    private boolean isEndOfGame() {
        for (Player p : players) {
            if (p.getBoard().hasFulfilledEndCondition())
                return true;
        }
        return false;
    }

    private void performMove(Function<TileColor, List<Tile>> popTiles, Function<TileColor, List<Tile>> popAllTiles,
                                   Function<List<Tile>, List<Tile>> putTiles, Function<List<Tile>, List<Tile>> dumpFloorLine, TileColor tileColor, boolean isMiddle) {
        List<Tile> tiles = popTiles.apply(tileColor); // factory or middle
        List<Tile> overflowTiles = putTiles.apply(tiles);  // either pattern line row or floor line
        List<Tile> returnedTilesFloorLine = dumpFloorLine.apply(overflowTiles); // floorline

        box.addAll(returnedTilesFloorLine);

        if (!isMiddle) {
            List<Tile> otherTiles = popAllTiles.apply(tileColor);            
            middle.addTiles(otherTiles);
        } else if (middle.hasPlayerTile()) {
            box.addAll(dumpFloorLine.apply(List.of(middle.popPlayerTile())));
        }

        turnOrder.add(turnOrder.remove(0));

        if (isEndOfRound()) {
            endRound();
        }
    }

    @Override
    public void performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, false);
    }

    @Override
    public void performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, false);
    }

    @Override
    public void performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, true);
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, true);
    }

    @Override
    public boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        return factories.get(factoryIndex).hasTiles(tileColor) &&
                turnOrder.get(0).getBoard().canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        return factories.get(factoryIndex).hasTiles(tileColor);
    }

    @Override
    public boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        return middle.hasTiles(tileColor) &&
                turnOrder.get(0).getBoard().canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveMiddleFloorLine(TileColor tileColor) {
        return middle.hasTiles(tileColor);
    }
}

