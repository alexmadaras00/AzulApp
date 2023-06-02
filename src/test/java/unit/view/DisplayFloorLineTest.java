// package view;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;

// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import model.TileColor;
// import static org.testfx.api.FxToolkit.registerPrimaryStage;

// public class DisplayFloorLineTest {
//     private DisplayFloorLine displayFloorLine;
//     static {

//         System.setProperty("java.awt.headless", "false");
//     }
//     @BeforeAll
//     public static void setupSpec() throws Exception {
//         if (Boolean.getBoolean("headless")) {
//             System.setProperty("testfx.robot", "glass");
//             System.setProperty("testfx.headless", "true");
//             System.setProperty("prism.order", "sw");
//             System.setProperty("prism.text", "t2k");
//             System.setProperty("java.awt.headless", "true");
//         }
//         registerPrimaryStage();
//     }

//     @BeforeEach
//     public void setUp() {
//         displayFloorLine = new DisplayFloorLine();
//     }

//     @Test
//     public void testConstructorFloorLine() {
//         assertDoesNotThrow(() -> {
//             displayFloorLine = new DisplayFloorLine();
//         });
//     }

//     @Test
//     public void testAddTileFactory() {
//         assertDoesNotThrow(() -> {
//             displayFloorLine.addTile(TileColor.RED);
//         });
//     }

//     @Test
//     public void testRemoveTileFactory() {
//         displayFloorLine.addTile(TileColor.RED);
//         assertDoesNotThrow(() -> {
//             displayFloorLine.removeTiles(TileColor.RED);
//         });

//     }

//     @Test
//     public void testClearFactory() {
//         displayFloorLine.addTile(TileColor.RED);
//         assertDoesNotThrow(() -> {
//             displayFloorLine.clear();
//         });
//     }
// }
