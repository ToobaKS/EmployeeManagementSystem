import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

public class NotificationDetailFrame extends JFrame implements View, ActionListener {
    private JPanel mainPanel;
    private JButton approveButton;
    private JButton rejectButton;
    private JTextArea content;
    private JLabel notifTitle;
    private JLabel notifBy;
    private JLabel notifType;
    private JLabel notifDate;
    private JLabel status;

    private Model model;
    private Controller controller;
    private HashMap<String, String> info;
    public NotificationDetailFrame(Model model, Controller controller, HashMap info) throws SQLException {

        this.model = model;
        this.controller = controller;
        this.info = info;
        model.addView(this);

        init();

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    private void init() throws SQLException {
        content.setText(info.get("NotificationContent"));
        notifTitle.setText(info.get("NotificationTitle"));
        notifBy.setText("Sent By: " + model.getEmployeeName(Integer.parseInt(info.get("Employee_idEmployee"))));
        notifType.setText("Subject: " + info.get("RequestType"));
        notifDate.setText("Date Received: " + info.get("NotificationDate").split(" ")[0]);
        status.setText("Pending");

        System.out.println(info.get("NotificationDate"));
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
