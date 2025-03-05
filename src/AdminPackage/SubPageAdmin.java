package AdminPackage;

import javax.swing.*;

public class SubPageAdmin extends DefaultAdmin {

    private final JButton backButton;

    public SubPageAdmin() {
        super();
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 70, 35);
        this.add(backButton);
    }

    public JButton getBackButton() {
        return backButton;
    }


}
