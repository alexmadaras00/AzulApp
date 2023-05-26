package model;

import dataobjects.GameState;
import dataobjects.PlayerData;
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
    GamePhase gamePhase;
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
        for (int i = 0; i < 5; i++) {
            Factory factory = new Factory();
            factories.add(factory);
        }
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
        int numberOfPlayers = game.getPlayers().size();
        assertFalse(game.isPlaying());
        assertEquals(0, game.getRound());
        assertEquals(0, game.getMiddle().getAllTiles().size());
        assertEquals(game.getTurnOrder().size(), game.getPlayers().size());
        assertEquals(0, game.getBox().size());
        assertEquals(0, game.getBag().getTiles().size());
        game.startGame();
        assertTrue(numberOfPlayers >= 2 && numberOfPlayers <= 4);
        if (numberOfPlayers == 2) assertEquals(5, game.getFactories().size());
        else if (numberOfPlayers == 3) assertEquals(7, game.getFactories().size());
        else assertEquals(9, game.getFactories().size());
        assertEquals(game.getTurnOrder().size(), game.getPlayers().size());
        assertEquals(0, game.getBox().size());
        assertEquals(100, game.getBag().getTiles().size());
        assertEquals(0, game.getRound());
        assertEquals(0, game.getMiddle().getAllTiles().size());
        assertEquals(0, game.getBox().size());
        assertTrue(game.isPlaying());
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
    }

    @Test
    public void testTerminateGame() {
        game.startGame();
        assertTrue(game.isPlaying());
        assertEquals(GamePhase.PREPARING_ROUND, game.getGamePhase());
        game.terminateGame();
        assertFalse(game.isPlaying());
        assertEquals(GamePhase.TERMINATED, game.getGamePhase());
    }

    @Test
    public void testIsCurrentlyPlayer() {
        Player randomPlayer = new Player("Cristiano");
        assertTrue(game.isCurrentPlayer(player1.getIdentifier()));
        assertTrue(game.isCurrentPlayer(player2.getIdentifier()));
        assertFalse(game.isCurrentPlayer(randomPlayer.getIdentifier()));
    }

    @Test
    public void testIsMoveValid() {

    }

    @Test
    public void testPerformMove() {

    }

    @Test
    public void testToObject() {
        GameState gameState = new GameState();
        List<List<Tile>> factoriesTiles = new ArrayList<>();
        List<Tile> middleTiles = game.getMiddle().getAllTiles();
        for (Factory f : game.getFactories()) {
            factoriesTiles.add(f.getAllTiles());
        }
        assertEquals(factoriesTiles, gameState.getFactories());
        assertEquals(middleTiles, gameState.getMiddle());
        assertNotNull(gameState.getCurrentPlayer());
        assertTrue(isCurrentPlayerInGame(gameState.getCurrentPlayer(), game.getPlayers()));
        assertEquals(game.getPlayers().size(), gameState.getPlayerBoards().size());
    }

    private boolean isCurrentPlayerInGame(PlayerData playerData, List<Player> players) {
        for (Player p : players)
            if (playerData.getIdentifier() == p.getIdentifier())
                return true;
        return false;
    }

}

