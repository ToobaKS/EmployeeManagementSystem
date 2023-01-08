import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    @Override
    public void systemUpdate(String info) {

    }
}
