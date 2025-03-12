package AdminPackage;

import javax.swing.*;
import java.awt.*;

public class CreateAdminPage extends SubPageAdmin{
    public CreateAdminPage() {
        this.setTitle("create admin");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        JButton backButton = this.getBackButton();
        this.remove(backButton);
        JPanel topBar = new JPanel();
        topBar.add(backButton);
        topBar.setLayout(new BorderLayout());
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(70,35));
        topBar.setMaximumSize(new Dimension(400,50));
        this.add(topBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel firstNameLabel = createLabel("First name:");
        JTextField firstNameField = createTextField();
        JLabel lastNameLabel = createLabel("Last name:");
        JTextField lastNameField = createTextField();
        JLabel usernameLabel = createLabel("Username:");
        JTextField usernameField = createTextField();
        JLabel passwordLabel1 = createLabel("Password:");
        JTextField passwordField1 = createPasswordField();
        JLabel passwordLabel2 = createLabel("Confirm password:");
        JTextField passwordField2 = createPasswordField();
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = createLabel("");
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

        this.add(panel);




        backButton.addActionListener(e-> goTo(new AdminLandingPage()));


    }
    public static void main(String[]args) {
        CreateAdminPage cap = new CreateAdminPage();
        cap.setVisible(true);

    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(160,30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }
    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(160,30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return passwordField;
    }
}
