import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame implements ActionListener {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JLabel messageLabel;

    public SignUpPage() {
        setTitle("Sign Up");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        JButton signUpButton = new JButton("Sign up");
        signUpButton.addActionListener(this);
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password1 = new String(passwordField1.getPassword());
        String password2 = new String(passwordField2.getPassword());

        if (username.equals("username")) {
            messageLabel.setText("Username taken!");
        } else if (!password1.equals(password2)){
            messageLabel.setText("Passwords don't match!");
        } else {
            messageLabel.setText("Success!");
        }
    }
}
