import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivitySelector {
    JFrame frame;
    JPanel mainPanel;
    JButton[] activityButtons = new JButton[7];
    static String usermail;
    String[] gearlist;
    int mycurrpt;

    // Instance variables to store user selections
    private String selectedActivity;
    private String selectedTerrain;
    private String selectedGear;
    private int enteredKilometers;
    private int enteredDuration;

    public ActivitySelector(String usermail) {
        ActivitySelector.usermail = usermail;
        frame = new JFrame("Activity Selector");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        mainPanel = createMainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static int calculatePoints(String selectedActivity, String selectedTerrain, int enteredKilometers, int enteredDuration, String usermail) {
        // Base points per activity
        int basePoints;
        switch (selectedActivity) {
            case "Running":
                basePoints = 10;
                break;
            case "Jogging":
                basePoints = 8;
                break;
            case "Walking":
                basePoints = 5;
                break;
            case "Sprinting":
                basePoints = 12;
                break;
            case "Cycling":
                basePoints = 9;
                break;
            case "Swimming":
                basePoints = 11;
                break;
            case "Exercise":
                basePoints = 6;
                break;
            default:
                basePoints = 4;
        }
    
        // Terrain multiplier based on activity's terrain or setting
        double terrainMultiplier;
        switch (selectedTerrain) {
            case "Uphill":
            case "Fast Flowing Water":
            case "Heavy Lifting":
                terrainMultiplier = 1.3;
                break;
            case "Downhill":
            case "Rough":
                terrainMultiplier = 1.2;
                break;
            case "Flat":
            case "Smooth":
            case "Still Water":
                terrainMultiplier = 1.1;
                break;
            default:
                terrainMultiplier = 1.0;
        }
    
        // Fetch user experience from the database
        Database db = new Database();
        int experience = db.fetchUserExperience(usermail);
        double experienceFactor = Math.max(0.8, 20.0 / (experience + 10)); // Higher experience lowers points but never below 0.8
    
        // Time of day multiplier for early or late hours
        int currentHour = java.time.LocalTime.now().getHour();
        double timeMultiplier = (currentHour < 6 || currentHour > 20) ? 1.2 : 1.0;
    
        // Calculate points based on duration or distance
        int pt;
        if (selectedActivity.equals("Exercise")) {
            pt = (int) (basePoints * terrainMultiplier * experienceFactor * timeMultiplier * (enteredDuration / 20.0));
        } else {
            pt = (int) (basePoints * terrainMultiplier * experienceFactor * timeMultiplier * (enteredKilometers / 2.0));
        }
    
        return Math.max(pt, 1); // Ensure at least 1 point is awarded
    }
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Top panel for title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Choose Activity Type");
        titleLabel.setForeground(new Color(245, 145, 65)); // Bright orange for title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Back Button on the left
        JButton backButton = createBackButton();
        topPanel.add(backButton, BorderLayout.WEST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center panel for activity buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 100;
        gbc.ipady = 20;

        final String[] activityNames = {"Running", "Jogging", "Walking", "Sprinting", "Cycling", "Swimming", "Exercise"};
        for (int i = 0; i < activityNames.length; i++) {
            activityButtons[i] = new JButton(activityNames[i]);
            activityButtons[i].setBackground(new Color(45, 45, 45)); // Dark gray
            activityButtons[i].setForeground(new Color(255, 165, 75)); // Brighter orange for text
            activityButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            activityButtons[i].setFocusPainted(false);
            activityButtons[i].setBorder(BorderFactory.createLineBorder(new Color(255, 140, 70), 2));

            final int index = i; // Capture current index
            activityButtons[i].addActionListener(e -> handleActivitySelection(activityNames[index]));

            gbc.gridy = i;
            centerPanel.add(activityButtons[i], gbc);
        }

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton(new ImageIcon("src\\back.jpg"));
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.dispose());
        return backButton;
    }

    private void handleActivitySelection(String activity) {
        selectedActivity = activity;
        Database db = new Database();
        if (activity.equals("Swimming")) {
            this.gearlist = db.fetchRelGear(usermail,"other" );
            showOptionsDialog("Swimming Options", new String[]{"Fast Flowing Water", "Still Water", "Butterfly", "Free Style"}, false);
        } else if (activity.equals("Exercise")) {
            this.gearlist = db.fetchRelGear(usermail,"other" );
            showOptionsDialog("Exercise Types", new String[]{"Yoga", "Calisthenics", "Endurance", "Heavy Lifting", "Outdoor"}, true);
        } else if(activity.equals("Cycling")){
            this.gearlist = db.fetchRelGear(usermail,"bike" );
            showOptionsDialog("Terrain Types", new String[]{"Uphill", "Downhill", "Flat", "Rough", "Smooth"}, false);
        } else{
            this.gearlist = db.fetchRelGear(usermail,"shoe" );
            showOptionsDialog("Terrain Types", new String[]{"Uphill", "Downhill", "Flat", "Rough", "Smooth"}, false);
        }
    }

    private void showOptionsDialog(String title, String[] options, boolean hasDuration) {
        JPanel panel = new JPanel(new GridLayout(options.length + 1, 1, 5, 5));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.BLACK);

        JDialog dialog = new JDialog(frame, title, true);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            checkBox.setForeground(new Color(255, 140, 70)); // Orange for options
            checkBox.setBackground(Color.BLACK);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 16));
            buttonGroup.add(checkBox);
            panel.add(checkBox);
        }

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(75, 75, 75));
        okButton.setForeground(new Color(255, 145, 65)); // Orange for OK button
        okButton.addActionListener(e -> {
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JCheckBox && ((JCheckBox) comp).isSelected()) {
                    selectedTerrain = ((JCheckBox) comp).getText();
                    break;
                }
            }
            dialog.dispose();
            if (hasDuration) {
                promptForDuration();
            } else {
                promptForKilometers();
            }
        });
        panel.add(okButton);

        dialog.getContentPane().add(panel);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void promptForKilometers() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel("Enter Kilometers:");
        label.setForeground(new Color(255, 140, 70)); // Orange label
        JTextField textField = new JTextField(10);
        panel.add(label);
        panel.add(textField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Distance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                enteredKilometers = Integer.parseInt(textField.getText());

                displayGearSelection(gearlist);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for kilometers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                promptForKilometers();
            }
        }
    }

    private void promptForDuration() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel("Enter Duration (minutes):");
        label.setForeground(new Color(255, 140, 70)); // Orange label
        JTextField textField = new JTextField(10);
        panel.add(label);
        panel.add(textField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Duration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                enteredDuration = Integer.parseInt(textField.getText());
                displayGearSelection(gearlist);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for duration.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                promptForDuration();
            }
        }
    }

    private void displayGearSelection(String[] gears) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel("Select Gear:");
        Database db = new Database();
        label.setForeground(new Color(255, 140, 70)); // Orange label for gear selection
        JComboBox<String> comboBox = new JComboBox<>(gears);
        comboBox.setBackground(new Color(50, 50, 50)); // Darker gray background
        comboBox.setForeground(Color.WHITE);
        panel.add(label);
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Gear Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            selectedGear = (String) comboBox.getSelectedItem();
            if (selectedGear != null && enteredKilometers!= 0 && enteredDuration == 0) {
                
                db.updateLastKm(usermail, selectedGear, enteredKilometers);
            }
            mycurrpt =  calculatePoints(selectedActivity, selectedTerrain, enteredKilometers,enteredDuration, usermail);
            int upex = db.updatePointsAndExperience(usermail, mycurrpt);
            if(upex == 1){
                JOptionPane.showMessageDialog(frame, "Level up! Your experience has increased.", "Level Up", JOptionPane.INFORMATION_MESSAGE);

            }
            System.out.println("Selected Activity: " + selectedActivity);
            System.out.println("Selected Terrain: " + selectedTerrain);
            System.out.println("Entered Kilometers: " + enteredKilometers);
            System.out.println("Entered Duration: " + enteredDuration);
            System.out.println("Selected Gear: " + selectedGear);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ActivitySelector(usermail));
    }
}