import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoActivity {
    private JFrame frame; // Frame for the application
    private String usermail;

    public DoActivity(String usermail) {
        this.usermail = usermail;
        createMainFrame(); // Create the main frame and components
    }

    private void createMainFrame() {
        frame = new JFrame("Activity Dashboard"); // Create a new frame
        frame.setLayout(new BorderLayout()); // Set layout for the frame

        JPanel upPanel = createUpPanel();
        JPanel downPanel = createDownPanel();
        JPanel mainPanel = createMainPanel(); // Create the main content panel

        // Add panels to the frame
        frame.add(upPanel, BorderLayout.NORTH); // Header
        frame.add(mainPanel, BorderLayout.CENTER); // Main content
        frame.add(downPanel, BorderLayout.SOUTH); // Footer

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setVisible(true); // Show the frame
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(20, 20, 20)); // Dark grey background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Define a standard size for the sections
        Dimension sectionSize = new Dimension(250, 350);

        // Add sections with consistent style
        gbc.gridx = 0;
        mainPanel.add(createDoActivityPanel("Do Activity", "Earn points!", "Add activity", sectionSize), gbc);

        gbc.gridx = 1;
        mainPanel.add(new Profile(usermail).createProfilePanel(sectionSize), gbc);

        gbc.gridx = 2;
        mainPanel.add(new Coaches(usermail).createCoachesPanel("My Coaches", "Ask for advice!", "Add", "Contact", sectionSize), gbc);

        gbc.gridx = 3;
        mainPanel.add(new Health(usermail).createWorkoutPanel("Workout & Diet", "Workout", "Diet", "Injury", "", sectionSize), gbc);

        return mainPanel;
    }

    private JPanel createDownPanel() {
        JPanel down = new JPanel();
        down.setBackground(new Color(226, 101, 25,255));
        down.setPreferredSize(new Dimension(JFrame.MAXIMIZED_HORIZ, 70));
        down.setLayout(new BorderLayout());

        ImageIcon image = new ImageIcon("src/bot.jpg");
        JButton chatBox = new JButton(image);
        chatBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chatBox.setContentAreaFilled(false);
        chatBox.setOpaque(false);
        chatBox.addActionListener(e -> new AIAssistant());

        down.add(chatBox, BorderLayout.EAST);
        return down;
    }

    private JPanel createUpPanel() {
        JPanel up = new JPanel();
        up.setBackground(new Color(226, 101, 25,255));
        up.setPreferredSize(new Dimension(JFrame.MAXIMIZED_HORIZ, 70));
        up.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("VVV SPORTS");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 25));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a leftPanel to hold both menuButton and backButton
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Menu button
        JButton menuButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D) g;
                gd.setColor(Color.BLACK);
                gd.setStroke(new BasicStroke(3));

                int width = getWidth();
                int height = getHeight();
                int lineHeight = 10;
                int lineWidth = width - 20;
                int y = (height - 3 * lineHeight) / 2 + 5;

                for (int i = 0; i < 3; i++) {
                    gd.drawLine(15, y, lineWidth, y);
                    y += lineHeight - 2;
                }
            }
        };
        menuButton.setPreferredSize(new Dimension(50, 50));
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(e -> new Menu());



        // Add both buttons to the leftPanel
        leftPanel.add(menuButton);


        // Logout button on the right side
        JButton logOut = new JButton("LOGOUT");
        logOut.setBackground(new Color(226, 101, 25,255));
        logOut.setForeground(Color.BLACK);
        logOut.setPreferredSize(new Dimension(100, 20));
        logOut.setBorderPainted(false);
        logOut.addActionListener(e -> System.exit(0));

        // Add components to the up panel
        up.add(leftPanel, BorderLayout.WEST);
        up.add(nameLabel, BorderLayout.CENTER);
        up.add(logOut, BorderLayout.EAST);

        return up;
    }

    private JPanel createDoActivityPanel(String titleText, String labelText, String buttonText, Dimension size) {
        JPanel sectionPanel = new JPanel(new GridBagLayout());
        sectionPanel.setPreferredSize(size);
        sectionPanel.setBackground(new Color(226, 101, 25, 255));
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        sectionPanel.add(title, gbc);

        // Image Placeholder
        gbc.gridy++;
        JLabel imagePlaceholder = new JLabel();
        ImageIcon icon = new ImageIcon("src\\doactivity.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        imagePlaceholder.setIcon(new ImageIcon(scaledImage));
        sectionPanel.add(imagePlaceholder, gbc);

        // Description Label
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.WHITE);
        sectionPanel.add(label, gbc);

        // Button with hover effect
        gbc.gridy++;
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(226, 101, 25));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.setForeground(new Color(226, 101, 25));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(226, 101, 25));
            }
        });
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActivitySelector(usermail);
            }
        });
        sectionPanel.add(button, gbc);

        return sectionPanel;
    }

}
