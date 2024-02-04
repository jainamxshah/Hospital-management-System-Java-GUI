import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class AdminMedicinePage extends JFrame implements ActionListener{
    
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
    JButton addButton,deleteButton,backButton,updateButton,submitButton,refreshButton;
    JTextField idField,nameField,priceField,phonenoField,usedforField,diseaseField;
    JLabel mainimg1,idJLabel,nameJLabel,priceJLabel,phonenoJLabel,usedforJLabel,diseaseJLabel;
    JScrollPane js,js1;
    JTable jt,jt1;
    String[] row,row1;
    Object[][] Medicinedata,cartdata;
    int quantity;
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
    Statement statement = connection.createStatement();

    AdminMedicinePage() throws Exception{
        setTitle("Hospital Management System");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Medicine Page");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(425, 220, 400, 40);

        mainimg1=new JLabel();
		mainimg1.setBorder(null);
		mainimg1.setOpaque(false);
		mainimg1.setLayout(null);
        mainimg1.setBackground(Color.GREEN);
		mainimg1.setBounds(-10,20,1200,170);
        ImageIcon i=new ImageIcon("Images\\logo.png");
		mainimg1.setIcon(i);

        row =new String[]{"id","name","price","description"};
        Medicinedata=getMedicineData();
        jt=new JTable(Medicinedata, row);
        js=new JScrollPane(jt);
        js.setBounds(50,290,730,500);
        
        idJLabel=new JLabel("Enter id:");
        idJLabel.setForeground(Color.WHITE);
        idJLabel.setBounds(800,495,120,45);
        idField=new JTextField();
        idField.setBounds(800, 530, 100, 25);
        
        nameJLabel=new JLabel("Enter name:");
        nameJLabel.setForeground(Color.WHITE);
        nameJLabel.setBounds(940,495,120,45);
        nameField=new JTextField();
        nameField.setBounds(940, 530, 100, 25);

        priceJLabel=new JLabel("Enter price:");
        priceJLabel.setForeground(Color.WHITE);
        priceJLabel.setBounds(1080,495,120,45);
        priceField=new JTextField();
        priceField.setBounds(1080, 530, 100, 25);
        
        usedforJLabel=new JLabel("Enter usedfor:");
        usedforJLabel.setForeground(Color.WHITE);
        usedforJLabel.setBounds(800,570,120,45);
        usedforField=new JTextField();
        usedforField.setBounds(800, 605, 100, 25);
        
        addButton = new JButton("Add Medicine");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        addButton.setBounds(850,300,250,40);
        addButton.addActionListener(this);
        
        deleteButton = new JButton("Delete Medicine");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        deleteButton.setBounds(850,365,250,40);
        deleteButton.addActionListener(this);
        
        updateButton = new JButton("Update Medicine");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        updateButton.setBounds(850,430,250,40);
        updateButton.addActionListener(this);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        backButton.setBounds(810,760,170,40);
        backButton.addActionListener(this);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        refreshButton.setBounds(1000,760,170,40);
        refreshButton.addActionListener(this);
        
        submitButton = new JButton("submit");
        submitButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        submitButton.setBounds(940,670,220,40);
        submitButton.addActionListener(this);
        
        idJLabel.setVisible(false);
        idField.setVisible(false);
        nameJLabel.setVisible(false);
        nameField.setVisible(false);
        priceJLabel.setVisible(false);
        priceField.setVisible(false);
        usedforJLabel.setVisible(false);
        usedforField.setVisible(false);
        submitButton.setVisible(false);

        contentPanel.add(js);
        contentPanel.add(addButton);
        contentPanel.add(idJLabel);
        contentPanel.add(idField);
        contentPanel.add(nameJLabel);
        contentPanel.add(nameField);
        contentPanel.add(priceJLabel);
        contentPanel.add(priceField);
        contentPanel.add(usedforJLabel);
        contentPanel.add(usedforField);
        contentPanel.add(submitButton);
        contentPanel.add(backButton);
        contentPanel.add(refreshButton);
        contentPanel.add(deleteButton);
        contentPanel.add(updateButton);
        contentPanel.add(mainimg1);
        contentPanel.add(titleLabel);

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    Object[][] getMedicineData() throws Exception{
    
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Medicine");
        int rowCount=0;
        while(resultSet.next()){
            rowCount++;
        }
        Object[][] data=new Object[rowCount][4];
        resultSet = statement.executeQuery("SELECT * FROM Medicine");
        int i = 0;
        while (resultSet.next()) {
            data[i][0] = resultSet.getString("Medicineid");
            data[i][1] = resultSet.getString("name");
            data[i][2] = resultSet.getString("price");
            data[i][3] = resultSet.getString("Usedfor");
            i++;
        }
        return data;
    }
    public static void main(String[] args) throws Exception {
        new AdminMedicinePage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton){
            idJLabel.setVisible(true);
            idField.setVisible(true);
            nameJLabel.setVisible(true);
            nameField.setVisible(true);
            priceJLabel.setVisible(true);
            priceField.setVisible(true);
            usedforJLabel.setVisible(true);
            usedforField.setVisible(true);
            submitButton.setVisible(true);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        PreparedStatement pst=connection.prepareCall("Insert into Medicine Values (?,?,?,?)");
                        pst.setInt(1, Integer.parseInt(idField.getText()));
                        pst.setString(2, nameField.getText());
                        pst.setInt(3, Integer.parseInt(priceField.getText()));
                        pst.setString(4, usedforField.getText());
                        int r=pst.executeUpdate();
                        if(r>0){
                            JOptionPane.showMessageDialog(null, "Medicine Added!\nRefresh the Page");
                            idJLabel.setVisible(false);
                            idField.setVisible(false);
                            nameJLabel.setVisible(false);
                            nameField.setVisible(false);
                            priceJLabel.setVisible(false);
                            priceField.setVisible(false);
                            usedforJLabel.setVisible(false);
                            usedforField.setVisible(false);
                            submitButton.setVisible(false);
                        }
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Some Values are wrong!");
                    }
                }    
            });
        }
        else if(e.getSource()==deleteButton){
            idJLabel.setVisible(true);
            idField.setVisible(true);
            submitButton.setVisible(true);
            submitButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    if(idField.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "No value Entered in quantity field!");
                    }
                    else{
                        int id=Integer.parseInt(idField.getText());
                        if(id<0){
                            JOptionPane.showMessageDialog(null, "Quantity can't be negative!");
                        }
                        else if(id==0){
                            JOptionPane.showMessageDialog(null, "Quantity is Zero!");
                        }
                        else{
                            try {
                                int r=statement.executeUpdate("delete from Medicine where Medicineid="+id);
                                if(r>0){
                                    JOptionPane.showMessageDialog(null, "Medicine Deleted!\nRefresh the Page");
                                    idJLabel.setVisible(false);
                                    idField.setVisible(false);
                                    nameJLabel.setVisible(false);
                                    nameField.setVisible(false);
                                    priceJLabel.setVisible(false);
                                    priceField.setVisible(false);
                                    usedforJLabel.setVisible(false);
                                    usedforField.setVisible(false);
                                    submitButton.setVisible(false);
                                }
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, "Some Values are wrong!");
                            }
                        }
                    }
                }
                
            });
        }
        else if(e.getSource()==updateButton){
            idJLabel.setVisible(true);
            idField.setVisible(true);
            nameJLabel.setVisible(true);
            nameField.setVisible(true);
            priceJLabel.setVisible(true);
            priceField.setVisible(true);
            usedforJLabel.setVisible(true);
            usedforField.setVisible(true);
            submitButton.setVisible(true);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        PreparedStatement pst=connection.prepareCall("Update Medicine SET name=?,price=?,usedfor=? where Medicineid=?");
                        pst.setInt(4, Integer.parseInt(idField.getText()));
                        pst.setString(1, nameField.getText());
                        pst.setDouble(2, Double.parseDouble(priceField.getText()));
                        pst.setString(3, usedforField.getText());
                        int r=pst.executeUpdate();
                        if(r>0){
                            JOptionPane.showMessageDialog(null, "Medicine Updated!\nRefresh the Page");
                            idJLabel.setVisible(false);
                            idField.setVisible(false);
                            nameJLabel.setVisible(false);
                            nameField.setVisible(false);
                            priceJLabel.setVisible(false);
                            priceField.setVisible(false);
                            usedforJLabel.setVisible(false);
                            usedforField.setVisible(false);
                            submitButton.setVisible(false);
                        }
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Some Values are wrong!");
                    }
                }    
            });
        }
        else if(e.getSource()==backButton){
            dispose();
            new AdminPage();
        }
        else if(e.getSource()==refreshButton){
            dispose();
            try {
                new AdminMedicinePage();
            } catch (Exception e1) {}
        }
    }
}
