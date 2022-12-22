import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Screen
 *
 * @author Tooba
 */
public class LoginScreen extends JFrame implements ActionListener {

    Model model = new Model();

    /**
     * Initializes the start screen
     */
    public LoginScreen() {
        super("EMS");

        //reference to the model and controller
        model = new Model();
        model.addView(this);

        this.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("Enter ID:");
        JLabel passwordLabel = new JLabel("Enter Password:");
        JLabel error = new JLabel("Invalid Login. Please Try Again");

        error.setVisible(false);

        JTextField idText = new JTextField(10);
        JTextField passwordText = new JTextField(10);

        // creates instance of JButton
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        this.add(idLabel);
        this.add(idText);

        this.add(passwordLabel);
        this.add(passwordText);

        this.add(loginButton);
        this.add(error);
        this.setSize(500, 500);

        // makes the frame visible
        this.setVisible(true);
    }

    public static void main(String[] args) {
        LoginScreen start = new LoginScreen();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        //Frame f = new Frame(model);
        ManagerFrame f = new ManagerFrame();
    }
}
