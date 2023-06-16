package model;

import model.factory.FactoryCreator;
import model.factory.FactoryInterface;
import model.factory.OurFactoryCreator;
import model.factory.TwinteamFactoryCreator;

import java.util.*;
import java.util.function.Function;

public class Game implements Model {
    private List<PlayerBoard> playerBoards;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<FactoryInterface> factories;
    private List<Integer> winners;
    private GamePhase gamePhase;
    private List<PlayerBoard> turnOrder;
    private Bag bag;
    private Middle middle;
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

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (PlayerBoard p : playerBoards){
            players.add(p.getPlayer());
        }
        return players;
    }

    public List<PlayerBoard> getPlayerBoards(){
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
        List<Tile> floorLineTiles = getPlayerBoardByIdentifier(identifier).getFloorLineTiles();
        for (int i = 0; i < Math.min(7,floorLineTiles.size()); i++) {
            result[i] = floorLineTiles.get(i);
        }
        return result;
    }


    @Override
    public String getName(int identifier) {
        return getPlayerBoardByIdentifier(identifier).getPlayerName();
    }

    @Override
    public Tile[] getPatternLine(int identifier, int row) {
        Tile[] result = new Tile[row+1];
        List<Tile> patternLineTiles = getPlayerBoardByIdentifier(identifier).getPatternLineTiles().get(row);
        for (int i = 0; i < Math.min(row+1,patternLineTiles.size()); i++) {
            result[row-i] = patternLineTiles.get(i);
        }
        return result;
    }

    @Override
    public List<Player> getPlayerList() {
        return getPlayers();
    }

    @Override
    public List<PlayerBoard> getPlayerBoardList(){
        return playerBoards;
    }

    @Override
    public int getScore(int identifier) {
        return getPlayerBoardByIdentifier(identifier).getScore();
    }

    @Override
    public Tile getWall(int identifier, int row, int col) {
        return getPlayerBoardByIdentifier(identifier).getWallTiles().get(row).get(col);
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
        for (int i = 0; i < playerBoards.size()*2+1; i++) {
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
        playerBoards.forEach(playerBoard -> {
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
        this.playerBoards.forEach(playerBoard -> {
            List<Tile> remainingTiles = playerBoard.wallTilling();
            if (remainingTiles.contains(PlayerTile.getInstance())) {
                setStartingPlayer(playerBoard.getPlayer());
                remainingTiles.remove(PlayerTile.getInstance());
            }
            box.addAll(remainingTiles);
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
        List<Integer> winners = new ArrayList<>();
        List<PlayerBoard> possibleWinners = new ArrayList<>();
        int maxScore = 0;
        for (PlayerBoard p : playerBoards) {
            int playerScore = p.getScore();
            if (playerScore > maxScore) {
                maxScore = playerScore;
                possibleWinners = new ArrayList<>();
                possibleWinners.add(p);
            } else if (playerScore == maxScore) {
                possibleWinners.add(p);
            }
        }
        int maxCompletedRows = 0;
        for (PlayerBoard p : possibleWinners) {
            int completedRowCount = p.getCompletedRowCount();
            if (completedRowCount > maxCompletedRows) {
                maxScore = completedRowCount;
                winners = new ArrayList<>();
                winners.add(p.getPlayerIdentifier());
            } else if (completedRowCount == maxCompletedRows) {
                winners.add(p.getPlayerIdentifier());
            }
        }
        return winners;
    }

    @Override
    public int getCurrentPlayer() {
        return turnOrder.get(0).getPlayerIdentifier();
    }

    @Override
    public void addPlayer(String name) {
        Player player = new Player(name);
        PlayerBoard playerBoard = new PlayerBoard(player);
        playerBoards.add(playerBoard);
        turnOrder.add(playerBoard);
    }

    @Override
    public boolean canStartGame() {
        return playerBoards.size() >= 2 && playerBoards.size() <= 4 && !isPlaying && round == 0
                && middle.getAllTiles().size() == 0 && turnOrder.size() == playerBoards.size()
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
        for (PlayerBoard p : playerBoards) {
            if (p.hasFulfilledEndCondition())
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
        PlayerBoard player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> player.performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, false);
    }

    @Override
    public void performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        PlayerBoard playerBoard = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> playerBoard.performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, false);
    }

    @Override
    public void performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        PlayerBoard playerBoard = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> playerBoard.performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> playerBoard.performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, true);
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        PlayerBoard playerBoard = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> playerBoard.performMoveFloorLine(tiles);
        performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, true);
    }

    @Override
    public boolean isValidMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        Boolean canAddTypePatternLine = turnOrder.get(0).canAddTypePatternLine(patternLineRow, tileColor);
        return factories.get(factoryIndex).hasTiles(tileColor) &&
                turnOrder.get(0).canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        return factories.get(factoryIndex).hasTiles(tileColor);
    }

    @Override
    public boolean isValidMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        return middle.hasTiles(tileColor) &&
                turnOrder.get(0).canAddTypePatternLine(patternLineRow, tileColor);
    }

    @Override
    public boolean isValidMoveMiddleFloorLine(TileColor tileColor) {
        return middle.hasTiles(tileColor);
    }

    @Override
    public void useTwinteamFactory() {
        factoryCreator = new TwinteamFactoryCreator();
    }

    private PlayerBoard getPlayerBoardByIdentifier(int identifier){
        for (PlayerBoard playerBoard:playerBoards){
            if (playerBoard.getPlayerIdentifier()==identifier){
                return playerBoard;
            }
        }
        return null;
    }
}

