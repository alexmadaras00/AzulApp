package controller;

import model.Model;
import model.Player;
import model.TileColor;
import view.View;

public class ControllerImpl implements Controller{
    private Model model;
    private View view;

    @Override
    public void joinPlayer(String name) {
        if (model.isPlaying()) {
            view.toast("Game already playing. Wait until the game is finished.");
            return;
        } else if (model.getPlayerList().size() > 4) {
            view.toast("Too many players");
            return;
        }
        else {
            model.addPlayer(new Player(name));
            view.toast(name + " added");
            view.update();
        }
    }

    @Override
    public void startGame() {
        model.startGame();
    }

    @Override
    public void performMoveMiddleFloorLine(TileColor tileColor) {
        if(model.isValidMoveMiddleFloorLine(tileColor))
            model.performMoveMiddleFloorLine(tileColor);
        else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void performMoveMiddlePatternLine(int row, TileColor tileColor) {
        if(model.isValidMoveMiddlePatternLine(row,tileColor))
            model.performMoveMiddlePatternLine(row,tileColor);
        else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void performMoveFactoryFloorLine(int index, TileColor tileColor) {
        if(model.isValidMoveFactoryFloorLine(index,tileColor))
            model.performMoveFactoryFloorLine(index,tileColor);
        else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void performMoveFactoryPatternLine(int index, int row, TileColor tileColor) {
        if(model.isValidMoveFactoryPatternLine(index,row,tileColor))
            model.performMoveFactoryPatternLine(index,row,tileColor);
        else throw new RuntimeException("Invalid move.");
    }

    @Override
    public void terminateGame() {
        if(model.isPlaying())
            model.terminateGame();
        else throw new RuntimeException("Invalid request.");
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }
}
