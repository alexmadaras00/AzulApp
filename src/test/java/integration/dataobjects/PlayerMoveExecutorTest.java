package integration.dataobjects;

import dataobjects.PlayerMove;
import dataobjects.executors.PlayerMoveExecutor;
import dataobjects.executors.RoundUpdateExecutor;
import model.Game;
import model.Model;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerMoveExecutorTest {
    Model model;
    static Player player1;
    static Player player2;
    static PlayerMoveExecutor playerMoveExecutor;

    @BeforeEach
    public void setUp() {
        model = new Game();
        player1 = new Player();
        player2 = new Player();
        model.addPlayer(player1);
        model.addPlayer(player2);
        model.startGame();
        model.startRound();
        playerMoveExecutor = new PlayerMoveExecutor();
    }

    @Test
    public void testExecute() {

        PlayerMove playerMove = playerMoveExecutor.execute(model);

    }

}
