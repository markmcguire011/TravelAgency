import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SubPageUser frame = new SubPageUser();
        frame.setVisible(true);
        JButton backButton = frame.getBackButton();
        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
        });

        UserHome home = new UserHome();
        home.setVisible(true);
    }
}
