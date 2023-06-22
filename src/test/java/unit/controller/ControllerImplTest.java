package unit.controller;

import controller.ControllerImpl;
import controller.GameProxy;
import javafx.util.Pair;
import model.Game;
import model.Model;
import model.TileColor;
import shared.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import view.GUI;
import view.ViewUpdateListener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

public class ControllerImplTest {

    private ControllerImpl controller;

    @Mock
    private Model model;

    @Mock
    private GUI view;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ControllerImpl();
        try {
            Field field = ControllerImpl.class.getDeclaredField("model");
            field.setAccessible(true);
            field.set(controller, model);
            GameProxy proxy = new GameProxy();
            proxy.setProxy(model);
            field = GUI.class.getDeclaredField("model");
            field.setAccessible(true);
            field.set(view, proxy);
        } catch (NoSuchFieldException | SecurityException  | IllegalArgumentException | IllegalAccessException e3) {
            e3.printStackTrace();
        }
    
        ViewUpdateListener listener = new ViewUpdateListener(view);
        controller.addListener(listener);
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
        model = new Game();
        try {
            Field field = ControllerImpl.class.getDeclaredField("model");
            field.setAccessible(true);
            field.set(controller, model);
        } catch (NoSuchFieldException | SecurityException  | IllegalArgumentException | IllegalAccessException e3) {
            e3.printStackTrace();
        }
        model.addPlayer("");
        model.addPlayer("");
        model.addPlayer("");
        model.addPlayer("");
        // Call the joinPlayer method and verify the result
        controller.joinPlayer("Player 5");
        verify(view).toast("Too many players");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testJoinPlayerWhenPlayerIsAdded() {
        // Set up the mock model
        model = new Game();
        try {
            Field field = ControllerImpl.class.getDeclaredField("model");
            field.setAccessible(true);
            field.set(controller, model);
        } catch (NoSuchFieldException | SecurityException  | IllegalArgumentException | IllegalAccessException e3) {
            e3.printStackTrace();
        }
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
        assertEquals(4, model.getPlayers().size());
    }

    @Test
    public void testStartGameWhenGameCanStart() {
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
    public void testStartGameWhenGameCannotStart() {
        // Set up the mock model
        when(model.canStartGame()).thenReturn(false);

        // Call the startGame method and verify the result
        controller.startGame();
        verify(view).toast("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveMiddleFloorLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveMiddleFloorLine(any(TileColor.class))).thenReturn(true);

        // Call the performMoveMiddleFloorLine method and verify the result
        controller.performMove(new Pair<>(Location.MIDDLE,0),new Pair<>(Location.FLOOR_LINE,0), model.getCurrentPlayer(), TileColor.RED);
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
        controller.performMove(new Pair<>(Location.MIDDLE,0),new Pair<>(Location.FLOOR_LINE,0), model.getCurrentPlayer(), TileColor.RED);
        verify(model).isValidMoveMiddleFloorLine(TileColor.RED);
    }

    @Test
    public void testPerformMoveMiddlePatternLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveMiddlePatternLine(anyInt(), any(TileColor.class))).thenReturn(true);

        // Call the performMoveMiddlePatternLine method and verify the result
        controller.performMove(new Pair<>(Location.MIDDLE,0),new Pair<>(Location.PATTERN_LINE,2), model.getCurrentPlayer(), TileColor.BLUE);
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
        controller.performMove(new Pair<>(Location.FACTORY,3), new Pair<>(Location.FLOOR_LINE,0), model.getCurrentPlayer(), TileColor.BLACK);
        verify(model).performMoveFactoryFloorLine(3, TileColor.BLACK);
        verify(view).update();
        verify(view).toast("Performing move... (tile: BLACK from the Factory 3 to the Floor Line)");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testPerformMoveFactoryFloorLineWhenMoveIsInvalid() {
        // Set up the mock model
        when(model.isValidMoveFactoryFloorLine(anyInt(), any(TileColor.class))).thenReturn(false);

        // Call the performMoveFactoryFloorLine method and verify the result
        controller.performMove(new Pair<>(Location.FACTORY,2), new Pair<>(Location.FLOOR_LINE,0), model.getCurrentPlayer(), TileColor.RED);
        verify(model).isValidMoveFactoryFloorLine(2, TileColor.RED);
    }

    @Test
    public void testPerformMoveFactoryPatternLineWhenMoveIsValid() {
        // Set up the mock model
        when(model.isValidMoveFactoryPatternLine(anyInt(), anyInt(), any(TileColor.class))).thenReturn(true);

        // Call the performMoveFactoryPatternLine method and verify the result
        controller.performMove(new Pair<>(Location.FACTORY,0), new Pair<>(Location.PATTERN_LINE,3), model.getCurrentPlayer(), TileColor.CYAN);
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
        controller.performMove(new Pair<>(Location.FACTORY,1), new Pair<>(Location.PATTERN_LINE,2), model.getCurrentPlayer(), TileColor.YELLOW);
        verify(model).isValidMoveFactoryPatternLine(1, 2, TileColor.YELLOW);
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
        controller.terminateGame();
        verify(model).isPlaying();
        verifyNoMoreInteractions(model);
        verify(view).update();
        verifyNoMoreInteractions(view);
    }
}