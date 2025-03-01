import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SubPage frame = new SubPage();
        frame.setVisible(true);
        JButton backButton = frame.getBackButton();
        backButton.addActionListener(e -> {
            System.out.println("Back button clicked");
        });
    }
}
