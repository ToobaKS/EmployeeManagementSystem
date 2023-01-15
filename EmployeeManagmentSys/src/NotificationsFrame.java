import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;

public class NotificationsFrame extends JFrame implements View, ActionListener {

    private JList notifList;
    private JPanel mainPanel;
    private JButton readButton;
    private JButton unreadButton;
    private JButton equipmentButton;
    private JButton vacationButton;
    private JButton WFOButton;
    private JButton allButton;

    private ArrayList<HashMap> notificationAttributes;

    private Model model;
    private Controller controller;
    private JDBCHolder jdbcHolder;
    private TimerTask task;
    private Timer timer;

    public NotificationsFrame(Model model, Controller controller) throws SQLException {

        this.model = model;
        this.controller = controller;
        model.addView(this);

        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        // timer function ---> unfinished
        task = new TimerTask() {
            @Override
            public void run() {
                Connection conn = jdbcHolder.getConnection();
                try {
                    // query for selectiing notofocation that belong to the person who is logged in
                    ResultSet rs = conn.prepareStatement("Select Count(*) from Notification").executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    // here update the display of the notification list or table
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        };
        //actual timer
        timer = new Timer(true); // true to run timer as daemon thread
        timer.schedule(task, 0, 5000);// Run task every 5 second
        //keep running
        // need to figure out a way to indicate it is a new notification
        // put an order where newest notification on top using  reverse order

        notificationAttributes = new ArrayList<>();
        model.setListNotifications();
        notificationAttributes = model.getHolderArray();

        init();

        notifList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String data = notifList.getSelectedValue().toString();
                    String t = String.valueOf(data.charAt(data.length()-2));

                    for(HashMap h : notificationAttributes){
                        if(h.get("NotificationNo").equals(t)){
                            try {
                                new NotificationDetailFrame(model, controller, h, t);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

                    try{
                        notifList.clearSelection();
                    } catch (NullPointerException exception){

                    }
                }
            }
        });

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    private void clear(){
        notifList.clearSelection();
    }

    private void init() {
        try {

            HashMap<String,String> temp = new HashMap<>();

            DefaultListModel listModel = new DefaultListModel();
            String data = "";

            if (notificationAttributes.size() == 0){
                data = "Nothing to show here";
                listModel.addElement(data);
            }else{
                for (int i = 0; i < notificationAttributes.size(); i++){
                    temp = notificationAttributes.get(i);
                    data = temp.get("NotificationTitle");
                    data += " from ";
                    data += model.getEmployeeName(Integer.parseInt(temp.get("Employee_idEmployee")));
                    data += " (Notification Id: " + temp.get("NotificationNo") + ")";

                    listModel.addElement(data);
                }
            }

            notifList.setModel(listModel);

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

    @Override
    public String getCubicleCombo() {
        return null;
    }

    @Override
    public String getDateCombo() {
        return null;
    }

    @Override
    public int getNotifNo() {
        return 0;
    }

    @Override
    public String getLeaveType() {
        return null;
    }

    @Override
    public String getEquipmentType() {
        return null;
    }

    @Override
    public String getEquipmentVer() {
        return null;
    }

    @Override
    public Date getStartDate() {
        return null;
    }

    @Override
    public Date getEndDate() {
        return null;
    }

    @Override
    public int getLeaveDays() {
        return 0;
    }
}

