package integration.model;

import dataobjects.*;
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
    static PlayerData playerData1;
    static Player player2;
    static PlayerData playerData2;
    List<List<TileColor>> factoryTiles;
    Game game;

    @BeforeEach
    public void init() {
        game = new Game();
        factories = new ArrayList<>();
        player1 = new Player("Boris");
        playerData1 = new PlayerData();
        playerData1.setIdentifier(player1.getIdentifier());
        playerData1.setName(player1.getName());
        player2 = new Player("Giani");
        playerData2 = new PlayerData();
        playerData2.setIdentifier(player2.getIdentifier());
        playerData2.setName(player2.getName());
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
    public void testStartInitialRound() {
        players = game.getPlayers();
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
        GameState gameState = game.startGame();
        assertPostConditionsStart(gameState);
        Game gameFailFewerPlayers = null;
        Game gameFailTooManyPlayers = null;
        try {
            Player p1 = new Player();
            p1.setName("Nano");
            gameFailFewerPlayers = new Game();
            game.addPlayer(player1);
            gameFailFewerPlayers.startGame();
            assertTrue(gameFailFewerPlayers.getPlayers().size() < 2);
            assertFalse(gameFailFewerPlayers.isValidStartGame());
            fail("Expected exception not thrown");
        } catch (ExceptionGameStart e) {
            assertEquals("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.", e.getMessage());
            assert gameFailFewerPlayers != null;
            assertFalse(gameFailFewerPlayers.isPlaying());
            assertEquals(GamePhase.INITIALIZED, gameFailFewerPlayers.getGamePhase());
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
            gameFailTooManyPlayers = new Game();
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
            assert gameFailTooManyPlayers != null;
            assertFalse(gameFailTooManyPlayers.isPlaying());
            assertEquals(GamePhase.INITIALIZED, gameFailTooManyPlayers.getGamePhase());
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
        assertEquals(80, game.getBag().getTiles().size());
        PlayerTile startingTile = PlayerTile.getInstance();
        assertTrue(game.getMiddle().getAllTiles().contains(startingTile));
        assertTrue(game.isPlaying());
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        assertEquals(factoryTiles, game.createGameState().getFactories());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        assertEquals(List.of(PlayerTile.getInstance()), game.getMiddle().getAllTiles());
        assertEquals(1, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    @Test
    public void testRestartRound() {
        game.startGame();
        factories = game.getFactories();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        List<ScoreUpdate> scoreUpdates = new ArrayList<>();
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);

        assertEquals(0, countFactoriesNotFull);
        RoundUpdate roundUpdate = (RoundUpdate) restartingRound();
        assertFactoryUpdates(roundUpdate);
        assertScoreUpdates(scoreUpdates, roundUpdate);
        assertEquals(60, game.getBag().getTiles().size());
        assertEquals(game.getTurnOrder().get(0).getIdentifier(), roundUpdate.getMove().getPlayer().getIdentifier());
        assertEquals(game.getTurnOrder().get(1).getIdentifier(), roundUpdate.getMove().getNextPlayer().getIdentifier());
        assertTrue(game.isCurrentPlayer(playerData2));
        assertEquals(2, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    private DataObject restartingRound() {
        factories = game.getFactories();
        PlayerTile startingPlayerTile = PlayerTile.getInstance();
        factories.forEach(Factory::popAllTiles);
        game.getMiddle().getAllTiles().clear();
        factories.get(1).addTiles(List.of(TileColor.RED, TileColor.RED, TileColor.BLUE, TileColor.BLACK));
        System.out.println("PLAYER1 MOVE: " + game.isCurrentPlayer(playerData1));
        System.out.println(player1.getBoard().getWall().getCopyTable().get(0));
        game.performMoveFactoryPatternLine(1, 0, TileColor.BLUE);
        System.out.println("PLAYER1 MOVE: " + game.isCurrentPlayer(playerData1));
        game.performMoveFactoryPatternLine(1, 2, TileColor.RED);
        game.performMoveFactoryPatternLine(1, 3, TileColor.RED);
        game.performMoveFactoryPatternLine(1, 2, TileColor.BLACK);
        game.performMoveMiddleFloorLine(TileColor.RED);
        player2.getBoard().getFloorLine().addTiles(List.of(startingPlayerTile));
        System.out.println(game.getMiddle().getAllTiles());
//      assertInstanceOf(EndGameUpdate.class, game.performMoveMiddleFloorLine(TileColor.BLUE));
        System.out.println(player1.getBoard().getWall().getCopyTable().get(0));
        System.out.println(player2.getBoard().getWall().getCopyTable().get(0));
        if (player1.getBoard().getWall().getCopyTable().get(0).size() == 4)
            return (EndGameUpdate) game.performMoveMiddleFloorLine(TileColor.BLACK);
        return (RoundUpdate) game.performMoveMiddleFloorLine(TileColor.BLACK);
    }

    private void assertFactoryUpdates(RoundUpdate roundUpdate) {
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals((factories.size() * 4) + 2, roundUpdate.getUpdates().size());
        assertEquals(0, countFactoriesNotFull);

    }

    // @Test
    // public void testEndRound() {
    //     factories.forEach(factory -> factory.popAllTiles());
    //     game.getMiddle().getAllTiles().clear();
    //     factories.get(1).addTiles(List.of(TileColor.RED, TileColor.RED, TileColor.BLUE, TileColor.BLACK));
    //     game.performMoveFactoryPatternLine(1, 2, TileColor.RED);

    //     assertEquals(1, game.getRound());
    //     List<Player> players = game.getPlayers();
    //     players.get(1).getBoard().getPatternLine().addTiles(2, List.of(TileColor.RED, TileColor.RED, TileColor.RED));
    //     players.get(1).getBoard().getFloorLine().addTiles(List.of(PlayerTile.getInstance(), TileColor.BLUE));
    //     List<ScoreUpdate> scoreUpdates = new ArrayList<>();
    //     PlayerData lastPlayer = new PlayerData();
    //     lastPlayer.setName(game.getTurnOrder().get(game.getTurnOrder().size() - 1).getName());
    //     lastPlayer.setIdentifier(game.getTurnOrder().get(game.getTurnOrder().size() - 1).getIdentifier());
    //     assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    //     PlayerData nextPlayer = getTurnOrderChange();

    //     RoundUpdate roundUpdate = (RoundUpdate) game.performMoveMiddlePatternLine(2, TileColor.BLACK);

    //     assertEquals(2, game.getRound());
    //     assertScoreUpdates(scoreUpdates, roundUpdate);
    //     assertEquals(0, game.getBox().size());
    //     assertEquals(0, game.getMiddle().getAllTiles().size());
    //     assertEquals(GamePhase.WALL_TILLING, game.getGamePhase());
    //     assertEquals(nextPlayer.getIdentifier(), roundUpdate.getMove().getNextPlayer().getIdentifier());
    //     assertEquals(lastPlayer.getIdentifier(), roundUpdate.getMove().getPlayer().getIdentifier());
    //     assertGameEnded(players);
    // }

    // private void assertGameEnded(List<Player> players) {
    //     incrementRound();
    //     game.startGame();
    //     Player p = players.get(0);
    //     p.getBoard().getWall().addTile(0, TileColor.RED);
    //     p.getBoard().getWall().addTile(0, TileColor.CYAN);
    //     p.getBoard().getWall().addTile(0, TileColor.YELLOW);
    //     p.getBoard().getWall().addTile(0, TileColor.BLUE);
    //     p.getBoard().getWall().addTile(0, TileColor.BLACK);
    //     game.performMoveMiddleFloorLine(TileColor.BLACK);
    //     assertTrue(p.getBoard().hasFulfilledEndCondition());
    //     assertFalse(game.isPlaying());
    //     assertEquals(GamePhase.FINISHED, game.getGamePhase());
    // }

    private void assertScoreUpdates(List<ScoreUpdate> scoreUpdates, RoundUpdate roundUpdate) {
        game.getPlayers().forEach(player -> {
            List<Integer> completedRows = player.getBoard().getPatternLine().completedRows();
            assertEquals(List.of(TileColor.BLACK,TileColor.RED,TileColor.RED, PlayerTile.getInstance()), game.getBox());
            ScoreUpdate scoreUpdatePlayer = roundUpdate.getScoreUpdates().stream().filter(scoreUpdate -> scoreUpdate.getPlayer().getIdentifier() == player.getIdentifier()).findFirst().orElseThrow();
            assertEquals(player.getIdentifier(), scoreUpdatePlayer.getPlayer().getIdentifier());
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
        game.startGame();
        List<List<TileColor>> factoryTiles = new ArrayList<>();
        // EndGameUpdate endedGame = endingGame();
        player2.getBoard().addFinalScores();
        player1.getBoard().addFinalScores();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        //    assertEquals(GamePhase.FINISHED, game.getGamePhase());
        //     assertEquals(10, player2.getBoard().getScore());
        // assertUpdateFinalScores(endedGame);
        //      assertFalse(game.isPlaying());
    }

    private EndGameUpdate endingGame() {
        player1.getBoard().getWall().addTile(1, TileColor.BLUE);
//        player1.getBoard().getWall().addTile(0, TileColor.BLUE);
        player1.getBoard().getWall().addTile(0, TileColor.RED);
        player1.getBoard().getWall().addTile(0, TileColor.CYAN);
        player1.getBoard().getWall().addTile(0, TileColor.BLACK);
        player1.getBoard().getWall().addTile(0, TileColor.YELLOW);
        player1.getBoard().getWall().addTile(2, TileColor.BLACK);
        player1.getBoard().getWall().addTile(3, TileColor.RED);
        player1.getBoard().getWall().addTile(4, TileColor.YELLOW);
        player2.getBoard().getWall().addTile(0, TileColor.BLUE);
        player2.getBoard().getWall().addTile(1, TileColor.BLUE);
        player2.getBoard().getWall().addTile(2, TileColor.BLUE);
        player2.getBoard().getWall().addTile(3, TileColor.BLUE);
        player2.getBoard().getWall().addTile(4, TileColor.BLUE);


        return (EndGameUpdate) restartingRound();
    }

//    private EndGameUpdate incrementRounds() {


//    private void assertUpdateFinalScores(EndGameUpdate endedGame) {
//        assertEquals(game.getPlayers().size(), endedGame.getPlayerBoards().size());
//        game.getPlayers().forEach(player -> {
//            PlayerBoardState playerBoardState = endedGame.getPlayerBoards().stream().filter(ps -> ps.getPlayer().getIdentifier() == player.getIdentifier()).findFirst().orElseThrow();
//            assertEquals(player.getBoard().getScore(), playerBoardState.getScore());
//            assertEquals(player.getBoard().getFloorLine().getCopyTiles(), playerBoardState.getFloorLine());
//            assertEquals(player.getBoard().getPatternLine().getCopyTable(), playerBoardState.getPatternLine());
//            assertEquals(player.getBoard().getWall().getCopyTable(), playerBoardState.getWall());
//        });
//
//    }


    // @Test
    // public void testIsCurrentPlayer() {
    //     Player randomPlayer = new Player("Cristiano");
    //     PlayerData playerData1 = new PlayerData();
    //     playerData1.setName(player1.getName());
    //     playerData1.setIdentifier(player1.getIdentifier());
    //     PlayerData playerData2 = new PlayerData();
    //     playerData2.setName(player2.getName());
    //     playerData2.setIdentifier(player2.getIdentifier());
    //     PlayerData playerDataRandom = new PlayerData();
    //     playerDataRandom.setName(randomPlayer.getName());
    //     playerDataRandom.setIdentifier(randomPlayer.getIdentifier());
    //     assertTrue(game.isCurrentPlayer(playerData1));
    //     assertTrue(game.isCurrentPlayer(playerData2));
    //     assertFalse(game.isCurrentPlayer(playerDataRandom));
    // }

    @Test
    public void testAddPlayer() {
        Player player = new Player("Marcelo");
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

    private void prepareSingleMove() {
        game.startGame();
        game.getFactories().forEach(factory -> factory.popAllTiles());
        List<TileColor> tiles = List.of(TileColor.RED, TileColor.RED, TileColor.BLUE);
        game.getFactories().get(1).addTiles(tiles);
        game.getMiddle().popAllTiles();
        game.getMiddle().addTiles(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN));
    }

    private void compareActionsSorted(List<Action> a, List<Action> b) {
        assertEquals(a.size(), b.size());
        for (int i = 0; i < a.size(); i++) {
            assertEquals(a.get(i), b.get(i));
            // Action aAc = a.get(i);
            // Action bAc = b.get(i);

            // assertEquals(aAc.getType(), bAc.getType());
            // assertEquals(aAc.getColor(), bAc.getColor());
            // assertEquals(aAc.getAmount(), bAc.getAmount());

            // if (aAc.getFrom() == null) {
            //     assertTrue(bAc.getFrom() == null);
            // } else {
            //     assertEquals(aAc.getFrom().getType(), bAc.getFrom().getType());
            //     assertEquals(aAc.getFrom().getIndex(), bAc.getFrom().getIndex());
            // }

            // if (aAc.getTo() == null) {
            //     assertTrue(bAc.getTo() == null);
            // } else {
            //     assertEquals(aAc.getTo().getType(), bAc.getTo().getType());
            //     assertEquals(aAc.getTo().getIndex(), bAc.getTo().getIndex());
            // }

        }
    }

    @Test
    public void testPerformMoveFactoryPatternLine() {
        prepareSingleMove();
        MoveUpdate update = (MoveUpdate) game.performMoveFactoryPatternLine(1, 2, TileColor.RED);
        List<Action> steps = new ArrayList<>();
        steps.add(new Action(ActionType.MOVE, TileColor.RED, 2, new Location(LocationType.FACTORY, 1), new Location(LocationType.PATTERN_LINE, 2)));
        steps.add(new Action(ActionType.MOVE, TileColor.BLUE, 1, new Location(LocationType.FACTORY, 1), new Location(LocationType.MIDDLE, 0)));
        assertEquals(steps.get(0).getColor(), update.getSteps().get(0).getColor());
        compareActionsSorted(steps, update.getSteps());

        assertEquals(update.getPlayer().getIdentifier(), playerData1.getIdentifier());
        assertEquals(update.getNextPlayer().getIdentifier(), playerData2.getIdentifier());
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        prepareSingleMove();
        MoveUpdate moveUpdate = (MoveUpdate) game.performMoveFactoryFloorLine(1, TileColor.BLUE);
        List<Action> steps = new ArrayList<>();
        steps.add(new Action(ActionType.MOVE, TileColor.BLUE, 1, new Location(LocationType.FACTORY, 1), new Location(LocationType.FLOOR_LINE, 0)));
        steps.add(new Action(ActionType.MOVE, TileColor.RED, 2, new Location(LocationType.FACTORY, 1), new Location(LocationType.MIDDLE, 0)));
        compareActionsSorted(steps, moveUpdate.getSteps());
        assertEquals(playerData1.getIdentifier(), moveUpdate.getPlayer().getIdentifier());
        assertEquals(moveUpdate.getNextPlayer().getIdentifier(), playerData2.getIdentifier());
    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        prepareSingleMove();
        MoveUpdate moveUpdate = (MoveUpdate) game.performMoveMiddlePatternLine(2, TileColor.YELLOW);
        List<Action> steps = new ArrayList<>();
        steps.add(new Action(ActionType.MOVE, TileColor.YELLOW, 2, new Location(LocationType.MIDDLE, 0), new Location(LocationType.PATTERN_LINE, 2)));
        compareActionsSorted(steps, moveUpdate.getSteps());
        assertEquals(moveUpdate.getPlayer().getIdentifier(), playerData1.getIdentifier());
        assertEquals(moveUpdate.getNextPlayer().getIdentifier(), playerData2.getIdentifier());
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        prepareSingleMove();
        MoveUpdate moveUpdate = (MoveUpdate) game.performMoveMiddleFloorLine(TileColor.YELLOW);
        List<Action> steps = new ArrayList<>();
        steps.add(new Action(ActionType.MOVE, TileColor.YELLOW, 2, new Location(LocationType.MIDDLE, 0), new Location(LocationType.FLOOR_LINE, 0)));
        compareActionsSorted(steps, moveUpdate.getSteps());
        assertEquals(moveUpdate.getPlayer().getIdentifier(), playerData1.getIdentifier());
        assertEquals(moveUpdate.getNextPlayer().getIdentifier(), playerData2.getIdentifier());

    }

    @Test
    public void testIsValidMoveFactoryPatternLine() {
        prepareSingleMove();
        assertTrue(game.isValidMoveFactoryPatternLine(1, 2, TileColor.RED));
        assertFalse(game.isValidMoveFactoryPatternLine(1, 2, TileColor.BLACK));
    }

    @Test
    public void testIsValidMoveFactoryFloorLine() {
        prepareSingleMove();
        assertTrue(game.isValidMoveFactoryFloorLine(1, TileColor.RED));
        assertFalse(game.isValidMoveFactoryFloorLine(1, TileColor.BLACK));

    }

    @Test
    public void testIsValidMoveMiddlePatternLine() {
        prepareSingleMove();
        assertTrue(game.isValidMoveMiddlePatternLine(2, TileColor.CYAN));
        assertFalse(game.isValidMoveMiddlePatternLine(2, TileColor.BLACK));
    }

    @Test
    public void testIsValidMoveMiddleFloorLine() {
        prepareSingleMove();
        assertTrue(game.isValidMoveMiddleFloorLine(TileColor.CYAN));
        assertFalse(game.isValidMoveMiddleFloorLine(TileColor.BLACK));
    }

}

