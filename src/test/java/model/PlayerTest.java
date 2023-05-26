package model;

import dataobjects.PlayerBoardState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    static Player player;
    static String name = "Trump";
    static int counter = 0;
    @BeforeEach
    void setUp(){
        player = new Player(name);
        assertEquals(++counter, player.getIdentifier());
    }
    @Test
    void testGetIdentifier() {
        String name2 = "Scholz";
        Player player2 = new Player(name2);
        assertEquals(++counter, player2.getIdentifier());
    }

    @Test
    public void testDefaultConstructor() {
        Player player2 = new Player();
        assertNotNull(player2.getName());
        assertNotNull(player2.getBoard());
        assertEquals("Player"+player2.getIdentifier(),player2.getName());
        assertEquals(++counter, player2.getIdentifier());
    }

    @Test
    public void testConstructorWithName() {
        assertEquals(name, player.getName());
        assertTrue(player.getBoard().canAddTypePatternLine(2, Color.RED));
        assertInstanceOf(PlayerBoardState.class,player.getBoard().toObject());
        assertEquals(counter, player.getIdentifier());
    }

    @Test
    public void testGetName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void testSetName() {
        String name2 = "Messi";
        player.setName(name2);
        assertEquals(name2, player.getName());
    }

    @Test
    public void testGetPlayerBoard() {
        assertTrue(player.getBoard().canAddTypePatternLine(2, Color.RED));
    }


}
