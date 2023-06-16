package integration.model;

import model.*;
import model.factory.FactoryInterface;
import model.factory.FactoryTwinteamWrapper;
import model.factory.TwinteamFactoryCreator;
import shared.PlayerTile;
import shared.Tile;
import shared.TileColor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GameProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    static List<FactoryInterface> factories;
    static List<PlayerBoard> playerBoards;
    static PlayerBoard playerBoard1;
    static PlayerBoard playerBoard2;
    List<List<TileColor>> factoryTiles;
    Game game;
    GameProxy gameProxy;

    @BeforeEach
    public void init() {
        game = new Game();
        gameProxy = new GameProxy();
        gameProxy.setProxy(game);
        factories = new ArrayList<>();
        assertEquals(0, game.getPlayers().size());
        assertEquals(0, game.getTurnOrder().size());
        assertEquals(0, game.getBox().size());
        game.addPlayer("Boris");
        game.addPlayer("Giani");
        playerBoard1 = game.getPlayerBoards().get(0);
        playerBoard2 = game.getPlayerBoards().get(1);
        factoryTiles = new ArrayList<>();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, game.getCenter().getAllTiles().size());
        assertEquals(0, game.getFactories().size());
        assertEquals(0, game.getRound());
        assertEquals(game.getPlayerBoards(), game.getTurnOrder());
        assertEquals(0, game.getBag().getTiles().size());
        assertFalse(game.isPlaying());
        assertFalse(gameProxy.isPlaying());
        Assertions.assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
    }

    @Test
    public void testStartInitialRound() {
        playerBoards = game.getPlayerBoards();
        assertEquals(GamePhase.INITIALIZED, game.getGamePhase());
        game.startGame();
        assertPostConditionsStart();
        Game gameFailFewerPlayers = null;
        Game gameFailTooManyPlayers = null;
        gameFailFewerPlayers = new Game();
        game.addPlayer("Nano");
        gameFailFewerPlayers.startGame();
        assertTrue(gameFailFewerPlayers.getPlayers().size() < 2);
        assertFalse(gameFailFewerPlayers.canStartGame());
        assert gameFailFewerPlayers != null;
        assertFalse(gameFailFewerPlayers.isPlaying());
        assertEquals(GamePhase.INITIALIZED, gameFailFewerPlayers.getGamePhase());

        gameFailTooManyPlayers = new Game();
        gameFailTooManyPlayers.addPlayer("Nano");
        gameFailTooManyPlayers.addPlayer("Miguel");
        gameFailTooManyPlayers.addPlayer("Santo");
        gameFailTooManyPlayers.addPlayer("Matei");
        gameFailTooManyPlayers.addPlayer("Rutter");
        gameFailTooManyPlayers.startGame();
        assertTrue(gameFailTooManyPlayers.getPlayers().size() > 4);
        assertFalse(gameFailTooManyPlayers.canStartGame());
        assert gameFailTooManyPlayers != null;
        assertFalse(gameFailTooManyPlayers.isPlaying());
        assertEquals(GamePhase.INITIALIZED, gameFailTooManyPlayers.getGamePhase());
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
        assertEquals(game.getFactories().get(0).getAllTiles(), Arrays.asList(game.getFactory(0)));
        assertEquals(game.getFactories().get(0).getAllTiles(), Arrays.asList(gameProxy.getFactory(0)));
        assertEquals(List.of(PlayerTile.getInstance()), game.getCenter().getAllTiles());
        assertEquals(1, game.getRound());
        assertEquals(1, gameProxy.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        assertEquals("Boris", game.getName(game.getPlayers().get(0).getIdentifier()));
        assertEquals("Giani", gameProxy.getName(playerBoard2.getPlayerIdentifier()));
    }

    @Test
    public void testRestartRound() {
        game.startGame();
        factories = game.getFactories();
        game.getFactories().forEach(factory -> factoryTiles.add(factory.getAllTiles()));
        int countFactoriesNotFull = (int) game.getFactories().stream().filter(factory -> factory.getAllTiles().size() < 4).count();
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        game.getFactories().forEach(factory -> assertEquals(4, factory.getAllTiles().size()));
        playerBoard1 = game.getPlayerBoards().get(0);
        playerBoard2 = game.getPlayerBoards().get(1);

        assertEquals(0, countFactoriesNotFull);
        restartingRound();
        assertFactoryUpdates();
        game.getPlayerBoards().forEach(playerBoard -> {
            List<Integer> completedRows = playerBoard.getPatternLine().completedRows();
            assertEquals(new HashSet<Tile>(List.of(TileColor.BLACK, TileColor.RED)), new HashSet<Tile>(game.getBox()));
            assertEquals(0, playerBoard.getFloorLine().getCopyTiles().size());
            completedRows.forEach(completedRow -> assertEquals(0, playerBoard.getPatternLine().getCopyTable().get(completedRow).size()));
        });
        assertEquals(60, game.getBag().getTiles().size());
        assertEquals(playerBoard2.getPlayerIdentifier(), game.getCurrentPlayer());
        assertEquals(playerBoard2.getPlayerIdentifier(), gameProxy.getCurrentPlayer());
        assertEquals(2, game.getRound());
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
    }

    private void restartingRound() {
        factories = game.getFactories();
        factories.forEach(FactoryInterface::popAllTiles);
        game.getCenter().getAllTiles().clear();
        game.getCenter().addTiles(List.of(PlayerTile.getInstance()));
        factories.get(1).addTiles(List.of(TileColor.RED, TileColor.RED, TileColor.BLUE, TileColor.BLACK));

        // player 1 completes patternline 0
        assertEquals(playerBoard1.getPlayerIdentifier(), game.getCurrentPlayer());
        assertEquals(game.getTurnOrder().get(0).getPlayerIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveFactoryPatternLine(1, 0, TileColor.BLUE));
        game.performMoveFactoryPatternLine(1, 0, TileColor.BLUE);
        // player 2 adds 2 red tiles to patternline 2
        assertEquals(playerBoard2.getPlayerIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveMiddlePatternLine(1, TileColor.RED));
        game.performMoveMiddlePatternLine(1, TileColor.RED);
        // check if player 2 got playertile
        assertTrue(playerBoard2.getFloorLine().getCopyTiles().contains(PlayerTile.getInstance()));

        assertEquals(playerBoard1.getPlayerIdentifier(), game.getCurrentPlayer());
        assertEquals(game.getTurnOrder().get(0).getPlayerIdentifier(), game.getCurrentPlayer());
        assertTrue(game.isValidMoveMiddleFloorLine(TileColor.BLACK));

        game.performMoveMiddleFloorLine(TileColor.BLACK);
    }

    @Test
    public void testRestartRoundBagTooFewTiles() {
        game.startGame();
        initializeBag();
        game.performMoveFactoryPatternLine(1, 3, TileColor.RED);
        game.performMoveMiddlePatternLine(3, TileColor.BLUE);
        game.performMoveMiddlePatternLine(4, TileColor.BLACK);
        assertEquals(GamePhase.FACTORY_OFFER, game.getGamePhase());
        assertEquals(0, game.getBag().getTiles().size());
        assertEquals(0, game.getBox().size());

    }

    private void initializeBag() {
        game.getBox().add(TileColor.BLUE);
        game.getBox().add(TileColor.BLUE);
        game.getBox().add(TileColor.BLUE);
        game.getBox().add(TileColor.RED);
        game.getBox().add(TileColor.YELLOW);
        game.getBox().add(TileColor.YELLOW);
        game.getBox().add(TileColor.YELLOW);
        game.getBox().add(TileColor.YELLOW);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBox().add(TileColor.BLACK);
        game.getBag().getTiles().clear();
        game.getBag().addTiles(List.of(TileColor.RED, TileColor.BLUE, TileColor.CYAN));
        factories = game.getFactories();
        factories.forEach(FactoryInterface::popAllTiles);
        game.getCenter().getAllTiles().clear();
        factories.get(1).addTiles(List.of(TileColor.RED, TileColor.RED, TileColor.BLUE, TileColor.BLACK));
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
        assertFalse(game.getPlayerBoards().get(0).hasFulfilledEndCondition());
        game.getPlayerBoards().forEach(p -> assertFalse(p.hasFulfilledEndCondition()));
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

        assertEquals(10, playerBoard2.getScore());
        assertEquals(6, playerBoard1.getScore());
        assertEquals(playerBoard1.getScore(), gameProxy.getScore(playerBoard1.getPlayerIdentifier()));
        assertEquals(List.of(playerBoard2.getPlayerIdentifier()), game.getWinners());
        assertEquals(List.of(playerBoard2.getPlayerIdentifier()), gameProxy.getWinners());
        assertFalse(game.isPlaying());
    }


    private void endingGame() {
        playerBoard1.getWall().addTile(1, TileColor.BLUE);
        playerBoard1.getWall().addTile(0, TileColor.RED);
        playerBoard1.getWall().addTile(0, TileColor.CYAN);
        playerBoard1.getWall().addTile(0, TileColor.BLACK);
        playerBoard1.getWall().addTile(0, TileColor.YELLOW);
        playerBoard1.getWall().addTile(2, TileColor.BLACK);
        playerBoard1.getWall().addTile(3, TileColor.RED);
        playerBoard1.getWall().addTile(4, TileColor.YELLOW);
        playerBoard2.getWall().addTile(0, TileColor.BLUE);
        playerBoard2.getWall().addTile(1, TileColor.BLUE);
        playerBoard2.getWall().addTile(2, TileColor.BLUE);
        playerBoard2.getWall().addTile(3, TileColor.BLUE);
        playerBoard2.getWall().addTile(4, TileColor.BLUE);

        restartingRound();
    }

    @Test
    public void testAddPlayer() {
        game.addPlayer("Marcelo");
        Player player = game.getPlayers().get(2);
        PlayerBoard playerBoard = game.getPlayerBoards().get(2);
        assertTrue(game.getPlayers().contains(player));
        assertTrue(game.getTurnOrder().contains(playerBoard));
    }

    @Test
    public void testCanStartGame() {
        assertTrue(game.canStartGame());
        Game gameInvalidPlayers = new Game();
        gameInvalidPlayers.addPlayer("Bogdan");
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
        assertEquals(List.of(TileColor.RED, TileColor.RED), playerBoard1.getPatternLine().getCopyTable().get(2));
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN, TileColor.BLUE), game.getCenter().getAllTiles());
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        prepareSingleMove();
        game.performMoveFactoryFloorLine(1, TileColor.BLUE);
        assertEquals(List.of(TileColor.BLUE), playerBoard1.getFloorLine().getCopyTiles());
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW, TileColor.CYAN, TileColor.RED, TileColor.RED), game.getCenter().getAllTiles());

    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        prepareSingleMove();
        game.performMoveMiddlePatternLine(2, TileColor.YELLOW);
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW), playerBoard1.getPatternLine().getCopyTable().get(2));
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        prepareSingleMove();
        game.performMoveMiddleFloorLine(TileColor.YELLOW);
        assertEquals(List.of(TileColor.YELLOW, TileColor.YELLOW), playerBoard1.getFloorLine().getCopyTiles());
    }

    @Test
    public void testFirstMoveMiddle() {
        prepareSingleMove();
        game.getCenter().addTiles(List.of(PlayerTile.getInstance()));
        game.performMoveMiddleFloorLine(TileColor.YELLOW);
        assertEquals(List.of(PlayerTile.getInstance(), TileColor.YELLOW, TileColor.YELLOW), playerBoard1.getFloorLine().getCopyTiles());
    }

    @Test
    public void testTurnQueue() {
        assertEquals(playerBoard1.getPlayerIdentifier(), game.getCurrentPlayer());
        prepareSingleMove();
        game.performMoveMiddlePatternLine(2, TileColor.YELLOW);
        assertEquals(playerBoard2.getPlayerIdentifier(), game.getCurrentPlayer());
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
        assertEquals(5, gameProxy.getFactoryCount());
    }

    @Test
    public void testGetFactory() {
        for (int i = 0; i < factories.size(); i++) {
            assertEquals(factories.get(i).getAllTiles(), List.of(game.getFactory(i)));
            assertEquals(factories.get(i).getAllTiles(), List.of(gameProxy.getFactory(i)));
        }
    }

    @Test
    public void testGetMiddle() {
        game.getCenter().addTiles(List.of(TileColor.RED));
        assertEquals(game.getCenter().getAllTiles(), game.getMiddle());
        assertEquals(game.getCenter().getAllTiles(), gameProxy.getMiddle());
    }

    @Test
    public void testGetPlayerList() {
        assertEquals(List.of(playerBoard1.getPlayer(), playerBoard2.getPlayer()), game.getPlayerList());
        assertEquals(List.of(playerBoard1.getPlayer(), playerBoard2.getPlayer()), gameProxy.getPlayerList());
    }

    @Test
    public void testGetPlayerBoardList() {
        assertEquals(List.of(playerBoard1, playerBoard2), game.getPlayerBoardList());
        assertEquals(List.of(playerBoard1, playerBoard2), gameProxy.getPlayerBoardList());
    }


    @Test
    public void testGetPlayerByIdentifier() {
        Player nonExistentPlayer = new Player("Adrian");
        assertEquals(playerBoard1.getScore(), game.getScore(playerBoard1.getPlayerIdentifier()));
        assertEquals(playerBoard2.getScore(), game.getScore(playerBoard2.getPlayerIdentifier()));
        assertThrows(NullPointerException.class, ()-> game.getName(nonExistentPlayer.getIdentifier()));
    }

    @Test
    public void testGetFloorLine() {
        playerBoard1.getFloorLine().addTiles(List.of(TileColor.YELLOW, TileColor.YELLOW));
        assertEquals(TileColor.YELLOW, game.getFloorLine(playerBoard1.getPlayerIdentifier())[0]);
        assertEquals(TileColor.YELLOW, game.getFloorLine(playerBoard1.getPlayerIdentifier())[1]);
        assertEquals(TileColor.YELLOW, gameProxy.getFloorLine(playerBoard1.getPlayerIdentifier())[1]);
    }

    @Test
    public void testGetWall() {
        playerBoard1.getWall().addTile(0, TileColor.BLUE);
        assertEquals(TileColor.BLUE, game.getWall(playerBoard1.getPlayerIdentifier(), 0, 0));
        assertEquals(TileColor.BLUE, gameProxy.getWall(playerBoard1.getPlayerIdentifier(), 0, 0));
        assertEquals(null, game.getWall(playerBoard1.getPlayerIdentifier(), 1, 1));
    }

    @Test
    public void testGetPatternLine() {
        playerBoard1.getPatternLine().addTiles(1, List.of(TileColor.BLUE));
        assertEquals(null, game.getPatternLine(playerBoard1.getPlayerIdentifier(), 1)[0]);
        assertEquals(TileColor.BLUE, game.getPatternLine(playerBoard1.getPlayerIdentifier(), 1)[1]);
        assertEquals(TileColor.BLUE, gameProxy.getPatternLine(playerBoard1.getPlayerIdentifier(), 1)[1]);
    }

    @Test
    public void testSetTwinteamFactory() {
        game.useTwinteamFactory();
        game.addPlayer("a");
        game.addPlayer("a");
        game.startGame();
        assertEquals(true, game.getFactories().get(0) instanceof FactoryTwinteamWrapper);
    }
}

