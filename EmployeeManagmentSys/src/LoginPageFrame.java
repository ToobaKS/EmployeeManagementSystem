import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPageFrame extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPasswordField password;
    private JButton login;
    private JTextField id;

    private Model model = new Model();

    public LoginPageFrame(){

        login = new JButton();
        id = new JTextField();
        password = new JPasswordField();

        login.addActionListener(this);

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        LoginPageFrame start = new LoginPageFrame();
    }

    public void actionPerformed(ActionEvent e) {
        this.dispose();
            System.out.println("here");
        try {
            Frame f = new Frame(model);
        } catch (SQLException ex) {

        }
    }
}
