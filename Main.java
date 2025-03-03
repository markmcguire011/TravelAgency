import javax.swing.*;

import AdminPages.SubPageAdmin;

public class Main {

    public static void main(String[] args) {
        SubPageAdmin frame = new SubPageAdmin();
        frame.setVisible(true);
        JButton backButton = frame.getBackButton();
        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
        });

    }
}
