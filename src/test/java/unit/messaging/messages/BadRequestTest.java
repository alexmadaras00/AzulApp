package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.messages.BadRequest;

public class BadRequestTest {
    private BadRequest response;
    private String requestId = "1";
    private String reason = "Great Test";
    @BeforeEach
    public void setup() {
        response = new BadRequest(requestId);
    }

    @Test
    public void testRequestId() {
        assertEquals(requestId, response.getRequestId());
    }

    @Test
    public void testReason() {
        response.setReason(reason);
        assertEquals(reason, response.getReason());
    }
    
}
