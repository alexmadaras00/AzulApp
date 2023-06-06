package model;

import dataobjects.*;
import utils.ExceptionGameStart;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
            PlayerTile startingPlayerTile = PlayerTile.getInstance();
            GameState gameState = new GameState();
            Collections.shuffle(turnOrder);
            middle.getAllTiles().add(startingPlayerTile);
            fillBag();
            initFactories();
            initGameState(gameState);
            isPlaying = true;
            gamePhase = GamePhase.PREPARING_ROUND;
            return gameState;
        } else
            throw new ExceptionGameStart("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.");

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
        player1Data.setName(turnOrder.get(0).getName());
        player1Data.setIdentifier(turnOrder.get(0).getIdentifier());
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
        gamePhase = GamePhase.PREPARING_ROUND;
        RoundUpdate roundUpdate = new RoundUpdate();
        MoveUpdate moveUpdate = new MoveUpdate();
        PlayerData playerData = new PlayerData();
        List<Action> updates = new ArrayList<>();

        fillFactories(updates);
        playerData.setIdentifier(turnOrder.get(0).getIdentifier());
        playerData.setName(turnOrder.get(0).getName());
        PlayerData nextPlayerData = new PlayerData();
        nextPlayerData.setIdentifier(turnOrder.get(1).getIdentifier());
        nextPlayerData.setName(turnOrder.get(1).getName());

        moveUpdate.setPlayer(playerData);
        moveUpdate.setNextPlayer(nextPlayerData);

        roundUpdate.setMove(moveUpdate);
        roundUpdate.setUpdates(updates);

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
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        List<Action> updates = new ArrayList<>();
        List<Action> steps = new ArrayList<>();
        MoveUpdate moveUpdate = new MoveUpdate();
        PlayerData currentPlayerData = new PlayerData();
        PlayerData nextPlayerData = new PlayerData();

        updateScore(scoreUpdates);
        currentPlayerData.setName(turnOrder.get(turnOrder.size() - 1).getName());
        currentPlayerData.setIdentifier(turnOrder.get(turnOrder.size() - 1).getIdentifier());
        moveUpdate.setPlayer(currentPlayerData);
        assignNextStartingPlayer(nextPlayerData);
        moveUpdate.setNextPlayer(nextPlayerData);
        wallTilling(updates);
        moveUpdate.setSteps(steps);

        gamePhase = GamePhase.WALL_TILLING;
        middle.getAllTiles().clear();
        box.clear();
        middle.getAllTiles().clear();

        roundUpdate.setScoreUpdates(scoreUpdates);
        roundUpdate.setMove(moveUpdate);
        roundUpdate.setUpdates(updates);

        for (Player p : players) {
            if (p.getBoard().hasFulfilledEndCondition()) {
                endGame();
                break;
            }
        }
        return roundUpdate;
    }

    private void wallTilling(List<Action> updates) {
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
            player.getBoard().addFinalScores();
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

    @Override
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

        gameState.setMiddle(middle.getAllTiles());

        factories.forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        gameState.setFactories(factoryTiles);
        updateFinalScores(gameState);
        determineWinner(gameState);

        gamePhase = GamePhase.FINISHED;
        isPlaying = false;
        return gameState;
    }
    private void determineWinner(GameState gameState) {
        Map<Player,Integer> scores = new HashMap<>();
        AtomicInteger maxScore = new AtomicInteger();
        AtomicReference<Player> maxScorePlayer = new AtomicReference<>();
        PlayerData winnerData = new PlayerData();
        players.forEach(player -> {
            scores.put(player,player.getBoard().getScore());
        });
        scores.forEach((player, integer) -> {
                if(integer > maxScore.get()){
                    maxScore.set(integer);
                    maxScorePlayer.set(player);
                }
        });
        winnerData.setName(maxScorePlayer.get().getName());
        winnerData.setIdentifier(maxScorePlayer.get().getIdentifier());
        gameState.setWinnerPlayerPlayer(winnerData);
    }

    private void updateFinalScores(GameState gameState) {
        PlayerData playerDataCompleteRow = new PlayerData();
        List<PlayerBoardState> playerBoards = new ArrayList<>();
        players.forEach(player -> {
            PlayerBoardState playerBoardState = player.getBoard().toObject();
            if (player.getBoard().getWall().hasCompleteRow()) {
                playerDataCompleteRow.setIdentifier(player.getIdentifier());
                playerDataCompleteRow.setName(player.getName());
                playerBoardState.setPlayer(playerDataCompleteRow);
            } else {
                PlayerData playerDataOtherPlayers = new PlayerData();
                playerDataOtherPlayers.setIdentifier(player.getIdentifier());
                playerDataOtherPlayers.setName(player.getName());
                playerBoardState.setPlayer(playerDataOtherPlayers);
            }
            playerBoards.add(playerBoardState);
        });
        gameState.setCurrentPlayer(playerDataCompleteRow);
        gameState.setPlayerBoards(playerBoards);
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


    public static void swap(List<Player> list, int index1, int index2) {
        Player temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

}
