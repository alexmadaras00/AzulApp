package controller;

import model.Model;
import model.Player;
import view.View;

public class Controller {
    private Model model;
    private View view;

    public void joinPlayer(String name) {
        if (model.isPlaying()) {
            view.toast("alreadypalying");
            return;
        }
        if (model.getPlayerList().size() > 4) {
            view.toast("to many players");
            return;
        }

        model.addPlayer(new Player(name));
        view.toast(name + " added");
        view.update();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }
}
