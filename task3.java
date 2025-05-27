import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AIBot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public AIBot() {
        setTitle("AI Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

       
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());

        
        appendBotMessage("Hello! I'm your AI assistant. How can I help you?");
    }

    private void processInput() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        appendUserMessage(userInput);
        String response = generateResponse(userInput);
        appendBotMessage(response);

        inputField.setText("");
    }

    private void appendUserMessage(String message) {
        chatArea.append("You: " + message + "\n");
    }

    private void appendBotMessage(String message) {
        chatArea.append("Bot: " + message + "\n");
    }

    private String generateResponse(String input) {
        input = input.toLowerCase();

        
        if (input.contains("hello") || input.contains("hi")) {
            return "Hi there! How can I assist you today?";
        } else if (input.contains("how are you")) {
            return "I'm just a bot, but I'm functioning as expected!";
        } else if (input.contains("name")) {
            return "I'm a simple Java chatbot. You can call me ChatBuddy.";
        } else if (input.contains("time")) {
            return "Current time is: " + new Date().toString();
        } else if (input.contains("bye") || input.contains("exit")) {
            return "Goodbye! Have a great day!";
        } else if (input.contains("help")) {
            return "You can ask me about the time, my name, or just say hello!";
        } else {
            return "Sorry, I didn't understand that. Could you rephrase?";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AIBot chatbot = new AIBot();
            chatbot.setVisible(true);
        });
    }
}
