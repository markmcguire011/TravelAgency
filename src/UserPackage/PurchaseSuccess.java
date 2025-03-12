package UserPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import javax.swing.border.AbstractBorder;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import models.User;

public class PurchaseSuccess extends JFrame {
    private User user;

    public PurchaseSuccess(int transactionId, User user) {
        this.user = user;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);

        // Success message
        JLabel successLabel = new JLabel("Purchase Successful!");
        successLabel.setFont(new Font("Arial", Font.BOLD, 24));
        successLabel.setBounds(280, 150, 300, 40);
        this.add(successLabel);

        // Order ID
        JLabel idLabel = new JLabel("Transaction ID: #" + transactionId);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setBounds(350, 200, 150, 30);
        this.add(idLabel);

        // Thank you message
        JLabel thankYouLabel = new JLabel("Thank you, " + user.getFirstName() + "!");
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 16));
        thankYouLabel.setBounds(250, 250, 300, 30);
        thankYouLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(thankYouLabel);

        // Welcome message
        JLabel welcomeLabel = new JLabel(
                "<html><center>Get ready for your upcoming adventure!<br>We look forward to seeing you again.</center></html>");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setBounds(250, 280, 300, 50);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(welcomeLabel);

        // Divider lines
        for (int i = 0; i < 3; i++) {
            JSeparator separator = new JSeparator();
            separator.setBounds(250, 340 + (i * 40), 300, 1);
            separator.setForeground(Color.GRAY);
            this.add(separator);
        }

        // Return home button
        JButton returnButton = new JButton("Return Home");
        returnButton.setBounds(300, 400, 200, 40);
        returnButton.setBackground(new Color(86, 107, 140));
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusPainted(false);
        returnButton.setBorderPainted(false);
        returnButton.setOpaque(true);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add click effect
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                returnButton.setBackground(new Color(66, 87, 120));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                returnButton.setBackground(new Color(86, 107, 140));
            }
        });

        returnButton.addActionListener(e -> {
            new UserHome(user).setVisible(true);
            this.dispose();
        });

        this.add(returnButton);
    }

    public static void main(String[] args) {
        PurchaseSuccess purchaseSuccess = new PurchaseSuccess(1, new User()); // Demo purposes only
        purchaseSuccess.setVisible(true);
    }
}
