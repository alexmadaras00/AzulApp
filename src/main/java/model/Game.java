package model;

import model.factory.FactoryCreator;
import model.factory.FactoryInterface;
import model.factory.OurFactoryCreator;
import model.factory.TwinteamFactoryCreator;
import shared.PlayerTile;
import shared.Tile;
import shared.TileColor;

import java.util.*;
import java.util.function.Function;

public class Game implements Model {
    private List<Player> players;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<FactoryInterface> factories;
    private List<Integer> winners;
    private GamePhase gamePhase;
    private List<Player> turnOrder;
    private Bag bag;
    private Middle middle;
    private FactoryCreator factoryCreator;

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
        this.factoryCreator = new OurFactoryCreator();
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

    public List<FactoryInterface> getFactories() {
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
        }
    }

    private void fillBag() {
        List<TileColor> colorTiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            colorTiles.addAll(List.of((TileColor[]) (Object) TileColor.values()));
        }
        bag.addTiles(colorTiles);
    }

    private void initFactories() {
        for (int i = 0; i < players.size()*2+1; i++) {
            factories.add(factoryCreator.createFactory());
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
        for (FactoryInterface factory : factories) {
            if (bag.getTiles().size() < 4) {
                bag.addTiles((List<TileColor>) (Object) box);
                box = new ArrayList<>();
            }
            List<TileColor> tiles = bag.popTiles(4);
            factory.addTiles(tiles);
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
    public void addPlayer(String name) {
        Player player = new Player(name);
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
        for (FactoryInterface factory : factories) {
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

    private void handleMove() {
        turnOrder.add(turnOrder.remove(0));

        if (isEndOfRound()) {
            endRound();
        }
    }

    private void handleMiddlePlayerTile() {
        Tile playerTile = middle.popPlayerTile();
        if (playerTile != null) {
            List<Tile> remainder = turnOrder.get(0).getBoard().performMoveFloorLine(List.of(playerTile));
            if (remainder != null) {   
                box.addAll(remainder);
            }
        }
    }

    @Override
    public void performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        List<TileColor> tiles = factories.get(factoryIndex).popTiles(tileColor);
        List<Tile> overflowTiles = turnOrder.get(0).getBoard().performMovePatternLine(patternLineRow, new ArrayList<Tile>(tiles));
        box.addAll(turnOrder.get(0).getBoard().performMoveFloorLine(overflowTiles));
        middle.addTiles((new ArrayList<Tile>(factories.get(factoryIndex).popAllTiles())));
        handleMove();
    }

    @Override
    public void performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        List<TileColor> tiles = factories.get(factoryIndex).popTiles(tileColor);
        box.addAll(turnOrder.get(0).getBoard().performMoveFloorLine(new ArrayList<Tile>(tiles)));
        middle.addTiles((new ArrayList<Tile>(factories.get(factoryIndex).popAllTiles())));
        handleMove();
    }

    @Override
    public void performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        List<Tile> tiles = middle.popTiles(tileColor);
        List<Tile> overflowTiles = turnOrder.get(0).getBoard().performMovePatternLine(patternLineRow, tiles);
        box.addAll(turnOrder.get(0).getBoard().performMoveFloorLine(overflowTiles));
        handleMiddlePlayerTile();
        handleMove();
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        List<Tile> tiles = middle.popTiles(tileColor);
        box.addAll(turnOrder.get(0).getBoard().performMoveFloorLine(tiles));
        handleMiddlePlayerTile();
        handleMove();
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

    @Override
    public void useTwinteamFactory() {
        factoryCreator = new TwinteamFactoryCreator();
    }
}

