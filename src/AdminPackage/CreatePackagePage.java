package AdminPackage;
import LoginPackage.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class CreatePackagePage extends SubPageAdmin implements ActionListener {
    public CreatePackagePage() {
        this.setLayout(new BorderLayout());
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

        Connection connection = Main.getConnection();



        panel.add(Box.createVerticalStrut(15));
        panel.add(create);
        this.getBackButton().addActionListener(e -> goTo(new AdminLandingPage()));

        this.add(panel);
    }
    public static void main(String[] args) {
        CreatePackagePage c = new CreatePackagePage();
        c.setVisible(true);

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
