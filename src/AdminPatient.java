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

class AdminPatientPage extends JFrame implements ActionListener{
    
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
    JTextField idField,nameField,ageField,phonenoField,genderField,diseaseField;
    JLabel mainimg1,idJLabel,nameJLabel,ageJLabel,phonenoJLabel,genderJLabel,diseaseJLabel;
    JScrollPane js,js1;
    JTable jt,jt1;
    String[] row,row1;
    Object[][] Patientdata,cartdata;
    int quantity;
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
    Statement statement = connection.createStatement();

    AdminPatientPage() throws Exception{
        setTitle("Hospital Management System");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Patient Page");
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

        row =new String[]{"id","name","age","gender","phoneno","disease"};
        Patientdata=getPatientData();
        jt=new JTable(Patientdata, row);
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

        ageJLabel=new JLabel("Enter age:");
        ageJLabel.setForeground(Color.WHITE);
        ageJLabel.setBounds(1080,495,120,45);
        ageField=new JTextField();
        ageField.setBounds(1080, 530, 100, 25);

        phonenoJLabel=new JLabel("Enter phoneno:");
        phonenoJLabel.setForeground(Color.WHITE);
        phonenoJLabel.setBounds(940,570,120,45);
        phonenoField=new JTextField();
        phonenoField.setBounds(940, 605, 100, 25);
        
        genderJLabel=new JLabel("Enter gender:");
        genderJLabel.setForeground(Color.WHITE);
        genderJLabel.setBounds(1080,570,120,45);
        genderField=new JTextField();
        genderField.setBounds(1080, 605, 100, 25);
        
        diseaseJLabel=new JLabel("Enter disease:");
        diseaseJLabel.setForeground(Color.WHITE);
        diseaseJLabel.setBounds(800,570,120,45);
        diseaseField=new JTextField();
        diseaseField.setBounds(800, 605, 100, 25);
        
        addButton = new JButton("Add Patient");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        addButton.setBounds(850,300,250,40);
        addButton.addActionListener(this);
        
        deleteButton = new JButton("Delete Patient");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        deleteButton.setBounds(850,365,250,40);
        deleteButton.addActionListener(this);
        
        updateButton = new JButton("Update Patient");
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
        ageJLabel.setVisible(false);
        ageField.setVisible(false);
        phonenoJLabel.setVisible(false);
        phonenoField.setVisible(false);
        genderJLabel.setVisible(false);
        genderField.setVisible(false);
        diseaseJLabel.setVisible(false);
        diseaseField.setVisible(false);
        submitButton.setVisible(false);

        contentPanel.add(js);
        contentPanel.add(addButton);
        contentPanel.add(idJLabel);
        contentPanel.add(idField);
        contentPanel.add(nameJLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageJLabel);
        contentPanel.add(ageField);
        contentPanel.add(phonenoJLabel);
        contentPanel.add(phonenoField);
        contentPanel.add(genderJLabel);
        contentPanel.add(genderField);
        contentPanel.add(diseaseJLabel);
        contentPanel.add(diseaseField);
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

    Object[][] getPatientData() throws Exception{
    
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Patient");
        int rowCount=0;
        while(resultSet.next()){
            rowCount++;
        }
        Object[][] data=new Object[rowCount][6];
        resultSet = statement.executeQuery("SELECT * FROM Patient");
        int i = 0;
        while (resultSet.next()) {
            data[i][0] = resultSet.getString("Patient_id");
            data[i][1] = resultSet.getString("Patient_name");
            data[i][2] = resultSet.getString("Patient_age");
            data[i][3] = resultSet.getString("Patient_gender");
            data[i][4] = resultSet.getString("Patient_phoneno");
            data[i][5] = resultSet.getString("Patient_disease");
            i++;
        }
        return data;
    }
    public static void main(String[] args) throws Exception {
        new AdminPatientPage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton){
            idJLabel.setVisible(true);
            idField.setVisible(true);
            nameJLabel.setVisible(true);
            nameField.setVisible(true);
            ageJLabel.setVisible(true);
            ageField.setVisible(true);
            phonenoJLabel.setVisible(true);
            phonenoField.setVisible(true);
            genderJLabel.setVisible(true);
            genderField.setVisible(true);
            diseaseJLabel.setVisible(true);
            diseaseField.setVisible(true);
            submitButton.setVisible(true);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        PreparedStatement pst=connection.prepareCall("Insert into Patient Values (?,?,?,?,?,?)");
                        pst.setInt(1, Integer.parseInt(idField.getText()));
                        pst.setString(2, nameField.getText());
                        pst.setInt(3, Integer.parseInt(ageField.getText()));
                        pst.setString(4, genderField.getText());
                        pst.setString(5, phonenoField.getText());
                        pst.setString(6, diseaseField.getText());
                        int r=pst.executeUpdate();
                        if(r>0){
                            JOptionPane.showMessageDialog(null, "Patient Added!\nRefresh the Page");
                            idJLabel.setVisible(false);
                            idField.setVisible(false);
                            nameJLabel.setVisible(false);
                            nameField.setVisible(false);
                            ageJLabel.setVisible(false);
                            ageField.setVisible(false);
                            phonenoJLabel.setVisible(false);
                            phonenoField.setVisible(false);
                            genderJLabel.setVisible(false);
                            genderField.setVisible(false);
                            diseaseJLabel.setVisible(false);
                            diseaseField.setVisible(false);
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
                                int r=statement.executeUpdate("delete from patient where patient_id="+id);
                                if(r>0){
                                    JOptionPane.showMessageDialog(null, "patient Deleted!\nRefresh the Page");
                                    idJLabel.setVisible(false);
                                    idField.setVisible(false);
                                    nameJLabel.setVisible(false);
                                    nameField.setVisible(false);
                                    ageJLabel.setVisible(false);
                                    ageField.setVisible(false);
                                    phonenoJLabel.setVisible(false);
                                    phonenoField.setVisible(false);
                                    genderJLabel.setVisible(false);
                                    genderField.setVisible(false);
                                    diseaseJLabel.setVisible(false);
                                    diseaseField.setVisible(false);
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
            ageJLabel.setVisible(true);
            ageField.setVisible(true);
            phonenoJLabel.setVisible(true);
            phonenoField.setVisible(true);
            genderJLabel.setVisible(true);
            genderField.setVisible(true);
            diseaseJLabel.setVisible(true);
            diseaseField.setVisible(true);
            submitButton.setVisible(true);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        PreparedStatement pst=connection.prepareCall("Update patient SET patient_name=?,patient_age=?,patient_phoneno=?,patient_gender=?,patient_disease=?,patient_serving_period=? where patient_id=?");
                        pst.setInt(6, Integer.parseInt(idField.getText()));
                        pst.setString(1, nameField.getText());
                        pst.setInt(2, Integer.parseInt(ageField.getText()));
                        pst.setString(4, genderField.getText());
                        pst.setString(3, phonenoField.getText());
                        pst.setString(5, diseaseField.getText());
                        int r=pst.executeUpdate();
                        if(r>0){
                            JOptionPane.showMessageDialog(null, "Patient Updated!\nRefresh the Page");
                            idJLabel.setVisible(false);
                            idField.setVisible(false);
                            nameJLabel.setVisible(false);
                            nameField.setVisible(false);
                            ageJLabel.setVisible(false);
                            ageField.setVisible(false);
                            phonenoJLabel.setVisible(false);
                            phonenoField.setVisible(false);
                            genderJLabel.setVisible(false);
                            genderField.setVisible(false);
                            diseaseJLabel.setVisible(false);
                            diseaseField.setVisible(false);
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
                new AdminPatientPage();
            } catch (Exception e1) {}
        }
    }
}
