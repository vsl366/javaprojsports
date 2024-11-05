

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Coaches {
    String usermail;
    public Coaches(String usermail){
        this.usermail = usermail;
    }
    public JPanel createCoachesPanel(String titleText, String labelText, String button1Text, String button2Text, Dimension size) {
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
        title.setForeground(Color.WHITE);  // Text color for better contrast
        sectionPanel.add(title, gbc);
    
        // Image Placeholder with Scaled Image
        gbc.gridy++;
        JLabel imagePlaceholder = new JLabel();
        ImageIcon icon = new ImageIcon("src\\mycoaches.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);  // Scale the image
        imagePlaceholder.setIcon(new ImageIcon(scaledImage));
        sectionPanel.add(imagePlaceholder, gbc);
    
        // Description Label
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.WHITE);  // Set text color to white
        sectionPanel.add(label, gbc);
    
        // Buttons Panel
        gbc.gridy++;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Make the panel transparent to inherit background color
    
        JButton button1 = new JButton(button1Text);
        button1.setPreferredSize(new Dimension(100, 50));
        button1.setBackground(Color.WHITE);
        button1.setForeground(new Color(226, 101, 25));
        button1.setFocusPainted(false);
        button1.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 2));
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

                new CoachesSelection(usermail);
            }
        });
    
        JButton button2 = new JButton(button2Text);
        button2.setPreferredSize(new Dimension(100, 50));
        button2.setBackground(Color.WHITE);
        button2.setForeground(new Color(226, 101, 25));
        button2.setFocusPainted(false);
        button2.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25), 2));
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effect
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
 // Enable background color for buttons
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContactCoach(usermail);
            }
        });
    
        // Add buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
    
        // Add button panel to the main section panel
        sectionPanel.add(buttonPanel, gbc);
    
        return sectionPanel;
    }
    
}
