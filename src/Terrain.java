

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Terrain {
    Layout lay = new Layout();
    JFrame frame = lay.getFrame();
    JButton[] button = new JButton[5];
    boolean[] isSelected = new boolean[5];
    String usermail;

    public Terrain(String usermail) {
        this.usermail = usermail;
        JPanel frameadd = createPanel();
        frame.add(frameadd, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(10,10,10,255));
        panel.setPreferredSize(new Dimension(500, 700));

        panel.setLayout(new GridBagLayout());

        // Add a label at the top for "Terrain Type"
        JLabel terrainLabel = new JLabel("Terrain Type");
        terrainLabel.setForeground(Color.WHITE);
        terrainLabel.setFont(new Font("Arial", Font.BOLD, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(terrainLabel, gbc);

        // Create buttons for different terrain types
        final String buttonName[] = {"Uphill", "Downhill", "Flat", "Rough", "Smooth"};
        for (int i = 0; i < 5; i++) {
            button[i] = new JButton(buttonName[i]);
            isSelected[i] = false;
            button[i].setBackground(Color.WHITE);
            button[i].setForeground(new Color(226, 101, 55));
            Border thickBorder = BorderFactory.createLineBorder(new Color(226,101,55), 10);
            button[i].setBorder(thickBorder);
            button[i].setPreferredSize(new Dimension(75, 90)); // Set button size
            int index = i;
            button[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonSelection(index);
                }
            });
        }

        // Adjust button sizes to take up more space
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;  // Reset gridwidth
        gbc.gridy++;  // Move to the next row

        // Add buttons in 2 rows: first row (Uphill, Downhill, Flat)
        for (int i = 0; i < 5; i++) {
            
            panel.add(button[i], gbc);
            gbc.gridy++;
        }

        

        // Add "Go" button to the southeast (bottom-right)
        JButton goButton = new JButton("Go");
        goButton.setBackground(Color.WHITE);
        goButton.setForeground(new Color(226, 101, 55));
         // Increase the size of the Go button
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Shoe();
                frame.dispose();
                
            }
        });
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridx = 2;  // Place Go button at the right end
        gbc.gridy = 3;  // Place Go button after the terrain buttons
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        panel.add(goButton, gbc);

        return panel;
    }

    public void handleButtonSelection(int index) {
        if (!isSelected[index]) {
            for (int i = 0; i < button.length; i++) {
                if (i != index) {
                    button[i].setEnabled(false);
                } else if (i == index) {
                    isSelected[index] = true;
                }
            }
        } else {
            for (int i = 0; i < button.length; i++) {
                button[i].setEnabled(true);
            }
            isSelected[index] = false;
        }
    }

}
