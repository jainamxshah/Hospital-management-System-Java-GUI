import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends JFrame implements ActionListener{

    JLabel mainimg1,mainimg2;
    JButton DoctorButton,PatientButton,VaccineButton,MedicineButton,BloodBagButton;

    AdminPage(){
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

        JLabel title=new JLabel("Welcome Admin!");
        title.setBounds(50, 220, 500, 40);
        title.setFont(new Font("Algerian", Font.BOLD, 35));
        title.setForeground(Color.WHITE);

        DoctorButton = new JButton("Doctors");
        DoctorButton.setBounds(280, 320, 250, 100);
        DoctorButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        DoctorButton.addActionListener(this);

        PatientButton = new JButton("Patients");
        PatientButton.setBounds(620, 320, 250, 100);
        PatientButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        PatientButton.addActionListener(this);

        MedicineButton = new JButton("Medicines");
        MedicineButton.setBounds(280, 470, 250, 100);
        MedicineButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        MedicineButton.addActionListener(this);

        VaccineButton = new JButton("Vaccines");
        VaccineButton.setBounds(620, 470, 250, 100);
        VaccineButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        VaccineButton.addActionListener(this);

        BloodBagButton = new JButton("Blood Bags");
        BloodBagButton.setBounds(450, 620, 250, 100);
        BloodBagButton.setFont(new Font("TimesRoman",Font.BOLD,30));
        BloodBagButton.addActionListener(this);

        mainimg1=new JLabel();
		mainimg1.setBorder(null);
		mainimg1.setOpaque(false);
		mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
		mainimg1.setBounds(-10,20,1200,170);
        ImageIcon i=new ImageIcon("Images\\logo.png");
		mainimg1.setIcon(i);

        contentPanel.add(mainimg1);
        contentPanel.add(title);
        contentPanel.add(MedicineButton);
        contentPanel.add(DoctorButton);
        contentPanel.add(PatientButton);
        contentPanel.add(VaccineButton);
        contentPanel.add(BloodBagButton);

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new AdminPage();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==DoctorButton){
            dispose();
            try {
                new AdminDoctorPage();
            } catch (Exception e1) {}
        }
        else if(e.getSource()==PatientButton){
            dispose();
            try {
                new AdminPatientPage();
            } catch (Exception e1) {}
        }
        else if(e.getSource()==MedicineButton){
            dispose();
            try {
                new AdminMedicinePage();
            } catch (Exception e1) {}
        }
        else if(e.getSource()==VaccineButton){
            dispose();
            try {
                new AdminVaccinePage();
            } catch (Exception e1) {}
        }
        else if(e.getSource()==BloodBagButton){
            dispose();
            try {
                new AdminBloodBagPage();
            } catch (Exception e1) {}
        }
    }
}
