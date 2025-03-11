package AdminPackage;

import javax.swing.*;

public class DefaultAdmin extends JFrame {

    public DefaultAdmin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
    }
    public void goTo(JFrame frame) {
        frame.setLocation(this.getLocation());
        frame.setVisible(true);
        this.dispose();
    };

}