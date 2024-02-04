import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HospitalManagementSystemHomepage extends JFrame {

    JLabel mainimg1,mainimg;

    public HospitalManagementSystemHomepage() {
        setTitle("Hospital Management System");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(7,47,56);
                Color color2 = new Color(24,130,107,255); 
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        contentPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome to ");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(425, 130, 400, 40);

        JLabel titleLabel1 = new JLabel("Lok Jagruti Hospital");
        titleLabel1.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setBounds(315, 190, 800, 40);

        mainimg1=new JLabel();
		mainimg1.setBorder(null);
		mainimg1.setOpaque(false);
		mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
		mainimg1.setBounds(190,430,900, 380);
        ImageIcon i1=new ImageIcon("Images\\resized-image-Promo (1).jpeg");
		mainimg1.setIcon(i1);

        JButton loginButton = new JButton("Login As Admin");
        loginButton.setBounds(450, 300, 250, 50);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage(1);
            }
        });

        JButton registerButton = new JButton("Login As User");
        registerButton.setBounds(450, 360, 250, 50);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage(2);
            }
        });

        contentPanel.add(mainimg1);
        contentPanel.add(titleLabel);
        contentPanel.add(titleLabel1);
        contentPanel.add(loginButton);
        contentPanel.add(registerButton);

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HospitalManagementSystemHomepage();
    }
}
