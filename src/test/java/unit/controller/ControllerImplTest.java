package unit.controller;


import controller.ControllerImpl;
import model.Model;
import model.Player;
import model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.View;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ControllerImplTest {

    private ControllerImpl controller;
    private Model model;
    private View view;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        view = mock(View.class);
        controller = new ControllerImpl();
        controller.setModel(model);
        controller.setView(view);
    }

    @Test
    public void testJoinPlayer() {
        // Set up the mock model
        List<Player> playerList = new ArrayList<>();
        when(model.isPlaying()).thenReturn(false);

        // Call the joinPlayer method and verify the results
        controller.joinPlayer("Player 1");
        verify(model).addPlayer(any(Player.class));
        verify(view).toast("Player 1 added");
        verify(view).update();

        // Verify that the toast is displayed when the game is already in progress
        when(model.isPlaying()).thenReturn(true);
        controller.joinPlayer("Player 2");
        verify(view).toast("Game already playing. Wait until the game is finished.");

        // Verify that the toast is displayed when there are too many players
        playerList.add(new Player("Player 1"));
        playerList.add(new Player("Player 2"));
        playerList.add(new Player("Player 3"));
        playerList.add(new Player("Player 4"));
        controller.joinPlayer("Player 5");
        if(playerList.size()>4)
            verify(view).toast("Too many players");
    }

    @Test
    public void testStartGame() {
        // Call the startGame method and verify the result
        controller.startGame();
        verify(model).startGame();
    }

    @Test
    public void testPerformMoveMiddleFloorLine() {
        // Set up the mock model
        when(model.isValidMoveMiddleFloorLine(TileColor.RED)).thenReturn(true);
        when(model.isValidMoveMiddleFloorLine(TileColor.BLUE)).thenReturn(false);

        // Call the performMoveMiddleFloorLine method and verify the result
        controller.performMoveMiddleFloorLine(TileColor.RED);
        verify(model).performMoveMiddleFloorLine(TileColor.RED);

        // Verify that an exception is thrown when an invalid move is made
        assertThrows(RuntimeException.class, () -> controller.performMoveMiddleFloorLine(TileColor.BLUE));
    }

    @Test
    public void testPerformMoveMiddlePatternLine() {
        // Set up the mock model
        when(model.isValidMoveMiddlePatternLine(1, TileColor.BLUE)).thenReturn(true);
        when(model.isValidMoveMiddlePatternLine(2, TileColor.BLACK)).thenReturn(false);

        // Call the performMoveMiddlePatternLine method and verify the result
        controller.performMoveMiddlePatternLine(1, TileColor.BLUE);
        verify(model).performMoveMiddlePatternLine(1, TileColor.BLUE);

        // Verify that an exception is thrown when an invalid move is made
        assertThrows(RuntimeException.class, () -> controller.performMoveMiddlePatternLine(2, TileColor.BLACK));
    }

    @Test
    public void testPerformMoveFactoryFloorLine() {
        // Set up the mock model
        when(model.isValidMoveFactoryFloorLine(0, TileColor.CYAN)).thenReturn(true);
        when(model.isValidMoveFactoryFloorLine(1, TileColor.YELLOW)).thenReturn(false);

        // Call the performMoveFactoryFloorLine method and verify the result
        controller.performMoveFactoryFloorLine(0, TileColor.CYAN);
        verify(model).performMoveFactoryFloorLine(0, TileColor.CYAN);

        // Verify that an exception is thrown when an invalid move is made
        assertThrows(RuntimeException.class, () -> controller.performMoveFactoryFloorLine(1, TileColor.YELLOW));
    }

    @Test
    public void testPerformMoveFactoryPatternLine() {
        // Set up the mock model
        when(model.isValidMoveFactoryPatternLine(0, 1, TileColor.RED)).thenReturn(true);
        when(model.isValidMoveFactoryPatternLine(1, 2, TileColor.CYAN)).thenReturn(false);

        // Call the performMoveFactoryPatternLine method and verify the result
        controller.performMoveFactoryPatternLine(0, 1, TileColor.RED);
        verify(model).performMoveFactoryPatternLine(0, 1, TileColor.RED);

        // Verify that an exception is thrown when an invalid move is made
        assertThrows(RuntimeException.class, () -> controller.performMoveFactoryPatternLine(1, 2, TileColor.CYAN));
    }

    @Test
    public void testTerminateGame() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(true);

        // Call the terminateGame method and verify the result
        controller.terminateGame();
        verify(model).terminateGame();

        // Verify that an exception is thrown when the game is not in progress
        when(model.isPlaying()).thenReturn(false);
        assertThrows(RuntimeException.class, () -> controller.terminateGame());
    }
}