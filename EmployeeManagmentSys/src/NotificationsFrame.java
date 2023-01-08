import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NotificationsFrame extends JFrame implements View, ActionListener {

    private JList notifList;
    private JPanel mainPanel;
    private JButton readButton;
    private JButton unreadButton;
    private JButton equipmentButton;
    private JButton vacationButton;
    private JButton WFOButton;
    private JButton allButton;

    private Model model;
    private Controller controller;
    public NotificationsFrame(Model model, Controller controller) {

        this.model = model;
        this.controller = controller;
        model.addView(this);

        init();
        notifList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String data = notifList.getSelectedValue().toString();
                    new NotificationDetailFrame(model, controller, data);
                }
            }
        });

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    private void init() {
        try {
            notifList.setModel(model.listNotifications());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
    }

    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void systemUpdate(String info) {

    }
}
