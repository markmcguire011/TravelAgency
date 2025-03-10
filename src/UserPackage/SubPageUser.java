package src.UserPackage;
import javax.swing.*;

public class SubPageUser extends JFrame {

    private JButton backButton;

    public SubPageUser() {
        super();
        this.setLayout(null);
        this.setTitle("SubPage");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 50);
        this.add(backButton);
    }

    public JButton getBackButton() {
        return backButton;
    }

}
