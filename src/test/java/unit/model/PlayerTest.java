package unit.model;

import model.TileColor;
import model.Player;
import model.PlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import messaging.dataobjects.PlayerBoardState;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Mock
    private PlayerBoard playerBoard;

    static Player player;
    static String name = "Trump";
    static int counter = 0;

    @BeforeEach
    void setUp() {
        player = new Player(name);
        counter = player.getIdentifier();
        assertEquals(counter, player.getIdentifier());
    }

    @Test
    void testGetIdentifier() {
        String name2 = "Scholz";
        Player player2 = new Player(name2);
        counter = player.getIdentifier();
        assertEquals(counter+1, player2.getIdentifier());
    }

    @Test
    public void testDefaultConstructor() {
        Player player2 = new Player();
        assertNotNull(player2.getName());
        assertNotNull(player2.getBoard());
        assertEquals("Player" + player2.getIdentifier(), player2.getName());
        assertEquals(++counter, player2.getIdentifier());
    }

    @Test
    public void testConstructorWithName() {
        assertEquals(name, player.getName());
        assertTrue(player.getBoard().canAddTypePatternLine(3, TileColor.RED));
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
        assertTrue(player.getBoard().canAddTypePatternLine(3, TileColor.RED));
    }


}
