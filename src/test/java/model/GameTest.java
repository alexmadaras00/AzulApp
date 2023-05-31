package model;

import dataobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    static List<Factory> factories;
    static List<Player> players;
    static List<Player> turnOrder;
    static Player player1;
    static Player player2;
    List<List<Tile>> factoryTiles;
    Game game;

    @BeforeEach
    public void init() {
        players = new ArrayList<>();
        factories = new ArrayList<>();
        player1 = new Player("Boris");
        player2 = new Player("Giani");
        players.add(player1);
        players.add(player2);
        turnOrder = new ArrayList<>(players);
        factoryTiles = new ArrayList<>();
        game = new Game(players);
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(game.getMiddle());
        assertNotNull(game.getFactories());
        assertEquals(0, game.getRound());
        assertNotNull(game.getPlayers());
        assertNotNull(game.getTurnOrder());
        assertEquals(game.getPlayers(), game.getTurnOrder());
        assertNotNull(game.getBox());
        assertEquals(0, game.getBag().getTiles().size());
        assertFalse(game.isPlaying());
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
    }

    @Test
    public void testStartGame() {
        players = game.getPlayers();
        assertPreConditionsStart();
        GameState gameState = game.startGame();
        assertPostConditionsStart(gameState);
    }


    private void assertPreConditionsStart() {
        assertTrue(game.isValidStartGame());

    }

    private void assertPostConditionsStart(GameState gameState) {
        factories = game.getFactories();
        int numberOfPlayers = game.getPlayers().size();
        if (numberOfPlayers == 2) assertEquals(5, game.getFactories().size());
        else if (numberOfPlayers == 3) assertEquals(7, game.getFactories().size());
        else assertEquals(9, game.getFactories().size());
        assertEquals(game.getTurnOrder().size(), game.getPlayers().size());
        assertEquals(0, game.getMiddle().getAllTiles().size());
        assertEquals(0, game.getBox().size());
        assertEquals(100, game.getBag().getTiles().size());
        assertTrue(game.isPlaying());
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
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
    public void testStartRoundInitial() {
        game.startGame();
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
        game.endRound();
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        PlayerTile startingPlayerTile = PlayerTile.getInstance();
        RoundUpdate roundUpdate = game.startRound();

        assertEquals(80, game.getBag().getTiles().size());
        assertEquals(List.of(startingPlayerTile), game.getMiddle().getAllTiles());
        assertEquals(game.getTurnOrder().get(0).getIdentifier(), roundUpdate.getMove().getPlayer().getIdentifier());
        assertEquals(game.getTurnOrder().get(1).getIdentifier(), roundUpdate.getMove().getNextPlayer().getIdentifier());

        assertNull(roundUpdate.getScoreUpdates());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        assertEquals(1, game.getRound());
    }

    @Test
    public void testStartRoundDuringGame() {
        game.startGame();
        game.endRound();
        game.startRound();
        System.out.println(game.getTurnOrder());
        List<Action> endGameUpdates = game.endRound().getUpdates();
        System.out.println(game.getTurnOrder());
        assertEquals(7, endGameUpdates.size());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        PlayerTile startingPlayerTile = PlayerTile.getInstance();
        RoundUpdate roundUpdateStartRound = game.startRound();
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        assertEquals(2, game.getRound());
        assertEquals(List.of(startingPlayerTile), game.getMiddle().getAllTiles());
        assertEquals(game.getTurnOrder().get(0).getIdentifier(), roundUpdateStartRound.getMove().getPlayer().getIdentifier());
        assertEquals(game.getTurnOrder().get(1).getIdentifier(), roundUpdateStartRound.getMove().getNextPlayer().getIdentifier());

    }

    @Test
    public void testEndRoundInitial() {
        game.getPlayers().get(1).getBoard().getPatternLine().addTiles(2, List.of(Color.RED, Color.RED, Color.RED));
        game.getPlayers().get(1).getBoard().getFloorLine().addTiles(List.of(PlayerTile.getInstance(), Color.BLUE));
        game.startGame();
        game.startRound();
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        int countFactoriesNotFull;
        assertTrue(game.getRound() >= 1);
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        countFactoriesNotFull = (int) game.getFactories().stream()
                .filter(factory -> factory.getAllTiles().size() < 4)
                .count();
        assertEquals(5, countFactoriesNotFull);

        RoundUpdate roundUpdate = game.endRound();

        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        assertFactoryUpdates(roundUpdate);
        assertEquals(scoreUpdates, roundUpdate.getScoreUpdates());
        assertEquals(0, game.getBox().size());
        assertEquals(1, game.getMiddle().getAllTiles().size());
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
    }

    @Test
    public void testEndRoundDuringGame() {
        game.getPlayers().get(1).getBoard().getPatternLine().addTiles(2, List.of(Color.RED, Color.RED, Color.RED));
        game.getPlayers().get(1).getBoard().getFloorLine().addTiles(List.of(PlayerTile.getInstance(), Color.BLUE));
        game.startGame();
        game.startRound();
        game.endRound();
        game.startRound();
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        assertTrue(game.getRound() >= 1);
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());

        RoundUpdate roundUpdate = game.endRound();

        assertScoreUpdates(scoreUpdates, roundUpdate);
        assertFactoryUpdates(roundUpdate);
        assertEquals(scoreUpdates, roundUpdate.getScoreUpdates());
        assertEquals(0, game.getBox().size());
        assertEquals(1, game.getMiddle().getAllTiles().size());
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
        assertTurnOrderChange();
        for (Player p : game.getPlayers()) {
            if (p.getBoard().getWall().hasCompleteRow()) {
                assertFalse(game.isPlaying());
                break;
            }
        }
    }

    private void assertFactoryUpdates(RoundUpdate roundUpdate) {
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        int countFactoriesNotFull = (int) game.getFactories().stream()
                .filter(factory -> factory.getAllTiles().size() < 4)
                .count();
        assertEquals(game.getFactories().size() + 2, roundUpdate.getUpdates().size());
        assertEquals(0, countFactoriesNotFull);
        List<Action> factoryUpdates = roundUpdate.getUpdates().stream().filter(r -> r.getTo() != null).toList();
        factoryUpdates.forEach(f -> assertEquals(4, f.getAmount()));
    }

    private void assertTurnOrderChange() {
        List<Player> newTurnOrder = new ArrayList<>();
        game.getPlayers().forEach(player -> {
            if (player.getBoard().getFloorLine().getCopyTiles().contains(PlayerTile.getInstance())) {
                newTurnOrder.add(player);
                System.out.println("Starting: " + player);
            }
        });
        game.getPlayers().forEach(player -> {
            if (!newTurnOrder.contains(player))
                newTurnOrder.add(player);
        });
        assertEquals(game.getPlayers().size(), turnOrder.size());
        assertEquals(newTurnOrder.get(0), game.getTurnOrder().get(0));
    }

    private void assertScoreUpdates(List<ScoreUpdate> scoreUpdates, RoundUpdate roundUpdate) {
        game.getPlayers().forEach(player -> {
            List<Integer> completedRows = player.getBoard().getPatternLine().completedRows();
            assertEquals(player.getBoard().wallTilting(), game.getBox());
            ScoreUpdate scoreUpdatePlayer = roundUpdate.getScoreUpdates().stream().filter(scoreUpdate -> scoreUpdate.getPlayer().getIdentifier() == player.getIdentifier()).toList().get(0);
            assertEquals(player.getIdentifier(), scoreUpdatePlayer.getPlayer().getIdentifier());
            assertEquals(player.getBoard().getScoreChanges(), scoreUpdatePlayer.getScoreChanges());
            assertEquals(player.getBoard().getScore(), scoreUpdatePlayer.getNewScore());
            assertEquals(0, player.getBoard().getFloorLine().getCopyTiles().size());
            completedRows.forEach(completedRow -> assertEquals(0, player.getBoard().getPatternLine().getCopyTable().get(completedRow).size()));
            scoreUpdates.add(scoreUpdatePlayer);
        });
    }

    @Test
    public void testTerminateGame() {
        game.startGame();
        assertTrue(game.isPlaying());
        assertFalse(game.getPlayers().get(0).getBoard().getWall().hasCompleteRow());
        game.getPlayers().forEach(p -> assertFalse(p.getBoard().getWall().hasCompleteRow()));
        assertNotNull(game.terminateGame());

        assertFalse(game.isPlaying());
        assertEquals(GamePhase.TERMINATED, game.getGamePhase());
    }

    @Test
    public void testEndGame() {
        game.startGame();
        game.endGame();
        List<List<Tile>> factoryTiles = new ArrayList<>();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        assertEquals(GamePhase.FINISHED, game.getGamePhase());
        assertEquals(game.endGame().getMiddle(), game.getMiddle().getAllTiles());
        assertEquals(game.endGame().getFactories(), factoryTiles);
        assertFalse(game.isPlaying());
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
        assertNotNull(game.addPlayer(new PlayerData()));
    }

    @Test
    public void testIsValidStartGame() {
        assertTrue(game.isValidStartGame());
    }

    @Test
    public void testPerformMoveFactoryPatternLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertNotNull(game.performMoveFactoryPatternLine(tiles, 1, 2, Color.RED));
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertNotNull(game.performMoveFactoryFloorLine(tiles, 1));

    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertNotNull(game.performMoveMiddlePatternLine(tiles, 1, (Color) tiles.get(0)));
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertNotNull(game.performMoveMiddleFloorLine(tiles));

    }

    @Test
    public void testPerformMovePatternLineFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertNotNull(game.performMovePatternLineFloorLine(tiles, 2, Color.RED));

    }

    @Test
    public void testIsValidMoveFactoryPatternLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertFalse(game.isValidMoveFactoryPatternLine(tiles, 1, 2, Color.RED));
    }

    @Test
    public void testIsValidMoveFactoryFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertFalse(game.isValidMoveFactoryFloorLine(tiles, 1));

    }

    @Test
    public void testIsValidMoveMiddlePatternLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertFalse(game.isValidMoveMiddlePatternLine(tiles, 1, (Color) tiles.get(0)));
    }

    @Test
    public void testIsValidMoveMiddleFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertFalse(game.isValidMoveMiddleFloorLine(tiles));

    }

    @Test
    public void testIsValidMovePatternLineFloorLine() {
        List<Tile> tiles = List.of(Color.RED, Color.RED);
        assertFalse(game.isValidMovePatternLineFloorLine(tiles, 2, Color.RED));

    }


}

