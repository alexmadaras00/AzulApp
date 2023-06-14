package controller;

import model.Model;
import model.Player;
import model.TileColor;
import utils.ExceptionGameStart;
import view.View;

public class ControllerImpl implements Controller {
    private Model model;
    private View view;

    @Override
    public void joinPlayer(String name) {
        if (model.isPlaying()) {
            view.toast("Game already playing. Wait until the game is finished.");
        } else if (model.getPlayerList().size() >= 4) {
            view.toast("Too many players");
        } else {
            model.addPlayer(new Player(name));
            view.toast(name + " added");
            view.update();
        }
    }

    @Override
    public void startGame() {
        if (model.canStartGame()) {
            model.startGame();
            view.update();
            view.toast("Game started");
        } else {
            try {
                model.startGame();
            } catch (ExceptionGameStart e) {
                view.toast(e.getMessage());
            }
        }
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        if (model.isValidMoveMiddleFloorLine(tileColor)) {
            model.performMoveMiddleFloorLine(tileColor);
            view.update();
            view.toast("Performing move... (tile: " + tileColor + " from the Middle to the Floor Line)");
        } else {
            throw new RuntimeException("Invalid move.");
        }
    }

    @Override
    public void performMoveMiddlePatternLine(int row, TileColor tileColor) {
        if (model.isValidMoveMiddlePatternLine(row, tileColor)) {
            model.performMoveMiddlePatternLine(row, tileColor);
            view.update();
            view.toast("Performing move... (tile: " + tileColor + " from the Middle to the row " + row + " in the" +
                    " Pattern Line)");
        } else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void performMoveFactoryFloorLine(int index, TileColor tileColor) {
        if (model.isValidMoveFactoryFloorLine(index, tileColor)) {
            model.performMoveFactoryFloorLine(index, tileColor);
            view.update();
            view.toast("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the row  the " +
                    "Floor Line)");
        } else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void performMoveFactoryPatternLine(int index, int row, TileColor tileColor) {
        if (model.isValidMoveFactoryPatternLine(index, row, tileColor)) {
            model.performMoveFactoryPatternLine(index, row, tileColor);
            view.update();
            view.toast("Performing move... (tile: " + tileColor + " from the Factory " + index + " to the row " + row + " in the" +
                    " Pattern Line)");
        } else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void terminateGame() {
        if (model.isPlaying()) {
            model.terminateGame();
            view.update();
            view.toast("Terminating game...");
        } else throw new RuntimeException("Invalid request.");
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }

}
