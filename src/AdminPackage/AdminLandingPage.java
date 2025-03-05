package AdminPackage;

import javax.swing.*;

import java.awt.*;

import static java.awt.Color.white;

class AdminLandingPage {
    public AdminLandingPage() {
        JFrame frame = new DefaultAdmin();
        frame.setLayout(new BorderLayout());

        JButton createPackage = createButton("create package");
        JButton addItems = createButton("add items");
        JButton viewAccounts = createButton("view accounts");
        JButton createAdmin = createButton("create admin");
        JButton logOut = createButton("Logout");

        JLabel greetings = new JLabel("Hi, *NAME*",SwingConstants.CENTER);
        greetings.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        greetings.setMaximumSize(new Dimension(40,30));

        JPanel topPanel = new JPanel(new BorderLayout(30,0));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(greetings,BorderLayout.WEST);
        topPanel.add(logOut,BorderLayout.EAST);
        topPanel.setPreferredSize(new Dimension(800,30));

        frame.add(topPanel,BorderLayout.NORTH);


        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.add(Box.createVerticalStrut(40));
        middlePanel.add(createPackage);
        middlePanel.add(addItems);
        middlePanel.add(Box.createVerticalStrut(30));
        middlePanel.add(viewAccounts);
        middlePanel.add(createAdmin);
        middlePanel.setBackground(white);



        frame.add(middlePanel,BorderLayout.CENTER);



        frame.setVisible(true);

    }
    public static void main(String[] args) {
        AdminLandingPage a = new AdminLandingPage();

    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160,35));
        return button;
    }





}