package AdminPages;
import javax.swing.*;

import java.awt.*;

public class AddItemsPage {
    public AddItemsPage() {
        JFrame frame = new SubPageAdmin();
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(65));

        JLabel itemTypeLabel = new JLabel("item type");
        JComboBox<String> itemTypeComboBox = new JComboBox<>();

        itemTypeComboBox.setMaximumSize(new Dimension(160,35));
        itemTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        itemTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);




        panel.add(itemTypeLabel);
        panel.add(itemTypeComboBox);

        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        AddItemsPage aip = new AddItemsPage();
    }


}
