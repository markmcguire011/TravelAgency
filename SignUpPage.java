import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUpPage extends JFrame implements ActionListener {
    static Connection connect;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel messageLabel;
    private JButton backButton;
    private JButton signUpButton;

    public SignUpPage(Connection connect) {
        SignUpPage.connect = connect;
        setTitle("Sign Up");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton = new JButton("←");
        backButton.addActionListener(this);

        JLabel firstNameLabel = new JLabel("First name:");
        firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last name:");
        lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel1 = new JLabel("Password:");
        passwordField1 = new JPasswordField();
        JLabel passwordLabel2 = new JLabel("Confirm password:");
        passwordField2 = new JPasswordField();
        signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(this);
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

        panel.add(backButton);
        panel.add(new JLabel(""));

        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel1);
        panel.add(passwordField1);
        panel.add(passwordLabel2);
        panel.add(passwordField2);
        panel.add(signUpButton);
        panel.add(messageLabel);

        add(panel);
        setVisible(true);
    }

    public void goTo(JFrame frame){
        frame.setVisible(true);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(backButton)){
            LandingPage landingPage = new LandingPage(connect);
            goTo(landingPage);
        } else if (ae.getSource().equals(signUpButton)){
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password1 = new String(passwordField1.getPassword());
            String password2 = new String(passwordField2.getPassword());

            if (firstName.isEmpty() || lastName.isEmpty()){
                messageLabel.setText("Name cannot be empty!");
            } else if (username.isEmpty()){
                messageLabel.setText("Username cannot be empty!");
            } else if (!password1.equals(password2)){
                messageLabel.setText("Passwords don't match!");
            } else {
                try{
                    String selectUser = "SELECT * FROM Users WHERE userName = ?";
                    PreparedStatement ps1 = connect.prepareStatement(selectUser);
                    ps1.setString(1, username);
                    ResultSet rs = ps1.executeQuery();
                    if(!rs.next()){
                        String userString = "INSERT INTO Users (userName, firstName, lastName, userPassword) " +
                                "VALUES (?, ?, ?, ?);";
                        PreparedStatement ps2 = connect.prepareStatement(userString);
                        ps2.setString(1, username);
                        ps2.setString(2, firstName);
                        ps2.setString(3, lastName);
                        ps2.setString(4, password1);
                        ps2.executeUpdate();
                        messageLabel.setText("Success!");
                        int delay = 5000; // Delay in milliseconds
                        ActionListener taskPerformer = evt -> {
                            LandingPage landingPage = new LandingPage(connect);
                            goTo(landingPage);
                        };
                        Timer timer = new Timer(delay, taskPerformer);
                        timer.setRepeats(false); // Execute only once
                        timer.start();
                    } else {
                        messageLabel.setText("Username taken!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            passwordField1.setText("");
            passwordField2.setText("");
        }
    }
}
