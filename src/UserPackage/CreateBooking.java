package src.UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.ArrayList;

import src.models.Package;
import src.utils.PackageDAO;

public class CreateBooking extends JFrame {
    private String username;

    public CreateBooking(String username) {
        this.username = username;
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
        PackageDAO packageDAO = new PackageDAO();
        List<Package> packages = packageDAO.getAllPackages();

        for (Package pkg : packages) {
            JPanel row = new JPanel(new BorderLayout());
            row.setMaximumSize(new Dimension(250, 40));
            row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Checkbox panel (left)
            JCheckBox checkbox = new JCheckBox();
            JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            checkboxPanel.add(checkbox);

            // City label (center)
            JLabel cityLabel = new JLabel(pkg.getCity());
            cityLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            // Price label (right)
            JLabel priceLabel = new JLabel("$" + pkg.getPrice());
            priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            row.add(checkboxPanel, BorderLayout.WEST);
            row.add(cityLabel, BorderLayout.CENTER);
            row.add(priceLabel, BorderLayout.EAST);

            locationTable.add(row);
            locationTable.add(Box.createVerticalStrut(5)); // Add spacing between rows
        }

        this.add(scrollPane);

        // Price range slider
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBounds(50, 180, 100, 30);
        this.add(priceLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Add padding (top, left, bottom, right)
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
            List<Package> selectedPackages = new ArrayList<>();

            // Collect selected packages
            for (Component comp : locationTable.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel row = (JPanel) comp;
                    Component checkboxPanel = row.getComponent(0);
                    if (checkboxPanel instanceof JPanel) {
                        JCheckBox checkbox = (JCheckBox) ((JPanel) checkboxPanel).getComponent(0);
                        if (checkbox.isSelected()) {
                            // Get the package associated with this row
                            int index = -1;
                            Component[] components = locationTable.getComponents();
                            for (int i = 0; i < components.length; i++) {
                                if (components[i] == comp) {
                                    index = i;
                                    break;
                                }
                            }
                            Package pkg = packages.get(index / 2); // Divide by 2 because of struts
                            selectedPackages.add(pkg);
                        }
                    }
                }
            }

            if (selectedPackages.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please select at least one package",
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

            new Purchase(selectedPackages, fromDateText, toDateText, username).setVisible(true);
            this.dispose();
        });
        this.add(purchaseButton);
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

    public static void main(String[] args) {
        CreateBooking createBooking = new CreateBooking("user1");
        createBooking.setVisible(true);
    }
}
