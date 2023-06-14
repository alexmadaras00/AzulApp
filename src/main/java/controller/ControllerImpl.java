package controller;

import model.GameProxy;
import model.Model;
import model.ModelProxy;
import model.TileColor;
import view.View;

public class ControllerImpl implements Controller {
    private Model model;
    private View view;

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
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        if (model.isValidMoveMiddleFloorLine(tileColor)) {
            model.performMoveMiddleFloorLine(tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the Floor Line)");
            pushUpdate();
        }
    }

    @Override
    public void performMoveMiddlePatternLine(int row, TileColor tileColor) {
        if (model.isValidMoveMiddlePatternLine(row, tileColor)) {
            model.performMoveMiddlePatternLine(row, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Middle to the row " + row + " in the" +
                    " Pattern Line)");
            pushUpdate();
        } 

    }

    @Override
    public void performMoveFactoryFloorLine(int index, TileColor tileColor) {
        if (model.isValidMoveFactoryFloorLine(index, tileColor)) {
            model.performMoveFactoryFloorLine(index, tileColor);
            pushMessage("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the " +
                    "Floor Line)");
            pushUpdate();
        } 
        
    }

    @Override
    public void performMoveFactoryPatternLine(int index, int row, TileColor tileColor) {
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

    public void setView(View view) {
        this.view = view;
    }

    private void pushMessage(String message) {
        view.toast(message);
    }

    private void pushUpdate() {
        view.update();
    }
}
