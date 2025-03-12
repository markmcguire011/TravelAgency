package AdminPackage;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import utils.DatabaseConnection;
public class ViewAccountsPage extends SubPageAdmin{
    public ViewAccountsPage() {

        List<HashSet<String>> possibleChanges = new ArrayList<>();
        possibleChanges.add(new HashSet<>());
        possibleChanges.add(new HashSet<>());


        this.setTitle("View Accounts");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        JButton backButton = this.getBackButton();
        this.remove(backButton);
        JPanel topBar = new JPanel();
        topBar.add(backButton);
        topBar.setLayout(new BorderLayout());
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(70,35));
        topBar.setMaximumSize(new Dimension(400,50));
        this.add(topBar);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setMaximumSize(new Dimension(80,45));
        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setMaximumSize(new Dimension(80,45));

        firstNamePanel.setLayout(new BoxLayout(firstNamePanel,BoxLayout.Y_AXIS));
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel,BoxLayout.Y_AXIS));

        JLabel firstNameLabel = new JLabel("first name");
        JLabel lastNameLabel = new JLabel("last name");

        JTextField firstNameTextBox = new JTextField();
        JTextField lastNameTextBox = new JTextField();

        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstNameTextBox);

        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameTextBox);

        namePanel.add(firstNamePanel);
        namePanel.add(lastNamePanel);


        JButton saveChanges = new JButton("save changes");

        saveChanges.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> String.class;
                    case 1 -> String.class;
                    case 2 -> String.class;
                    case 3 -> Boolean.class;
                    default -> String.class;
                };
            }
        };

        table.setModel(model);
        model.addColumn("last name");
        model.addColumn("first name");
        model.addColumn("username");
        model.addColumn("is active");

        JScrollPane scrollPane = new JScrollPane(table);

        table.setMaximumSize(new Dimension(300,175));
        scrollPane.setMaximumSize(new Dimension(300,175));
        scrollPane.setPreferredSize(new Dimension(300,175));

        refreshTable(model);

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            int type = e.getType();
            if(type == TableModelEvent.UPDATE) {
                int isActive = (boolean) model.getValueAt(row,column) ? 1:0;
                int notIsActive = (boolean) model.getValueAt(row,column) ? 0:1;
                String username = (String) model.getValueAt(row,2);

                possibleChanges.get(notIsActive).remove(username);
                possibleChanges.get(isActive).add(username);
            }
        });

        this.add(namePanel);
        JButton searchButton = new JButton("search");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchButton.addActionListener(e -> {
            String firstNameInput = firstNameTextBox.getText();
            String lastNameInput = lastNameTextBox.getText();
            String query;
            if(!firstNameInput.isEmpty() && !lastNameInput.isEmpty()) {
                query = String.format("SELECT * FROM Users WHERE isAdmin = 0 AND firstName =\"%s\" AND lastName = \"%s\" ORDER BY lastName ASC;",firstNameInput,lastNameInput);
            }
            else if(!firstNameInput.isEmpty()) {
                query = String.format("SELECT * FROM Users WHERE isAdmin = 0 AND firstName = \"%s\" ORDER BY lastName ASC;",firstNameInput);
            }
            else if(!lastNameInput.isEmpty()) {
                query = String.format("SELECT * FROM Users WHERE isAdmin = 0 AND lastName = \"%s\" ORDER BY lastName ASC;",lastNameInput);
            }
            else {
                query = String.format("SELECT * FROM Users WHERE isAdmin = 0 ORDER BY lastName ASC;");
            }

            try {
                model.setNumRows(0);
                Statement statement =  DatabaseConnection.getConnection().createStatement(); //replace with Main.connection
                ResultSet rs = statement.executeQuery(query);
                while(rs.next()) {
                    String lastName = rs.getString("lastName");
                    String firstName = rs.getString("firstName");
                    String userName = rs.getString("userName");
                    boolean isActive = rs.getBoolean("isActive");
                    Object[] row = new Object[]{lastName,firstName,userName,isActive};
                    model.addRow(row);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.add(searchButton);
        this.add(scrollPane);
        this.add(saveChanges);

        JLabel errorMessage = new JLabel();
        errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(errorMessage);

        saveChanges.addActionListener(e -> {
            Object[] setInactive = possibleChanges.get(0).toArray();
            Object[] setActive = possibleChanges.get(1).toArray();
            try {
                Statement statement =  DatabaseConnection.getConnection().createStatement(); //replace with Main.connection
                if(setActive.length > 0) {
                    StringBuilder query =
                            new StringBuilder("UPDATE Users SET isActive = 1 WHERE ");
                    for(int i = 0; i < setActive.length; i++) {
                        String userName = (String)setActive[i];
                        query.append(String.format("username = \"%s\" OR ",userName));
                    }
                    query.setLength(query.length()-4);
                    query.append(";");
                    statement.executeUpdate(query.toString());
                    System.out.println(query.toString());
                }
                if(setInactive.length > 0){
                    StringBuilder query = new StringBuilder("UPDATE Users SET isActive = 0 WHERE ");
                    for(int i = 0; i < setInactive.length; i++) {
                        String userName = (String)setInactive[i];
                        query.append(String.format("username = \"%s\" OR ",userName));
                    }
                    query.setLength(query.length()-4);
                    query.append(";");
                    statement.executeUpdate(query.toString());
                    System.out.println(query);
                }
                if(setActive.length > 0 || setInactive.length > 0) {
                    errorMessage.setText("changes successfully made");
                    possibleChanges.removeFirst();
                    possibleChanges.removeFirst();
                    possibleChanges.add(new HashSet<>());
                    possibleChanges.add(new HashSet<>());
                    refreshTable(model);
                }
                else {
                    errorMessage.setText("no changes selected");
                }


            } catch (SQLException ex) {
                possibleChanges.removeFirst();
                possibleChanges.removeFirst();
                possibleChanges.add(new HashSet<>());
                possibleChanges.add(new HashSet<>());
                refreshTable(model);
                throw new RuntimeException(ex);

            }

        });

        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));

    }

    private static void refreshTable(DefaultTableModel model) {
        model.setNumRows(0);
        try {
            Statement statement =  DatabaseConnection.getConnection().createStatement(); //replace with Main.connection
            ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE isAdmin = 0 ORDER BY lastName ASC;");
            while(rs.next()) {
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String userName = rs.getString("userName");
                boolean isActive = rs.getBoolean("isActive");
                Object[] row = new Object[]{lastName,firstName,userName,isActive};
                model.addRow(row);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        ViewAccountsPage vap = new ViewAccountsPage();
        vap.setVisible(true);
    }

}
