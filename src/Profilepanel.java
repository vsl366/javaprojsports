import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;
import java.util.Map;

public class Profilepanel {
    Layout lay = new Layout();
    JFrame frame = lay.getFrame();
    String usermail;
    Database db = new Database(); // Database instance

    public Profilepanel(String usermail) {
        this.usermail = usermail;
        JPanel body = createProfilepanel();
        frame.add(body);
        frame.setVisible(true);
        
    }

    public JPanel createProfilepanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);  // Set left panel background to black

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(226, 101, 55));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> frame.dispose());

        JLabel Head = new JLabel("Profile");
        Head.setFont(new Font("Arial", Font.BOLD, 25));
        Head.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        JLabel Mail = new JLabel("Mail : " + usermail);
        Mail.setFont(new Font("Arial", Font.BOLD, 20));
        Mail.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        // Fetch user details
        Map<String, Object> userDetails = db.getUserDetails(usermail);

        JLabel Age = new JLabel("Age : " + userDetails.get("age"));
        Age.setFont(new Font("Arial", Font.BOLD, 20));
        Age.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        JLabel Height = new JLabel("Height : " + userDetails.get("height"));
        Height.setFont(new Font("Arial", Font.BOLD, 20));
        Height.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        JLabel Weight = new JLabel("Weight : " + userDetails.get("weight"));
        Weight.setFont(new Font("Arial", Font.BOLD, 20));
        Weight.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        JLabel Experience = new JLabel("Experience : " + userDetails.get("experience"));
        Experience.setFont(new Font("Arial", Font.BOLD, 20));
        Experience.setForeground(new Color(226, 101, 25, 255));  // Set text color to orange

        JButton edit1 = new JButton("Edit");
        edit1.setPreferredSize(new Dimension(60, 30));
        edit1.setForeground(Color.BLACK);
        edit1.addActionListener(e -> {
            String updatedHeight = JOptionPane.showInputDialog(panel, "Enter new Height:");
            if (updatedHeight != null) {
                db.updateHeight(usermail, Double.parseDouble(updatedHeight));
                Height.setText("Height: " + updatedHeight);
            }
        });

        JButton edit2 = new JButton("Edit");
        edit2.setPreferredSize(new Dimension(60, 30));
        edit2.setForeground(Color.BLACK);
        edit2.addActionListener(e -> {
            String updatedWeight = JOptionPane.showInputDialog(panel, "Enter new Weight:");
            if (updatedWeight != null) {
                db.updateWeight(usermail, Double.parseDouble(updatedWeight));
                Weight.setText("Weight: " + updatedWeight);
            }
        });

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 50, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(backButton, gbc);

        gbc.insets = new Insets(50, 10, 10, 10);
        gbc.gridy++;
        panel.add(Head, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy++;
        panel.add(Mail, gbc);
        gbc.gridy++;
        panel.add(Age, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(Height, gbc);
        gbc.gridx = 1;
        panel.add(edit1, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(Weight, gbc);
        gbc.gridx = 1;
        panel.add(edit2, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(Experience, gbc);

        return panel;
    }

     JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Activity/Reports");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = {"Gear Name", "Gear Type", "KM Used", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Custom cell renderer to highlight specific rows in mild red
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        // Fetch and display gear data
        List<String[]> gearData = db.fetchGearList(usermail);
        for (String[] rowData : gearData) {
            model.addRow(rowData);
        }

        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), model));

        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(new Color(226, 101, 55));

        addButton.addActionListener(e -> {
            JTextField gearNameField = new JTextField();
            JTextField gearTypeField = new JTextField();
            JTextField kmUsedField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            inputPanel.add(new JLabel("Gear Name:"));
            inputPanel.add(gearNameField);
            inputPanel.add(new JLabel("Gear Type:"));
            inputPanel.add(gearTypeField);
            inputPanel.add(new JLabel("KM Used:"));
            inputPanel.add(kmUsedField);

            int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Gear Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                db.addGear(usermail, gearNameField.getText(), gearTypeField.getText(), Double.parseDouble(kmUsedField.getText()));
                model.addRow(new Object[]{gearNameField.getText(), gearTypeField.getText(), kmUsedField.getText(), "Remove"});
            }
        });

        rightPanel.add(headerLabel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(addButton, BorderLayout.SOUTH);

        return rightPanel;
    }

    // Custom TableCellRenderer to highlight rows based on conditions
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String gearType = (String) table.getValueAt(row, 1);
            double kmUsed = Double.parseDouble(table.getValueAt(row, 2).toString());

            // Check conditions and set background color
            if ((gearType.equalsIgnoreCase("Shoe") && kmUsed > 500) || 
                (gearType.equalsIgnoreCase("Bike") && kmUsed > 5000)) {
                cell.setBackground(new Color(255, 204, 204)); // Mild red color
            } else {
                cell.setBackground(Color.WHITE); // Default color
            }

            return cell;
        }
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.BLACK);
            setForeground(new Color(226, 101, 55));
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Remove");
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label = "Remove";
        private int row;
        private boolean clicked;
    
        public ButtonEditor(JCheckBox checkBox, DefaultTableModel model) {
            super(checkBox);
    
            button = new JButton(label);
            button.setBackground(Color.BLACK);
            button.setForeground(new Color(226, 101, 55));
            button.setOpaque(true);
    
            button.addActionListener(e -> {
                // Ask for confirmation before removing
                int confirm = JOptionPane.showConfirmDialog(button, "Are you sure you want to remove this gear?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String gearName = model.getValueAt(row, 0).toString();
                    db.removeGear(usermail, gearName);
                    model.removeRow(row);
                }
                clicked = true;
                fireEditingStopped(); // Stop editing after the action is taken
            });
        }
    
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label); // Ensure the text remains "Remove"
            clicked = false;
            return button;
        }
    
        @Override
        public Object getCellEditorValue() {
            return clicked ? label : ""; // Return the correct value to avoid "false" display
        }
    }
}    