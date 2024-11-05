import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ContactCoach {
    private String usermail;
    private JFrame frame;
    private JPanel coachPanel;
    private Database db;

    public ContactCoach(String usermail) {
        this.usermail = usermail;
        this.db = new Database();  // Assuming Database class exists
        initializeUI();
    }
    private void showMessageDialog(String coachName, String coachEmail) {
        // Create the dialog for messaging
        JDialog messageDialog = new JDialog(frame, "Message " + coachName, true);
        messageDialog.setSize(400, 300);
        messageDialog.setLocationRelativeTo(frame);
        messageDialog.setLayout(new BorderLayout());
    
        // Text area for message content
        JTextArea messageContent = new JTextArea(10, 30);
        messageContent.setLineWrap(true);
        messageContent.setWrapStyleWord(true);
        messageContent.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(messageContent);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        // Send button
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(226, 101, 25));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputContent = messageContent.getText().trim();
                if (!inputContent.isEmpty()) {
                    try {
                        // Initialize EmailSender and send the email
                        EmailSender.sendEmail(coachEmail, "Sent by " + usermail, inputContent);
                        JOptionPane.showMessageDialog(messageDialog, "Message sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        messageDialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(messageDialog, "Failed to send message. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(messageDialog, "Please enter a message.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
    
        // Add components to the dialog
        messageDialog.add(scrollPane, BorderLayout.CENTER);
        messageDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Make dialog visible
        messageDialog.setVisible(true);
    }
    private void initializeUI() {
        frame = new JFrame("Contact Coaches");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(new Color(15, 15, 15)); // Set main frame background to very dark grey
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Panel (upPanel)
        JPanel upPanel = createUpPanel();
        frame.add(upPanel, BorderLayout.NORTH);

        // Bottom Panel (downPanel)
        JPanel downPanel = createDownPanel();
        frame.add(downPanel, BorderLayout.SOUTH);

        // Coach Panel for displaying coaches in a grid layout
        coachPanel = new JPanel();
        coachPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns layout with gaps
        coachPanel.setBackground(new Color(15, 15, 15)); // Set coach panel background to dark grey

        List<Map<String, Object>> coaches = db.getPurchasedCoaches(usermail);

        for (Map<String, Object> coach : coaches) {
            JPanel coachItemPanel = new JPanel(new BorderLayout(10, 10));
            coachItemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            coachItemPanel.setBackground(new Color(30, 30, 30)); // Slightly lighter grey for coach items
            coachItemPanel.setLayout(new BorderLayout());

            // Create insets using EmptyBorder
            EmptyBorder insets = new EmptyBorder(10, 10, 10, 10);
            coachItemPanel.setBorder(insets);
            coachItemPanel.setPreferredSize(new Dimension(250, 200));

            // Coach details
            String coachName = (String) coach.get("coachname");
            int coachExperience = (int) coach.get("coachexperience");
            BigDecimal cost = (BigDecimal) coach.get("cost");
            String imagePath = (String) coach.get("imagepath");
            String coachmail = (String) coach.get("coachmail");

            // Left side: Image of the coach
            if (imagePath != null) {
                ImageIcon coachImage = new ImageIcon(imagePath);
                Image scaledImage = coachImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                coachItemPanel.add(imageLabel, BorderLayout.WEST);
            }

            // Center: Coach details
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBackground(new Color(30, 30, 30));
            JLabel nameLabel = new JLabel(coachName);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            nameLabel.setForeground(Color.WHITE);

            JLabel experienceLabel = new JLabel("Experience: " + coachExperience + " years");
            experienceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            experienceLabel.setForeground(Color.LIGHT_GRAY);

            JLabel costLabel = new JLabel("Cost: $" + cost);
            costLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            costLabel.setForeground(Color.LIGHT_GRAY);

            detailsPanel.add(nameLabel);
            detailsPanel.add(experienceLabel);
            detailsPanel.add(costLabel);
            coachItemPanel.add(detailsPanel, BorderLayout.CENTER);

            // Bottom: Message button
            JButton messageButton = new JButton("Message");
            messageButton.setBackground(Color.WHITE);
            messageButton.setForeground(new Color(226, 101, 25));
            messageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(frame, "Messaging " + coachName);
                    showMessageDialog(coachName, coachmail);
                }
            });
            coachItemPanel.add(messageButton, BorderLayout.SOUTH);

            // Add each coach item panel to the main coach panel
            coachPanel.add(coachItemPanel);
        }

        // Scroll Pane for the coach panel
        JScrollPane scrollPane = new JScrollPane(coachPanel);
        scrollPane.getViewport().setBackground(new Color(15, 15, 15)); // Set scroll pane background to dark grey
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make frame visible
        frame.setVisible(true);
    }

    private JPanel createUpPanel() {
        JPanel upPanel = new JPanel();
        upPanel.setBackground(new Color(226, 101, 25, 255));
        upPanel.setPreferredSize(new Dimension(1000, 70));
        upPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("VVV SPORTS - Your Coaches");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        upPanel.add(title, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 17));
        backButton.setBackground(new Color(226, 101, 55));
        backButton.setPreferredSize(new Dimension(100, 150));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> frame.dispose());
        backButton.setBorderPainted(false);
        upPanel.add(backButton, BorderLayout.WEST);

        JButton logOut = new JButton("LOGOUT");
        logOut.setBackground(new Color(226, 101, 25, 255));
        logOut.setForeground(Color.BLACK);
        logOut.setPreferredSize(new Dimension(100, 20));
        logOut.setBorderPainted(false);
        logOut.addActionListener(e -> System.exit(0));
        upPanel.add(logOut, BorderLayout.EAST);

        return upPanel;
    }

    private JPanel createDownPanel() {
        JPanel downPanel = new JPanel();
        downPanel.setBackground(new Color(226, 101, 25, 255));
        downPanel.setPreferredSize(new Dimension(1000, 70));
        downPanel.setLayout(new BorderLayout());

        ImageIcon chatIcon = new ImageIcon("src//bot.jpg");
        JButton chatButton = new JButton(chatIcon);
        chatButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chatButton.setContentAreaFilled(false);
        chatButton.setOpaque(false);
        chatButton.addActionListener(e -> new AIAssistant());

        downPanel.add(chatButton, BorderLayout.EAST);
        return downPanel;
    }
}
