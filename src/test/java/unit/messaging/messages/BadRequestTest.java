package unit.messaging.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import messaging.messages.BadRequest;

public class BadRequestTest {
    private BadRequest response;
    private String id = "1";
    private String reason = "Great Test";
    @BeforeEach
    public void setup() {
        response = new BadRequest(id);
    }

    @Test
    public void testRequestId() {
        assertEquals(id, response.getRequestId());
    }

    @Test
    public void testReason() {
        response.setReason(reason);
        assertEquals(reason, response.getReason());
    }
    
}
