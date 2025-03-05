package AdminPackage;

import LoginPackage.Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddItemsPage extends SubPageAdmin{
    public AddItemsPage() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(65));

        JLabel itemTypeLabel = new JLabel("item type");
        itemTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> itemTypeComboBox = initializeITypeComboBox();



        panel.add(itemTypeLabel);
        panel.add(itemTypeComboBox);
        panel.add(Box.createVerticalStrut(35));

        itemTypeComboBox.addActionListener(e -> {
            if(e.getSource() == itemTypeComboBox) {
                JComboBox<String> comboBox = (JComboBox<String>)e.getSource();
                String selected = itemTypeComboBox.getSelectedItem().toString();
                System.out.println(selected);
                switch (selected) {
                    case "hotel" -> {
                        try {
                            JPanel hotelPanel = hotelInsertJPanel();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    case "flights" -> {
                    }
                    case "location" -> {
                    }
                    case "..." -> {
                    }
                }

            }
        });

        this.add(panel);
        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));
    }
    public static void main(String[] args) {
        AddItemsPage aip = new AddItemsPage();
        aip.setVisible(true);
    }

    private JComboBox<String> initializeITypeComboBox() {
        JComboBox<String> itemTypeComboBox = new JComboBox<>();

        itemTypeComboBox.addItem("...");
        itemTypeComboBox.addItem("hotel");
        itemTypeComboBox.addItem("airline");
        itemTypeComboBox.addItem("flight");
        itemTypeComboBox.addItem("location");

        itemTypeComboBox.setMaximumSize(new Dimension(160,45));
        itemTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        return itemTypeComboBox;

    }

    private JPanel hotelInsertJPanel() throws SQLException {
        JFrame frame = this;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel nameLabel = createLabel("hotel name");
        JLabel locationLabel = createLabel("location");
        JLabel priceLabel = createLabel("price");

        JTextField nameText = createTextField();
        nameText.setMaximumSize(new Dimension(160,30));
        JComboBox<String> locationCombo = createComboBox();
        //YOU LEFT OFF RIGHT HERE

        Statement statement = Main.getConnection().createStatement(); //replace with Main.connection
        ResultSet rs = statement.executeQuery("SELECT cityName FROM City ORDER BY cityName ASC");
        while(rs.next()) {
            String cityName = rs.getString("cityName");
            locationCombo.addItem(cityName);
        }


        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(locationLabel);
        panel.add(locationCombo);


        JTextField priceText = createTextField();
        System.out.println(this.getClass());
        frame.add(panel);


        frame.setVisible(true);
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
        textField.setSize(new Dimension(160,30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }


    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setSize(new Dimension(160,30));
        return comboBox;
    }


}
