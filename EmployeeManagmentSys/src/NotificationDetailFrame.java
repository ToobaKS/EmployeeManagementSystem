import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationDetailFrame extends JFrame implements View, ActionListener {
    private JPanel mainPanel;
    private JButton approveButton;
    private JButton rejectButton;
    private JTextArea content;
    private JLabel notifTitle;
    private JLabel notifBy;
    private JLabel notifType;
    private JLabel notifDate;

    private Model model;
    private Controller controller;
    private String data;
    public NotificationDetailFrame(Model model, Controller controller, String data) {

        this.model = model;
        this.controller = controller;
        this.data = data;
        model.addView(this);

        init();

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    private void init() {
        model.showNotificationDetails();
    }

    @Override
    public void systemUpdate(String info) {

    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
