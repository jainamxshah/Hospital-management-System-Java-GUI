import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserPage extends JFrame {

    JLabel mainimg1,mainimg2;

    public UserPage() {
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

        JButton MedicineButton = new JButton("Medicines");
        MedicineButton.setBounds(280, 320, 250, 100);
        MedicineButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        MedicineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new MedicinePage();
                } catch (Exception e1) {}
            }
        });

        JButton VaccineButton = new JButton("Vaccines");
        VaccineButton.setBounds(620, 320, 250, 100);
        VaccineButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        VaccineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new VaccinePage();
                } catch (Exception e1) {}
            }
        });
        JButton BloodBagButton = new JButton("Donate Blood");
        BloodBagButton.setBounds(280, 470, 250, 100);
        BloodBagButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        BloodBagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new BloodDonation();
                } catch (Exception e1) {}
            }
        });
        JButton BillButton = new JButton("Checkout Bill");
        BillButton.setBounds(620, 470, 250, 100);
        BillButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        BillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new Bill();
                } catch (Exception e1) {}
            }
        });

        mainimg1=new JLabel();
		mainimg1.setBorder(null);
		mainimg1.setOpaque(false);
		mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
		mainimg1.setBounds(-10,20,1200,170);
        ImageIcon i=new ImageIcon("Images\\logo.png");
		mainimg1.setIcon(i);

        contentPanel.add(mainimg1);
        contentPanel.add(MedicineButton);
        contentPanel.add(BillButton);
        contentPanel.add(VaccineButton);
        contentPanel.add(BloodBagButton);
        

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new UserPage();
    }
}