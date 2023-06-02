package model;

import dataobjects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public Game(List<Player> players) {
        this.players = players;
        this.factories = new ArrayList<>();
        this.gamePhase = GamePhase.INITIALIZED;
        this.middle = new Middle();
        this.bag = new Bag();
        this.isPlaying = false;
        this.box = new ArrayList<>();
        this.round = 0;
        this.turnOrder = new ArrayList<>(players);
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

        GameState gameState = new GameState();
        Collections.shuffle(turnOrder);
        fillBag();
        initFactories();
        initGameState(gameState);
        isPlaying = true;
        return gameState;
    }

    private void fillBag() {
        for (int i = 0; i < 20; i++) {
            List<Tile> colorTiles = new ArrayList<>();
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


    private void initGameState(GameState gameState) {
        List<List<Tile>> factoryTiles = new ArrayList<>();
        List<PlayerBoardState> playerBoardStates = new ArrayList<>();
        factories.forEach(factory -> {
            factoryTiles.add(factory.getAllTiles());
        });
        PlayerData player1Data = new PlayerData();
        player1Data.setName(players.get(0).getName());
        player1Data.setIdentifier(players.get(0).getIdentifier());
        initPlayerBoardStates(playerBoardStates);
        gameState.setFactories(factoryTiles);
        gameState.setMiddle(middle.getAllTiles());
        gameState.setPlayerBoards(playerBoardStates);
        gameState.setCurrentPlayer(player1Data);
    }

    private void initPlayerBoardStates(List<PlayerBoardState> playerBoardStates) {
        players.forEach(player -> {
            PlayerBoardState playerBoardState = new PlayerBoardState();
            PlayerData playerData = new PlayerData();
            playerData.setName(player.getName());
            playerData.setIdentifier(player.getIdentifier());
            playerBoardState.setPlayer(playerData);
            playerBoardState.setScore(player.getBoard().getScore());
            playerBoardState.setPatternLine(player.getBoard().getPatternLine().getCopyTable());
            playerBoardState.setWall(player.getBoard().getWall().getCopyTable());
            playerBoardState.setFloorLine(player.getBoard().getFloorLine().getCopyTiles());
            playerBoardStates.add(playerBoardState);
        });
    }

    @Override
    public RoundUpdate startRound() {

        RoundUpdate roundUpdate = new RoundUpdate();
        MoveUpdate moveUpdate = new MoveUpdate();
        PlayerData playerData = new PlayerData();

        playerData.setIdentifier(turnOrder.get(0).getIdentifier());
        playerData.setName(turnOrder.get(0).getName());
        PlayerData nextPlayerData = new PlayerData();
        nextPlayerData.setIdentifier(turnOrder.get(1).getIdentifier());
        nextPlayerData.setName(turnOrder.get(1).getName());

        moveUpdate.setPlayer(playerData);
        moveUpdate.setNextPlayer(nextPlayerData);

        roundUpdate.setMove(moveUpdate);

        gamePhase = GamePhase.FACTORY_OFFER;
        round += 1;

        return roundUpdate;
    }

    private void fillFactories(List<Action> gameUpdates) {
        for (Factory factory : factories) {
            for (int i = 0; i < 4; i++) {
                if (factory.getAllTiles().size() >= 4) {
                    break;
                }
                List<Tile> bagTiles = bag.popTiles(1);
                factory.addTiles(bagTiles);
            }
            Action actionFillFactories = new Action();
            Location factoryLocation = new Location();
            factoryLocation.setType(LocationType.FACTORY);
            actionFillFactories.setTo(factoryLocation);
            actionFillFactories.setAmount(4);
            gameUpdates.add(actionFillFactories);
        }

    }

    @Override
    public RoundUpdate endRound() {
        RoundUpdate roundUpdate = new RoundUpdate();
        Tile startingPlayerTile = PlayerTile.getInstance();
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        List<Action> updates = new ArrayList<>();
        List<Action> steps = new ArrayList<>();
        MoveUpdate moveUpdate = new MoveUpdate();
        PlayerData currentPlayerData = new PlayerData();
        PlayerData nextPlayerData = new PlayerData();
        if (round > 1) {
            updateScore(scoreUpdates);
        }

        currentPlayerData.setName(turnOrder.get(turnOrder.size() - 1).getName());
        currentPlayerData.setIdentifier(turnOrder.get(turnOrder.size() - 1).getIdentifier());
        moveUpdate.setPlayer(currentPlayerData);
        assignNextStartingPlayer(nextPlayerData);
        moveUpdate.setNextPlayer(nextPlayerData);

        wallTilting(updates);
        moveUpdate.setSteps(steps);

        gamePhase = GamePhase.PREPARING_ROUND;
        middle.getAllTiles().clear();
        box.clear();
        middle.addTiles(List.of(startingPlayerTile));

        fillFactories(updates);
        roundUpdate.setScoreUpdates(scoreUpdates);
        roundUpdate.setMove(moveUpdate);
        roundUpdate.setUpdates(updates);

        for (Player p : players) {
            if (p.getBoard().getWall().hasCompleteRow()) {
                endGame();
                break;
            }
        }
        return roundUpdate;
    }

    private void wallTilting(List<Action> updates) {
        this.players.forEach(player -> {
            List<Tile> remainingTiles = player.getBoard().wallTilling();
            box.addAll(remainingTiles);
            Action actionFloorLine = new Action();
            Location floorLineLocation = new Location();
            floorLineLocation.setIndex(0);
            floorLineLocation.setType(LocationType.FLOOR_LINE);
            actionFloorLine.setType(ActionType.REMOVE);
            actionFloorLine.setFrom(floorLineLocation);
            actionFloorLine.setAmount(remainingTiles.size());
            updates.add(actionFloorLine);
        });
    }
    private void updateScore(List<ScoreUpdate> scoreUpdates) {
        players.forEach(player -> {
            ScoreUpdate scoreUpdate = new ScoreUpdate();
            PlayerData playerData = new PlayerData();
            scoreUpdate.setScoreChanges(player.getBoard().getScoreChanges());
            playerData.setName(player.getName());
            playerData.setIdentifier(player.getIdentifier());
            scoreUpdate.setPlayer(playerData);
            scoreUpdate.setNewScore(player.getBoard().getScore());
            scoreUpdates.add(scoreUpdate);
        });
    }
    private void assignNextStartingPlayer(PlayerData nextPlayerData) {
        for (int i = 0; i < players.size(); i++) {
            List<Tile> floorLineTiles = players.get(i).getBoard().getFloorLine().getCopyTiles();
            if (floorLineTiles.contains(PlayerTile.getInstance())) {
                nextPlayerData.setIdentifier(players.get(i).getIdentifier());
                nextPlayerData.setName(players.get(i).getName());
                swap(turnOrder, 0, i);
            }
        }
    }
    public GameState terminateGame() {
        GameState gameState = new GameState();
        List<List<Tile>> factoryTiles = new ArrayList<>();
        factories.forEach(factory -> {
            factoryTiles.add(factory.getAllTiles());
        });
        gameState.setFactories(factoryTiles);
        gameState.setMiddle(middle.getAllTiles());

        gamePhase = GamePhase.TERMINATED;
        isPlaying = false;

        return gameState;
    }
    @Override
    public GameState endGame() {
        GameState gameState = new GameState();
        List<List<Tile>> factoryTiles = new ArrayList<>();
        factories.forEach(factory -> {
            factoryTiles.add(factory.getAllTiles());
        });
        gameState.setFactories(factoryTiles);
        gameState.setMiddle(middle.getAllTiles());
        gamePhase = GamePhase.FINISHED;
        isPlaying = false;
        return gameState;
    }
    @Override
    public boolean isCurrentPlayer(PlayerData player) {
        for (Player p : players) {
            if (p.getIdentifier() == player.getIdentifier())
                return true;
        }
        return false;
    }
    @Override
    public PlayerData addPlayer(PlayerData playerData) {
        return new PlayerData();
    }

    @Override
    public boolean isValidStartGame() {
        return players.size() >= 2 && players.size() <= 4 && !isPlaying && round == 0
                && middle.getAllTiles().size() == 0 && turnOrder.size() == players.size()
                && box.size() == 0 && bag.getTiles().size() == 0
                && factories.stream().allMatch(factory -> factory.getAllTiles().size() == 0);
    }

    @Override
    public DataObject performMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor
            tileColor) {
        return new MoveUpdate();
    }

    @Override
    public DataObject performMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
        return new MoveUpdate();
    }
    @Override
    public DataObject performMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
        return new MoveUpdate();
    }
    @Override
    public DataObject performMoveMiddleFloorLine(List<Tile> tiles) {
        return new MoveUpdate();
    }

    @Override
    public DataObject performMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
        return new MoveUpdate();
    }

    @Override
    public boolean isValidMoveFactoryPatternLine(List<Tile> tiles, int factoryIndex, int patternLineRow, TileColor
            tileColor) {
        return false;
    }

    @Override
    public boolean isValidMoveFactoryFloorLine(List<Tile> tiles, int factoryIndex) {
        return false;
    }

    @Override
    public boolean isValidMoveMiddlePatternLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
        return false;
    }

    @Override
    public boolean isValidMoveMiddleFloorLine(List<Tile> tiles) {
        return false;
    }

    @Override
    public boolean isValidMovePatternLineFloorLine(List<Tile> tiles, int patternLineRow, TileColor tileColor) {
        return false;
    }

    public static void swap(List<Player> list, int index1, int index2) {
        Player temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

}
