package LoginPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import models.User;
import UserPackage.UserHome;
import AdminPackage.AdminLandingPage;

public class LoginPage extends JFrame implements ActionListener {
    static Connection connect;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JButton backButton;
    private JButton loginButton;

    public LoginPage(Connection connect) {
        LoginPage.connect = connect;
        setTitle("Login");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        backButton = new JButton("←");
        backButton.addActionListener(this);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

        panel.add(backButton);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(messageLabel);

        add(panel);
        setVisible(true);
    }

    public void goTo(JFrame frame) {
        frame.setVisible(true);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(backButton)) {
            LandingPage landingPage = new LandingPage(connect);
            goTo(landingPage);
        } else if (ae.getSource().equals(loginButton)) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                String selectUser = "SELECT * FROM Users WHERE userName = ?";
                PreparedStatement ps = connect.prepareStatement(selectUser);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    messageLabel.setText("Invalid username or password.");
                    usernameField.setText("");
                    passwordField.setText("");
                } else {
                    if (!password.equals(rs.getString("userPassword"))) {
                        messageLabel.setText("Invalid username or password.");
                        usernameField.setText("");
                        passwordField.setText("");
                    } else if (rs.getBoolean("isActive") == false) {
                        messageLabel.setText("Account is not active.");
                        usernameField.setText("");
                        passwordField.setText("");
                    } else {
                        messageLabel.setText("Login successful!");
                        if (rs.getBoolean("isAdmin")) {
                            AdminLandingPage adminLandingPage = new AdminLandingPage();
                            goTo(adminLandingPage);
                        } else {
                            UserHome userHome = new UserHome(
                                    new User(username, rs.getString("firstName"), rs.getString("lastName"), password,
                                            rs.getBoolean("isAdmin"), rs.getBoolean("isActive")));
                            goTo(userHome);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
