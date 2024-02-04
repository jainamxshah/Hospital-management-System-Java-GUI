import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Bill extends JFrame implements ActionListener{

    JLabel mainimg1,totalJLabel;
    JTextField totalField;
    JButton netBanking,Upi,CreditCard,DebitCard,back;
    Connection connection;
    JScrollPane js,js1;
    JTable jt,jt1;
    String[] row,row1;
    Object[][] cartdata;
    double bill=calculateTotalBill();
    
    Bill() throws Exception{
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

        JLabel titleLabel = new JLabel("CheckOut Bill");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(350, 220, 1000, 40);

        JLabel donor = new JLabel("Your cart");
        donor.setFont(new Font("Algerian", Font.BOLD, 30));
        donor.setForeground(Color.WHITE);
        donor.setBounds(170, 280, 1000, 40);

        JLabel payment = new JLabel("Pay Here");
        payment.setFont(new Font("Algerian", Font.BOLD, 30));
        payment.setForeground(Color.WHITE);
        payment.setBounds(170, 280, 1000, 40);

        netBanking=new JButton("Net Banking");
        netBanking.setBounds(700,400,330,40);
        netBanking.addActionListener(this);

        Upi = new JButton("UPI");
        Upi.setBounds(700, 470, 330, 40);
        Upi.addActionListener(this);

        CreditCard = new JButton("Credit Card");
        CreditCard.setBounds(700, 540, 330, 40);
        CreditCard.addActionListener(this);

        DebitCard = new JButton("Debit Card");
        DebitCard.setBounds(700, 610, 330, 40);
        DebitCard.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(740, 720, 250, 40);
        back.addActionListener(this);

        mainimg1=new JLabel();
        mainimg1.setBorder(null);
        mainimg1.setOpaque(false);
        mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
        mainimg1.setBounds(-10,20,1200,170);
        ImageIcon i=new ImageIcon("Images\\logo.png");
        mainimg1.setIcon(i);

        row1 =new String[]{"id","name","price","quantity"};
        cartdata=getCartData();
        jt1=new JTable(cartdata, row1);
        js1=new JScrollPane(jt1);
        js1.setBounds(50,340,400,270);

        totalJLabel=new JLabel("Total:");
        totalJLabel.setForeground(Color.WHITE);
        totalJLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        totalJLabel.setBounds(240,630,120,45);
        totalField=new JTextField();
        totalField.setBounds(330, 630, 120, 40);
        totalField.setText(Double.toString(bill));

        contentPanel.add(titleLabel);
        contentPanel.add(mainimg1);
        contentPanel.add(donor);
        contentPanel.add(Upi);
        contentPanel.add(back);
        contentPanel.add(DebitCard);
        contentPanel.add(CreditCard);
        contentPanel.add(netBanking);
        contentPanel.add(js1);
        contentPanel.add(totalField);
        contentPanel.add(totalJLabel);

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    Object[][] getCartData() throws Exception{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        Statement statement = connection.createStatement();
        Object[][] data=new Object[0][0];
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+LoginSystem.name+"Cart");
            int rowCount=0;
            while(resultSet.next()){
                rowCount++;
            }
            data=new Object[rowCount][4];
            resultSet = statement.executeQuery("SELECT * FROM "+LoginSystem.name+"Cart");
            int i = 0;
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("srno");
                data[i][1] = resultSet.getString("name");
                data[i][2] = resultSet.getString("price");
                data[i][3] = resultSet.getString("quantity");
                i++;
            }
        } catch (Exception e) {}
        return data;
    }
    public static void main(String[] args) throws Exception {
        new Bill();
    }
    double calculateTotalBill() throws SQLException{
        double bill=0;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        Statement st=connection.createStatement();
        ResultSet rs=st.executeQuery("Select * from "+LoginSystem.name+"Cart");
        while(rs.next()){
            bill=bill+(rs.getInt("quantity")*rs.getDouble("price"));
        }
        return bill;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==netBanking){
            JFrame frame = new JFrame("Net Banking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel Accno = new JLabel("Account no:");
        Accno.setBounds(10, 20, 120, 30);
        JTextField accField=new JTextField();
        accField.setBounds(100, 20, 120, 30);

        JLabel pswd = new JLabel("Password:");
        pswd.setBounds(10, 60, 120, 30);
        JTextField passField=new JTextField();
        passField.setBounds(100, 60, 120, 30);

        JButton submit=new JButton("Submit");
        submit.setBounds(90,100,130,30);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!accField.getText().equals("") || !pswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Payment Successfully Done!");
                    frame.dispose();
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
                        Statement st=connection.createStatement();
                        st.executeUpdate("TRUNCATE TABLE "+LoginSystem.name+"Cart;");
                    } catch (SQLException e1) {}
                    dispose();
                    try {
                        dispose();
                        new Bill();
                    } catch (Exception e1) {}
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter Valid Details!");

                }
            }
            
        });


        panel.add(Accno);
        panel.add(submit);
        panel.add(pswd);
        panel.add(accField);
        panel.add(passField);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
        else if(e.getSource()==Upi){
            JFrame frame = new JFrame("UPI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel Accno = new JLabel("UPI id:");
        Accno.setBounds(10, 20, 120, 30);
        JTextField accField=new JTextField();
        accField.setBounds(100, 20, 120, 30);

        JLabel pswd = new JLabel("Password:");
        pswd.setBounds(10, 60, 120, 30);
        JTextField passField=new JTextField();
        passField.setBounds(100, 60, 120, 30);

        JButton submit=new JButton("Submit");
        submit.setBounds(90,100,130,30);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!accField.getText().equals("") || !pswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Payment Successfully Done!");
                    frame.dispose();
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
                        Statement st=connection.createStatement();
                        st.executeUpdate("TRUNCATE TABLE "+LoginSystem.name+"Cart;");
                    } catch (SQLException e1) {}
                    dispose();
                    try {
                        new UserPage();
                    } catch (Exception e1) {}
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter Valid Details!");

                }
            }
            
        });
        
        
        panel.add(Accno);
        panel.add(submit);
        panel.add(pswd);
        panel.add(accField);
        panel.add(passField);
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    else if(e.getSource()==CreditCard){
            JFrame frame = new JFrame("Credit Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 230);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel Accno = new JLabel("Account no:");
        Accno.setBounds(10, 20, 120, 30);
        JTextField accField=new JTextField();
        accField.setBounds(100, 20, 120, 30);
        
        JLabel cvv = new JLabel("CVV:");
        cvv.setBounds(10, 60, 120, 30);
        JTextField cvvField=new JTextField();
        cvvField.setBounds(100, 60, 120, 30);

        JLabel pswd = new JLabel("Password:");
        pswd.setBounds(10, 100, 120, 30);
        JTextField passField=new JTextField();
        passField.setBounds(100, 100, 120, 30);
        
        JButton submit=new JButton("Submit");
        submit.setBounds(90,140,130,30);
        submit.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!accField.getText().equals("") || !pswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Payment Successfully Done!");
                    frame.dispose();
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
                        Statement st=connection.createStatement();
                        st.executeUpdate("TRUNCATE TABLE "+LoginSystem.name+"Cart;");
                    } catch (SQLException e1) {}
                    dispose();
                    try {
                        dispose();
                        new Bill();
                    } catch (Exception e1) {}
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter Valid Details!");
        
                }
            }
            
        });
        
        
        panel.add(Accno);
        panel.add(accField);
        panel.add(cvv);
        panel.add(cvvField);
        panel.add(pswd);
        panel.add(passField);
        panel.add(submit);
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    else if(e.getSource()==DebitCard){
            JFrame frame = new JFrame("Debit Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 230);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel Accno = new JLabel("Account no:");
        Accno.setBounds(10, 20, 120, 30);
        JTextField accField=new JTextField();
        accField.setBounds(100, 20, 120, 30);
        
        JLabel cvv = new JLabel("CVV:");
        cvv.setBounds(10, 60, 120, 30);
        JTextField cvvField=new JTextField();
        cvvField.setBounds(100, 60, 120, 30);
        
        JLabel pswd = new JLabel("Password:");
        pswd.setBounds(10, 100, 120, 30);
        JTextField passField=new JTextField();
        passField.setBounds(100, 100, 120, 30);
        
        JButton submit=new JButton("Submit");
        submit.setBounds(90,140,130,30);
        submit.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!accField.getText().equals("") || !pswd.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Payment Successfully Done!");
                    frame.dispose();
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
                        Statement st=connection.createStatement();
                        st.executeUpdate("TRUNCATE TABLE "+LoginSystem.name+"Cart;");
                    } catch (SQLException e1) {}
                    dispose();
                    try {
                        dispose();
                        new Bill();
                    } catch (Exception e1) {}
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter Valid Details!");
        
                }
            }
            
        });
        
        
        panel.add(Accno);
        panel.add(accField);
        panel.add(cvv);
        panel.add(cvvField);
        panel.add(pswd);
        panel.add(passField);
        panel.add(submit);
        
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        }
        else if(e.getSource()==back){
            dispose();
            new UserPage();
        }
    }
}


