package LoginPackage;

import AdminPackage.AdminLandingPage;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Connection connect;

    static {
        try {
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/travelProjectUser","travelProjectUser","365projectpassword123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static String userName = null;

    public static void main(String[] args) throws SQLException {
        AdminLandingPage alp = new AdminLandingPage();
        alp.setVisible(true);

        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM City;");
        while (rs.next()) {
            String studentName = rs.getString(1); // name is first field
            System.out.println("City name = " +
                    studentName);
        }


    }
    public static Connection getConnection() {
        return Main.connect;
    }
}