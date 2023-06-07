package messaging.messages;

public class JoinGame extends Request {
    private String playerName;

    public JoinGame(String playerName) {
        super();
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
