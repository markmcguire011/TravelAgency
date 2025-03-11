package AdminPackage;

import LoginPackage.Main;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.NumberFormat;

public class AddItemsPage extends SubPageAdmin{
    JPanel fluctuatingPanel = null;
    public AddItemsPage() {
        this.setTitle("add items");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        JButton backButton = this.getBackButton();
        this.remove(backButton);
        JPanel topBar = new JPanel();
        topBar.add(backButton);
        topBar.setLayout(new BorderLayout());
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(70,35));
        topBar.setMaximumSize(new Dimension(400,50));
        this.add(topBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(5));

        JLabel itemTypeLabel = new JLabel("item type");
        itemTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> itemTypeComboBox = initializeITypeComboBox();

        panel.add(itemTypeLabel);
        panel.add(itemTypeComboBox);
        this.add(panel);
        itemTypeComboBox.addActionListener(e -> {
            if(e.getSource() == itemTypeComboBox) {
                JComboBox<String> comboBox = (JComboBox<String>)e.getSource();
                String selected = itemTypeComboBox.getSelectedItem().toString();
                switch (selected) {
                    case "hotel" -> {
                        try {
                            if(fluctuatingPanel != null) {
                                this.remove(fluctuatingPanel);
                                this.revalidate();
                                this.repaint();
                            }

                            fluctuatingPanel = hotelInsertJPanel();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    case "flight" -> {
                        if(fluctuatingPanel != null) {
                            this.remove(fluctuatingPanel);
                            this.revalidate();
                            this.repaint();
                        }
                        try {
                            fluctuatingPanel = flightInsertJPanel();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    case "city" -> {
                        if(fluctuatingPanel != null) {
                            this.remove(fluctuatingPanel);
                            this.revalidate();
                            this.repaint();
                        }
                        fluctuatingPanel = singleStringInsertJPanel("city");
                    }
                    case "airline" -> {
                        if(fluctuatingPanel != null) {
                            this.remove(fluctuatingPanel);
                            this.revalidate();
                            this.repaint();
                        }
                        fluctuatingPanel = singleStringInsertJPanel("airline");
                    }
                }

            }
        });


        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));
    }
    public static void main(String[] args) {
        AddItemsPage aip = new AddItemsPage();
        aip.setVisible(true);
    }

    private JComboBox<String> initializeITypeComboBox() {
        JComboBox<String> itemTypeComboBox = new JComboBox<>();

        itemTypeComboBox.addItem("...");
        itemTypeComboBox.addItem("airline");
        itemTypeComboBox.addItem("city");
        itemTypeComboBox.addItem("flight");
        itemTypeComboBox.addItem("hotel");


        itemTypeComboBox.setMaximumSize(new Dimension(190,45));
        itemTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        return itemTypeComboBox;

    }

    private JPanel flightInsertJPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel originCityLabel = createLabel("origin city");
        JLabel destinationCityLabel = createLabel("destination city");
        JLabel airlineLabel = createLabel("airline");
        JLabel priceLabel = createLabel("price");

        JComboBox<String> originCityComboBox = createComboBox();
        JComboBox<String> destinationCityComboBox = createComboBox();
        JComboBox<String> airlineComboBox = createComboBox();
        JTextField priceField = createTextField();


        Statement statement = Main.getConnection().createStatement(); //replace with Main.connection
        ResultSet rs = statement.executeQuery("SELECT cityName FROM City ORDER BY cityName ASC");
        originCityComboBox.addItem("...");
        destinationCityComboBox.addItem("...");
        while(rs.next()) {
            String cityName = rs.getString("cityName");
            originCityComboBox.addItem(cityName);
            destinationCityComboBox.addItem(cityName);
        }

        rs = statement.executeQuery("SELECT airlineName FROM Airline ORDER BY airlineName ASC");
        airlineComboBox.addItem("...");
        while(rs.next()) {
            String airlineName= rs.getString("airlineName");
            airlineComboBox.addItem(airlineName);
        }

        panel.add(airlineLabel);
        panel.add(airlineComboBox);
        panel.add(originCityLabel);
        panel.add(originCityComboBox);
        panel.add(destinationCityLabel);
        panel.add(destinationCityComboBox);
        panel.add(priceLabel);
        panel.add(priceField);

        JButton createButton = createCreateButton();
        panel.add(Box.createVerticalStrut(5));

        panel.add(createButton);

        JLabel message = createLabel("");

        createButton.addActionListener(e -> {
            String airlineInput = airlineComboBox.getSelectedItem().toString();
            String originCityInput = originCityComboBox.getSelectedItem().toString();
            String destinationCityInput = destinationCityComboBox.getSelectedItem().toString();
            String priceInput = priceField.getText();
            try {
                String priceNum = String.format("%.2f",Double.parseDouble(priceInput));
                if(airlineInput.equals("...") || originCityInput.equals("...") || destinationCityInput.equals("...")) {
                    message.setText("please make sure all fields are selected");
                }
                else {
                    Connection conn = Main.getConnection();
                    String query = String.format("INSERT INTO Flight(originCity,destinationCity,airline,fPrice) VALUES (?,?,?,?);");
                    PreparedStatement statement2 = conn.prepareStatement(query);
                    statement2.setString(1,originCityInput);
                    statement2.setString(2,destinationCityInput);
                    statement2.setString(3,airlineInput);
                    statement2.setString(4,priceNum);
                    statement2.executeUpdate();
                    String successMessage = String.format("flight successfully created!");
                    message.setText(successMessage);
                }
            }
            catch (NumberFormatException nfe){
                message.setText("price must be a double.");
            } catch (SQLException ex) {
                message.setText("creation unsuccessful.");
                throw new RuntimeException(ex);
            }
        });

        panel.add(message);
        panel.repaint();
        panel.invalidate();

        this.add(panel);
        this.setVisible(true);

        return panel;
    }
    private JPanel singleStringInsertJPanel(String s) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel nameLabel = createLabel(s + " name");
        JTextField nameText = createTextField();

