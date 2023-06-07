package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.messages.BadRequest;

public class BadRequestTest {
    private BadRequest request;
    private String id = "1";
    private String reason = "Great Test";
    @BeforeEach
    public void setup() {
        request = new BadRequest(id);
    }

    @Test
    public void testRequestId() {
        assertEquals(id, request.getRequestId());
    }

    @Test
    public void testReason() {
        request.setReason(reason);
        assertEquals(reason, request.getReason());
    }
    
}
