import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NotificationsFrame extends JFrame implements ActionListener {

    public NotificationsFrame(){

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
