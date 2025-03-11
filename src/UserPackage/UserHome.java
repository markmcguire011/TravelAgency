package src.UserPackage;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import src.models.Booking;
import src.utils.BookingDAO;
import src.models.Package;
import src.utils.PackageDAO;
import src.models.User;
import java.util.Comparator;
public class UserHome extends JFrame {
    private User user;

    public UserHome(User user) {
        this.user = user;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(690, 10, 100, 30);
        this.add(logoutButton);

        JLabel welcomeMessage = new JLabel("Welcome, " + user.getFirstName());
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

        // Replace the hardcoded booking cards with actual data
        loadBookingCards(); // Load the actual booking data

        // View Bookings button
        viewBookingsButton.addActionListener(e -> {
            new ViewBookings(user).setVisible(true);
            this.dispose();
        });

        // Create Booking button
        createBookingsButton.addActionListener(e -> {
            new CreateBooking(user).setVisible(true);
            this.dispose();
        });

        // Logout button
        logoutButton.addActionListener(e -> {
            // Add login page navigation here
            this.dispose();
        });
    }

    private void loadBookingCards() {
        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getCurrentBookings(user.getUserName());

        JPanel bookingsPanel = new JPanel();
        bookingsPanel.setBounds(200, 230, 400, 280);

        if (bookings.isEmpty()) {
            bookingsPanel.setLayout(new BorderLayout());
            JLabel noBookingsLabel = new JLabel("No bookings, let's plan your next trip!", SwingConstants.CENTER);
            noBookingsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noBookingsLabel.setForeground(new Color(128, 128, 128)); // Gray color
            bookingsPanel.add(noBookingsLabel, BorderLayout.CENTER);
        } else {
            bookingsPanel.setLayout(new GridLayout(Math.min(bookings.size(), 5), 1, 0, 20));

            // Sort bookings by fromDate
            bookings.sort(Comparator.comparing(Booking::getFromDate));
            int limit = Math.min(bookings.size(), 5);

            for (int i = 0; i < limit; i++) {
                Booking booking = bookings.get(i);
                PackageDAO packageDAO = new PackageDAO();
                Package pkg = packageDAO.getPackageById(booking.getPackageID());
                JPanel card = createBookingCard(
                        pkg.getCity(),
                        booking.getFromDate().toString(),
                        booking.getToDate().toString());
                bookingsPanel.add(card);
            }
        }

        this.add(bookingsPanel);
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
        UserHome home = new UserHome(new User("aanderson2803", "Alonzo", "Anderson", "#br1*^yURYkBhY&", false, true));
        home.setVisible(true);
    }
}
