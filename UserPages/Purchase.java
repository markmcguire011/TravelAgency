package UserPages;

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

public class Purchase extends JFrame {
    public Purchase() {
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
            new CreateBooking().setVisible(true);
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

        // Example bookings (replace with actual selected bookings)
        for (int i = 0; i < 10; i++) { // Added more items to test scrolling
            addBookingItem(summaryPanel, "New York (Apr " + i + " - Apr " + (i + 5) + ")", "$500");
        }

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBounds(200, 290, 400, 30);
        JLabel totalLabel = new JLabel("Total");
        JLabel totalAmount = new JLabel("$1,300");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalAmount.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(totalLabel, BorderLayout.WEST);
        totalPanel.add(totalAmount, BorderLayout.EAST);
        this.add(totalPanel);

        // Payment details
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(null);
        paymentPanel.setBounds(200, 340, 400, 140);

        // Card number
        JTextField cardNumber = new JTextField("Card number");
        cardNumber.setBounds(0, 0, 400, 30);
        setupPlaceholder(cardNumber, "Card number");

        // Expiry and CVV
        JTextField expiry = new JTextField("MM/YY");
        expiry.setBounds(0, 40, 195, 30);
        setupPlaceholder(expiry, "MM/YY");

        JTextField cvv = new JTextField("CVV");
        cvv.setBounds(205, 40, 195, 30);
        setupPlaceholder(cvv, "CVV");

        // ZIP code
        JTextField zip = new JTextField("ZIP Code");
        zip.setBounds(0, 80, 400, 30);
        setupPlaceholder(zip, "ZIP Code");

        paymentPanel.add(cardNumber);
        paymentPanel.add(expiry);
        paymentPanel.add(cvv);
        paymentPanel.add(zip);

        // Purchase button
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setFont(new Font("Arial", Font.PLAIN, 15));
        purchaseButton.setBounds(200, 480, 400, 40);
        purchaseButton.setBackground(new Color(119, 209, 142));
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFocusPainted(false);
        purchaseButton.setBorderPainted(false);
        purchaseButton.setOpaque(true);
        purchaseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click effect
        purchaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                purchaseButton.setBackground(new Color(89, 179, 112)); // Darker green when pressed
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                purchaseButton.setBackground(new Color(119, 209, 142)); // Original green when released
            }
        });

        // Purchase button
        purchaseButton.addActionListener(e -> {
            new PurchaseSuccess().setVisible(true);
            this.dispose();
        });

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
                }
            }
        });
    }

    public static void main(String[] args) {
        Purchase purchase = new Purchase();
        purchase.setVisible(true);
    }
}
