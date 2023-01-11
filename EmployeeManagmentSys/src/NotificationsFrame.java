import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;

public class NotificationsFrame extends JFrame implements  ActionListener {

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
    private JDBCHolder jdbcHolder;
    private TimerTask task;
    private Timer timer;

    public NotificationsFrame(Model model, Controller controller) {

        this.model = model;
        this.controller = controller;
        //model.addView(this);
        jdbcHolder = new JDBCHolder();
        jdbcHolder.initializer();
        // timer function ---> unfinished
        task = new TimerTask() {
            @Override
            public void run() {
                Connection conn = jdbcHolder.getConnection();
                try {
                    // query for selectiing notofocation that belong to the person who is logged in
                    ResultSet rs = conn.prepareStatement("Select Count(*) from table").executeQuery();
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


        notifList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }




    public static void main(String[] args) {
        LoginPageFrame start = new LoginPageFrame();
    }

    public void actionPerformed(ActionEvent e) {

    }



}
