package LoginPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import utils.DatabaseConnection;

public class LandingPage extends JFrame implements ActionListener {
    static Connection connect;
    private JLabel titleLabel;
    private JButton loginButton;
    private JButton signUpButton;

    public LandingPage(Connection connect) {
        LandingPage.connect = connect;
        setTitle("Welcome!");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Welcome to XYZ Travel Agency!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(this);

        panel.add(titleLabel);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        panel.add(signUpButton);

        add(panel);
        setVisible(true);
    }

    public void goTo(JFrame frame) {
        frame.setVisible(true);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(loginButton)) {
            LoginPage loginPage = new LoginPage(DatabaseConnection.getConnection());
            goTo(loginPage);
        } else if (ae.getSource().equals(signUpButton)) {
            SignUpPage signUpPage = new SignUpPage(DatabaseConnection.getConnection());
            goTo(signUpPage);
        }
    }
}