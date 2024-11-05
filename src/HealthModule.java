import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface HealthModule {
    JFrame frame = new JFrame();
    JTextArea dbox = new JTextArea();
    JTextArea outputBox = new JTextArea();
    List<JToggleButton> bodyButtons = new ArrayList<>();

    default void initialize(String usermail) {
        JPanel body = createBody();
        JPanel content = createContent();

        frame.add(body, BorderLayout.WEST);
        frame.add(content, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    JLabel createInstructionLabel(); // Customize instruction label for each module

    String generateAIInput(String userInput, String selectedParts); // Customize AI input for each module

    default JPanel createContent() {
        JPanel content = new JPanel();
        JLabel descrip = new JLabel("Description:");
        descrip.setFont(new Font("Arial", Font.BOLD, 18));

        dbox.setPreferredSize(new Dimension(550, 130));
        dbox.setLineWrap(true);
        dbox.setWrapStyleWord(true);
        dbox.setFont(new Font("Arial", Font.PLAIN, 12));
        dbox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(226, 101, 55));
        submit.setForeground(Color.BLACK);

        JLabel instruction = createInstructionLabel();

        outputBox.setEditable(false);
        outputBox.setLineWrap(true);
        outputBox.setWrapStyleWord(true);
        outputBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        outputBox.setPreferredSize(new Dimension(550, 180));

        submit.addActionListener(e -> {
            Gemini g = new Gemini();
            String userInput = dbox.getText();

            // Collect selected body parts
            StringBuilder selectedParts = new StringBuilder();
            for (JToggleButton button : bodyButtons) {
                if (button.isSelected()) {
                    if (selectedParts.length() > 0) selectedParts.append(", ");
                    selectedParts.append(button.getText());
                }
            }

            // Formulate AI input based on module-specific implementation
            String aiInput = generateAIInput(userInput, selectedParts.toString());
            String aiResult = g.aiOutput(aiInput);
            outputBox.setText(aiResult);
        });

        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(instruction, gbc);
        gbc.gridy++;
        content.add(descrip, gbc);
        gbc.gridy++;
        content.add(dbox, gbc);
        gbc.gridy++;
        content.add(submit, gbc);
        gbc.gridy++;
        content.add(new JLabel("AI Output:"), gbc);
        gbc.gridy++;
        content.add(outputBox, gbc);

        return content;
    }

    default JPanel createBody() {
        JPanel body = new JPanel();
        body.setLayout(null);

        ImageIcon img = new ImageIcon("src\\body.jpg");
        JLabel photo = new JLabel(img);
        photo.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        body.setPreferredSize(new Dimension(img.getIconWidth(), img.getIconHeight()));

        // List of body parts and positions
        addBodyButton("Head", 110, 10, body);
        addBodyButton("Shoulder", 110, 90, body);
        // Add more buttons as needed

        body.add(photo);
        return body;
    }

    default void addBodyButton(String label, int x, int y, JPanel body) {
        JToggleButton button = new JToggleButton(label);
        button.setBounds(x, y, 80, 25);
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) button.setBackground(new Color(226, 101, 55));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) button.setBackground(Color.LIGHT_GRAY);
            }
        });

        button.addActionListener(e -> button.setBackground(button.isSelected() ? new Color(226, 101, 55) : Color.LIGHT_GRAY));

        body.add(button);
        bodyButtons.add(button);
    }
}
