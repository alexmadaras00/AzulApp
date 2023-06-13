package integration.model;

import model.*;
import model.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ExceptionGameStart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
        assertEquals(0, game.getCenter().getAllTiles().size());
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
        game.startGame();
        assertPostConditionsStart();
        Game gameFailFewerPlayers = null;
        Game gameFailTooManyPlayers = null;
        try {
            Player p1 = new Player();
            p1.setName("Nano");
            gameFailFewerPlayers = new Game();
            game.addPlayer(player1);
            gameFailFewerPlayers.startGame();
            assertTrue(gameFailFewerPlayers.getPlayers().size() < 2);
            assertFalse(gameFailFewerPlayers.canStartGame());
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
            assertFalse(gameFailTooManyPlayers.canStartGame());
            fail("Expected exception not thrown");
        } catch (ExceptionGameStart e) {
            assertEquals("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.", e.getMessage());
            assert gameFailTooManyPlayers != null;
            assertFalse(gameFailTooManyPlayers.isPlaying());
            assertEquals(GamePhase.INITIALIZED, gameFailTooManyPlayers.getGamePhase());
        }
    }

    private void assertPostConditionsStart() {
        factories = game.getFactories();
        int numberOfPlayers = game.getPlayers().size();
        if (numberOfPlayers == 2) assertEquals(5, game.getFactories().size());
        else if (numberOfPlayers == 3) assertEquals(7, game.getFactories().size());
        else assertEquals(9, game.getFactories().size());
        assertEquals(game.getTurnOrder().size(), game.getPlayers().size());
        assertEquals(1, game.getCenter().getAllTiles().size());
        assertEquals(0, game.getBox().size());
        assertEquals(80, game.getBag().getTiles().size());
        PlayerTile startingTile = PlayerTile.getInstance();
        assertTrue(game.getCenter().getAllTiles().contains(startingTile));
        assertTrue(game.isPlaying());
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        assertEquals(List.of(PlayerTile.getInstance()), game.getCenter().getAllTiles());
        assertEquals(1, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    @Test
    public void testRestartRound() {
        game.startGame();
        factories = game.getFactories();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        player1 = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);

        assertEquals(0, countFactoriesNotFull);
        restartingRound();
        assertFactoryUpdates();
        game.getPlayers().forEach(player -> {
            List<Integer> completedRows = player.getBoard().getPatternLine().completedRows();
            assertEquals(new HashSet<Tile>(List.of(TileColor.BLACK,TileColor.RED)), new HashSet<Tile>(game.getBox()));
            assertEquals(0, player.getBoard().getFloorLine().getCopyTiles().size());
            completedRows.forEach(completedRow -> assertEquals(0, player.getBoard().getPatternLine().getCopyTable().get(completedRow).size()));
        });
        assertEquals(60, game.getBag().getTiles().size());
        assertEquals(player2.getIdentifier(), game.getCurrentPlayer());
        assertEquals(2, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    private void restartingRound() {
        factories = game.getFactories();
        factories.forEach(Factory::popAllTiles);
        game.getCenter().getAllTiles().clear();
        game.getCenter().addTiles(List.of(PlayerTile.getInstance()));
        factories.get(1).addTiles(List.of(TileColor.RED, TileColor.RED, TileColor.BLUE, TileColor.BLACK));

        // player 1 completes patternline 0
        assertEquals(player1.getIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveFactoryPatternLine(1,0,TileColor.BLUE));
        game.performMoveFactoryPatternLine(1, 0, TileColor.BLUE);
        // player 2 adds 2 red tiles to patternline 2
        assertEquals(player2.getIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveMiddlePatternLine(1, TileColor.RED));
        game.performMoveMiddlePatternLine(1, TileColor.RED);
        // check if player 2 got playertile
        assertTrue(player2.getBoard().getFloorLine().getCopyTiles().contains(PlayerTile.getInstance()));

        assertEquals(player1.getIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveMiddleFloorLine(TileColor.BLACK));

        game.performMoveMiddleFloorLine(TileColor.BLACK);
    }

    private void assertFactoryUpdates() {
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(0, countFactoriesNotFull);
    }

    @Test
    public void testTerminateGame() {
        game.startGame();
        assertTrue(game.isPlaying());
        assertFalse(game.getPlayers().get(0).getBoard().hasFulfilledEndCondition());
        game.getPlayers().forEach(p -> assertFalse(p.getBoard().hasFulfilledEndCondition()));
        game.terminateGame();
        assertFalse(game.isPlaying());
        assertEquals(GamePhase.TERMINATED, game.getGamePhase());
    }

    @Test
    public void testEndGame() {
        game.startGame();
        List<List<TileColor>> factoryTiles = new ArrayList<>();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        endingGame();
        assertEquals(GamePhase.FINISHED, game.getGamePhase());
        
        assertEquals(10, player2.getBoard().getScore());
        assertEquals(6, player1.getBoard().getScore());
        assertEquals(List.of(player2.getIdentifier()), game.getWinners());
        assertFalse(game.isPlaying());
    }


    private void endingGame() {
        player1.getBoard().getWall().addTile(1, TileColor.BLUE);
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

        restartingRound();
    }

    @Test
    public void testAddPlayer() {
        Player player = new Player("Marcelo");
        game.addPlayer(player);
        assertTrue(game.getPlayers().contains(player));
        assertTrue(game.getTurnOrder().contains(player));
    }

    @Test
    public void testCanStartGame() {
        assertTrue(game.canStartGame());
        Game gameInvalidPlayers = new Game();
        gameInvalidPlayers.addPlayer(new Player("Bogdan"));
        assertFalse(gameInvalidPlayers.canStartGame());
    }

    private void prepareSingleMove() {
        game.startGame();
        game.getFactories().forEach(factory -> factory.popAllTiles());
        List<TileColor> tiles = List.of(TileColor.RED, TileColor.RED, TileColor.BLUE);
        game.getFactories().get(1).addTiles(tiles);
        game.getCenter().popAllTiles();
        game.getCenter().addTiles(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN));
    }

    @Test
    public void testPerformMoveFactoryPatternLine() {
        prepareSingleMove();
        game.performMoveFactoryPatternLine(1, 2, TileColor.RED);
        assertEquals(List.of(TileColor.RED, TileColor.RED), player1.getBoard().getPatternLine().getCopyTable().get(2));
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN,TileColor.BLUE), game.getCenter().getAllTiles());
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        prepareSingleMove();
        game.performMoveFactoryFloorLine(1, TileColor.BLUE);
        assertEquals(List.of(TileColor.BLUE), player1.getBoard().getFloorLine().getCopyTiles());
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN,TileColor.RED,TileColor.RED), game.getCenter().getAllTiles());

    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        prepareSingleMove();
        game.performMoveMiddlePatternLine(2, TileColor.YELLOW);
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW), player1.getBoard().getPatternLine().getCopyTable().get(2));
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        prepareSingleMove();
        game.performMoveMiddleFloorLine(TileColor.YELLOW);
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW), player1.getBoard().getFloorLine().getCopyTiles());
    }

    @Test
    public void testFirstMoveMiddle() {
        prepareSingleMove();
        game.getCenter().addTiles(List.of(PlayerTile.getInstance()));
        game.performMoveMiddleFloorLine(TileColor.YELLOW);
        assertEquals(List.of(PlayerTile.getInstance(), TileColor.YELLOW, TileColor.YELLOW), player1.getBoard().getFloorLine().getCopyTiles());
    }

    @Test
    public void testTurnQueue() {
        assertEquals(player1.getIdentifier(), game.getCurrentPlayer());
        prepareSingleMove();
        game.performMoveMiddlePatternLine(2, TileColor.YELLOW);
        assertEquals(player2.getIdentifier(), game.getCurrentPlayer());
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

    @Test
    public void testGetFactoryCount() {
        game.startGame();
        assertEquals(5, game.getFactoryCount());
    }

    @Test
    public void testGetFactory() {
        for (int i = 0; i < factories.size(); i++) {
            assertEquals(factories.get(i).getAllTiles(), List.of(game.getFactory(i)));
        }
    }

    @Test
    public void testGetMiddle() {
        game.getCenter().addTiles(List.of(TileColor.RED));
        assertEquals(game.getCenter().getAllTiles(), game.getMiddle());
    }

    @Test
    public void testGetPlayerList() {
        assertEquals(List.of(player1.getIdentifier(), player2.getIdentifier()), game.getPlayerList());
    }

    @Test
    public void testGetFloorLine() {
        player1.getBoard().getFloorLine().addTiles(List.of(TileColor.YELLOW, TileColor.YELLOW));
        assertEquals(TileColor.YELLOW, game.getFloorLine(player1.getIdentifier())[0]);
        assertEquals(TileColor.YELLOW, game.getFloorLine(player1.getIdentifier())[1]);
    }

    @Test
    public void testGetWall() {
        player1.getBoard().getWall().addTile(0, TileColor.BLUE);
        assertEquals(TileColor.BLUE, game.getWall(player1.getIdentifier(),0,0));
        assertEquals(null, game.getWall(player1.getIdentifier(),1,1));
    }

    @Test
    public void testGetPatternLine() {
        player1.getBoard().getPatternLine().addTiles(1, List.of(TileColor.BLUE));
        assertEquals(null, game.getPatternLine(player1.getIdentifier(),1)[0]);
        assertEquals(TileColor.BLUE, game.getPatternLine(player1.getIdentifier(),1)[1]);
    }
}

