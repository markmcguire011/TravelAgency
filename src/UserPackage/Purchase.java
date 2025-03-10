package src.UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.border.AbstractBorder;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import src.models.Package;
import src.models.Booking;
import src.utils.BookingDAO;
import src.models.Transaction;
import src.utils.TransactionDAO;

public class Purchase extends JFrame {
    private JButton purchaseButton;
    private JTextField cardNumber, expiry, cvv, zip;
    private boolean isCardValid, isExpiryValid, isCvvValid, isZipValid;
    private List<Package> selectedPackages;
    private String fromDate;
    private String toDate;
    private String username;
    private double totalPrice;

    public Purchase(List<Package> selectedPackages, String fromDate, String toDate, String username) {
        this.selectedPackages = selectedPackages;
        this.fromDate = fromDate;
        this.toDate = toDate;
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
            new CreateBooking(username).setVisible(true);
            this.dispose();
        });
        this.add(backButton);

        // Title (adjusted Y position to align with back button)
        JLabel titleLabel = new JLabel("Order Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(300, 20, 200, 30);
        this.add(titleLabel);

        // Order summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create scroll pane for summary panel
        JScrollPane scrollPane = new JScrollPane(summaryPanel);
        scrollPane.setBounds(200, 80, 400, 200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Calculate total price
        totalPrice = 0;
        for (Package pkg : selectedPackages) {
            addBookingItem(summaryPanel, pkg.getCity() + " (" + fromDate + " - " + toDate + ")", "$" + pkg.getPrice());
            totalPrice += pkg.getPrice();
        }

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBounds(200, 290, 400, 30);
        JLabel totalLabel = new JLabel("Total");
        JLabel totalAmount = new JLabel("$" + String.format("%.2f", totalPrice));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmount.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(totalLabel, BorderLayout.WEST);
        totalPanel.add(totalAmount, BorderLayout.EAST);
        this.add(totalPanel);

        // Payment details
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(null);
        paymentPanel.setBounds(200, 340, 400, 140);

        // Initialize fields
        cardNumber = new JTextField("Card number");
        expiry = new JTextField("MM/YY");
        cvv = new JTextField("CVV");
        zip = new JTextField("ZIP Code");

        // Card number
        cardNumber.setBounds(0, 0, 400, 30);
        setupPlaceholder(cardNumber, "Card number");

        // Expiry and CVV
        expiry.setBounds(0, 40, 195, 30);
        setupPlaceholder(expiry, "MM/YY");

        cvv.setBounds(205, 40, 195, 30);
        setupPlaceholder(cvv, "CVV");

        // ZIP code
        zip.setBounds(0, 80, 400, 30);
        setupPlaceholder(zip, "ZIP Code");

        paymentPanel.add(cardNumber);
        paymentPanel.add(expiry);
        paymentPanel.add(cvv);
        paymentPanel.add(zip);

        // Initialize purchase button
        purchaseButton = new JButton("Purchase");
        purchaseButton.setFont(new Font("Arial", Font.PLAIN, 15));
        purchaseButton.setBounds(200, 480, 400, 40);
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFocusPainted(false);
        purchaseButton.setBorderPainted(false);
        purchaseButton.setOpaque(true);
        purchaseButton.setEnabled(false);
        purchaseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click effect
        purchaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (purchaseButton.isEnabled()) {
                    purchaseButton.setBackground(new Color(89, 179, 112)); // Darker green when pressed
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (purchaseButton.isEnabled()) {
                    purchaseButton.setBackground(new Color(119, 209, 142)); // Original green when released
                }
            }
        });

        // Add action listener
        purchaseButton.addActionListener(e -> {
            // Create booking and transaction
            BookingDAO bookingDAO = new BookingDAO();
            TransactionDAO transactionDAO = new TransactionDAO();

            try {
                // Create booking
                int bookingID = bookingDAO.createBooking(new Booking(0, username, selectedPackages.get(0).getPid(),
                        java.sql.Date.valueOf(fromDate), java.sql.Date.valueOf(toDate)));

                Transaction transaction = transactionDAO.createTransaction(
                        bookingID,
                        cardNumber.getText(),
                        totalPrice);

                if (transaction != null) {
                    new PurchaseSuccess(transaction.getTid(), username).setVisible(true);
                    this.dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to process purchase: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        updatePurchaseButtonState();

        this.add(scrollPane); // Add scrollPane instead of summaryPanel directly
        this.add(paymentPanel);
        this.add(purchaseButton);
    }

    private void addBookingItem(JPanel panel, String description, String price) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setMaximumSize(new Dimension(400, 40));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel descLabel = new JLabel(description);
        JLabel priceLabel = new JLabel(price);

        itemPanel.add(descLabel, BorderLayout.WEST);
        itemPanel.add(priceLabel, BorderLayout.EAST);

        panel.add(itemPanel);
        panel.add(Box.createVerticalStrut(10));
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
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
                    updateFieldValidity(field, placeholder, false);
                    return;
                }

                String text = field.getText();
                boolean isValid = true;
                String message = "";

                switch (placeholder) {
                    case "Card number":
                        isValid = text.matches("\\d{16}");
                        message = "Card number must be 16 digits";
                        isCardValid = isValid;
                        break;
                    case "MM/YY":
                        isValid = text.matches("^(0[1-9]|1[0-2])/\\d{2}$");
                        message = "Expiry must be in MM/YY format";
                        isExpiryValid = isValid;
                        break;
                    case "CVV":
                        isValid = text.matches("\\d{3,4}");
                        message = "CVV must be 3 or 4 digits";
                        isCvvValid = isValid;
                        break;
                    case "ZIP Code":
                        isValid = text.matches("\\d{5}");
                        message = "ZIP code must be 5 digits";
                        isZipValid = isValid;
                        break;
                }

                if (!isValid) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                    JOptionPane.showMessageDialog(null, message, "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
                updateFieldValidity(field, placeholder, isValid);
                updatePurchaseButtonState();
            }
        });
    }

    private void updateFieldValidity(JTextField field, String placeholder, boolean isValid) {
        switch (placeholder) {
            case "Card number":
                isCardValid = isValid;
                break;
            case "MM/YY":
                isExpiryValid = isValid;
                break;
            case "CVV":
                isCvvValid = isValid;
                break;
            case "ZIP Code":
                isZipValid = isValid;
                break;
        }
    }

    private void updatePurchaseButtonState() {
        boolean allValid = isCardValid && isExpiryValid && isCvvValid && isZipValid;
        purchaseButton.setEnabled(allValid);
        purchaseButton.setBackground(allValid ? new Color(119, 209, 142) : Color.GRAY);
    }

    public static void main(String[] args) {
        Purchase purchase = new Purchase(new ArrayList<Package>(), "Apr 1", "Apr 5", "user1");
        purchase.setVisible(true);
    }
}
