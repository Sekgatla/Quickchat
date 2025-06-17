

import com.mycompany.sendingmessages.SendMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SendMessageTest {

    @Test
    public void testMessageLengthSuccess() {
        SendMessage msg = new SendMessage(1, "+2771234567", "Short message");
        assertTrue(msg.getMessageText().length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(300);
        int excess = longMsg.length() - 250;
        assertTrue(longMsg.length() > 250, "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testRecipientSuccess() {
        SendMessage msg = new SendMessage(2, "+27831234567", "Test");
        assertTrue(msg.checkRecipientCell(), "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientFailure() {
        SendMessage msg = new SendMessage(3, "0831234567", "Test");
        assertFalse(msg.checkRecipientCell(), "Cell phone number is incorrectly formatted or does not contain an international code.");
    }

    @Test
    public void testMessageHash() {
        SendMessage msg = new SendMessage(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String expectedHashPrefix = msg.getMessageId().substring(0, 2) + ":0:";
        assertEquals(expectedHashPrefix + "HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testMessageIDGenerated() {
        SendMessage msg = new SendMessage(1, "+27831234567", "Hello!");
        assertEquals(10, msg.getMessageId().length(), "Message ID generated: " + msg.getMessageId());
    }

    @Test
    public void testSendMessageOptionSend() {
        SendMessage msg = new SendMessage(1, "+27831234567", "Hello!");
        assertEquals("Message successfully sent.", msg.sentMessage("send"));
    }

    @Test
    public void testSendMessageOptionDisregard() {
        SendMessage msg = new SendMessage(1, "+27831234567", "Hello!");
        assertEquals("Press 0 to delete message.", msg.sentMessage("disregard"));
    }

    @Test
    public void testSendMessageOptionStore() {
        SendMessage msg = new SendMessage(1, "+27831234567", "Hello!");
        assertEquals("Message successfully stored.", msg.sentMessage("store"));
    }
}
