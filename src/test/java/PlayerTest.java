import model.Color;
import model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    static Player player;
    static String name = "Trump";

    @Test
    public void testDefaultConstructor() {
        Player playerX = new Player();
        assertNull(playerX.getName());
        assertNotNull(playerX.getBoard());
    }

    @Test
    public void testConstructorWithName() {
        Player playerY = new Player(name);
        assertEquals(name, playerY.getName());
        assertNotNull(playerY.getBoard());
    }

    @Test
    public void testPlayer() {
        Player player2 = new Player("Fabri Fibra");
        assertEquals("Fabri Fibra", player2.getName());
        assertEquals(1, player2.getIdentifier());
        assertFalse(player2.getBoard().canAddTypePattern(2, Color.RED));
    }

    @Test
    public void testGetName() {
        player = new Player(name);
        assertEquals(name, player.getName());
    }

    @Test
    public void testSetName() {
        player = new Player(name);
        String name2 = "Messi";
        player.setName(name2);
        assertEquals(name2, player.getName());
    }

    @Test
    public void testGetPlayerBoard() {
        player = new Player();
        assertFalse(player.getBoard().canAddTypePattern(2, Color.RED));
    }

    @Test
    public void testGetIdentifier() {
        String name2 = "Scholz";
        player = new Player(name);
        Player player2 = new Player(name2);
        assertEquals(1, player.getIdentifier());
        assertEquals(2, player2.getIdentifier());
    }
}
