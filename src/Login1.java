
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login1 {
    JFrame frame = new JFrame("VVV Sports");
    JPanel leftPanel = new JPanel();
    JPanel mainPanel = new JPanel(new CardLayout());
    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    
    private JTextField emailField = new JTextField(20);
    private String enteredEmail;
    private String generatedOTP;

    public Login1() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(10,10,10,255));

        leftPanel = createLeftPanel();
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.WEST);

        mainPanel.add(createEnterPage(), "Enter");
        mainPanel.add(createOTPPage(), "OTP");
        mainPanel.add(createSignUpPage(), "SignUp");
        mainPanel.add(createPasswordPage(), "Password");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel() {
            private Image logoImage;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                if (logoImage == null) {
                    logoImage = new ImageIcon("src\\logo.jpg").getImage();
                }
                g.drawImage(logoImage, 50, 150, 200, 200, this);
            }
        };
        leftPanel.setPreferredSize(new Dimension(300, 600));
        return leftPanel;
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    private JPanel createCommonRightPanel() {
        JPanel greyPanel = new JPanel();
        greyPanel.setBackground(new Color(20, 20, 20));
        greyPanel.setLayout(new GridBagLayout());
        return greyPanel;
    }

    private JPanel createWhiteRectangle() {
        JPanel whiteRectangle = new JPanel();
        whiteRectangle.setBackground(new Color(10,10,10,255));
        whiteRectangle.setBorder(BorderFactory.createLineBorder(new Color(255,101,25,255), 2));
        whiteRectangle.setPreferredSize(new Dimension(350, 300));
        whiteRectangle.setLayout(new GridBagLayout());
        return whiteRectangle;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255,101,25,255));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createEnterPage() {
        JPanel enterPanel = createCommonRightPanel();
        JPanel whitePanel = createWhiteRectangle();

        JLabel titleLabel = new JLabel("E-Mail");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(255,101,25,255));

        emailField.setPreferredSize(new Dimension(300, 30));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(255,101,25,255)));

        JButton loginButton = createStyledButton("Sign In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enteredEmail = emailField.getText().trim();
                switchToPanel("Password");
            }
        });

        JButton signUpButton = createStyledButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enteredEmail = emailField.getText().trim();
                if (EmailSender.isValidEmail(enteredEmail)) {
                    generatedOTP = EmailSender.generateRandomString();
                    try {
                        EmailSender.sendEmail(enteredEmail, "Your OTP", "Your OTP is: " + generatedOTP);
                        switchToPanel("OTP");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error sending email: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        whitePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        whitePanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        whitePanel.add(emailField, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 30, 10, 15);
        whitePanel.add(loginButton, gbc);

        gbc.gridx = 1;
        whitePanel.add(signUpButton, gbc);

        enterPanel.add(whitePanel);
        return enterPanel;
    }

    private JPanel createOTPPage() {
        JPanel OTPPanel = createCommonRightPanel();
        JPanel whitePanel = createWhiteRectangle();

        JLabel OTPtitle = new JLabel("Enter OTP");
        OTPtitle.setFont(new Font("Arial", Font.BOLD, 25));
        OTPtitle.setForeground(new Color(226,101,25,255));

        JTextField OTPfield = new JTextField(20);
        OTPfield.setPreferredSize(new Dimension(300, 30));
        OTPfield.setBorder(BorderFactory.createLineBorder(new Color(255,101,25,255)));

        JButton verifyButton = createStyledButton("Verify");
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (generatedOTP.equals(OTPfield.getText().trim())) {
                    switchToPanel("SignUp");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid OTP!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton resendButton = createStyledButton("Resend");
        resendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EmailSender.isValidEmail(enteredEmail)) {
                    generatedOTP = EmailSender.generateRandomString();
                    try {
                        EmailSender.sendEmail(enteredEmail, "Your OTP", "Your OTP is: " + generatedOTP);
                        JOptionPane.showMessageDialog(frame, "New OTP has been sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error sending email: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        whitePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        whitePanel.add(OTPtitle, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        whitePanel.add(OTPfield, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 30, 10, 15);
        whitePanel.add(verifyButton, gbc);

        gbc.gridx = 1;
        whitePanel.add(resendButton, gbc);

        OTPPanel.add(whitePanel);
        return OTPPanel;
    }
    private JPanel createSignUpPage() {
        JPanel signupPanel = createCommonRightPanel();
        JPanel whitePanel = createWhiteRectangle();
        whitePanel.setPreferredSize(new Dimension(300, 400)); // Increase the size of the panel
    
        JLabel profTitle = new JLabel("Sign Up Details");
        profTitle.setFont(new Font("Arial", Font.BOLD, 25));
        
        JTextField ageField = new JTextField(20);
        ageField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextField expField = new JTextField(20);
        expField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextField genderField = new JTextField(20);
        genderField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JTextField weightField = new JTextField(20);
        weightField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JTextField heightField = new JTextField(20);
        heightField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JButton signUpButton = new JButton("Complete Sign Up");
        signUpButton.setFont(new Font("ARIAL", Font.PLAIN, 19));
        signUpButton.setBackground(new Color(226, 101, 55));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
            int age, experience;
            String gender, password;
            double weight, height;
            @Override
            
            public void actionPerformed(ActionEvent e) {
                // Logic to handle user signup
                try {
                    age = Integer.parseInt(ageField.getText());
                    experience = Integer.parseInt(expField.getText());
                    gender = genderField.getText();
                    weight = Double.parseDouble(weightField.getText());
                    height = Double.parseDouble(heightField.getText());
                    password = new String(passwordField.getPassword());
                } catch (NumberFormatException n) {
                    // Handle the error: show a message to the user or log it
                    System.out.println("Please enter valid numeric values.");
                }
                
                Database db = new Database();
                db.insertUser(enteredEmail,password,age,experience,gender,weight,height);
                new DoActivity(enteredEmail);
                frame.dispose();
                
            }
        });
    
        whitePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        whitePanel.add(profTitle, gbc);
    
        // Add Labels and Fields
        String[] labels = {"Age:", "Experience:", "Gender:", "Weight:", "Height:", "Password:"};
        JTextField[] fields = {ageField, expField, genderField, weightField, heightField, passwordField};
    
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy++;
            gbc.gridwidth = 1;
            JLabel j = new JLabel(labels[i]);
            j.setFont(new Font("ARIAL", Font.PLAIN,17));
            j.setForeground(Color.LIGHT_GRAY);
            whitePanel.add(j, gbc);
            gbc.gridx = 1;
            whitePanel.add(fields[i], gbc);
            gbc.gridx = 0; // Reset x position for next label
        }
    
        gbc.gridy++;
        gbc.gridwidth = 2;
        whitePanel.add(signUpButton, gbc);
    
        signupPanel.add(whitePanel);
        return signupPanel;
    }

    private JPanel createPasswordPage() {
        JPanel passwordPanel = createCommonRightPanel();
        passwordPanel.setBackground(new Color(30, 30, 30)); // Extremely dark grey background
        
        JPanel whitePanel = createWhiteRectangle();
        
        JLabel passwordTitle = new JLabel("Enter Password");
        passwordTitle.setFont(new Font("Arial", Font.BOLD, 28));
        passwordTitle.setForeground(new Color(226, 101, 25, 255)); // Updated orange color for clarity
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(226, 101, 25, 255)));
        passwordField.setPreferredSize(new Dimension(300, 35));
        
        JButton backButton = createStyledButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel("Enter");
            }
        });
        
        JButton nextButton = createStyledButton("Next");
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword()).trim();
                Database db = new Database();
                int validationResult = db.validateUser(enteredEmail, password);
    
                if (validationResult == 1) {
                    
                    new DoActivity(enteredEmail);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        whitePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        whitePanel.add(passwordTitle, gbc);
        
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        whitePanel.add(passwordField, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 0, 10);
        whitePanel.add(backButton, gbc);
        
        gbc.gridx = 1;
        gbc.insets = new Insets(15, 10, 0, 0);
        whitePanel.add(nextButton, gbc);
        
        passwordPanel.add(whitePanel);
        return passwordPanel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login1());
    }
}
