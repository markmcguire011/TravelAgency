package AdminPackage;

import javax.swing.*;
import java.awt.*;

public class SubPageAdmin extends DefaultAdmin {
    private final JButton backButton;

    public SubPageAdmin() {
        super();
        backButton = new JButton("←");
        backButton.setFont(new Font("Arial",Font.PLAIN,25));
        backButton.setBounds(10, 10, 70, 35);
        this.add(backButton);

    }
    public JButton getBackButton() {return backButton;}


}