        panel.add(nameLabel);
        panel.add(nameText);

        JButton createButton = createCreateButton();
        String table;
        if(s.equals("airline")) {
            table = "Airline";
        }
        else if(s.equals("city")) {
            table = "City";
        } else {
            table = null;
        }


        panel.add(Box.createVerticalStrut(5));
        panel.add(createButton);

        JLabel message = createLabel("");

        createButton.addActionListener(e -> {
            try {
                Connection conn = Main.getConnection();
                String query = String.format("INSERT INTO %s (%s) VALUES (?);",table,s + "name");

                String input = nameText.getText();
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1,input);
                statement.executeUpdate();
                String successMessage = String.format("%s %s successfully created!",s,input);
                message.setText(successMessage);
            } catch (SQLException ex) {
                message.setText("item name has already been taken.");
                ex.printStackTrace();
            }

        });
        panel.add(message);
        panel.revalidate();
        panel.repaint();

        this.add(panel);
        this.setVisible(true);
        return panel;
    }

    private JPanel hotelInsertJPanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel nameLabel = createLabel("hotel name");
        JLabel locationLabel = createLabel("city");
        JLabel priceLabel = createLabel("price");

        JTextField nameText = createTextField();
        JComboBox<String> locationCombo = createComboBox();
        JTextField priceText = createTextField();


        Statement statement = Main.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT cityName FROM City ORDER BY cityName ASC");
        locationCombo.addItem("...");
        while(rs.next()) {
            String cityName = rs.getString("cityName");
            locationCombo.addItem(cityName);
        }

        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(locationLabel);
        panel.add(locationCombo);
        panel.add(priceLabel);
        panel.add(priceText);

        JButton createButton = createCreateButton();


        panel.add(Box.createVerticalStrut(5));
        panel.add(createButton);

        JLabel message = createLabel("");

        createButton.addActionListener(e -> {
            String nameInput = nameText.getText();
            String locationInput = locationCombo.getSelectedItem().toString();
            String priceInput = priceText.getText();
            try {
                String priceNum = String.format("%.2f",Double.parseDouble(priceInput));
                if(locationInput.equals("...")) {
                    message.setText("a location must be selected");
                }
                else {
                    Connection conn = Main.getConnection();
                    String query = String.format("INSERT INTO Hotel(hotelName,hPrice,city) VALUES (?,?,?);");
                    PreparedStatement statement2 = conn.prepareStatement(query);
                    statement2.setString(1,nameInput);
                    statement2.setString(2,priceNum);
                    statement2.setString(3,locationInput);
                    statement2.executeUpdate();
                    String successMessage = String.format("hotel %s successfully created!",nameInput);
                    message.setText(successMessage);
                }
            }
            catch (NumberFormatException nfe){
                message.setText("price must be a double.");

            } catch (SQLException ex) {
                message.setText("creation unsuccessful.");
                throw new RuntimeException(ex);
            }


        });

        panel.add(message);
        panel.repaint();
        panel.invalidate();


        this.add(panel);
        this.setVisible(true);
        //LEFT OFF HERE

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(160,30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }


    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(190,30));
        return comboBox;
    }

    public JButton createCreateButton() {
        JButton button = new JButton("create");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }



}
