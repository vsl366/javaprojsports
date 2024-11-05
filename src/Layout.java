
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Layout {
    JFrame frame = new JFrame("VVV Sports");
    JPanel upPanel = new JPanel();
    JPanel downPanel = new JPanel();

    public Layout() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        upPanel = createUpPanel();
        frame.setLayout(new BorderLayout());
        frame.add(upPanel, BorderLayout.NORTH);

        downPanel = createDownPanel();
        frame.add(downPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
     public JFrame getFrame(){
        return frame;
     }

    private JPanel createUpPanel() {
        JPanel up = new JPanel();
        up.setBackground(new Color(226, 101, 25, 255));
        up.setPreferredSize(new Dimension(1000, 70));
        up.setLayout(new BorderLayout()); // Keep BorderLayout for organizing components

        // Create and customize the label
        JLabel Name = new JLabel("VVV SPORTS");
        Name.setFont(new Font("Arial", Font.BOLD, 25)); 
        Name.setForeground(Color.BLACK);
        Name.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally

   // Create the LogOut button
JButton LogOut = new JButton("LOGOUT");
LogOut.setBackground(new Color(226, 101, 25, 255));
LogOut.setForeground(Color.BLACK);
LogOut.setPreferredSize(new Dimension(100, 20)); // Increase size for better visibility
LogOut.setBorderPainted(false);
LogOut.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Terminate the program
        System.exit(0);
    }
});

       // Makes the background transparent
         

        // Create the hamburger menu button
        JButton menuButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D) g;
                gd.setColor(Color.BLACK);
                gd.setStroke(new BasicStroke(3)); // Set stroke for line thickness

                // Drawing 3 horizontal lines for the hamburger menu
                int width = getWidth();
                int height = getHeight();
                int lineHeight = 10;
                int lineWidth = width - 20;
                int y = (height - 3 * lineHeight) / 2 + 5;

                for (int i = 0; i < 3; i++) {
                    gd.drawLine(15, y, lineWidth, y); // Draw line from (10, y) to (lineWidth, y)
                    y += lineHeight-2; // Add space between the lines
                }
            }
        };

        // Customize the menu button appearance
        menuButton.setPreferredSize(new Dimension(50, 50));
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
            }
        });

        // Add components to the up panel using BorderLayout
        up.add(Name, BorderLayout.CENTER);
        up.add(LogOut, BorderLayout.EAST);
        up.add(menuButton, BorderLayout.WEST);

        return up;
    }

    private JPanel createDownPanel() {
        JPanel down = new JPanel();
        down.setBackground(new Color(226, 101, 25, 255));
        down.setPreferredSize(new Dimension(1000, 70));
        down.setLayout(new BorderLayout());

        // Set a valid image path (Ensure the path is correct)
        ImageIcon image = new ImageIcon("src//bot.jpg");
        JButton ChatBox = new JButton(image);
        ChatBox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        ChatBox.setContentAreaFilled(false); // Make the background transparent
        ChatBox.setOpaque(false); // Ensure transparency
        //ChatBox.setFocusPainted(false);
        ChatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AIAssistant();
                System.out.println("ChatBox Clicked");//instead of this , put new AIAssistant
            }
        });
        down.add(ChatBox, BorderLayout.EAST);
        return down;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Layout::new);
    }
}

