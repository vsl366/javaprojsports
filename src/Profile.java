
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Profile {

    String usermail;

    public Profile(String usermail){
        this.usermail = usermail;
    }

    public JPanel createProfilePanel(Dimension size) {
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
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding within each section

        // Title Label
        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);  // Set text color to white for better contrast
        sectionPanel.add(title, gbc);

        // Image Placeholder with Scaled Image
        gbc.gridy++;
        JLabel imagePlaceholder = new JLabel();
        ImageIcon icon = new ImageIcon("bin\\userpic.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);  // Scale the image
        imagePlaceholder.setIcon(new ImageIcon(scaledImage));
        sectionPanel.add(imagePlaceholder, gbc);

        // Description Label
        gbc.gridy++;
        JLabel label = new JLabel("Catch Up your Profile");
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.WHITE);  // Set text color to white
        sectionPanel.add(label, gbc);

        // First Button
        gbc.gridy++;
        // Button with hover effect
        gbc.gridy++;
        JButton button = new JButton("My Profile");
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

                new Profilepanel(usermail);
            }
        });
        sectionPanel.add(button, gbc);


        

        return sectionPanel;
    }
}
