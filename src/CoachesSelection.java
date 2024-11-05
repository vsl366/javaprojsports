import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Coach {
    String name;
    String experienceLevel;
    String cost;
    String image;

    public Coach(String name, String cost, String experienceLevel, String image) {
        this.name = name;
        this.cost = cost;
        this.experienceLevel = experienceLevel;
        this.image = image;
    }
}

public class CoachesSelection {
    JFrame frame;
    List<Coach> coaches;
    String usermail;
    Database db;

    public CoachesSelection(String usermail) {
        this.usermail = usermail;
        db = new Database();

        // Fetch only unpurchased coaches
        List<Map<String, Object>> coachData = db.getAvailableCoaches(usermail);
        coaches = new ArrayList<>();
        for (Map<String, Object> data : coachData) {
            String name = (String) data.get("coachname");
            String experience = String.valueOf(data.get("coachexperience"));
            String cost = String.valueOf(data.get("cost"));
            String imagePath = (String) data.get("imagepath");

            Coach coach = new Coach(name, cost, experience, imagePath);
            coaches.add(coach);
        }

        frame = new JFrame("VVV Sports - Coaches Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);  // Set frame background to dark gray

        JPanel upPanel = createUpPanel();
        JPanel downPanel = createDownPanel();
        JPanel frameadd = createPanel();

        JScrollPane scrollpanel = new JScrollPane(frameadd);
        scrollpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpanel.getVerticalScrollBar().setUnitIncrement(16);

        frame.setLayout(new BorderLayout());
        frame.add(upPanel, BorderLayout.NORTH);
        frame.add(scrollpanel, BorderLayout.CENTER);
        frame.add(downPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public JPanel createPanel() {
        JPanel outer = new JPanel();
        outer.setBackground(new Color(15,15,15));  // Set outer panel background to dark gray
        outer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 50, 20, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (Coach coach : coaches) {
            JPanel inner = createCoachPanel(coach);
            outer.add(inner, gbc);
            gbc.gridx++;
            if (gbc.gridx >= 2) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        return outer;
    }

    private JPanel createCoachPanel(Coach coach) {
        JPanel inn = new JPanel();
        inn.setPreferredSize(new Dimension(300, 500));
        inn.setBackground(new Color(226,101,25,255));  // Dark gray background for coach panel
        JLabel coachText = new JLabel(coach.name);
        coachText.setForeground(Color.WHITE);
        coachText.setFont(new Font("Arial", Font.BOLD, 25));

        ImageIcon image = new ImageIcon(coach.image);
        Image scaledImg = image.getImage().getScaledInstance(250, 240, Image.SCALE_SMOOTH);
        JButton coachPic = new JButton(new ImageIcon(scaledImg));
        coachPic.addActionListener(e -> System.out.println(coach.name + " clicked"));
        coachPic.setBackground(Color.DARK_GRAY);

        JLabel experienceLabel = new JLabel("Experience: " + coach.experienceLevel);
        experienceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        experienceLabel.setForeground(Color.WHITE);

        JLabel costLabel = new JLabel("Cost: " + coach.cost);
        costLabel.setFont(new Font("Arial", Font.BOLD, 20));
        costLabel.setForeground(Color.WHITE);

        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.BLACK);  // Slightly lighter gray for button
        addButton.setForeground(new Color(226,101,25,255));
        addButton.addActionListener(e -> showBankDetailsDialog(coach.name));

        inn.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inn.add(coachText, gbc);
        gbc.gridy++;
        inn.add(coachPic, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        inn.add(experienceLabel, gbc);
        gbc.gridy++;
        inn.add(costLabel, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        inn.add(addButton, gbc);

        return inn;
    }

    private JPanel createUpPanel() {
        JPanel up = new JPanel();
        up.setBackground(new Color(226,101,25,255));
        up.setPreferredSize(new Dimension(1000, 70));
        up.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("VVV SPORTS");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 25));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

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

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("ARIAL", Font.PLAIN, 17));
        backButton.setBackground(new Color(226,101,25,255));
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> frame.dispose());
        backButton.setBorderPainted(false);

        leftPanel.add(menuButton);
        leftPanel.add(backButton);

        JButton logOut = new JButton("LOGOUT");
        logOut.setBackground(new Color(226,101,25,255));
        logOut.setForeground(Color.BLACK);
        logOut.setPreferredSize(new Dimension(100, 20));
        logOut.setBorderPainted(false);
        logOut.addActionListener(e -> System.exit(0));

        up.add(leftPanel, BorderLayout.WEST);
        up.add(nameLabel, BorderLayout.CENTER);
        up.add(logOut, BorderLayout.EAST);

        return up;
    }

    private JPanel createDownPanel() {
        JPanel down = new JPanel();
        down.setBackground(new Color(226,101,25,255));
        down.setPreferredSize(new Dimension(1000, 70));
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

    private void showBankDetailsDialog(String coachName) {
        JTextField accntField = new JTextField(10);
        JTextField passField = new JPasswordField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Account Number:"));
        panel.add(accntField);
        panel.add(new JLabel("Account Password:"));
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Enter Bank Details for " + coachName, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String accntno = accntField.getText();
            String accntpass = passField.getText();

            Coach selectedCoach = coaches.stream().filter(c -> c.name.equals(coachName)).findFirst().orElse(null);
            if (selectedCoach != null && db.canAffordCoach(accntno, accntpass, new BigDecimal(selectedCoach.cost))) {
                db.addCoachToUser(usermail, coachName);
                JOptionPane.showMessageDialog(frame, "Coach " + coachName + " added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
