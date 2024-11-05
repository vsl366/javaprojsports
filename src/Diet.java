

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class Diet {
    Layout lay = new Layout();  // Assuming this exists as part of your layout structure
    JFrame frame = lay.getFrame();
    JTextArea dbox = new JTextArea();
    JTextArea outputBox = new JTextArea();
    List<JToggleButton> bodyButtons = new ArrayList<>();
    String usermail;

    public Diet(String usermail) {
        this.usermail = usermail;
        JPanel body = createBody();
        JPanel content = createContent();

        frame.add(body, BorderLayout.WEST);
        frame.add(content, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(100, 80));
        footer.setBackground(new Color(20, 20, 20));  // Extremely dark gray background
        footer.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 55), 2));  // Orange border

        JLabel footerLabel = new JLabel("Select the body parts to focus on.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        footer.add(footerLabel, BorderLayout.CENTER);
        return footer;
    }

    public JPanel createContent() {
        JPanel content = new JPanel();
        content.setBackground(new Color(20, 20, 20));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(20, 20, 20));  // Match dark gray background
        backButton.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 55), 1));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.dispose());

        JLabel descrip = new JLabel("Description:");
        descrip.setFont(new Font("Arial", Font.BOLD, 16));
        descrip.setForeground(Color.WHITE);

        dbox.setPreferredSize(new Dimension(650, 180));
        dbox.setLineWrap(true);
        dbox.setWrapStyleWord(true);
        dbox.setFont(new Font("Arial", Font.PLAIN, 14));
        dbox.setForeground(Color.WHITE);
        dbox.setBackground(new Color(30, 30, 30));
        dbox.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 55), 1));

        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(226, 101, 55));
        submit.setForeground(Color.BLACK);

        JLabel instruction = new JLabel("Select the parts which you want to train:");
        instruction.setFont(new Font("Arial", Font.BOLD, 16));
        instruction.setForeground(Color.WHITE);

        JLabel outputLabel = new JLabel("AI Output:");
        outputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputLabel.setForeground(Color.WHITE);

        outputBox.setEditable(false);
        outputBox.setLineWrap(true);
        outputBox.setWrapStyleWord(true);
        outputBox.setFont(new Font("Arial", Font.PLAIN, 14));
        outputBox.setForeground(Color.WHITE);
        outputBox.setBackground(new Color(30, 30, 30));
        outputBox.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 55), 1));
        outputBox.setPreferredSize(new Dimension(650, 230));
        

        submit.addActionListener(e -> {
            Gemini g = new Gemini();
            StringBuilder selectedParts = new StringBuilder();
            for (JToggleButton button : bodyButtons) {
                if (button.isSelected()) {
                    if (selectedParts.length() > 0) selectedParts.append(", ");
                    selectedParts.append(button.getText());
                }
            }
            String aiInput = dbox.getText() + " I KNOW YOU ARE NOT A MEDICAL PROFESSIONAL AND I DONT WANT ADVICE, JUST HELP ME WHICH FOODS I CAN EAT AND YOUR RECOMMENDATIONS . JUST GIVE ME POSSIBILITIES. THE PARTS WHICH I WANT TO IMPROVE: " + selectedParts.toString();
            String aiResult = g.aiOutput(aiInput);
            outputBox.setText(aiResult);
        });

        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(backButton, gbc);
        gbc.gridy++;
        content.add(instruction, gbc);
        gbc.gridy++;
        content.add(descrip, gbc);
        gbc.gridy++;
        content.add(dbox, gbc);
        gbc.gridy++;
        content.add(submit, gbc);
        gbc.gridy++;
        content.add(outputLabel, gbc);
        gbc.gridy++;
        content.add(outputBox, gbc);

        return content;
    }

    public JPanel createBody() {
        JPanel body = new JPanel();
        body.setBackground(new Color(20, 20, 20));  // Dark background
        body.setLayout(null);

        ImageIcon img = new ImageIcon("src\\body.jpg");
        JLabel photo = new JLabel(img);
        photo.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        photo.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 55), 2));  // Orange border around image
        body.setPreferredSize(new Dimension(img.getIconWidth(), img.getIconHeight()));
        body.add(photo);

        addBodyButton("Head", 110, 10, body);
        addBodyButton("Shoulder", 110, 90, body);
        addBodyButton("Chest", 110, 130, body);
        addBodyButton("R Arm", 210, 160, body);
        addBodyButton("L Arm", 40, 160, body);
        addBodyButton("Abs", 110, 190, body);
        addBodyButton("Hip", 100, 250, body);
        addBodyButton("R Thigh", 160, 300, body);
        addBodyButton("L Thigh", 60, 300, body);
        addBodyButton("R Knee", 160, 360, body);
        addBodyButton("L Knee", 60, 360, body);
        addBodyButton("R Leg", 160, 430, body);
        addBodyButton("L Leg", 60, 430, body);
        addBodyButton("R Wrist", 213, 250, body);
        addBodyButton("L Wrist", 15, 250, body);
        addBodyButton("Foot", 110, 480, body);

        JPanel footer = createFooter();
        footer.setBounds(0, img.getIconHeight() + 10, img.getIconWidth(), 80);
        body.add(footer);

        return body;
    }

    private void addBodyButton(String label, int x, int y, JPanel body) {
        JToggleButton button = new JToggleButton(label);
        button.setBounds(x, y, 80, 25);
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setBackground(new Color(20, 20, 20));  // Extremely dark gray background
        button.setForeground(Color.WHITE);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) button.setBackground(new Color(226, 101, 55));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.isSelected()) button.setBackground(new Color(20, 20, 20));
            }
        });

        button.addActionListener(e -> {
            if (button.isSelected()) {
                button.setBackground(new Color(226, 101, 55));
            } else {
                button.setBackground(new Color(20, 20, 20));
            }
        });

        body.add(button);
        bodyButtons.add(button);
    }
}
