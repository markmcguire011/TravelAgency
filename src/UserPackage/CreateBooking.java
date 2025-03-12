package UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Package;
import utils.PackageDAO;
import models.User;
import utils.DatabaseConnection;
import UserPackage.UserHome;

public class CreateBooking extends JFrame {
    private User user;

    public CreateBooking(User user) {
        this.user = user;
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

        // Date range selectors
        JLabel fromLabel = new JLabel("From");
        fromLabel.setBounds(50, 80, 50, 30);
        this.add(fromLabel);

        JTextField fromDate = new JTextField();
        fromDate.setBounds(100, 80, 120, 30);
        setupDateField(fromDate, "MM/DD/YY");
        this.add(fromDate);

        JLabel toLabel = new JLabel("to");
        toLabel.setBounds(50, 120, 50, 30);
        this.add(toLabel);

        JTextField toDate = new JTextField();
        toDate.setBounds(100, 120, 120, 30);
        setupDateField(toDate, "MM/DD/YY");
        this.add(toDate);

        // Location section
        JLabel locationLabel = new JLabel("Find your next destination");
        locationLabel.setBounds(300, 80, 200, 30);
        locationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(locationLabel);

        // Search section
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(300, 120, 250, 30);
        searchPanel.setLayout(new BorderLayout(5, 0));

        JTextField locationEntry = new JTextField();
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

        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add components to search panel
        searchPanel.add(locationEntry, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        this.add(searchPanel);

        // Location table panel with radio buttons
        JPanel locationTable = new JPanel();
        locationTable.setLayout(new BoxLayout(locationTable, BoxLayout.Y_AXIS));
        locationTable.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Create scroll pane and add location table to it
        JScrollPane scrollPane = new JScrollPane(locationTable);
        scrollPane.setBounds(200, 180, 500, 250);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add locations to the table
        PackageDAO packageDAO = new PackageDAO();
        List<Package> packages = packageDAO.getAllPackages();
        ButtonGroup packageGroup = new ButtonGroup(); // For mutually exclusive selection

        for (Package pkg : packages) {
            addPackageRow(pkg, locationTable, packageGroup);
        }

        this.add(scrollPane);

        // Purchase button
        JButton purchaseButton = new JButton("Checkout");
        purchaseButton.setBounds(350, 450, 150, 40);
        purchaseButton.addActionListener(e -> {
            Package selectedPackage = null;

            // Find selected package
            for (Component comp : locationTable.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel row = (JPanel) comp;
                    Component radioPanel = row.getComponent(0);
                    if (radioPanel instanceof JPanel) {
                        JRadioButton radio = (JRadioButton) ((JPanel) radioPanel).getComponent(0);
                        if (radio.isSelected()) {
                            selectedPackage = (Package) row.getClientProperty("package");
                            break;
                        }
                    }
                }
            }

            if (selectedPackage == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a package",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String fromDateText = fromDate.getText();
            String toDateText = toDate.getText();

            if (fromDateText.equals("MM/DD/YY") || toDateText.equals("MM/DD/YY")) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid dates",
                        "Invalid Dates",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate date range
            if (!isValidDateRange(fromDateText, toDateText)) {
                return;
            }

            List<Package> selectedPackages = new ArrayList<>();
            selectedPackages.add(selectedPackage);
            new Purchase(selectedPackages, fromDateText, toDateText, user).setVisible(true);
            this.dispose();
        });
        this.add(purchaseButton);

        // Add action listeners for search functionality
        ActionListener searchAction = e -> filterPackages(locationEntry.getText(), locationTable, packages,
                packageGroup);
        searchButton.addActionListener(searchAction);
        locationEntry.addActionListener(searchAction);
    }

    private void setupDateField(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                } else {
                    // Validate date format
                    String text = field.getText();
                    if (!text.matches("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{2}$")) {
                        field.setText(placeholder);
                        field.setForeground(Color.GRAY);
                        JOptionPane.showMessageDialog(null,
                                "Please enter date in MM/DD/YY format",
                                "Invalid Date Format",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    private boolean isValidDateRange(String fromDateStr, String toDateStr) {
        try {
            // Parse the dates
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yy");
            sdf.setLenient(false); // Strict date parsing
            java.util.Date fromDate = sdf.parse(fromDateStr);
            java.util.Date toDate = sdf.parse(toDateStr);
            java.util.Date today = new java.util.Date();

            // Set today to start of day for comparison
            today = sdf.parse(sdf.format(today));

            // Check if fromDate is in the future
            if (fromDate.before(today)) {
                JOptionPane.showMessageDialog(this,
                        "Start date must be in the future",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Check if toDate is after fromDate
            if (toDate.before(fromDate) || toDate.equals(fromDate)) {
                JOptionPane.showMessageDialog(this,
                        "End date must be after start date",
                        "Invalid Date Range",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            return true;
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid dates in MM/DD/YY format",
                    "Invalid Date Format",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    private void showPackageDetails(Package pkg) {
        // Create the popup dialog
        JDialog dialog = new JDialog(this, "Package Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 400); // Increased height
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
                    hotelInfo = String.format("Hotel: %s%nLocation: %s%nCost: $%.2f",
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

        // Total price
        JLabel priceLabel = new JLabel(String.format("$%.2f", pkg.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(cityLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(hotelLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(flightLabel);
        contentPanel.add(Box.createVerticalStrut(25));

        // Add separator before price
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        contentPanel.add(separator);
        contentPanel.add(Box.createVerticalStrut(15));

        // Add price at the bottom with more visibility
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setForeground(new Color(0, 100, 0)); // Dark green color
        contentPanel.add(priceLabel);

        // Add close button at bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);

        // Add scroll capability to content panel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void filterPackages(String searchText, JPanel locationTable, List<Package> allPackages,
            ButtonGroup packageGroup) {
        // Clear existing radio buttons from the group
        packageGroup.clearSelection();

        // Clear the location table
        locationTable.removeAll();

        // If search is empty or placeholder, show all packages
        if (searchText.isEmpty() || searchText.equals("Search location...")) {
            for (Package pkg : allPackages) {
                addPackageRow(pkg, locationTable, packageGroup);
            }
        } else {
            // Filter packages based on search text
            String searchLower = searchText.toLowerCase();
            boolean foundMatches = false;

            for (Package pkg : allPackages) {
                String cityLower = pkg.getCity().toLowerCase();
                // Check if city contains search text or vice versa
                if (cityLower.contains(searchLower) || searchLower.contains(cityLower)) {
                    addPackageRow(pkg, locationTable, packageGroup);
                    foundMatches = true;
                }
            }

            // If no matches found, show a message
            if (!foundMatches) {
                JPanel noResultsPanel = new JPanel();
                noResultsPanel.setBackground(Color.WHITE);
                noResultsPanel.add(new JLabel("No matching destinations found"));
                locationTable.add(noResultsPanel);
            }
        }

        // Refresh the table
        locationTable.revalidate();
        locationTable.repaint();
    }

    private void addPackageRow(Package pkg, JPanel locationTable, ButtonGroup packageGroup) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.putClientProperty("package", pkg);
        row.setMaximumSize(new Dimension(480, 60));
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        row.setBackground(Color.WHITE);

        // Radio button panel (left)
        JRadioButton radioButton = new JRadioButton();
        packageGroup.add(radioButton);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.setBackground(Color.WHITE);
        radioPanel.add(radioButton);

        // Center panel with city and package info
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // City and hotel info
        JPanel topLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        topLine.setBackground(Color.WHITE);

        JLabel cityLabel = new JLabel(pkg.getCity());
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Get hotel name
        String hotelName = "Loading...";
        try {
            String query = "SELECT hotelName FROM Hotel WHERE hid = ?";
            try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
                pstmt.setInt(1, pkg.getHotelID());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    hotelName = rs.getString("hotelName");
                }
            }
        } catch (SQLException e) {
            hotelName = "Unknown Hotel";
            e.printStackTrace();
        }

        JLabel hotelLabel = new JLabel(" • " + hotelName);
        hotelLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topLine.add(cityLabel);
        topLine.add(hotelLabel);

        // Flight info
        JPanel bottomLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomLine.setBackground(Color.WHITE);

        String flightInfo = "No flight included";
        if (pkg.getFlight() != 0) {
            try {
                String query = "SELECT airline, originCity, destinationCity FROM Flight WHERE fid = ?";
                try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
                    pstmt.setInt(1, pkg.getFlight());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        flightInfo = String.format("%s (%s → %s)",
                                rs.getString("airline"),
                                rs.getString("originCity"),
                                rs.getString("destinationCity"));
                    }
                }
            } catch (SQLException e) {
                flightInfo = "Flight information unavailable";
                e.printStackTrace();
            }
        }
        JLabel flightLabel = new JLabel(flightInfo);
        flightLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        flightLabel.setForeground(new Color(100, 100, 100));
        bottomLine.add(flightLabel);

        centerPanel.add(topLine);
        centerPanel.add(bottomLine);

        // Right panel with details button and price
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        JButton detailsButton = new JButton("Details");
        detailsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsButton.setPreferredSize(new Dimension(80, 25));
        detailsButton.addActionListener(e -> showPackageDetails(pkg));

        JLabel priceLabel = new JLabel(String.format("$%.2f", pkg.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(Color.WHITE);
        buttonWrapper.add(detailsButton);

        rightPanel.add(buttonWrapper);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(priceLabel);

        // Add all panels to the row
        row.add(radioPanel, BorderLayout.WEST);
        row.add(centerPanel, BorderLayout.CENTER);
        row.add(rightPanel, BorderLayout.EAST);

        locationTable.add(row);
    }

    public static void main(String[] args) {
        CreateBooking createBooking = new CreateBooking(new User());
        createBooking.setVisible(true);
    }
}
