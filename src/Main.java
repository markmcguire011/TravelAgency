import javax.swing.*;
import java.sql.Connection;
import utils.DatabaseConnection;
import LoginPackage.LandingPage;

public class Main {
    public static void main(String[] args) {
        Connection connect = DatabaseConnection.getConnection();
        LandingPage landingPage = new LandingPage(connect);
        landingPage.setVisible(true);
    }
}