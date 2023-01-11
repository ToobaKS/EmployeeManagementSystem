import javax.management.NotificationBroadcaster;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

public class NotificationDetailFrame extends JFrame implements View, ActionListener {
    private JPanel mainPanel;
    private JButton approveButton;
    private JButton rejectButton;
    private JTextArea notifContent;
    private JLabel notifTitle;
    private JLabel notifBy;
    private JLabel notifType;
    private JLabel notifDate;
    private JLabel status;

    private Model model;
    private Controller controller;
    private HashMap<String, String> info;
    private int notifNo; //primary key identifying the notification
    private int requestNo; //primary key identifying the row in the respective request table
    public NotificationDetailFrame(Model model, Controller controller, HashMap info, String notifNo) throws SQLException {

        this.model = model;
        this.controller = controller;
        this.info = info;
        this.notifNo = Integer.parseInt(notifNo);

        model.addView(this);

        approveButton.addActionListener(controller);
        approveButton.setActionCommand(approveButton.getText() + " " + notifNo);
        rejectButton.addActionListener(controller);
        rejectButton.setActionCommand(rejectButton.getText() + " " + notifNo);

        approveButton.enable();
        rejectButton.enable();

        approveButton.setVisible(true);
        rejectButton.setVisible(true);

        init();

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);


    }

    private void init() throws SQLException {

        String content = info.get("NotificationContent");
        String temp[] = info.get("NotificationContent").split(" ");
        this.requestNo = Integer.parseInt(temp[temp.length - 1]);
        content = content.substring(0, content.length() - 2);

        notifContent.setText(content);
        notifTitle.setText(info.get("NotificationTitle"));
        notifBy.setText("Sent By: " + model.getEmployeeName(Integer.parseInt(info.get("Employee_idEmployee"))));
        notifType.setText("Subject: " + info.get("RequestType"));
        notifDate.setText("Date Received: " + info.get("NotificationDate").split(" ")[0]);

        updateNotifStatus();

        if(requestNo == 0){
            System.out.println(info.get("got here"));

            disableButtons();

            status.setVisible(false);

        }else{
            String attribute = info.get("RequestType") + "Status";
            String keyAtrributeName = info.get("RequestType") + "No";;

            String tableName = info.get("RequestType");

            if(tableName.equals("Leave")){
                tableName = "'Leave'";
            } else if(tableName.equals("WFO")){
                keyAtrributeName = "CubicleID";
            }

            String requestS = model.getAttributeValue(requestNo, keyAtrributeName, attribute, tableName);

            status.setText(requestS);

            if(!requestS.equals("Pending")){
                disableButtons();
            }
        }
    }

    private void updateNotifStatus() throws SQLException {
        model.updateAttribute("Notification", "NotificationStatus", "read", notifNo, "NotifictationNo");
    }

    private void disableButtons(){
        approveButton.disable();
        rejectButton.disable();

        approveButton.setVisible(false);
        rejectButton.setVisible(false);
    }

    @Override
    public void systemUpdate(String info) {
        status.setText(info);
        disableButtons();
    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
