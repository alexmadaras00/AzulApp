package unit.controller;

import controller.ControllerImpl;
import model.Game;
import model.Model;
import model.Player;
import model.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.ExceptionGameStart;
import view.View;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerImplTest {

    private ControllerImpl controller;

    @Mock
    private Model model;

    @Mock
    private View view;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ControllerImpl();
        controller.setModel(model);
        controller.setView(view);
    }

    @Test
    public void testJoinPlayerWhenGameIsPlaying() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(true);

        // Call the joinPlayer method and verify the result
        controller.joinPlayer("Player 1");
        verify(view).toast("Game already playing. Wait until the game is finished.");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testJoinPlayerWhenTooManyPlayers() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(false);
        model = new Game();
        controller.setModel(model);
        model.addPlayer(new Player());
        model.addPlayer(new Player());
        model.addPlayer(new Player());
        model.addPlayer(new Player());
        // Call the joinPlayer method and verify the result
        controller.joinPlayer("Player 5");
        verify(view).toast("Too many players");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testJoinPlayerWhenPlayerIsAdded() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(false);
        model = new Game();
        controller.setModel(model);
        controller.joinPlayer("Player 1");
        verify(view).update();
        controller.joinPlayer("Player 2");
        verify(view, times(2)).update();
        controller.joinPlayer("Player 3");
        verify(view, times(3)).update();
        // Call the joinPlayer method and verify the result
        controller.joinPlayer("Player 4");
        verify(view).toast("Player 4 added");
        verify(view, times(4)).update();
        assertEquals(4, model.getPlayerList().size());
    }

    @Test
    public void testStartGameWhenGameCanStart() throws ExceptionGameStart {
        // Set up the mock model
        when(model.canStartGame()).thenReturn(true);

        // Call the startGame method and verify the result
        controller.startGame();
        verify(model).startGame();
        verify(view).update();
        verify(view).toast("Game started");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testStartGameWhenGameCannotStart() throws ExceptionGameStart {
        // Set up the mock model
        when(model.canStartGame()).thenReturn(false);
        doThrow(new ExceptionGameStart("Cannot start game")).when(model).startGame();

        // Call the startGame method and verify the result
        controller.startGame();
        verify(model).startGame();
        verify(view).toast("Cannot start game");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveMiddleFloorLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveMiddleFloorLine(any(TileColor.class))).thenReturn(true);

        // Call the performMoveMiddleFloorLine method and verify the result
        controller.performMoveMiddleFloorLine(TileColor.RED);
        verify(model).performMoveMiddleFloorLine(TileColor.RED);
        verify(view).update();
        verify(view).toast("Performing move... (tile: RED from the Middle to the Floor Line)");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveMiddleFloorLineWhenMoveIsInvalid() {
        // Set up the mock model
        when(model.isValidMoveMiddleFloorLine(any(TileColor.class))).thenReturn(false);

        // Call the performMoveMiddleFloorLine method and verify the result
        assertThrows(RuntimeException.class, () -> controller.performMoveMiddleFloorLine(TileColor.RED));
        verify(model).isValidMoveMiddleFloorLine(TileColor.RED);
        verifyNoMoreInteractions(model);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveMiddlePatternLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveMiddlePatternLine(anyInt(), any(TileColor.class))).thenReturn(true);

        // Call the performMoveMiddlePatternLine method and verify the result
        controller.performMoveMiddlePatternLine(2, TileColor.BLUE);
        verify(model).performMoveMiddlePatternLine(2, TileColor.BLUE);
        verify(view).update();
        verify(view).toast("Performing move... (tile: BLUE from the Middle to the row 2 in the Pattern Line)");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveFactoryFloorLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveFactoryFloorLine(anyInt(), any(TileColor.class))).thenReturn(true);

        // Call the performMoveFactoryFloorLine method and verify the result
        controller.performMoveFactoryFloorLine(3, TileColor.BLACK);
        verify(model).performMoveFactoryFloorLine(3, TileColor.BLACK);
        verify(view).update();
        verify(view).toast("Performing move... (tile: BLACK from the Factory 3 to the row  the Floor Line)");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveFactoryFloorLineWhenMoveIsInvalid() {
        // Set up the mock model
        when(model.isValidMoveFactoryFloorLine(anyInt(), any(TileColor.class))).thenReturn(false);

        // Call the performMoveFactoryFloorLine method and verify the result
        assertThrows(RuntimeException.class, () -> controller.performMoveFactoryFloorLine(2, TileColor.RED));
        verify(model).isValidMoveFactoryFloorLine(2, TileColor.RED);
        verifyNoMoreInteractions(model);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveFactoryPatternLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveFactoryPatternLine(anyInt(), anyInt(), any(TileColor.class))).thenReturn(true);

        // Call the performMoveFactoryPatternLine method and verify the result
        controller.performMoveFactoryPatternLine(0, 3, TileColor.CYAN);
        verify(model).performMoveFactoryPatternLine(0, 3, TileColor.CYAN);
        verify(view).update();
        verify(view).toast("Performing move... (tile: CYAN from the Factory 0 to the row 3 in the Pattern Line)");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveFactoryPatternLineWhenMoveIsInvalid() {
        // Set up the mock model
        when(model.isValidMoveFactoryPatternLine(anyInt(), anyInt(), any(TileColor.class))).thenReturn(false);

        // Call the performMoveFactoryPatternLine method and verify the result
        assertThrows(RuntimeException.class, () -> controller.performMoveFactoryPatternLine(1, 2, TileColor.YELLOW));
        verify(model).isValidMoveFactoryPatternLine(1, 2, TileColor.YELLOW);
        verifyNoMoreInteractions(model);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testTerminateGameWhenGameIsPlaying() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(true);

        // Call the terminateGame method and verify the result
        controller.terminateGame();
        verify(model).terminateGame();
        verify(view).update();
        verify(view).toast("Terminating game...");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testTerminateGameWhenGameIsNotPlaying() {
        // Set up the mock model
        when(model.isPlaying()).thenReturn(false);

        // Call the terminateGame method and verify the result
        assertThrows(RuntimeException.class, () -> controller.terminateGame());
        verify(model).isPlaying();
        verifyNoMoreInteractions(model);
        verifyNoMoreInteractions(view);
    }
}