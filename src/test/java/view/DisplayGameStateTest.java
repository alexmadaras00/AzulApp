// package view;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.api.FxToolkit;

// import model.Wall;

// public class DisplayGameStateTest {
//     private DisplayGameState gameState;
//     // static {

//     //     System.setProperty("java.awt.headless", "false");
//     // }

//     // @BeforeAll
//     // public static void setupSpec() throws Exception {
//     //     if (Boolean.getBoolean("headless")) {
//     //         System.setProperty("testfx.robot", "glass");
//     //         System.setProperty("testfx.headless", "true");
//     //         System.setProperty("prism.order", "sw");
//     //         System.setProperty("prism.text", "t2k");
//     //         System.setProperty("java.awt.headless", "true");
//     //     }
//     //     FxToolkit.registerPrimaryStage();
//     // }

//     @BeforeEach
//     public void setUp() {
//         gameState = new DisplayGameState();
//         gameState.setWallPattern(Wall.wallPattern());
//         gameState.addPlayer(2, "Bob");
//     }

//     @Test
//     public void testEmptyOutputNoErrors() {
//         assertDoesNotThrow(() -> gameState.toString(), "Tee print does not work correctly");
//     }

//     @Test
//     public void testGetPlayer() {
//         DisplayPlayer player1 = gameState.getPlayer(2);
//         DisplayPlayer player2 = gameState.getPlayer(1);
//         assertEquals("Bob", player1.name);
//         assertEquals(null, player2);

//     }

//     @Test
//     public void testAddPlayer() {
//         DisplayPlayer player1 = gameState.getPlayer(2);
//         gameState.addPlayer(1, "Alice");
//         DisplayPlayer player2 = gameState.getPlayer(1);
//         assertEquals("Bob", player1.name);
//         assertEquals("Alice", player2.name);
//     }

//     @Test
//     public void testAddFactory() {
//         gameState.addFactory(0);
//         assertEquals(1, gameState.factories.size());
//         assertEquals(0, gameState.factories.get(0).id);
//     }

//     @Test
//     public void testGetFGactory() {
//         gameState.addFactory(0);
//         DisplayFactory factory1 = gameState.getFactory(0);
//         DisplayFactory factory2 = gameState.getFactory(2);
//         assertEquals(0, factory1.id);
//         assertEquals(null, factory2);

//     }

//     @Test
//     public void testSetActivePlayer() {
//         gameState.setActivePlayer(2);
//         assertEquals(0, gameState.activePlayer);
//         assertEquals(gameState.getPlayer(2), gameState.players.get(gameState.activePlayer));
//     }
// }
