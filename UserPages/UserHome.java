package UserPages;

import javax.swing.*;
import java.awt.*;

public class UserHome extends JFrame {
    public UserHome() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(690, 10, 100, 30);
        this.add(logoutButton);

        JLabel welcomeMessage = new JLabel("Welcome, " + "John Doe");
        welcomeMessage.setBounds(20, 0, 200, 50);
        this.add(welcomeMessage);

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.setBounds(180, 120, 200, 50);
        this.add(viewBookingsButton);

        JButton createBookingsButton = new JButton("Create Booking");
        createBookingsButton.setBounds(420, 120, 200, 50);
        this.add(createBookingsButton);

        JLabel bookingHistoryLabel = new JLabel("Upcoming Bookings");
        bookingHistoryLabel.setBounds(200, 190, 200, 50);
        this.add(bookingHistoryLabel);

        // Add panel for booking cards
        JPanel bookingsPanel = new JPanel();
        bookingsPanel.setLayout(new GridLayout(5, 1, 0, 20));
        bookingsPanel.setBounds(200, 230, 400, 280);

        // Example of adding booking cards (replace with your actual booking data)
        for (int i = 0; i < 5; i++) {
            JPanel card = createBookingCard("New York", "2024-04-" + (i + 1), "2024-04-" + (i + 5));
            bookingsPanel.add(card);
        }

        this.add(bookingsPanel);

        // View Bookings button
        viewBookingsButton.addActionListener(e -> {
            new ViewBookings().setVisible(true);
            this.dispose();
        });

        // Create Booking button
        createBookingsButton.addActionListener(e -> {
            new CreateBooking().setVisible(true);
            this.dispose();
        });

        // Logout button
        logoutButton.addActionListener(e -> {
            // Add login page navigation here
            this.dispose();
        });
    }

    private JPanel createBookingCard(String city, String startDate, String endDate) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        card.setBackground(Color.WHITE);

        // Create a sub-panel for the content using FlowLayout with LEFT alignment
        JPanel contentPanel = new JPanel(new BorderLayout(10, 0));
        contentPanel.setBackground(Color.WHITE);

        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel dateLabel = new JLabel(startDate + " to " + endDate);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Align date to the right

        contentPanel.add(cityLabel, BorderLayout.WEST);
        contentPanel.add(dateLabel, BorderLayout.EAST);

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    public static void main(String[] args) {
        UserHome home = new UserHome();
        home.setVisible(true);
    }
}
