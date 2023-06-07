package messaging.messages;

import messaging.dataobjects.GameState;

public class OkStartGame extends Response {
    private GameState gameState;

    public OkStartGame(String requestId) {
        super(requestId);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
