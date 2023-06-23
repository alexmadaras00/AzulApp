package model;

import model.factory.FactoryCreator;
import model.factory.FactoryInterface;
import model.factory.OurFactoryCreator;
import model.factory.TwinteamFactoryCreator;
import shared.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game implements Model {
    private final List<PlayerBoard> playerBoards;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private final List<FactoryInterface> factories;
    private List<Integer> winners;
    private GamePhase gamePhase;
    private List<PlayerBoard> turnOrder;
    private final Bag bag;
    private final Middle middle;
    private FactoryCreator factoryCreator;

    public Game() {
        this.playerBoards = new ArrayList<>();
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

    public List<PlayerBoard> getTurnOrder() {
        return turnOrder;
    }
    @Override
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (PlayerBoard p : getPlayerBoards()) {
            players.add(p.getPlayer());
        }
        return players;
    }

    public List<PlayerBoard> getPlayerBoards() {
        return playerBoards;
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
        for (int i = 0; i < Math.min(4, factoryTiles.size()); i++) {
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
        List<Tile> floorLineTiles = Objects.requireNonNull(getPlayerBoardByIdentifier(identifier)).getFloorLineTiles();
        for (int i = 0; i < Math.min(7, floorLineTiles.size()); i++) {
            result[i] = floorLineTiles.get(i);
        }
        return result;
    }


    @Override
    public String getName(int identifier) {
        return Objects.requireNonNull(getPlayerBoardByIdentifier(identifier)).getPlayerName();
    }

    @Override
    public Tile[] getPatternLine(int identifier, int row) {
        Tile[] result = new Tile[row + 1];
        List<Tile> patternLineTiles = Objects.requireNonNull(getPlayerBoardByIdentifier(identifier)).getPatternLineTiles().get(row);
        for (int i = 0; i < Math.min(row + 1, patternLineTiles.size()); i++) {
            result[row - i] = patternLineTiles.get(i);
        }
        return result;
    }

    @Override
    public int getScore(int identifier) {
        return Objects.requireNonNull(getPlayerBoardByIdentifier(identifier)).getScore();
    }

    @Override
    public List<List<Tile>> getWall(int identifier) {
        return Objects.requireNonNull(getPlayerBoardByIdentifier(identifier)).getWallTiles();
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
            colorTiles.addAll(List.of(TileColor.values()));
        }
        getBag().addTiles(colorTiles);
    }

    private void initFactories() {
        for (int i = 0; i < getPlayerBoards().size() * 2 + 1; i++) {
            getFactories().add(factoryCreator.createFactory());
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
        for (FactoryInterface factory : getFactories()) {
            if (getBag().getTiles().size() < 4) {
                getBag().addTiles((List<TileColor>) (Object) getBox());
                box = new ArrayList<>();
            }
            List<TileColor> tiles = getBag().popTiles(4);
            factory.addTiles(tiles);
        }
    }
    private void endRound() {
        gamePhase = GamePhase.WALL_TILLING;
        wallTilling();
        getPlayerBoards().forEach(playerBoard -> {
            if (isEndOfGame()) {
                playerBoard.addFinalScores();
            }
        });
        if (isEndOfGame()) {
            endGame();
        } else {
            startRound();
        }
    }

    private void wallTilling() {
        this.getPlayerBoards().forEach(playerBoard -> {
            List<Tile> remainingTiles = playerBoard.wallTilling();
            if (remainingTiles.contains(PlayerTile.getInstance())) {
                setStartingPlayer(playerBoard.getPlayer());
                remainingTiles.remove(PlayerTile.getInstance());
            }
            getBox().addAll(remainingTiles);
        });
    }


    private void setStartingPlayer(Player player) {
        for (int i = 0; i < turnOrder.size(); i++) {
            if (turnOrder.get(0).getPlayer().equals(player)) {
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
        List<PlayerBoard> possibleWinners = determinePossibleWinners();
        List<Integer> winners = determineFinalWinners(possibleWinners);
        return winners;
    }

    private List<Integer> determineFinalWinners(List<PlayerBoard> possibleWinners) {
        int maxCompletedRows = 0;
        List<Integer> winners = new ArrayList<>();
        for (PlayerBoard p : possibleWinners) {
            int completedRowCount = p.getCompletedRowCount();
            if (completedRowCount > maxCompletedRows) {
                winners = new ArrayList<>();
                winners.add(p.getPlayerIdentifier());
            } else if (completedRowCount == maxCompletedRows) {
                winners.add(p.getPlayerIdentifier());
            }
        }
        return winners;
    }

    private List<PlayerBoard> determinePossibleWinners() {
        int maxScore = 0;
        List<PlayerBoard> possibleWinners = new ArrayList<>();
        for (PlayerBoard p : getPlayerBoards()) {
            int playerScore = p.getScore();
            if (playerScore > maxScore) {
                maxScore = playerScore;
                possibleWinners = new ArrayList<>();
                possibleWinners.add(p);
            } else if (playerScore == maxScore) {
                possibleWinners.add(p);
            }
        }
        return possibleWinners;
    }

    @Override
    public int getCurrentPlayer() {
        return turnOrder.get(0).getPlayerIdentifier();
    }

    @Override
    public void addPlayer(String name) {
        Player player = new Player(name);
        PlayerBoard playerBoard = new PlayerBoard(player);
        getPlayerBoards().add(playerBoard);
        turnOrder.add(playerBoard);
    }

    @Override
    public boolean canStartGame() {
        return getPlayerBoards().size() >= 2 && getPlayerBoards().size() <= 4 && !isPlaying && round == 0
                && getMiddle().size() == 0 && getTurnOrder().size() == getPlayerBoards().size()
                && getBox().size() == 0 && getBag().getTiles().size() == 0
                && getFactories().stream().allMatch(factory -> factory.getAllTiles().size() == 0);
    }

    private boolean isEndOfRound() {
        for (FactoryInterface factory : getFactories()) {
            if (factory.getAllTiles().size() > 0) {
                return false;
            }
        }
        return getMiddle().size() == 0;
    }

    private boolean isEndOfGame() {
        for (PlayerBoard p : getPlayerBoards()) {
            if (p.hasFulfilledEndCondition())
                return true;
        }
        return false;
    }

    private void handleMove() {
       getTurnOrder().add(getTurnOrder().remove(0));

        if (isEndOfRound()) {
            endRound();
        }
    }

    private void handleMiddlePlayerTile() {
        Tile playerTile = middle.popPlayerTile();
        if (playerTile != null) {
            List<Tile> remainder = getTurnOrder().get(0).performMoveFloorLine(List.of(playerTile));
            if (remainder != null) {
                getBox().addAll(remainder);
            }
        }
    }

    @Override
    public void performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        List<TileColor> tiles = getFactories().get(factoryIndex).popTiles(tileColor);
        List<Tile> overflowTiles = getTurnOrder().get(0).performMovePatternLine(patternLineRow, new ArrayList<>(tiles));
        getBox().addAll(getTurnOrder().get(0).performMoveFloorLine(overflowTiles));
        middle.addTiles((new ArrayList<>(getFactories().get(factoryIndex).popAllTiles())));
        handleMove();
    }

    @Override
    public void performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        List<TileColor> tiles = getFactories().get(factoryIndex).popTiles(tileColor);
        getBox().addAll(getTurnOrder().get(0).performMoveFloorLine(new ArrayList<>(tiles)));
        middle.addTiles((new ArrayList<>(getFactories().get(factoryIndex).popAllTiles())));
        handleMove();
    }

    @Override
    public void performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        List<Tile> tiles = middle.popTiles(tileColor);
        List<Tile> overflowTiles = getTurnOrder().get(0).performMovePatternLine(patternLineRow, tiles);
        getBox().addAll(getTurnOrder().get(0).performMoveFloorLine(overflowTiles));
        handleMiddlePlayerTile();
        handleMove();
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        List<Tile> tiles = middle.popTiles(tileColor);
        getBox().addAll(getTurnOrder().get(0).performMoveFloorLine(tiles));
        handleMiddlePlayerTile();
        handleMove();
    }

    @Override
    public boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        return getFactories().get(factoryIndex).hasTiles(tileColor) &&
                getTurnOrder().get(0).canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        return getFactories().get(factoryIndex).hasTiles(tileColor);
    }

    @Override
    public boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        return middle.hasTiles(tileColor) &&
                getTurnOrder().get(0).canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveMiddleFloorLine(TileColor tileColor) {
        return middle.hasTiles(tileColor);
    }

    @Override
    public void useTwinteamFactory() {
        factoryCreator = new TwinteamFactoryCreator();
    }

    private PlayerBoard getPlayerBoardByIdentifier(int identifier) {
        for (PlayerBoard playerBoard : getPlayerBoards()) {
            if (playerBoard.getPlayerIdentifier() == identifier) {
                return playerBoard;
            }
        }
        return null;
    }
}

