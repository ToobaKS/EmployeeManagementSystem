import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPageFrame extends JFrame implements View, ActionListener{
    private JPanel mainPanel;
    private JPasswordField password;
    private JTextField id;
    private JButton login;

    private String buttonText = "";

    private Controller control;
    private Model model;


    public LoginPageFrame() {
        super("ERP");

        model = new Model();
        model.addView(this);
        control = new Controller(model, this);

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

    public void setPassword(JPasswordField password) {
        this.password = password;
    }

    /*
    public void setId(JTextField id) {
        this.id = id;
    }*/

    @Override
    public void systemUpdate(String info) {
        if(info.equals("valid")){
            this.dispose();
            Frame f = new Frame(model, control);
        } else{
            JOptionPane.showMessageDialog(this, info);
        }
    }

    @Override
    public String getID() {
        return id.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //idInput = id.getText();
        buttonText = login.getText();
        //passwordInput = password.getText();

        //login.setActionCommand(idInput);
    }
}
