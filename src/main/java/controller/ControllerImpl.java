package controller;

import model.Model;
import model.TileColor;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {
    private Model model;
    private List<ControllerEventListener> listeners;

    public ControllerImpl() {
        listeners = new ArrayList<>();
    }

    @Override
    public void joinPlayer(String name) {
        if (model.isPlaying()) {
            pushMessage("Game already playing. Wait until the game is finished.");
        } else if (model.getPlayerList().size() >= 4) {
            pushMessage("Too many players");
        } else {
            model.addPlayer(name);
            pushMessage(name + " added");
            pushUpdate();
        }
    }

    @Override
    public void startGame() {
        if (model.canStartGame()) {
            model.startGame();
            pushMessage("Game started");
            pushUpdate();
        } else {
            pushMessage("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.");
        }
    }

    @Override
    public void performMove(Location from, Location to, int fromIndex, int toIndex, int playerID, TileColor color) {
        if (playerID == model.getCurrentPlayer()) {
            if (from == Location.FACTORY && to == Location.PATTERN_LINE) {
                performMoveFactoryPatternLine(fromIndex, toIndex, color);
            }
            if (from == Location.MIDDLE && to == Location.PATTERN_LINE) {
                performMoveMiddlePatternLine(toIndex, color);
            }
            if (from == Location.FACTORY && to == Location.FLOOR_LINE) {
                performMoveFactoryFloorLine(fromIndex, color);
            }
            if (from == Location.MIDDLE && to == Location.FLOOR_LINE) {
                performMoveMiddleFloorLine(color);
            }
        } else {
            pushMessage("Invalid move. Wait for your turn!");
        }
    }

    private void performMoveMiddleFloorLine(TileColor tileColor) {
        if (model.isValidMoveMiddleFloorLine(tileColor)) {
            model.performMoveMiddleFloorLine(tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the Floor Line)");
            pushUpdate();
        }
    }

    private void performMoveMiddlePatternLine(int row, TileColor tileColor) {
        if (model.isValidMoveMiddlePatternLine(row, tileColor)) {
            model.performMoveMiddlePatternLine(row, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the row " + row + " in the" +
                    " Pattern Line)");
            pushUpdate();
        }

    }

    private void performMoveFactoryFloorLine(int index, TileColor tileColor) {
        if (model.isValidMoveFactoryFloorLine(index, tileColor)) {
            model.performMoveFactoryFloorLine(index, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the " +
                    "Floor Line)");
            pushUpdate();
        }

    }


    private void performMoveFactoryPatternLine(int index, int row, TileColor tileColor) {
        if (model.isValidMoveFactoryPatternLine(index, row, tileColor)) {
            model.performMoveFactoryPatternLine(index, row, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the row " + row + " in the" +
                    " Pattern Line)");
            pushUpdate();
        }

    }

    @Override
    public void terminateGame() {
        if (model.isPlaying()) {
            model.terminateGame();
            pushMessage("Terminating game...");
        }
        pushUpdate();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void pushMessage(String message) {
        notifyListeners(EventType.MESSAGE, message);
    }

    private void pushUpdate() {
        notifyListeners(EventType.UPDATE, null);
    }

    @Override
    public void addListener(ControllerEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyListeners(EventType type, String message) {
        for (ControllerEventListener listener : listeners) {
            listener.update(type, message);
        }
    }

    @Override
    public void removeListener(ControllerEventListener listener) {
        listeners.remove(listener);
    }

    
}
