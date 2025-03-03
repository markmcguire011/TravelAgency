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
        viewBookingsButton.setBounds(180, 180, 200, 50);
        this.add(viewBookingsButton);

        JButton createBookingButton = new JButton("Create Booking");
        createBookingButton.setBounds(420, 180, 200, 50);
        this.add(createBookingButton);

        JLabel bookingHistoryLabel = new JLabel("Upcoming Bookings");
        bookingHistoryLabel.setBounds(200, 250, 200, 50);
        this.add(bookingHistoryLabel);

        // Add panel for booking cards
        JPanel bookingsPanel = new JPanel();
        bookingsPanel.setLayout(new GridLayout(5, 1, 0, 10)); // 5 rows, 1 column, 10px vertical gap
        bookingsPanel.setBounds(200, 300, 400, 250);

        // Example of adding booking cards (replace with your actual booking data)
        for (int i = 0; i < 5; i++) {
            JPanel card = createBookingCard("New York", "2024-04-" + (i + 1), "2024-04-" + (i + 5));
            bookingsPanel.add(card);
        }

        this.add(bookingsPanel);
    }

    private JPanel createBookingCard(String city, String startDate, String endDate) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setBackground(Color.WHITE);

        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel dateLabel = new JLabel(startDate + " to " + endDate);

        card.add(cityLabel, BorderLayout.NORTH);
        card.add(dateLabel, BorderLayout.CENTER);

        return card;
    }
}
