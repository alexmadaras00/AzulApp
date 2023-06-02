// package view;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;

// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import model.TileColor;
// import static org.testfx.api.FxToolkit.registerPrimaryStage;

// public class DisplayTileTest {
//     private DisplayTile displayTile;
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

//     @Test
//     public void testConstructor() {

//         assertDoesNotThrow(() -> {
//             displayTile = new DisplayTile(TileColor.RED);
//         });
//     }

// }
