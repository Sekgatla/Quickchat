package com.mycompany.sendingmessages;

import java.util.Random;

public class SendMessage {
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    public SendMessage(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        String id = String.format("%010d", Math.abs(rand.nextLong() % 10000000000L));
        return id;
    }

    public boolean checkMessageID() {
        return this.messageId.length() == 10;
    }

    public boolean checkRecipientCell() {
        return recipient.length() <= 10 && recipient.startsWith("+");
    }

    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        return messageId.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String sentMessage(String choice) {
        switch (choice.toLowerCase()) {
            case "send":
                return "Message successfully sent.";
            case "disregard":
                return "Press 0 to delete message.";
            case "store":
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public String printMessage() {
        return "Message ID: " + messageId + "\nHash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + messageText;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
}
