import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

class AIAssistant {
    private JFrame chatFrame;
    private JPanel chatPanel;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;

    public AIAssistant() {
        // Set up the main frame for the chatbot
        chatFrame = new JFrame("AIAssistant - ChatBot Help");
        chatFrame.setSize(1000, 530);
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatFrame.setLayout(new BorderLayout());
        chatFrame.setLocationRelativeTo(null);

        // Panel to display conversation
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chatFrame.add(scrollPane, BorderLayout.CENTER);

        // Panel at the bottom for user input
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(176, 224, 230));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Send button to trigger response
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        sendButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add action listener to send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Action listener to allow Enter key to send message
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        chatFrame.add(inputPanel, BorderLayout.SOUTH);
        chatFrame.setVisible(true);
    }

    // Method to send a message
    private void sendMessage() {
        String userText = inputField.getText().trim();
        if (!userText.isEmpty()) {
            addMessage("You: " + userText, new Color(144, 238, 144), SwingConstants.RIGHT); // Light green bubble
            inputField.setText(""); // Clear the input field

            // Get AI response from Gemini's aiOutput function
            Gemini gemini = new Gemini();
            String aiResponse = gemini.aiOutput(userText);

            // Display the AI response in a light blue bubble
            addMessage("AIAssistant: " + aiResponse, new Color(173, 216, 230), SwingConstants.LEFT); // Light blue bubble
        }
    }

    // Method to add message bubbles to the chat panel
    private void addMessage(String message, Color backgroundColor, int alignment) {
        JPanel messageBubble = new JPanel();
        messageBubble.setLayout(new BoxLayout(messageBubble, BoxLayout.Y_AXIS));
        messageBubble.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel messageLabel = new JLabel("<html><body style='width: 250px'>" + message + "</body></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(backgroundColor);
        messageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        messageLabel.setAlignmentX(alignment == SwingConstants.RIGHT ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        messageBubble.add(messageLabel);
        chatPanel.add(messageBubble);
        chatPanel.revalidate();
        chatPanel.repaint();

        // Auto-scroll to the bottom of the chat
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }
}
