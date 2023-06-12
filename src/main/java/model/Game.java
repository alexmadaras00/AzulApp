package model;

import dataobjects.*;
import model.factory.Factory;
import utils.ExceptionGameStart;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Game implements Model {
    private List<Player> players;
    private Boolean isPlaying;
    private int round;
    private List<Tile> box;
    private List<Factory> factories;
    private GamePhase gamePhase;
    private List<Player> turnOrder;
    private Bag bag;
    private Middle middle;
    private int maxScore;

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
    }

    public List<Tile> getBox() {
        return box;
    }

    public List<Player> getTurnOrder() {
        return turnOrder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Middle getMiddle() {
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

    public int getRound() {
        return round;
    }

    public Boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public GameState startGame() {
        if (isValidStartGame()) {
            // Collections.shuffle(turnOrder);
            middle.addTiles(List.of(PlayerTile.getInstance()));
            fillBag();
            initFactories();
            isPlaying = true;
            gamePhase = GamePhase.PREPARING_ROUND;
            startRound();
            return createGameState();
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

    public GameState createGameState() {
        GameState gameState = new GameState();
        List<List<TileColor>> factoryTiles = new ArrayList<>();
        factories.forEach(factory -> {
            factoryTiles.add(Collections.unmodifiableList(factory.getAllTiles()));
        });
        gameState.setFactories(factoryTiles);
        gameState.setMiddle(Collections.unmodifiableList(middle.getAllTiles()));
        PlayerData currentPlayerData = new PlayerData();
        currentPlayerData.setName(turnOrder.get(0).getName());
        currentPlayerData.setIdentifier(turnOrder.get(0).getIdentifier());
        gameState.setCurrentPlayer(currentPlayerData);
        List<PlayerBoardState> playerBoardStates = new ArrayList<>();
        players.forEach(player -> {
            PlayerBoardState playerBoardState = player.getBoard().toObject();
            PlayerData playerData = new PlayerData();
            playerData.setName(player.getName());
            playerData.setIdentifier(player.getIdentifier());
            playerBoardState.setPlayer(playerData);
            playerBoardStates.add(playerBoardState);
        });
        gameState.setPlayerBoards(playerBoardStates);
        return gameState;
    }

    private RoundUpdate startRound() {
        gamePhase = GamePhase.PREPARING_ROUND;
        RoundUpdate roundUpdate = new RoundUpdate();
        List<Action> updates = fillFactories();
        roundUpdate.setUpdates(updates);
        gamePhase = GamePhase.FACTORY_OFFER;
        round += 1;

        return roundUpdate;
    }

    private List<Action> fillFactories() {
        List<Action> updates = new ArrayList<>();
        for (int i = 0; i < factories.size(); i++) {
            if (bag.getTiles().size() < 4) {
                bag.addTiles((List<TileColor>) (Object) box);
                box = new ArrayList<>();
            }

            List<TileColor> tiles = bag.popTiles(4);

            for (TileColor tileColor : TileColor.values()) {
                int count = Collections.frequency(tiles, tileColor);
                if (count > 0) {
                    updates.add(new Action(ActionType.ADD, tileColor, count, null, new Location(LocationType.FACTORY, i)));
                }
            }
            factories.get(i).addTiles(tiles);
        }
        return updates;
    }

    private DataObject endRound() {
        RoundUpdate roundUpdate = new RoundUpdate();
        gamePhase = GamePhase.WALL_TILLING;
        List<Action> updates = wallTilling();
        List<ScoreUpdate> scoreUpdates = getScoreUpdates();
        roundUpdate.setScoreUpdates(scoreUpdates);
        if (isEndOfGame()) {
            EndGameUpdate endGameUpdate = endGame();
            roundUpdate.setUpdates(updates);
            endGameUpdate.setRoundUpdate(roundUpdate);
            return endGameUpdate;
        } else {
            updates.addAll(startRound().getUpdates());
            roundUpdate.setUpdates(updates);
            return roundUpdate;
        }
    }

    private List<Action> wallTilling() {
        List<Action> updates = new ArrayList<>();
        this.players.forEach(player -> {
            HashMap<Location, List<Tile>> remainingTiles = player.getBoard().wallTilling();
            Location floorLineLocation = remainingTiles.keySet().stream().toList().stream().filter(location -> location.getType() == LocationType.FLOOR_LINE && location.getIndex() == 0).findFirst().orElseThrow();
            List<Tile> floorTiles = new ArrayList<>(remainingTiles.get(floorLineLocation).stream().toList());
            if (floorTiles.contains(PlayerTile.getInstance())) {
                setStartingPlayer(player);
                floorTiles.remove(PlayerTile.getInstance());
                Action removePlayerTile = new Action(ActionType.REMOVE, null, 1, new Location(LocationType.FLOOR_LINE, 0), null);
                removePlayerTile.setIsPlayerTile(true);
                updates.add(removePlayerTile);
            }
            for (Location key : remainingTiles.keySet()) {
                List<Tile> tiles = remainingTiles.get(key);
                for (TileColor tileColor : TileColor.values()) {
                    int count = Collections.frequency(tiles, tileColor);
                    // move 1 tile from pattern line to wall
                    if (count > 0 && key.getType() == LocationType.PATTERN_LINE) {
                        updates.add(new Action(ActionType.MOVE, tileColor, 1, key, new Location(LocationType.WALL, key.getIndex())));
                    } 
                    // remove left over tiles in pattern line
                    if (count > 1 && key.getType() == LocationType.PATTERN_LINE) {
                        updates.add(new Action(ActionType.REMOVE, tileColor, count-1, key, null));
                    }
                    // remove all tiles from floor line
                    if (count > 0 && key.getType() == LocationType.FLOOR_LINE) {
                        updates.add(new Action(ActionType.REMOVE, tileColor, count, key, null));
                    } 
                }
                if (key.getType() == LocationType.FLOOR_LINE) {
                    box.addAll(tiles);
                } else {
                    box.addAll(tiles.subList(1, tiles.size()));
                }
            }
        });
        return updates;
    }

    private List<ScoreUpdate> getScoreUpdates() {
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        players.forEach(player -> {
            if (isEndOfGame()) {
                player.getBoard().addFinalScores();
            }
            List<ScoreChange> scoreChanges = player.getBoard().popAllScoreChanges();
            ScoreUpdate scoreUpdate = new ScoreUpdate();
            PlayerData playerData = new PlayerData();
            scoreUpdate.setScoreChanges(scoreChanges);
            playerData.setName(player.getName());
            playerData.setIdentifier(player.getIdentifier());
            scoreUpdate.setPlayer(playerData);
            scoreUpdate.setNewScore(player.getBoard().getScore());
            scoreUpdates.add(scoreUpdate);
        });
        return scoreUpdates;
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
    public GameState terminateGame() {
        GameState gameState = new GameState();
        List<List<TileColor>> factoryTiles = new ArrayList<>();
        factories.forEach(factory -> {
            factoryTiles.add(factory.getAllTiles());
        });
        gameState.setFactories(factoryTiles);
        gameState.setMiddle(middle.getAllTiles());

        gamePhase = GamePhase.TERMINATED;
        isPlaying = false;

        return gameState;
    }

    private EndGameUpdate endGame() {
        EndGameUpdate endGameUpdate = new EndGameUpdate();
        List<PlayerData> winners = determineWinners();
        endGameUpdate.setWinners(winners);
        gamePhase = GamePhase.FINISHED;
        isPlaying = false;
        return endGameUpdate;
    }

    private List<PlayerData> determineWinners() {
        List<PlayerData> winners = new ArrayList<>();
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
            PlayerData playerData = new PlayerData();
            playerData.setName(p.getName());
            playerData.setIdentifier(p.getIdentifier());
            if (completedRowCount > maxCompletedRows) {
                maxScore = completedRowCount;
                winners = new ArrayList<>();
                winners.add(playerData);
            } else if (completedRowCount == maxCompletedRows) {
                winners.add(playerData);
            }
        }
        return winners;
    }

    @Override
    public boolean isCurrentPlayer(PlayerData player) {
        return turnOrder.get(0).getIdentifier() == player.getIdentifier();
    }

    @Override
    public PlayerData addPlayer(Player player) {
        players.add(player);
        turnOrder.add(player);
        PlayerData playerData = new PlayerData();
        playerData.setIdentifier(player.getIdentifier());
        playerData.setName(player.getName());
        return playerData;
    }

    @Override
    public boolean isValidStartGame() {
        return players.size() >= 2 && players.size() <= 4 && !isPlaying && round == 0
                && middle.getAllTiles().size() == 0 && turnOrder.size() == players.size()
                && box.size() == 0 && bag.getTiles().size() == 0
                && factories.stream().allMatch(factory -> factory.getAllTiles().size() == 0);
    }

    private MoveUpdate initialMoveUpdate() {
        MoveUpdate moveUpdate = new MoveUpdate();
        PlayerData player = new PlayerData();
        player.setIdentifier(turnOrder.get(0).getIdentifier());
        player.setName(turnOrder.get(0).getName());
        moveUpdate.setPlayer(player);

        // push the player that made the move to the back of the queue
        turnOrder.add(turnOrder.remove(0));

        PlayerData currentPlayer = new PlayerData();
        currentPlayer.setIdentifier(turnOrder.get(0).getIdentifier());
        currentPlayer.setName(turnOrder.get(0).getName());
        moveUpdate.setNextPlayer(currentPlayer);
        return moveUpdate;
    }

    private Map<TileColor, Integer> getDistributionColors(List<Tile> tiles) {
        Map<TileColor, Integer> distribution = new HashMap<>();
        TileColor tileColor;
        for (Tile tile : tiles) {
            tileColor = (TileColor) tile;
            if (distribution.containsKey(tileColor)) {
                distribution.replace(tileColor, distribution.get(tileColor) + 1);
            } else {
                distribution.put(tileColor, 1);
            }
        }
        return distribution;
    }

    private boolean isEndOfRound() {
        for (Factory factory : factories) {
            if (factory.getAllTiles().size() > 0) {
                return false;
            }
        }
        return middle.getAllTiles().size() == 0;
    }

    private DataObject performMove(Function<TileColor, List<Tile>> popTiles, Function<TileColor, List<Tile>> popAllTiles,
                                   Function<List<Tile>, List<Tile>> putTiles, Function<List<Tile>, List<Tile>> dumpFloorLine, TileColor tileColor, Location from, Location to) {

        MoveUpdate moveUpdate = initialMoveUpdate();
        List<Action> steps = new ArrayList<>();

        List<Tile> tiles = popTiles.apply(tileColor); // factory or middle
        int amount = tiles.size();
        List<Tile> overflowTiles = putTiles.apply(tiles);  // either pattern line row or floor line
        int overflowAmount = overflowTiles.size();
        List<Tile> returnedTilesFloorLine = dumpFloorLine.apply(overflowTiles); // floorline
        int returnedAmount = returnedTilesFloorLine.size();

        if (amount - overflowAmount > 0) // tiles that were put into the specified location
            steps.add(new Action(ActionType.MOVE, tileColor, amount - overflowAmount, from, to));

        if (overflowAmount - returnedAmount > 0) // excess tiles that were put into the floor line 
            steps.add(new Action(ActionType.MOVE, tileColor, overflowAmount - returnedAmount, from, new Location(LocationType.FLOOR_LINE, 0)));

        if (returnedAmount > 0) // excess tiles that are put into the box
            steps.add(new Action(ActionType.REMOVE, tileColor, returnedAmount, from, null));

        box.addAll(returnedTilesFloorLine);

        if (from.getType() != LocationType.MIDDLE) {
            List<Tile> otherTiles = popAllTiles.apply(tileColor);
            Map<TileColor, Integer> distributionOtherTiles = getDistributionColors(otherTiles);
            for (TileColor key : distributionOtherTiles.keySet()) {
                steps.add(new Action(ActionType.MOVE, key, distributionOtherTiles.get(key), from, new Location(LocationType.MIDDLE, 0)));
            }
            middle.addTiles(otherTiles);
        } else if (middle.hasPlayerTile()) {
                List<Tile> swappedTile = dumpFloorLine.apply(List.of(middle.popPlayerTile()));
                Action movePlayerTile = new Action(ActionType.MOVE, null, 1, from, new Location(LocationType.FLOOR_LINE, 0));
                movePlayerTile.setIsPlayerTile(true); 
                steps.add(movePlayerTile);
                if (swappedTile.size() == 1) {
                    box.addAll(swappedTile);
                    steps.add(new Action(ActionType.REMOVE, (TileColor) swappedTile.get(0), 1, new Location(LocationType.FLOOR_LINE, 0), null));
                }
        }

        moveUpdate.setSteps(steps);

        if (isEndOfRound()) {
            DataObject roundUpdate = endRound();
            if (isEndOfGame()) {
                EndGameUpdate result = (EndGameUpdate) roundUpdate;
                result.getRoundUpdate().setMove(moveUpdate);
                return result;
            } else {
                RoundUpdate result = (RoundUpdate) roundUpdate;  
                result.setMove(moveUpdate);  
                return result;
            }
        }
        return moveUpdate;
    }

    @Override
    public DataObject performMoveFactoryPatternLine(int factoryIndex, int patternLineRow, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        Location from = new Location(LocationType.FACTORY, factoryIndex);
        Location to = new Location(LocationType.PATTERN_LINE, patternLineRow);
        return performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, from, to);
    }

    @Override
    public DataObject performMoveFactoryFloorLine(int factoryIndex, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) factories.get(factoryIndex).popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        Location from = new Location(LocationType.FACTORY, factoryIndex);
        Location to = new Location(LocationType.FLOOR_LINE, 0);
        return performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, from, to);
    }

    @Override
    public DataObject performMoveMiddlePatternLine(int patternLineRow, TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMovePatternLine(patternLineRow, tiles);
        Function<List<Tile>, List<Tile>> dumpFloorLine = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        Location from = new Location(LocationType.MIDDLE, 0);
        Location to = new Location(LocationType.PATTERN_LINE, patternLineRow);
        return performMove(popTiles, popAllTiles, putTiles, dumpFloorLine, tileColor, from, to);
    }

    @Override
    public DataObject performMoveMiddleFloorLine(TileColor tileColor) {
        Player player = turnOrder.get(0);
        Function<TileColor, List<Tile>> popTiles = (color) -> (List<Tile>) (Object) middle.popTiles(color);
        Function<TileColor, List<Tile>> popAllTiles = (color) -> (List<Tile>) (Object) middle.popAllTiles();
        Function<List<Tile>, List<Tile>> putTiles = (tiles) -> player.getBoard().performMoveFloorLine(tiles);
        Location from = new Location(LocationType.MIDDLE, 0);
        Location to = new Location(LocationType.FLOOR_LINE, 0);
        return performMove(popTiles, popAllTiles, putTiles, putTiles, tileColor, from, to);
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

    private boolean isEndOfGame() {
        for (Player p : players) {
            if (p.getBoard().hasFulfilledEndCondition())
                return true;
        }
        return false;
    }
}

