package AdminPackage;

import LoginPackage.LandingPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DatabaseConnection;

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

        JLabel firstNameLabel = createLabel("first name:");
        JTextField firstNameField = createTextField();
        JLabel lastNameLabel = createLabel("last name:");
        JTextField lastNameField = createTextField();
        JLabel usernameLabel = createLabel("username:");
        JTextField usernameField = createTextField();
        JLabel passwordLabel1 = createLabel("password:");
        JPasswordField passwordField1 = createPasswordField();
        JLabel passwordLabel2 = createLabel("confirm password:");
        JPasswordField passwordField2 = createPasswordField();
        JButton signUpButton = new JButton("sign up");
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

        signUpButton.addActionListener(ae->{
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password1 = new String(passwordField1.getPassword());
                String password2 = new String(passwordField2.getPassword());

                if (firstName.isEmpty() || lastName.isEmpty()){
                    messageLabel.setText("name cannot be empty!");
                } else if (username.isEmpty()){
                    messageLabel.setText("username cannot be empty!");
                } else if (!password1.equals(password2)){
                    messageLabel.setText("passwords don't match!");
                } else {
                    try{
                        String selectUser = "SELECT * FROM Users WHERE userName = ?";
                        PreparedStatement ps1 = DatabaseConnection.getConnection().prepareStatement(selectUser);
                        ps1.setString(1, username);
                        ResultSet rs = ps1.executeQuery();
                        if(!rs.next()){
                            String userString = "INSERT INTO Users (userName, firstName, lastName, userPassword, isAdmin) " +
                                    "VALUES (?, ?, ?, ?, ?);";
                            PreparedStatement ps2 = DatabaseConnection.getConnection().prepareStatement(userString);
                            ps2.setString(1, username);
                            ps2.setString(2, firstName);
                            ps2.setString(3, lastName);
                            ps2.setString(4, password1);
                            ps2.setBoolean(5, true);    
                            ps2.executeUpdate();
                            messageLabel.setText("success!");
                        } else {
                            messageLabel.setText("username taken!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                passwordField1.setText("");
                passwordField2.setText("");
            });


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