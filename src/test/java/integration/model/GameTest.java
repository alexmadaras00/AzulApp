package integration.model;

import dataobjects.*;
import dataobjects.data.Action;
import dataobjects.data.GameState;
import dataobjects.data.PlayerBoardState;
import dataobjects.data.PlayerData;
import dataobjects.data.RoundUpdate;
import dataobjects.data.ScoreUpdate;
import model.*;
import model.factory.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ExceptionGameStart;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    static List<Factory> factories;
    static List<Player> players;
    static Player player1;
    static Player player2;
    List<List<TileColor>> factoryTiles;
    Game game;

    @BeforeEach
    public void init() {
        game = new Game();
        factories = new ArrayList<>();
        player1 = new Player("Boris");
        player2 = new Player("Giani");
        assertEquals(0, game.getPlayers().size());
        assertEquals(0, game.getTurnOrder().size());
        assertEquals(0, game.getBox().size());
        game.addPlayer(player1);
        game.addPlayer(player2);
        factoryTiles = new ArrayList<>();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, game.getMiddle().getAllTiles().size());
        assertEquals(0, game.getFactories().size());
        assertEquals(0, game.getRound());
        assertEquals(game.getPlayers(), game.getTurnOrder());
        assertEquals(0, game.getBag().getTiles().size());
        assertFalse(game.isPlaying());
        Assertions.assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
    }

    @Test
    public void testStartGame() throws ExceptionGameStart {
        players = game.getPlayers();
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
        GameState gameState = game.startGame();
        assertPostConditionsStart(gameState);
        try {
            Player p1 = new Player();
            p1.setName("Nano");
            Game gameFailFewerPlayers = new Game();
            game.addPlayer(player1);
            gameFailFewerPlayers.startGame();
            assertTrue(gameFailFewerPlayers.getPlayers().size() < 2);
            assertFalse(gameFailFewerPlayers.isValidStartGame());
            fail("Expected exception not thrown");
        } catch (ExceptionGameStart e) {
            assertEquals("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.", e.getMessage());
        }
        try {
            Player p1 = new Player();
            p1.setName("Nano");
            Player p2 = new Player();
            p1.setName("Miguel");
            Player p3 = new Player();
            p1.setName("Santo");
            Player p4 = new Player();
            p1.setName("Matei");
            Player p5 = new Player();
            p1.setName("Rutter");
            Game gameFailTooManyPlayers = new Game();
            gameFailTooManyPlayers.addPlayer(p1);
            gameFailTooManyPlayers.addPlayer(p2);
            gameFailTooManyPlayers.addPlayer(p3);
            gameFailTooManyPlayers.addPlayer(p4);
            gameFailTooManyPlayers.addPlayer(p5);
            gameFailTooManyPlayers.startGame();
            assertTrue(gameFailTooManyPlayers.getPlayers().size() < 2);
            assertFalse(gameFailTooManyPlayers.isValidStartGame());
            fail("Expected exception not thrown");
        } catch (ExceptionGameStart e) {
            assertEquals("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.", e.getMessage());
        }
    }

    private void assertPostConditionsStart(GameState gameState) {
        factories = game.getFactories();
        int numberOfPlayers = game.getPlayers().size();
        if (numberOfPlayers == 2) assertEquals(5, game.getFactories().size());
        else if (numberOfPlayers == 3) assertEquals(7, game.getFactories().size());
        else assertEquals(9, game.getFactories().size());
        assertEquals(game.getTurnOrder().size(), game.getPlayers().size());
        assertEquals(1, game.getMiddle().getAllTiles().size());
        assertEquals(0, game.getBox().size());
        assertEquals(100, game.getBag().getTiles().size());
        PlayerTile startingTile = PlayerTile.getInstance();
        assertTrue(game.getMiddle().getAllTiles().contains(startingTile));
        assertTrue(game.isPlaying());
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
        assertGameState(gameState, factories);
    }

    private void assertGameState(GameState gameState, List<Factory> factories) {
        factoryTiles = new ArrayList<>();
        factories.forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        assertEquals(factoryTiles, gameState.getFactories());
        assertEquals(game.getPlayers().size(), gameState.getPlayerBoards().size());
        assertEquals(game.getMiddle().getAllTiles(), gameState.getMiddle());
        assertEquals(0, gameState.getPlayerBoards().get(0).getScore());
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerData playerData = new PlayerData();
            playerData.setName(game.getPlayers().get(i).getName());
            playerData.setIdentifier(game.getPlayers().get(i).getIdentifier());
            assertEquals(new ArrayList<>(), gameState.getPlayerBoards().get(i).getFloorLine());
            assertEquals(game.getPlayers().get(i).getBoard().getWall().getCopyTable(), gameState.getPlayerBoards().get(i).getWall());
            assertEquals(game.getPlayers().get(i).getBoard().getPatternLine().getCopyTable(), gameState.getPlayerBoards().get(i).getPatternLine());
            assertEquals(playerData, gameState.getPlayerBoards().get(i).getPlayer());
            assertEquals(game.getPlayers().get(i).getBoard().getScore(), gameState.getPlayerBoards().get(i).getScore());
        }
    }

    @Test
    public void testStartRound() {
        game.startGame();
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
        int initialBagSize = game.getBag().getTiles().size();
        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        PlayerTile startingPlayerTile = PlayerTile.getInstance();
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(5, countFactoriesNotFull);

        RoundUpdate roundUpdate = game.startRound();

        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        assertFactoryUpdates(roundUpdate);
        assertEquals(initialBagSize - (game.getFactories().size() * 4), game.getBag().getTiles().size());
        assertEquals(List.of(startingPlayerTile), game.getMiddle().getAllTiles());
        assertEquals(game.getTurnOrder().get(0).getIdentifier(), roundUpdate.getMove().getPlayer().getIdentifier());
        assertEquals(game.getTurnOrder().get(1).getIdentifier(), roundUpdate.getMove().getNextPlayer().getIdentifier());
        assertNull(roundUpdate.getScoreUpdates());
        assertEquals(1, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    private void assertFactoryUpdates(RoundUpdate roundUpdate) {
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(game.getFactories().size(), roundUpdate.getUpdates().size());
        assertEquals(0, countFactoriesNotFull);
        List<Action> factoryUpdates = roundUpdate.getUpdates().stream().filter(r -> r.getTo() != null).toList();
        factoryUpdates.forEach(f -> assertEquals(4, f.getAmount()));
    }

    @Test
    public void testEndRound() {
        game.startGame();
        game.startRound();
        game.endRound();
        assertEquals(1, game.getRound());
        game.startRound();
        List<Player> players = game.getPlayers();
        players.get(1).getBoard().getPatternLine().addTiles(2, List.of(TileColor.RED, TileColor.RED, TileColor.RED));
        players.get(1).getBoard().getFloorLine().addTiles(List.of(PlayerTile.getInstance(), TileColor.BLUE));
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        PlayerData lastPlayer = new PlayerData();
        lastPlayer.setName(game.getTurnOrder().get(game.getTurnOrder().size() - 1).getName());
        lastPlayer.setIdentifier(game.getTurnOrder().get(game.getTurnOrder().size() - 1).getIdentifier());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        PlayerData nextPlayer = getTurnOrderChange();

        RoundUpdate roundUpdate = game.endRound();

        assertEquals(2, game.getRound());
        assertScoreUpdates(scoreUpdates, roundUpdate);
        assertEquals(0, game.getBox().size());
        assertEquals(0, game.getMiddle().getAllTiles().size());
        assertEquals(GamePhase.WALL_TILLING, game.getGamePhase());
        assertEquals(nextPlayer.getIdentifier(), roundUpdate.getMove().getNextPlayer().getIdentifier());
        assertEquals(lastPlayer.getIdentifier(), roundUpdate.getMove().getPlayer().getIdentifier());
        assertGameEnded(players);
    }

    private PlayerData getTurnOrderChange() {
        List<Player> newTurnOrder = new ArrayList<>();
        PlayerData nextPlayer = new PlayerData();
        Player nextStartingPlayer = game.getPlayers().stream().filter(player -> player.getBoard().getFloorLine().getCopyTiles().contains(PlayerTile.getInstance())).findFirst().orElseThrow();
        newTurnOrder.add(nextStartingPlayer);
        game.getPlayers().forEach(player -> {
            if (!newTurnOrder.contains(player)) newTurnOrder.add(player);
        });
        nextPlayer.setName(nextStartingPlayer.getName());
        nextPlayer.setIdentifier(nextStartingPlayer.getIdentifier());
        return nextPlayer;
    }

    private void assertGameEnded(List<Player> players) {
        incrementRound();
        game.startRound();
        Player p = players.get(0);
        p.getBoard().getWall().addTile(0, TileColor.RED);
        p.getBoard().getWall().addTile(0, TileColor.CYAN);
        p.getBoard().getWall().addTile(0, TileColor.YELLOW);
        p.getBoard().getWall().addTile(0, TileColor.BLUE);
        p.getBoard().getWall().addTile(0, TileColor.BLACK);
        game.endRound();
        assertTrue(p.getBoard().hasFulfilledEndCondition());
        assertFalse(game.isPlaying());
        assertEquals(GamePhase.FINISHED, game.getGamePhase());
    }

    private void assertScoreUpdates(List<ScoreUpdate> scoreUpdates, RoundUpdate roundUpdate) {
        game.getPlayers().forEach(player -> {
            List<Integer> completedRows = player.getBoard().getPatternLine().completedRows();
            assertEquals(player.getBoard().wallTilling(), game.getBox());
            ScoreUpdate scoreUpdatePlayer = roundUpdate.getScoreUpdates().stream().filter(scoreUpdate -> scoreUpdate.getPlayer().getIdentifier() == player.getIdentifier()).findFirst().orElseThrow();
            assertEquals(player.getIdentifier(), scoreUpdatePlayer.getPlayer().getIdentifier());
            assertEquals(player.getBoard().getScoreChanges(), scoreUpdatePlayer.getScoreChanges());
            assertEquals(player.getBoard().getScore(), scoreUpdatePlayer.getNewScore());
            assertEquals(0, player.getBoard().getFloorLine().getCopyTiles().size());
            completedRows.forEach(completedRow -> assertEquals(0, player.getBoard().getPatternLine().getCopyTable().get(completedRow).size()));
            scoreUpdates.add(scoreUpdatePlayer);
        });
        assertEquals(scoreUpdates, roundUpdate.getScoreUpdates());
    }

    @Test
    public void testTerminateGame() {
        game.startGame();
        assertTrue(game.isPlaying());
        assertFalse(game.getPlayers().get(0).getBoard().hasFulfilledEndCondition());
        game.getPlayers().forEach(p -> assertFalse(p.getBoard().hasFulfilledEndCondition()));
        assertNotNull(game.terminateGame());
        assertFalse(game.isPlaying());
        assertEquals(GamePhase.TERMINATED, game.getGamePhase());
    }

    @Test
    public void testEndGame() {
        player1 = new Player();
        player2 = new Player();
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
        incrementRound();
        assertTrue(game.getRound() >= 5);
        List<List<TileColor>> factoryTiles = new ArrayList<>();

        player1.getBoard().getWall().addTile(0, TileColor.BLUE);
        player1.getBoard().getWall().addTile(1, TileColor.BLUE);
        player1.getBoard().getWall().addTile(1, TileColor.RED);
        player1.getBoard().getWall().addTile(1, TileColor.CYAN);
        player1.getBoard().getWall().addTile(1, TileColor.BLACK);
        player1.getBoard().getWall().addTile(1, TileColor.YELLOW);
        player1.getBoard().getWall().addTile(2, TileColor.BLACK);
        player1.getBoard().getWall().addTile(3, TileColor.RED);
        player1.getBoard().getWall().addTile(4, TileColor.YELLOW);
        player2.getBoard().getWall().addTile(0, TileColor.BLUE);
        player2.getBoard().getWall().addTile(1, TileColor.BLUE);
        player2.getBoard().getWall().addTile(2, TileColor.BLUE);
        player2.getBoard().getWall().addTile(3, TileColor.BLUE);
        player2.getBoard().getWall().addTile(4, TileColor.BLUE);
        player2.getBoard().addFinalScores();
        player1.getBoard().addFinalScores();
        PlayerData playerData2 = new PlayerData();
        playerData2.setIdentifier(player2.getIdentifier());
        playerData2.setName(player2.getName());
        PlayerData playerData1 = new PlayerData();
        playerData1.setIdentifier(player1.getIdentifier());
        playerData1.setName(player1.getName());
        playerData1.setName(player1.getName());

        GameState endedGame = game.endGame();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        assertEquals(GamePhase.FINISHED, game.getGamePhase());
        assertEquals(endedGame.getMiddle(), game.getMiddle().getAllTiles());
        assertEquals(factoryTiles, endedGame.getFactories());
        assertEquals(playerData2, endedGame.getWinnerPlayer());
        assertEquals(10, player2.getBoard().getScore());
        assertUpdateFinalScores(endedGame);
        assertFalse(game.isPlaying());
    }

    private void assertUpdateFinalScores(GameState endedGame) {
        assertEquals(game.getPlayers().size(), endedGame.getPlayerBoards().size());
        game.getPlayers().forEach(player -> {
            PlayerBoardState playerBoardState = endedGame.getPlayerBoards().stream().filter(ps -> ps.getPlayer().getIdentifier() == player.getIdentifier()).findFirst().orElseThrow();
            assertEquals(player.getBoard().getScore(), playerBoardState.getScore());
            assertEquals(player.getBoard().getFloorLine().getCopyTiles(), playerBoardState.getFloorLine());
            assertEquals(player.getBoard().getPatternLine().getCopyTable(), playerBoardState.getPatternLine());
            assertEquals(player.getBoard().getWall().getCopyTable(), playerBoardState.getWall());
        });

    }

    private void incrementRound() {
        game.startRound();
        game.endRound();
        game.startRound();
        game.endRound();
        game.startRound();
        game.endRound();
        game.startRound();
        game.endRound();
        game.startRound();
        game.endRound();
    }

    @Test
    public void testIsCurrentPlayer() {
        Player randomPlayer = new Player("Cristiano");
        PlayerData playerData1 = new PlayerData();
        playerData1.setName(player1.getName());
        playerData1.setIdentifier(player1.getIdentifier());
        PlayerData playerData2 = new PlayerData();
        playerData2.setName(player2.getName());
        playerData2.setIdentifier(player2.getIdentifier());
        PlayerData playerDataRandom = new PlayerData();
        playerDataRandom.setName(randomPlayer.getName());
        playerDataRandom.setIdentifier(randomPlayer.getIdentifier());
        assertTrue(game.isCurrentPlayer(playerData1));
        assertTrue(game.isCurrentPlayer(playerData2));
        assertFalse(game.isCurrentPlayer(playerDataRandom));
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player();
        player.setName("Marcelo");
        PlayerData playerData = game.addPlayer(player);
        assertEquals(player.getIdentifier(), playerData.getIdentifier());
        assertEquals(player.getName(), playerData.getName());
        assertTrue(game.getPlayers().contains(player));
        assertTrue(game.getTurnOrder().contains(player));
    }

    @Test
    public void testIsValidStartGame() {
        assertTrue(game.isValidStartGame());
    }

    @Test
    public void testPerformMoveFactoryPatternLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertNotNull(game.performMoveFactoryPatternLine(tiles, 1, 2, TileColor.RED));
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertNotNull(game.performMoveFactoryFloorLine(tiles, 1));

    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertNotNull(game.performMoveMiddlePatternLine(tiles, 1, (TileColor) tiles.get(0)));
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertNotNull(game.performMoveMiddleFloorLine(tiles));

    }

    @Test
    public void testIsValidMoveFactoryPatternLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertFalse(game.isValidMoveFactoryPatternLine(tiles, 1, 2, TileColor.RED));
    }

    @Test
    public void testIsValidMoveFactoryFloorLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertFalse(game.isValidMoveFactoryFloorLine(tiles, 1));

    }

    @Test
    public void testIsValidMoveMiddlePatternLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertFalse(game.isValidMoveMiddlePatternLine(tiles, 1, (TileColor) tiles.get(0)));
    }

    @Test
    public void testIsValidMoveMiddleFloorLine() {
        List<Tile> tiles = List.of(TileColor.RED, TileColor.RED);
        assertFalse(game.isValidMoveMiddleFloorLine(tiles));
    }

}

