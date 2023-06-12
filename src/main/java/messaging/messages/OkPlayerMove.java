package messaging.messages;

import messaging.dataobjects.DataObject;
import messaging.dataobjects.MoveUpdate;
import messaging.dataobjects.RoundUpdate;

public class OkPlayerMove extends Response {
    private DataObject dataObject;

    private boolean endOfRound;

    public OkPlayerMove(String requestId) {
        super(requestId);
    }

    public boolean isEndOfRound() {
        return endOfRound;
    }

    public void setEndOfRound(boolean endOfRound) {
        this.endOfRound = endOfRound;
    }

    public MoveUpdate getMoveUpdate() {
        return dataObject instanceof MoveUpdate ? (MoveUpdate) dataObject : null;
    }

    public RoundUpdate getRoundUpdate() {
        return dataObject instanceof RoundUpdate ? (RoundUpdate) dataObject : null;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

}
