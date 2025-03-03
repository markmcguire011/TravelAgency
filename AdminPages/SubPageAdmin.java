package AdminPages;
import javax.swing.*;

public class SubPageAdmin extends DefaultAdmin {

    private final JButton backButton;

    public SubPageAdmin() {
        super();
        this.setLayout(null);
        this.setTitle("SubPage");
        this.setSize(300, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 70, 35);
        this.add(backButton);
    }

    public JButton getBackButton() {
        return backButton;
    }

}
