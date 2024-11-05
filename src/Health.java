

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Health {
    String usermail;

    public Health(String usermail){
        this.usermail = usermail;
    }
    public JPanel createWorkoutPanel(String titleText, String button1Text, String button2Text, String button3Text, String labelText, Dimension size) {
        // Panel setup
        JPanel sectionPanel = new JPanel();
        sectionPanel.setPreferredSize(size);
        sectionPanel.setMinimumSize(size);
        sectionPanel.setMaximumSize(size);
        sectionPanel.setLayout(new GridBagLayout());
        sectionPanel.setBackground(new Color(226, 101, 25, 255));
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Title Label
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);  // Set text color to white for better contrast
        sectionPanel.add(title, gbc);
    
        // Image Placeholder with Scaled Image
        gbc.gridy++;
        JLabel imagePlaceholder = new JLabel();
        ImageIcon icon = new ImageIcon("src\\dumbell.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);  // Scale the image
        imagePlaceholder.setIcon(new ImageIcon(scaledImage));
        sectionPanel.add(imagePlaceholder, gbc);
    
        // Description Label
        gbc.gridy++;
        JLabel label1 = new JLabel(labelText);
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        label1.setForeground(Color.WHITE);  // Set text color to white
        sectionPanel.add(label1, gbc);
    
        // Buttons Panel
        gbc.gridy++;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Make the panel transparent to inherit background color
    
        // Button 1
        JButton button1 = new JButton(button1Text);
        button1.setPreferredSize(new Dimension(70, 30));
        button1.setBackground(Color.WHITE);
        button1.setForeground(new Color(226, 101, 25, 255));
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button1.setFocusPainted(false);
        button1.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 0));

        // Button hover effect
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button1.setBackground(Color.BLACK);
                button1.setForeground(new Color(226, 101, 25));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button1.setBackground(Color.WHITE);
                button1.setForeground(new Color(226, 101, 25));
            }
        });
         // Enable background color for buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Workout(usermail);

            }
        });
    
        // Button 2
        JButton button2 = new JButton(button2Text);
        button2.setPreferredSize(new Dimension(70, 30));
        button2.setBackground(Color.WHITE);
        button2.setForeground(new Color(226, 101, 25, 255));
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button2.setFocusPainted(false);
        button2.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 0));
        button2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button2.setBackground(Color.BLACK);
                button2.setForeground(new Color(226, 101, 25));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button2.setBackground(Color.WHITE);
                button2.setForeground(new Color(226, 101, 25));
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Diet(usermail);

            }
        });
    
        // Add Button 1 and Button 2 to button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
    
        // Add the button panel to the section
        sectionPanel.add(buttonPanel, gbc);
    
        // Button 3 (Standalone)
        gbc.gridy++;
        JButton button3 = new JButton(button3Text);
        //button3.setPreferredSize(new Dimension(150, 50));
        button3.setPreferredSize(new Dimension(70, 30));
        button3.setBackground(Color.WHITE);
        button3.setForeground(new Color(226, 101, 25, 255));
        button3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button3.setFocusPainted(false);
        button3.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 0));
        button3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button3.setBackground(Color.BLACK);
                button3.setForeground(new Color(226, 101, 25));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button3.setBackground(Color.WHITE);
                button3.setForeground(new Color(226, 101, 25));
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BodyCheck(usermail);
            }
        });
    
        // Add Button 3 to the section panel
        sectionPanel.add(button3, gbc);
    
        return sectionPanel;
    }

    
}
