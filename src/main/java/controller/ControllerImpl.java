package controller;

import model.Game;
import model.Model;
import model.TileColor;
import shared.EventType;
import shared.Location;
import shared.ModelProxy;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ControllerImpl implements Controller {
    private Model model;
    private List<ControllerEventListener> listeners;

    public ControllerImpl() {
        listeners = new ArrayList<>();
        model = new Game();
    }

    public ControllerImpl(boolean useTwinTeamImplementation) {
        listeners = new ArrayList<>();
        model = new Game();
        if (useTwinTeamImplementation) {
            model.useTwinteamFactory();
        }
    }
    public Model getModel(){
        return model;
    }

    @Override
    public void joinPlayer(String name) {
        if (getModel().isPlaying()) {
            pushMessage("Game already playing. Wait until the game is finished.");
        } else if (getModel().getPlayerList().size() >= 4) {
            pushMessage("Too many players");
        } else {
            getModel().addPlayer(name);
            pushMessage(name + " added");
            pushUpdate();
        }
    }

    @Override
    public void startGame() {
        if (getModel().canStartGame()) {
            getModel().startGame();
            pushMessage("Game started");
            pushUpdate();
        } else {
            pushMessage("Invalid number of players. The game requires at least 2 and at most 4 players. Please adjust the number of players and try again.");
        }
    }

    @Override
    public void performMove(Pair<Location, Integer> from, Pair<Location, Integer> to, int playerID, TileColor color) {
        if (playerID == getModel().getCurrentPlayer()) {
            if (from.getKey() == Location.FACTORY && to.getKey() == Location.PATTERN_LINE) {
                performMoveFactoryPatternLine(from.getValue(), to.getValue(), color);
            }
            if (from.getKey() == Location.MIDDLE && to.getKey() == Location.PATTERN_LINE) {
                performMoveMiddlePatternLine(to.getValue(), color);
            }
            if (from.getKey() == Location.FACTORY && to.getKey() == Location.FLOOR_LINE) {
                performMoveFactoryFloorLine(from.getValue(), color);
            }
            if (from.getKey() == Location.MIDDLE && to.getKey() == Location.FLOOR_LINE) {
                performMoveMiddleFloorLine(color);
            }
        } else {
            pushMessage("Invalid move. Wait for your turn!");
        }
    }

    private void performMoveMiddleFloorLine(TileColor tileColor) {
        if (getModel().isValidMoveMiddleFloorLine(tileColor)) {
            getModel().performMoveMiddleFloorLine(tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the Floor Line)");
            pushUpdate();
        }
    }

    private void performMoveMiddlePatternLine(int row, TileColor tileColor) {
        if (getModel().isValidMoveMiddlePatternLine(row, tileColor)) {
            getModel().performMoveMiddlePatternLine(row, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the row " + row + " in the" +
                    " Pattern Line)");
            pushUpdate();
        }

    }

    private void performMoveFactoryFloorLine(int index, TileColor tileColor) {
        if (getModel().isValidMoveFactoryFloorLine(index, tileColor)) {
            getModel().performMoveFactoryFloorLine(index, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the " +
                    "Floor Line)");
            pushUpdate();
        }

    }


    private void performMoveFactoryPatternLine(int index, int row, TileColor tileColor) {
        if (getModel().isValidMoveFactoryPatternLine(index, row, tileColor)) {
            getModel().performMoveFactoryPatternLine(index, row, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the row " + row + " in the" +
                    " Pattern Line)");
            pushUpdate();
        }

    }

    @Override
    public void terminateGame() {
        if (getModel().isPlaying()) {
            getModel().terminateGame();
            pushMessage("Terminating game...");
        }
        pushUpdate();
    }

    public ModelProxy getProxy() {
        GameProxy proxy = new GameProxy();
        proxy.setProxy(model);
        return proxy;
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
