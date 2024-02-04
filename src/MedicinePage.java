import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicinePage extends JFrame implements ActionListener{
    
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
    JButton addToCartButton,displayButton,backButton,refreshButton;
    JTextField quantityField;
    JLabel mainimg1;
    JScrollPane js,js1;
    JTable jt,jt1;
    String[] row,row1;
    Object[][] medicinedata,cartdata;
    int quantity;
    Statement statement;

    MedicinePage() throws Exception{
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

        row =new String[]{"medicineid","name","price","description"};
        medicinedata=getMedicineData();
        jt=new JTable(medicinedata, row);
        js=new JScrollPane(jt);
        js.setBounds(50,290,530,500);

        row1 =new String[]{"id","name","price","quantity"};
        cartdata=getCartData();
        jt1=new JTable(cartdata, row1);
        js1=new JScrollPane(jt1);
        js1.setBounds(720,410,400,270);
        js1.setVisible(false);
        
        JLabel titleLabel1 = new JLabel("Cart");
        titleLabel1.setFont(new Font("Algerian", Font.BOLD, 35));
        titleLabel1.setForeground(Color.WHITE);
        titleLabel1.setBounds(850, 260, 150, 150);
        
        JLabel label1=new JLabel("Enter Quantity:");
        label1.setForeground(Color.WHITE);
        label1.setBounds(800,360,120,25);
        quantityField=new JTextField();
        quantityField.setBounds(900, 360, 120, 25);
        
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        addToCartButton.setBounds(720,730,200,30);
        addToCartButton.addActionListener(this);

        displayButton = new JButton("Display Cart");
        displayButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        displayButton.setBounds(950,730,200,30);
        displayButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        backButton.setBounds(720,770,200,30);
        backButton.addActionListener(this);

        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        refreshButton.setBounds(950,770,200,30);
        refreshButton.addActionListener(this);
    
        contentPanel.add(js);
        contentPanel.add(js1);
        contentPanel.add(label1);
        contentPanel.add(quantityField);
        contentPanel.add(refreshButton);
        contentPanel.add(titleLabel1);
        contentPanel.add(addToCartButton);
        contentPanel.add(backButton);
        contentPanel.add(displayButton);
        contentPanel.add(mainimg1);
        contentPanel.add(titleLabel);

        add(contentPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    Object[][] getMedicineData() throws Exception{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM medicine");
        int rowCount=0;
        while(resultSet.next()){
            rowCount++;
        }
        Object[][] data=new Object[rowCount][4];
        resultSet = statement.executeQuery("SELECT * FROM medicine");
        int i = 0;
        while (resultSet.next()) {
            data[i][0] = resultSet.getString("medicineid");
            data[i][1] = resultSet.getString("name");
            data[i][2] = resultSet.getString("price");
            data[i][3] = resultSet.getString("UsedFor");
            i++;
        }
        return data;
    }

    Object[][] getCartData() throws Exception{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagementSystem", "root", "");
        statement = connection.createStatement();
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
        new MedicinePage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addToCartButton){

            int selectedRow=jt.getSelectedRow();
            if (selectedRow != -1) { // -1 means no row is selected
                // Get data from the selected row
                String name = jt.getValueAt(selectedRow, 1).toString();
                double price=Double.parseDouble(jt.getValueAt(selectedRow, 2).toString());
                if(quantityField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "No value Entered in quantity field!");
                }
                else{
                    int quantity=Integer.parseInt(quantityField.getText());
                    if(quantity<0){
                        JOptionPane.showMessageDialog(null, "Quantity can't be negative!");
                    }
                    else if(quantity==0){
                        JOptionPane.showMessageDialog(null, "Quantity is Zero!");
                    }
                    else{
                        try {
                            statement.executeUpdate("CREATE TABLE "+LoginSystem.name+"Cart (srno INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, price DECIMAL(10, 2) NOT NULL, quantity INT)");
                        } catch (Exception e2) {}
            
                        try {
                            statement.executeUpdate("insert into "+LoginSystem.name+"Cart (name , price,quantity) values ('"+name+"',"+price+","+quantity+")");
                            JOptionPane.showMessageDialog(null, "Added to cart!");
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(null, "Some Values are wrong!");
                        }
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No item selected!");
            }
        }
        else if(e.getSource()==displayButton){
            js1.setVisible(true);
        }
        else if(e.getSource()==refreshButton){
            dispose();
            try {
                new MedicinePage();
            } catch (Exception e1) {}
        }
        else{
            dispose();
            new UserPage();
        }
    }
}
