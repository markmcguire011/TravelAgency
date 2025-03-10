package AdminPackage;
import LoginPackage.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

public class CreatePackagePage extends SubPageAdmin{
//    class FlightDisplay {
//        int fid;
//        int hid;
//        String airline;
//        public Flight
//    }
//    class HotelDisplay{
//
//    }
    public CreatePackagePage() {
        this.setLayout(new BorderLayout());
        this.setTitle("create package");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JComboBox<String> hotelComboBox = createComboBox();
        JComboBox<String> flightComboBox = createComboBox();
        JComboBox<String> originCityComboBox = createComboBox();
        JComboBox<String> destinationCityComboBox = createComboBox();
        try {
            initializeCities(originCityComboBox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        destinationCityComboBox.addItem("...");
        hotelComboBox.addItem("...");
        flightComboBox.addItem("...");

        JButton create = new JButton("create");
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        create.setMaximumSize(new Dimension(80,30));

        JLabel hotelLabel = createLabel("hotel");
        JLabel flightLabel = createLabel("flight");
        JLabel originCityLabel = createLabel("origin city");
        JLabel destinationCityLabel = createLabel("destination city");

        JLabel errorMessage = createLabel("");

        panel.add(Box.createVerticalStrut(65));

        panel.add(originCityLabel);
        panel.add(originCityComboBox);
        panel.add(destinationCityLabel);
        panel.add(destinationCityComboBox);
        panel.add(flightLabel);
        panel.add(flightComboBox);
        panel.add(hotelLabel);
        panel.add(hotelComboBox);

        originCityComboBox.addActionListener(e -> {
            String originCity = originCityComboBox.getSelectedItem().toString();
            destinationCityComboBox.removeAllItems();
            flightComboBox.removeAllItems();
            hotelComboBox.removeAllItems();
            flightComboBox.addItem("...");
            hotelComboBox.addItem("...");
            if(!originCity.equals("...")) {
                try {
                    String query = "SELECT DISTINCT destinationCity FROM Flight WHERE originCity = ?";
                    PreparedStatement statement = Main.getConnection().prepareStatement(query);
                    statement.setString(1,originCity);
                    ResultSet rs = statement.executeQuery();
                    destinationCityComboBox.removeAllItems();
                    destinationCityComboBox.addItem("...");
                    while(rs.next()) {
                        String destinationCity = rs.getString(1);
                        destinationCityComboBox.addItem(destinationCity);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                destinationCityComboBox.addItem("...");
            }
        });

        destinationCityComboBox.addActionListener(e-> {
            if(destinationCityComboBox.getSelectedItem() != null ) {
                String destinationCity = destinationCityComboBox.getSelectedItem().toString();
                String originCity = originCityComboBox.getSelectedItem().toString();
                flightComboBox.removeAllItems();
                hotelComboBox.removeAllItems();

                flightComboBox.addItem("...");
                hotelComboBox.addItem("...");

                if(!originCity.equals("...") && !destinationCity.equals("...")) {
                    try {
                        String query = "SELECT DISTINCT fid,airline,price FROM Flight WHERE originCity = ? AND destinationCity = ?";
                        PreparedStatement statement = Main.getConnection().prepareStatement(query);
                        statement.setString(1,originCity);
                        statement.setString(2,destinationCity);
                        ResultSet rs = statement.executeQuery();
                        while(rs.next()) {
                            int fid = rs.getInt(1);
                            String airlineName = rs.getString(2);
                            Double price = rs.getDouble(3);
                            String flightDisplay = String.format("id %d - %s - $%.2f",fid,airlineName,price);
                            flightComboBox.addItem(flightDisplay);
                        }
                        String query2 = "SELECT DISTINCT hid,hotelName,cost FROM Hotel WHERE city = ?;";
                        statement = Main.getConnection().prepareStatement(query2);
                        statement.setString(1,destinationCity);
                        rs = statement.executeQuery();
                        while(rs.next()) {
                            int hid = rs.getInt(1);
                            String hotelName = rs.getString(2);
                            Double price = rs.getDouble(3);
                            String hotelDisplay = String.format("id %d - %s - $%.2f",hid,hotelName,price);
                            hotelComboBox.addItem(hotelDisplay);
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        create.addActionListener(e -> {
            String hotelDisplay = hotelComboBox.getSelectedItem().toString();
            String flightDisplay = flightComboBox.getSelectedItem().toString();
            String originCity = originCityComboBox.getSelectedItem().toString();
            String destinationCity = destinationCityComboBox.getSelectedItem().toString();

            int hid = findFirstInteger(hotelDisplay);
            int fid = findFirstInteger(flightDisplay);

            System.out.printf("hid: %d fid: %d\n",hid,fid);

        });

        panel.add(create);
        panel.add(errorMessage);
        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));

        this.add(panel);
    }
    public static void main(String[] args) {
        CreatePackagePage c = new CreatePackagePage();
        c.setVisible(true);

    }
    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setMaximumSize(new Dimension(190,35));
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);



        return comboBox;
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void initializeCities(JComboBox<String> comboBox) throws SQLException {
        Statement statement = Main.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT cityName FROM City ORDER BY cityName ASC");
        comboBox.addItem("...");
        while(rs.next()) {
            String cityName = rs.getString(1);
            comboBox.addItem(cityName);
        }
    }
    private static int findFirstInteger(String str) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                num.append(c);
            } else if (!num.isEmpty()) {
                break;
            }
        }
        if (!num.isEmpty()) {
            return Integer.parseInt(num.toString());
        }
        return -1;
    }
}
