import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPageFrame extends JFrame implements LoginView{
    private JPanel mainPanel;
    private JPasswordField password;
    private JTextField id;
    private JButton login;

    private LoginController control;
    private Model model;


    public LoginPageFrame() {
        super("ERP");

        model = new Model();
        model.addLoginView(this);
        control = new LoginController(model, this);

        // creates instance of JButton
        //login.addActionListener(this);
        login.addActionListener(control);

        this.add(mainPanel);
        this.setSize(800, 500);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        LoginPageFrame start = new LoginPageFrame();
    }

    @Override
    public String getPassword() {
        return password.getText();
    }

    @Override
    public String getID() {
        return id.getText();
    }

    @Override
    public void systemUpdate(String info) {
        if(info.equals("valid")){
            this.dispose();
            try {
                Frame f = new Frame(model);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else{
            JOptionPane.showMessageDialog(this, info);
        }
    }
}
