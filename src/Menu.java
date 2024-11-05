import javax.swing.*;
import java.awt.*;

public class Menu {
    JFrame frame = new JFrame("VVV Sports");
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel(new BorderLayout());

    public Menu(){
        frame.getContentPane().setBackground(new Color(10,10,10,255));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        leftPanel = createLeftPanel();
        frame.add(leftPanel, BorderLayout.WEST);

        showAboutContent();  // Display About content by default
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createLeftPanel(){
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(226, 101, 55));
        leftPanel.setPreferredSize(new Dimension(300, 700));
        leftPanel.setLayout(null);  // Disable layout manager for manual positioning

        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(226, 101, 55));
        backButton.setForeground(Color.BLACK);
        backButton.setFont(buttonFont);
        backButton.setBounds(10, 10, 80, 40);
        backButton.addActionListener(e -> frame.dispose());


        JButton aboutButton = new JButton("About Us");
        aboutButton.setForeground(Color.BLACK);
        aboutButton.setFont(buttonFont);
        aboutButton.setBorderPainted(false);
        aboutButton.setOpaque(false);
        aboutButton.setContentAreaFilled(false);
        aboutButton.setSize(150, 40);
        aboutButton.setLocation(10, 75);
        aboutButton.addActionListener(e -> showAboutContent());

        JButton termsButton = new JButton("Terms & Conditions");
        termsButton.setForeground(Color.BLACK);
        termsButton.setFont(buttonFont);
        termsButton.setBorderPainted(false);
        termsButton.setOpaque(false);
        termsButton.setContentAreaFilled(false);
        termsButton.setSize(250, 40);
        termsButton.setLocation(10, 125);
        termsButton.addActionListener(e -> showTermsContent());

        leftPanel.add(backButton);
        leftPanel.add(aboutButton);
        leftPanel.add(termsButton);

        return leftPanel;
    }

    private void showAboutContent() {
        rightPanel.removeAll();

        JLabel headerLabel = new JLabel("About Us");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea contentArea = new JTextArea();
        contentArea.setFont(new Font("Arial", Font.PLAIN, 16));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(new Color(245, 245, 245));
        contentArea.setText(
                "VVV Sports is an innovative sports platform developed with dedication and passion. Our team consists of:\n\n"
                + "Vishal S: An experienced developer with a knack for creating smooth, user-friendly applications, "
                + "Vishal drives the technical aspects of VVV Sports. His expertise and commitment to excellence bring "
                + "robust features to the app, ensuring it meets user needs efficiently.\n\n"
                + "Vijay K: A creative problem-solver, Vijay focuses on design and user experience. His attention to detail "
                + "and understanding of sports enthusiasts' needs shape VVV Sports' look and feel, making it enjoyable "
                + "and accessible.\n\n"
                + "Vinay: The energetic force of our team, Vinay contributes insights into sports trends and user preferences. "
                + "His knowledge of the industry helps VVV Sports stay relevant and appealing to a broad audience."
        );

        rightPanel.add(headerLabel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void showTermsContent() {
        rightPanel.removeAll();

        JLabel headerLabel = new JLabel("Terms & Conditions");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea termsArea = new JTextArea();
        termsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        termsArea.setLineWrap(true);
        termsArea.setWrapStyleWord(true);
        termsArea.setEditable(false);
        termsArea.setBackground(new Color(245, 245, 245));
        termsArea.setText(
                "1. VVV Sports is designed for informational purposes only. Use the app at your own risk.\n\n"
                + "2. By using VVV Sports, you agree to our data policies and understand that we may store non-identifiable user data.\n\n"
                + "3. Unauthorized use of this application, including misuse of content, is strictly prohibited.\n\n"
                + "4. The creators of VVV Sports reserve the right to modify the Terms & Conditions at any time.\n\n"
                + "5. For any concerns, please reach out to our support team."
        );

        rightPanel.add(headerLabel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(termsArea), BorderLayout.CENTER);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

}
