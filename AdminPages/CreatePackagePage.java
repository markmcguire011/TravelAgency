package AdminPages;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePackagePage implements ActionListener {
    public CreatePackagePage() {
        JFrame frame = new SubPageAdmin();
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JComboBox<String> hotel = createComboBox();
        JComboBox<String> flight = createComboBox();
        JComboBox<String> location = createComboBox();

        hotel.addItem("asdj");
        hotel.addItem("ASJDKLADJ");

        JButton create = new JButton("create");
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        create.setMaximumSize(new Dimension(80,55));

        JLabel hotelLabel = createLabel("hotel");
        JLabel flightLabel = createLabel("flight");
        JLabel locationLabel = createLabel("location");
        panel.add(Box.createVerticalStrut(65));

        panel.add(locationLabel);
        panel.add(location);
        panel.add(hotelLabel);
        panel.add(hotel);
        panel.add(flightLabel);
        panel.add(flight);


        panel.add(Box.createVerticalStrut(15));
        panel.add(create);

        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        CreatePackagePage c = new CreatePackagePage();

    }
    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setMaximumSize(new Dimension(160,35));
        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        return comboBox;
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
