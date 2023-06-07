package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.dataobjects.MoveUpdate;
import messaging.dataobjects.RoundUpdate;
import messaging.messages.OkPlayerMove;

public class OkPlayerMoveTest {
    private OkPlayerMove response;
    private String requestId = "1";
    private MoveUpdate moveUpdate = new MoveUpdate();
    private RoundUpdate roundUpdate = new RoundUpdate();

    @BeforeEach
    public void setup() {
        response = new OkPlayerMove(requestId);
    }

    @Test
    public void testRequestId() {
        assertEquals(requestId, response.getRequestId());
    }

    @Test
    public void testEndOfRound() {
        response.setEndOfRound(true);
        assertEquals(true, response.isEndOfRound());
    }

    @Test
    public void testMoveUpdate() {
        response.setDataObject(moveUpdate);
        assertEquals(moveUpdate, response.getMoveUpdate());
        assertEquals(null, response.getRoundUpdate());
    }
    
    @Test
    public void testRoundUpdate() {
        response.setDataObject(roundUpdate);
        assertEquals(roundUpdate, response.getRoundUpdate());
        assertEquals(null, response.getMoveUpdate());
    }
}
