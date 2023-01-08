import javax.swing.*;

public class NotificationDetailFrame extends JFrame implements View {
    private JPanel mainPanel;
    private JButton approveButton;
    private JButton rejectButton;
    private JTextArea content;
    private JLabel notifTitle;
    private JLabel notifBy;
    private JLabel notifType;

    private Model model;
    private Controller controller;
    public NotificationDetailFrame(Model model, Controller controller) {

        this.model = model;
        this.controller = controller;
        model.addView(this);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    @Override
    public void systemUpdate(String info) {

    }

    public static void main(String[] args) {
        NotificationDetailFrame start = new NotificationDetailFrame(new Model(), new Controller(new Model(), null));
    }
}
