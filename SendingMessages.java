package com.mycompany.sendingmessages;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SendingMessages {

    private static List<SendMessage> sentMessages = new ArrayList<>();
    private static JSONArray storedMessages = new JSONArray();
    private static int totalMessages = 0;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        String loggedIn = JOptionPane.showInputDialog("Enter password to login:");
        if (!"admin".equals(loggedIn)) {
            JOptionPane.showMessageDialog(null, "Login failed.");
            return;
        }

        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
        int msgCounter = 1;

        while (true) {
            String menu = JOptionPane.showInputDialog("1) Send Message\n2) Show recently sent messages\n3) Quit");

            switch (menu) {
                case "1":
                    if (msgCounter > numMessages) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        continue;
                    }

                    String recipient = JOptionPane.showInputDialog("Enter recipient number (start with +):");
                    String messageText = JOptionPane.showInputDialog("Enter your message:");

                    if (messageText.length() > 250) {
                        JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + (messageText.length() - 250) + ", please reduce size.");
                        break;
                    }

                    SendMessage msg = new SendMessage(msgCounter, recipient, messageText);

                    if (!msg.checkRecipientCell()) {
                        JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code.");
                        break;
                    }

                    String action = JOptionPane.showInputDialog("Send, Store or Disregard?");
                    String result = msg.sentMessage(action);

                    if (action.equalsIgnoreCase("send")) {
                        sentMessages.add(msg);
                        msgCounter++;
                        totalMessages++;
                    } else if (action.equalsIgnoreCase("store")) {
                        storeMessageToJson(msg);
                    }

                    JOptionPane.showMessageDialog(null, msg.printMessage() + "\n" + result);
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessages);
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    public static void storeMessageToJson(SendMessage msg) {
        JSONObject obj = new JSONObject();
        obj.put("messageId", msg.getMessageId());
        obj.put("messageHash", msg.getMessageHash());
        obj.put("recipient", msg.getRecipient());
        obj.put("message", msg.getMessageText());
        storedMessages.add(obj);

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(storedMessages.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
