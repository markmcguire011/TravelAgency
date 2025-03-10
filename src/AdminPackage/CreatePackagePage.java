package AdminPackage;
import LoginPackage.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CreatePackagePage extends SubPageAdmin{
    class FlightDisplay {
        int fid;
        double price;
        String airline;
        public FlightDisplay(int fid, String airline, double price) {
            this.fid = fid;
            this.price = price;
            this.airline = airline;
        }
        @Override
        public String toString() {
            if(fid == -1) {
                return "...";
            }
            return String.format("id %d - %s - $%.2f",fid,airline,price);
        }
    }
    class HotelDisplay{
        int hid;
        String name;
        double price;
        public HotelDisplay(int hid,String name,double price) {
            this.hid = hid;
            this.name = name;
            this.price = price;
        }
        @Override
        public String toString() {
            if(hid == -1) {
                return "...";
            }
            return String.format("id %d - %s - $%.2f",hid,name,price);
        }
    }
    public CreatePackagePage() {
        this.setLayout(new BorderLayout());
        this.setTitle("create package");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JComboBox<HotelDisplay> hotelComboBox = createComboBox();
        JComboBox<FlightDisplay> flightComboBox = createComboBox();
        JComboBox<String> originCityComboBox = createComboBox();
        JComboBox<String> destinationCityComboBox = createComboBox();
        try {
            initializeCities(originCityComboBox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        destinationCityComboBox.addItem("...");
        hotelComboBox.addItem(new HotelDisplay(-1,null,-1.));
        flightComboBox.addItem(new FlightDisplay(-1,null,-1.));

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
            hotelComboBox.addItem(new HotelDisplay(-1,null,-1.));
            flightComboBox.addItem(new FlightDisplay(-1,null,-1.));
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

                hotelComboBox.addItem(new HotelDisplay(-1,null,-1.));
                flightComboBox.addItem(new FlightDisplay(-1,null,-1.));

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
                            flightComboBox.addItem(new FlightDisplay(fid,airlineName,price));
                        }
                        String query2 = "SELECT DISTINCT hid,hotelName,cost FROM Hotel WHERE city = ?;";
                        statement = Main.getConnection().prepareStatement(query2);
                        statement.setString(1,destinationCity);
                        rs = statement.executeQuery();
                        while(rs.next()) {
                            int hid = rs.getInt(1);
                            String hotelName = rs.getString(2);
                            Double price = rs.getDouble(3);
                            hotelComboBox.addItem(new HotelDisplay(hid,hotelName,price));
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        create.addActionListener(e -> {
            HotelDisplay hotelDisplay = (HotelDisplay)hotelComboBox.getSelectedItem();
            FlightDisplay flightDisplay = (FlightDisplay)flightComboBox.getSelectedItem();
            int hid = hotelDisplay.hid;
            int fid = flightDisplay.fid;
            String originCity = originCityComboBox.getSelectedItem().toString();
            String destinationCity = destinationCityComboBox.getSelectedItem().toString();
            if(!originCity.equals("...") && !destinationCity.equals("...") && hid != -1 && fid != -1) {
                Connection conn = Main.getConnection();
                String query = String.format("INSERT INTO Package(hotelID,flight,city,price) VALUES (?,?,?,?);");
                PreparedStatement statement2 = null;
                try {
                    statement2 = conn.prepareStatement(query);
                    statement2.setInt(1,hid);
                    statement2.setInt(2,fid);
                    statement2.setString(3,destinationCity);
                    statement2.setDouble(4,hotelDisplay.price + flightDisplay.price);
                    statement2.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String successMessage = String.format("package successfully created!");
                errorMessage.setText(successMessage);
            }
            else {
                errorMessage.setText("please make sure all fields are selected.");
            }


            System.out.printf("hid: %d fid: %d price: %.2f\n",hotelDisplay.hid,flightDisplay.fid,hotelDisplay.price + flightDisplay.price);

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
    private <E> JComboBox<E> createComboBox() {
        JComboBox<E> comboBox = new JComboBox<>();
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
}
