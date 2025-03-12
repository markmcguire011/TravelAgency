package AdminPackage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.white;
import utils.DatabaseConnection;
import LoginPackage.LandingPage;

public class AdminLandingPage extends DefaultAdmin {
    public AdminLandingPage() {
        this.setTitle("landing page - admin");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JButton createPackage = createButton("create package");
        JButton addItems = createButton("add items");
        JButton viewAccounts = createButton("view accounts");
        JButton createAdmin = createButton("create admin");
        JButton logOut = createButton("logout");

        JLabel greetings = new JLabel("Welcome, Administrator", SwingConstants.CENTER);
        greetings.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        greetings.setMaximumSize(new Dimension(40, 30));

        JPanel topPanel = new JPanel(new BorderLayout(30, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(greetings, BorderLayout.WEST);
        topPanel.add(logOut, BorderLayout.EAST);
        topPanel.setPreferredSize(new Dimension(800, 30));

        this.add(topPanel, BorderLayout.NORTH);

        // Add logout button action listener
        logOut.addActionListener(e -> {
            new LandingPage(DatabaseConnection.getConnection()).setVisible(true);
            this.dispose();
        });

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(Box.createVerticalStrut(40));
        middlePanel.add(createPackage);
        middlePanel.add(addItems);
        middlePanel.add(Box.createVerticalStrut(30));
        middlePanel.add(viewAccounts);
        middlePanel.add(createAdmin);
        middlePanel.setBackground(white);

        createPackage.addActionListener(e -> goTo(new CreatePackagePage()));
        addItems.addActionListener(e -> goTo(new AddItemsPage()));
        viewAccounts.addActionListener(e -> goTo(new ViewAccountsPage()));
        createAdmin.addActionListener(e -> goTo(new CreateAdminPage()));
        // CREATE ADMIN PAGE TO BE MADE

        this.add(middlePanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        AdminLandingPage a = new AdminLandingPage();
        a.setVisible(true);
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 35));
        return button;
    }
}