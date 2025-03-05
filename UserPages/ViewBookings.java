package UserPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewBookings extends JFrame {
    private JPanel bookingsPanel;
    private ButtonGroup filterGroup;

    public ViewBookings() {
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

        // Filter buttons panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(200, 20, 400, 40);
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Create filter buttons
        filterGroup = new ButtonGroup();
        String[] filters = { "Past", "Current", "All" };
        for (String filter : filters) {
            JToggleButton filterBtn = new JToggleButton(filter);
            filterBtn.setFocusPainted(false);
            filterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (filter.equals("Current"))
                filterBtn.setSelected(true);
            filterGroup.add(filterBtn);
            filterPanel.add(filterBtn);
        }
        this.add(filterPanel);

        // Bookings panel with scroll
        bookingsPanel = new JPanel();
        bookingsPanel.setLayout(new BoxLayout(bookingsPanel, BoxLayout.Y_AXIS));
        bookingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(bookingsPanel);
        scrollPane.setBounds(200, 80, 400, 450);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add example bookings
        for (int i = 0; i < 10; i++) {
            addBookingCard("New York, NY", "09/18/25 - 09/25/25", "ABC Hotel", "123 Airlines");
            addBookingCard("Rome, IT", "09/18/25 - 09/25/25", "ABC Hotel", "123 Airlines");
            addBookingCard("Paris, FR", "10/01/25 - 10/08/25", "XYZ Hotel", "456 Airlines");
        }

        this.add(scrollPane);
    }

    private void addBookingCard(String city, String dates, String hotel, String airline) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(380, 150));
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setBackground(Color.WHITE);

        // City and dates panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel dateLabel = new JLabel(dates);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(cityLabel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);

        // Hotel and airline info
        JLabel hotelLabel = new JLabel("Hotel: " + hotel);
        hotelLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel airlineLabel = new JLabel("Flight: " + airline);
        airlineLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        card.add(headerPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(hotelLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(airlineLabel);

        bookingsPanel.add(card);
        bookingsPanel.add(Box.createVerticalStrut(20));
    }

    public static void main(String[] args) {
        ViewBookings viewBookings = new ViewBookings();
        viewBookings.setVisible(true);
    }
}
