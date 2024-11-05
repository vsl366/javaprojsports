

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shoe{
    Layout lay = new Layout();
    JFrame frame = lay.getFrame();

    public Shoe(){
        JPanel frameadd = createPanel();
        frame.add(frameadd, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public JPanel createPanel(){
        JPanel outer = new JPanel();
        outer.setBackground(new Color(10,10,10,255));
        outer.setPreferredSize(new Dimension(1000, 500));
        JPanel[] inner = new JPanel[3];
        String img1 = "F:\\JAVA\\Mini_Project\\Sports_App\\Gear 1.png";
        String img2 = "F:\\JAVA\\Mini_Project\\Sports_App\\Gear 2.png";
        String img3 = "F:\\JAVA\\Mini_Project\\Sports_App\\Gear 3.png";
        inner[0] = createOrange("Shoe 1",img1);
        inner[1] = createOrange("Shoe 2",img2);
        inner[2] = createOrange("Shoe 3",img3);
        outer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridy++;
        gbc.gridx = 0;
        outer.add(inner[0],gbc);
        gbc.gridx = 1;
        outer.add(inner[1],gbc);
        gbc.gridx = 2;
        outer.add(inner[2],gbc);
        gbc.gridy++;
        return outer;
    }

    private JPanel createOrange(String text,String img){
        JPanel inn = new JPanel();
        inn.setPreferredSize(new Dimension(300,400));
        inn.setBackground(new Color(226,101,55));
        JLabel ShoeText = new JLabel("Shoe 1");
        ShoeText.setForeground(Color.BLACK);
        ShoeText.setFont(new Font("Arial",Font.BOLD,25));
        ShoeText.setOpaque(false);
        ImageIcon image = new ImageIcon(img);
        Image scaledimg = image.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        JButton Shoepic = new JButton(new ImageIcon(scaledimg));
        Shoepic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" clicked");
            }
        });
        Shoepic.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridy++;
        inn.add(ShoeText,gbc);
        gbc.gridy++;
        inn.add(Shoepic,gbc);
        return inn;

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Shoe::new);
    }
}