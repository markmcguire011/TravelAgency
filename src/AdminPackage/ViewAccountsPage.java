package AdminPackage;

import javax.swing.*;

import java.awt.*;

public class ViewAccountsPage extends SubPageAdmin{
    public ViewAccountsPage() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(55));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setMaximumSize(new Dimension(80,45));
        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setMaximumSize(new Dimension(80,45));

        firstNamePanel.setLayout(new BoxLayout(firstNamePanel,BoxLayout.Y_AXIS));
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel,BoxLayout.Y_AXIS));

        JLabel firstNameLabel = new JLabel("first name");
        JLabel lastNameLabel = new JLabel("last name");

        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();

        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstName);

        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastName);

        namePanel.add(firstNamePanel);
        namePanel.add(lastNamePanel);

        panel.add(namePanel);

        JButton deactivate = new JButton("deactivate");

        deactivate.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(200));
        panel.add(deactivate);


        this.add(panel);
        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));

    }

    public static void main(String[] args) {

        ViewAccountsPage vap = new ViewAccountsPage();
        vap.setVisible(true);
    }

}
