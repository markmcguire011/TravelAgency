package UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Booking;
import utils.BookingDAO;
import models.User;
import utils.PackageDAO;
import models.Package;
import utils.DatabaseConnection;

public class ViewBookings extends JFrame {
    private JPanel bookingsPanel;
    private ButtonGroup filterGroup;
    private BookingDAO bookingDAO;
    private JPanel filterPanel;
    private User user;

    public ViewBookings(User user) {
        this.user = user;
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
            new UserHome(user).setVisible(true);
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

        switch (filter.toLowerCase()) {
            case "past":
                bookings = bookingDAO.getPastBookings(user.getUserName());
                break;
            case "current":
                bookings = bookingDAO.getCurrentBookings(user.getUserName());
                break;
            default:
                bookings = bookingDAO.getAllBookings(user.getUserName());
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
        card.setMaximumSize(new Dimension(380, 200));
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setBackground(Color.WHITE);

        // Get package information
        PackageDAO packageDAO = new PackageDAO();
        Package pkg = packageDAO.getPackageById(booking.getPackageID());

        // Header panel with location and booking ID
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Left side with location and booking ID
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Location as primary title
        JLabel locationLabel = new JLabel(pkg.getCity());
        locationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Booking ID as subtitle
        JLabel bookingIDLabel = new JLabel("Booking #" + booking.getBid());
        bookingIDLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingIDLabel.setForeground(new Color(100, 100, 100));
        bookingIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(locationLabel);
        leftPanel.add(Box.createVerticalStrut(3));
        leftPanel.add(bookingIDLabel);

        // Right side with dates
        JLabel datesLabel = new JLabel(String.format("<html>%s<br>to<br>%s</html>",
                booking.getFromDate().toString(),
                booking.getToDate().toString()));
        datesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        datesLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(datesLabel, BorderLayout.EAST);

        // Hotel info
        JPanel hotelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hotelPanel.setBackground(Color.WHITE);
        JLabel hotelLabel = new JLabel("Hotel ID: " + pkg.getHotelID());
        hotelLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelPanel.add(hotelLabel);

        // Flight info
        JPanel flightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flightPanel.setBackground(Color.WHITE);
        String flightInfo = pkg.getFlight() != 0 ? "Flight ID: " + pkg.getFlight() : "No flight included";
        JLabel flightLabel = new JLabel("Flight: " + flightInfo);
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        flightPanel.add(flightLabel);

        // Price
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(String.format("Price: $%.2f", pkg.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pricePanel.add(priceLabel);

        // Details button
        JButton detailsButton = new JButton("Details");
        detailsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsButton.setPreferredSize(new Dimension(70, 25));
        detailsButton.setBackground(new Color(240, 240, 240));
        detailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        detailsButton.addActionListener(e -> showPackageDetails(pkg));

        // Add all components to card
        card.add(headerPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(hotelPanel);
        card.add(flightPanel);
        card.add(pricePanel);
        card.add(detailsButton);

        bookingsPanel.add(card);
        bookingsPanel.add(Box.createVerticalStrut(20));
    }

    private void showPackageDetails(Package pkg) {
        // Create the popup dialog
        JDialog dialog = new JDialog(this, "Package Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add package information
        JLabel titleLabel = new JLabel("Package #" + pkg.getPid());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cityLabel = new JLabel("Destination: " + pkg.getCity());
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get hotel details
        String hotelInfo = "Loading hotel information...";
        try {
            String query = "SELECT hotelName, hPrice, city FROM Hotel WHERE hid = ?";
            try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
                pstmt.setInt(1, pkg.getHotelID());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    hotelInfo = String.format("Hotel: %s%nLocation: %s%nCost per night: $%.2f",
                            rs.getString("hotelName"),
                            rs.getString("city"),
                            rs.getDouble("hPrice"));
                }
            }
        } catch (SQLException e) {
            hotelInfo = "Error loading hotel information";
            e.printStackTrace();
        }
        JLabel hotelLabel = new JLabel("<html>" + hotelInfo.replace("\n", "<br>") + "</html>");
        hotelLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        hotelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get flight details if available
        String flightInfo = "No flight included in package";
        if (pkg.getFlight() != 0) {
            try {
                String query = "SELECT originCity, destinationCity, airline, fPrice FROM Flight WHERE fid = ?";
                try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
                    pstmt.setInt(1, pkg.getFlight());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        flightInfo = String.format("Flight: %s%nFrom: %s%nTo: %s%nCost: $%.2f",
                                rs.getString("airline"),
                                rs.getString("originCity"),
                                rs.getString("destinationCity"),
                                rs.getDouble("fPrice"));
                    }
                }
            } catch (SQLException e) {
                flightInfo = "Error loading flight information";
                e.printStackTrace();
            }
        }
        JLabel flightLabel = new JLabel("<html>" + flightInfo.replace("\n", "<br>") + "</html>");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        flightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add separator before price
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Package price
        JLabel priceLabel = new JLabel(String.format("$%.2f", pkg.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(cityLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(hotelLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(flightLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(separator);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(priceLabel);

        // Add close button at bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);

        // Add panels to dialog
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show the dialog
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        ViewBookings viewBookings = new ViewBookings(new User());
        viewBookings.setVisible(true);
    }
}
