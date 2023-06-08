package messaging.messages;

import messaging.dataobjects.GameState;
import messaging.dataobjects.RoundUpdate;

public class OkStartGame extends Response {
    private GameState gameState;
    private RoundUpdate roundUpdate;

    public OkStartGame(String requestId) {
        super(requestId);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public RoundUpdate getRoundUpdate() { return roundUpdate;}

    public void setRoundUpdate(RoundUpdate roundUpdate) { this.roundUpdate = roundUpdate; }

}
