import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JFrame {

    private JLabel titleLabel;

    public LandingPage() {
        setTitle("Welcome!");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Welcome to XYZ Travel Agency!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign up");

        panel.add(titleLabel);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        panel.add(signUpButton);

        add(panel);
        setVisible(true);
    }
}
