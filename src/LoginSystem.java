import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SignupPage extends JFrame{
    private JTextField usernameField,nameField;
    private JPasswordField passwordField,confirmField;
    JLabel titleLabel;
    
    public SignupPage() throws Exception{
        String dburl1= "jdbc:mysql://localhost:3306/stockList";
        String dbuser= "root";
        String dbpassword="";
        String driver= "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection conn=DriverManager.getConnection(dburl1, dbuser, dbpassword);
        Statement st=conn.createStatement();

        setTitle("SignUp Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Use null layout

        titleLabel = new JLabel("Signup as User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(20,10,180,25);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 80, 25);
        nameField = new JTextField();
        nameField.setBounds(140, 50, 200, 25);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 90, 80, 25);
        usernameField = new JTextField();
        usernameField.setBounds(140, 90, 200, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 130, 200, 25);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(50, 170, 80, 25);
        confirmField = new JPasswordField();
        confirmField.setBounds(140, 170, 200, 25);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(160, 210, 80, 30);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(nameLabel);
        add(nameField);
        add(confirmLabel);
        add(confirmField);
        add(titleLabel);
        add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name=nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmField.getPassword());
                if(!password.equals(confirmPassword)){
                    JOptionPane.showMessageDialog(null, "Password Not Matching!");
                }
                else if (!LoginSystem.isStrongPassword(password)) {
                    JOptionPane.showMessageDialog(null, "Password Not Strong");
                } else {
                    try {
                        st.executeUpdate("insert into accounts values (7,'"+name+"','"+username+"','"+password+"')");
                    } catch (SQLException e1) {}
                    dispose();
                    new LoginPage(2);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
class LoginPage extends JFrame implements MouseListener {

    private JTextField usernameField,nameField;
    private JPasswordField passwordField;
    JLabel titleLabel,signup;

    public LoginPage(int data) {
        setTitle("Login Page");
        setSize(400, 240);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); 

        if(data==1){
            titleLabel = new JLabel("Login as Admin");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        }
        else{
            titleLabel = new JLabel("Login as User");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        }
        titleLabel.setBounds(20,10,180,25);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 39, 80, 25);
        nameField = new JTextField();
        nameField.setBounds(140, 39, 200, 25);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 67, 80, 25);
        usernameField = new JTextField();
        usernameField.setBounds(140, 67, 200, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 94, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 94, 200, 25);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(160, 130, 80, 30);
        
        signup = new JLabel("New User? Signup.");
        signup.setForeground(Color.BLUE);
        signup.setBounds(145, 165, 113, 25);
        signup.addMouseListener(this);
        if(data==1){
            signup.setVisible(false);
        }
        else{
            signup.setVisible(true);
        }

        add(usernameLabel);
        add(signup);
        add(usernameField);
        add(nameLabel);
        add(nameField);
        add(passwordLabel);
        add(passwordField);
        add(titleLabel);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name=nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (isValidCredentials(name,username, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        if(data==1){
                            dispose();
                            new AdminPage();
                        }
                        else{
                            dispose();
                            new UserPage();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                    }
                } catch (Exception e1) {}
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isValidCredentials(String name,String username, String password) throws Exception{
        String email="admin@123";
        String pswd="1234";

        String dburl1= "jdbc:mysql://localhost:3306/stockList";
        String dbuser= "root";
        String dbpassword="";
        String driver= "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection conn=DriverManager.getConnection(dburl1, dbuser, dbpassword);
        Statement st=conn.createStatement();

        try {
            ResultSet rs=st.executeQuery("Select * from accounts where name='"+name+"'");
            while(rs.next()){
                email=rs.getString("loginid");
                pswd=rs.getString("password");
            }
            return username.equals(email) && password.equals(pswd);
        } catch (Exception e) {
           
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage(2);
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==signup){
            dispose();
            try {
                new SignupPage();
            } catch (Exception e1) {}
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        signup.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLUE));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        signup.setBorder(null);
    }
}


class LoginSystem {

    static String name="admin";
    static String email = "admin@123";
    static String password = "1234";
    Scanner sc = new Scanner(System.in);

    public static boolean isStrongPassword(String password) {
        // Check length (at least 8 characters)
        if (password.length() < 8) {
            return false;
        }

        // Check for at least one uppercase letter
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        if (!uppercaseMatcher.find()) {
            return false;
        }

        // Check for at least one lowercase letter
        Pattern lowercasePattern = Pattern.compile("[a-z]");
        Matcher lowercaseMatcher = lowercasePattern.matcher(password);
        if (!lowercaseMatcher.find()) {
            return false;
        }

        // Check for at least one digit
        Pattern digitPattern = Pattern.compile("\\d");
        Matcher digitMatcher = digitPattern.matcher(password);
        if (!digitMatcher.find()) {
            return false;
        }

        // Check for at least one special character
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (!specialCharMatcher.find()) {
            return false;
        }

        return true;
    }
}
