import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class AdminBloodBagPage extends JFrame{
    
    JLabel mainimg1;
    Connection connection;
    JButton backButton;
    JScrollPane js,js1;
    JTable jt,jt1;
    String[] row,row1;
    Object[][] BloodBagdata,blooddonordata;

    AdminBloodBagPage() throws Exception{

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

        JLabel titleLabel = new JLabel("Blood Bag Section");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(350, 220, 1000, 40);

        JLabel donor = new JLabel("Blood Donor Table");
        donor.setFont(new Font("Algerian", Font.BOLD, 30));
        donor.setForeground(Color.WHITE);
        donor.setBounds(170, 280, 1000, 40);

        JLabel bag = new JLabel("Blood Bag Table");
        bag.setFont(new Font("Algerian", Font.BOLD, 30));
        bag.setForeground(Color.WHITE);
        bag.setBounds(780, 320, 1000, 40);

        mainimg1=new JLabel();
        mainimg1.setBorder(null);
        mainimg1.setOpaque(false);
        mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
        mainimg1.setBounds(-10,20,1200,170);
        ImageIcon i=new ImageIcon("Images\\logo.png");
        mainimg1.setIcon(i);

        row =new String[]{"donorid","name","age","blood group","date","quantity"};
        blooddonordata=getbloodonorData();
        jt=new JTable(blooddonordata, row);
        js=new JScrollPane(jt);
        js.setBounds(50,320,530,470);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        backButton.setBounds(810,740,170,40);
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminPage();
            }
            
        });

        row1 =new String[]{"id","name","quantity"};
        BloodBagdata=getbloodbagData();
        jt1=new JTable(BloodBagdata, row1);
        js1=new JScrollPane(jt1);
        js1.setBounds(720,360,400,150);

        contentPanel.add(mainimg1);
        contentPanel.add(titleLabel);
        contentPanel.add(backButton);
        contentPanel.add(js);
        contentPanel.add(js1);
        contentPanel.add(donor);
        contentPanel.add(bag);

        add(contentPanel);
    
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new AdminBloodBagPage();
    }
    Object[][] getbloodonorData() throws Exception{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM blooddonor");
        int rowCount=0;
        while(resultSet.next()){
            rowCount++;
        }
        Object[][] data=new Object[rowCount][6];
        resultSet = statement.executeQuery("SELECT * FROM blooddonor");
        int i = 0;
        while (resultSet.next()) {
            data[i][0] = resultSet.getString("donationid");
            data[i][1] = resultSet.getString("donorname");
            data[i][2] = resultSet.getString("donorage");
            data[i][3] = resultSet.getString("Bloodgroup");
            data[i][4] = resultSet.getString("donationdate");
            data[i][5] = resultSet.getString("quantity");
            i++;
        }
        return data;
    }

    Object[][] getbloodbagData() throws Exception{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        Statement  statement = connection.createStatement();
        Object[][] data=new Object[0][0];
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bloodbag");
            int rowCount=0;
            while(resultSet.next()){
                rowCount++;
            }
            data=new Object[rowCount][3];
            resultSet = statement.executeQuery("SELECT * FROM bloodbag");
            int i = 0;
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("bloodid");
                data[i][1] = resultSet.getString("name");
                data[i][2] = resultSet.getString("quantity");
                i++;
            }
        } catch (Exception e) {}
        return data;
    }
}