package src.UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import src.models.Booking;
import src.utils.BookingDAO;

public class ViewBookings extends JFrame {
    private JPanel bookingsPanel;
    private ButtonGroup filterGroup;
    private BookingDAO bookingDAO;
    private JPanel filterPanel;
    private String username;

    public ViewBookings(String username) {
        this.username = username;
        bookingDAO = new BookingDAO();
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
            new UserHome(username).setVisible(true);
            this.dispose();
        });
        this.add(backButton);

        // Filter buttons panel
        filterPanel = new JPanel();
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
        setupFilterButtons();

        // Bookings panel with scroll
        bookingsPanel = new JPanel();
        bookingsPanel.setLayout(new BoxLayout(bookingsPanel, BoxLayout.Y_AXIS));
        bookingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(bookingsPanel);
        scrollPane.setBounds(200, 80, 400, 450);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Load current bookings by default
        loadBookings("current");

        this.add(scrollPane);
    }

    private void loadBookings(String filter) {
        bookingsPanel.removeAll(); // Clear existing bookings

        List<Booking> bookings;
        String username = "user1";

        switch (filter.toLowerCase()) {
            case "past":
                bookings = bookingDAO.getPastBookings(username);
                break;
            case "current":
                bookings = bookingDAO.getCurrentBookings(username);
                break;
            default:
                bookings = bookingDAO.getAllBookings(username);
        }

        for (Booking booking : bookings) {
            addBookingCard(booking);
        }

        bookingsPanel.revalidate();
        bookingsPanel.repaint();
    }

    // Add filter button listeners
    private void setupFilterButtons() {
        for (Component c : filterPanel.getComponents()) {
            if (c instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) c;
                btn.addActionListener(e -> loadBookings(btn.getText()));
            }
        }
    }

    private void addBookingCard(Booking booking) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(380, 150));
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setBackground(Color.WHITE);

        // City and dates panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel bookingIDLabel = new JLabel(Integer.toString(booking.getBid()));
        bookingIDLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel fromDateLabel = new JLabel(booking.getFromDate().toString());
        fromDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        fromDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel toDateLabel = new JLabel(booking.getToDate().toString());
        toDateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        toDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(bookingIDLabel, BorderLayout.WEST);
        headerPanel.add(fromDateLabel, BorderLayout.CENTER);
        headerPanel.add(toDateLabel, BorderLayout.EAST);

        // Hotel and airline info
        // JLabel hotelLabel = new JLabel("Hotel: " + hotel);
        // hotelLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // JLabel airlineLabel = new JLabel("Flight: " + airline);
        // airlineLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        card.add(headerPanel);
        card.add(Box.createVerticalStrut(10));
        // card.add(hotelLabel);
        // card.add(Box.createVerticalStrut(5));
        // card.add(airlineLabel);

        bookingsPanel.add(card);
        bookingsPanel.add(Box.createVerticalStrut(20));
    }

    public static void main(String[] args) {
        ViewBookings viewBookings = new ViewBookings("user1");
        viewBookings.setVisible(true);
    }
}
