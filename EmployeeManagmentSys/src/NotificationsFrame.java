import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
    public NotificationsFrame(Model model, Controller controller) throws SQLException {

        this.model = model;
        this.controller = controller;
        model.addView(this);
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
}
