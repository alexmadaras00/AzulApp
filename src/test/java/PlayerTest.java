import model.Color;
import model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTest {
    static Player player;
    static String name = "Trump";

    @BeforeAll
    static void setUp() {
        player = new Player();
        player.getBoard().canAddTypePattern(2, Color.RED);
        player.setName(name);
        assertEquals(1, player.getIdentifier());
    }
    @Test
    public void testPlayer() {
        Player player2 = new Player("Fabri Fibra");
        assertEquals("Fabri Fibra", player2.getName());
        assertEquals(2, player2.getIdentifier());
    }

    @Test
    public void testGetName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void testSetName() {
        String name2 = "Messi";
        player.setName(name2);
        assertEquals(name2,player.getName());
    }
    @Test
    public void testGetPlayerBoard(){
        assertFalse(player.getBoard().canAddTypePattern(2, Color.RED));
    }
    @Test
    public void testGetIdentifier(){
        assertEquals(1,player.getIdentifier());
    }
}
