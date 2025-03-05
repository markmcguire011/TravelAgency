package UserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CreateBooking extends JFrame {
    public CreateBooking() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        // Back button
        JButton backButton = new JButton("←");
        backButton.setBounds(20, 20, 50, 30);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            new UserHome().setVisible(true);
            this.dispose();
        });
        this.add(backButton);

        // Date range selectors
        JLabel fromLabel = new JLabel("From");
        fromLabel.setBounds(50, 80, 50, 30);
        this.add(fromLabel);

        JTextField fromDate = new JTextField();
        fromDate.setBounds(100, 80, 120, 30);
        this.add(fromDate);

        JLabel toLabel = new JLabel("to");
        toLabel.setBounds(50, 120, 50, 30);
        this.add(toLabel);

        JTextField toDate = new JTextField();
        toDate.setBounds(100, 120, 120, 30);
        this.add(toDate);

        // Location section
        JLabel locationLabel = new JLabel("Find your next destination");
        locationLabel.setBounds(300, 80, 200, 30);
        locationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(locationLabel);

        JTextField locationEntry = new JTextField();
        locationEntry.setBounds(300, 120, 120, 30);
        locationEntry.setText("Search location...");
        locationEntry.setForeground(Color.GRAY);

        // Add focus listener to handle placeholder behavior
        locationEntry.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (locationEntry.getText().equals("Search location...")) {
                    locationEntry.setText("");
                    locationEntry.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (locationEntry.getText().isEmpty()) {
                    locationEntry.setText("Search location...");
                    locationEntry.setForeground(Color.GRAY);
                }
            }
        });

        this.add(locationEntry);

        // Location table panel with checkboxes
        JPanel locationTable = new JPanel();
        locationTable.setLayout(new BoxLayout(locationTable, BoxLayout.Y_AXIS));
        locationTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Create scroll pane and add location table to it
        JScrollPane scrollPane = new JScrollPane(locationTable);
        scrollPane.setBounds(300, 180, 250, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add locations to the table
        String[] locations = { "New York", "London", "Paris", "Tokyo", "Rome", "Barcelona",
                "Amsterdam", "Berlin", "Madrid", "San Francisco", "Chicago",
                "Miami", "San Diego", "San Jose", "Austin", "Seattle",
                "Washington D.C.", "Boston", "Philadelphia", "San Antonio" };

        for (String location : locations) {
            JPanel row = new JPanel(new BorderLayout());
            row.setMaximumSize(new Dimension(250, 40));
            JCheckBox checkbox = new JCheckBox();
            JLabel label = new JLabel(location);
            row.add(checkbox, BorderLayout.WEST);
            row.add(label, BorderLayout.CENTER);
            locationTable.add(row);
        }

        this.add(scrollPane); // Add scrollPane instead of locationTable

        // Price range slider
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBounds(50, 180, 100, 30);
        this.add(priceLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)  // Add padding (top, left, bottom, right)
        ));
        pricePanel.setBounds(50, 220, 80, 110);
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));

        JLabel lowLabel = new JLabel("low");
        JSlider lowPriceSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        JLabel highLabel = new JLabel("high");
        JSlider highPriceSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        pricePanel.add(lowLabel);
        pricePanel.add(lowPriceSlider);
        pricePanel.add(highLabel);
        pricePanel.add(highPriceSlider);
        this.add(pricePanel);

        // Purchase button
        JButton purchaseButton = new JButton("Checkout");
        purchaseButton.setBounds(350, 400, 150, 40);
        purchaseButton.addActionListener(e -> {
            new Purchase().setVisible(true);
            this.dispose();
        });
        this.add(purchaseButton);
    }

    public static void main(String[] args) {
        CreateBooking createBooking = new CreateBooking();
        createBooking.setVisible(true);
    }
}
