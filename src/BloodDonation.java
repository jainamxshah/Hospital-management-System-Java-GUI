import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
public class BloodDonation extends JFrame implements ActionListener{

    JTextField idField,nameField,ageField,phonenoField,BloodgroupField,historyField,DateField,quantityField;
    JLabel mainimg1,mainimg,idJLabel,nameJLabel,ageJLabel,phonenoJLabel,BloodgroupJLabel,historyJLabel,DateJLabel,quantityJLabel;
    JButton submitButton,backButton;
    Connection connection;

    BloodDonation() throws SQLException{

        setTitle("Hospital Management System");
            setSize(1200, 850);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
    
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
    
            JLabel titleLabel = new JLabel("Welcome to Blood Donation Page");
            titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBounds(150, 230, 1000, 40);

            JPanel intro=new JPanel();
            intro.setBounds(50, 320, 450, 450);
            mainimg=new JLabel();
            mainimg.setIcon(new ImageIcon("Images\\Safeimagekit-resized-img.png"));
            intro.add(mainimg);
        
        nameJLabel=new JLabel("Name:");
        nameJLabel.setForeground(Color.WHITE);
        nameJLabel.setBounds(580,310,120,45);
        nameJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nameField=new JTextField();
        nameField.setBounds(580, 350, 120, 30);
        
        ageJLabel=new JLabel("Age:");
        ageJLabel.setForeground(Color.WHITE);
        ageJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        ageJLabel.setBounds(770,310,120,45);
        ageField=new JTextField();
        ageField.setBounds(770, 350, 120, 30);

        phonenoJLabel=new JLabel("Phone no:");
        phonenoJLabel.setForeground(Color.WHITE);
        phonenoJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        phonenoJLabel.setBounds(960,310,150,45);
        phonenoField=new JTextField();
        phonenoField.setBounds(960, 350, 120, 30);
        
        BloodgroupJLabel=new JLabel("Blood Group:");
        BloodgroupJLabel.setForeground(Color.WHITE);
        BloodgroupJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        BloodgroupJLabel.setBounds(580,430,170,45);
        BloodgroupField=new JTextField();
        BloodgroupField.setBounds(580, 470, 120, 30);
        
        historyJLabel=new JLabel("Medical History:");
        historyJLabel.setForeground(Color.WHITE);
        historyJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        historyJLabel.setBounds(580,550,170,45);
        historyField=new JTextField();
        historyField.setBounds(580, 590, 120, 30);
        
        DateJLabel=new JLabel("Date:");
        DateJLabel.setForeground(Color.WHITE);
        DateJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        DateJLabel.setBounds(770,430,120,45);
        DateField=new JTextField();
        DateField.setBounds(770, 470, 120, 30);
        
        quantityJLabel=new JLabel("Quantity:");
        quantityJLabel.setForeground(Color.WHITE);
        quantityJLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        quantityJLabel.setBounds(770,550,120,45);
        quantityField=new JTextField();
        quantityField.setBounds(770, 590, 120, 30);

        mainimg1=new JLabel();
            mainimg1.setBorder(null);
            mainimg1.setOpaque(false);
            mainimg1.setLayout(null);
            mainimg1.setBackground(Color.GREEN);
            mainimg1.setBounds(-10,20,1200,170);
            ImageIcon i=new ImageIcon("Images\\logo.png");
            mainimg1.setIcon(i);

            submitButton = new JButton("Submit");
            submitButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
            submitButton.setBounds(700,690,220,40);
            submitButton.addActionListener(this);

            backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
            backButton.setBounds(700,760,220,40);
            backButton.addActionListener(this);
    
            contentPanel.add(mainimg1);
            contentPanel.add(titleLabel);
            contentPanel.add(intro);
            contentPanel.add(nameJLabel);
            contentPanel.add(nameField);
            contentPanel.add(ageJLabel);
            contentPanel.add(ageField);
            contentPanel.add(phonenoJLabel);
            contentPanel.add(phonenoField);
            contentPanel.add(BloodgroupJLabel);
            contentPanel.add(BloodgroupField);
            contentPanel.add(historyJLabel);
            contentPanel.add(historyField);
            contentPanel.add(DateJLabel);
            contentPanel.add(DateField);
            contentPanel.add(quantityJLabel);
            contentPanel.add(quantityField);
            contentPanel.add(submitButton);
            contentPanel.add(backButton);
            
    
            add(contentPanel);
    
            setLocationRelativeTo(null);
            setVisible(true);
    }
    public static void main(String[] args) throws SQLException {
        new BloodDonation();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submitButton){
            try {
                PreparedStatement pst=connection.prepareStatement("INSERT INTO BloodDonor (DonorName,DonorAge, BloodGroup, DonationDate, Quantity) VALUES(?,?,?,?,?)");
                pst.setString(1, nameField.getText());
                pst.setInt(2, Integer.parseInt(ageField.getText()));
                pst.setString(3, BloodgroupField.getText());
                pst.setDate(4, Date.valueOf(DateField.getText()));
                pst.setInt(5, Integer.parseInt(quantityField.getText()));
                int r=pst.executeUpdate();
                if(r>0){
                    JOptionPane.showMessageDialog(null, "Your appointment is booked!");
                    setbloodbagdetails();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Some Values are wrong!");
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Some Values are wrong!");
            }
        }
        else if(e.getSource()==backButton){
            dispose();
            new UserPage();
        }
    }
    static void setbloodbagdetails() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        HashMap<String,Integer> hm=new HashMap<>();
        try {
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("Select * from blooddonor");
            while(rs.next()){
                hm.put(rs.getString("bloodgroup"), hm.getOrDefault(rs.getString("bloodgroup"),0)+rs.getInt("quantity"));
            }
            rs=st.executeQuery("Select name from bloodbag");
            while(rs.next()){
                PreparedStatement pst=connection.prepareCall("update bloodbag set quantity=? where name='"+rs.getString("name")+"'");
                pst.setInt(1, hm.get(rs.getString("name")));
                pst.executeUpdate();
            }
        } catch (Exception e) {}
    }
}
