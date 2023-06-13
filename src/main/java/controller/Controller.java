package controller;

import main.AzulApp;
import model.Model;
import model.Player;
import view.View;

public class Controller {
    private Model model = AzulApp.getModel();
    private View view = AzulApp.getView();

    public void joinPlayer(String name) {
        if (model.isPlaying()) {
            view.toast("alreadypalying");
            return;
        }
        if (model.getPlayers().size() >= 4) {
            view.toast("to many players");
            return;
        }

        model.addPlayer(new Player(name));
        view.toast(name + " added");
        view.update();
    }
}
