import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener{

    private Model model;
    private LoginView view;

    public LoginController(Model model, LoginView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "Login":
                try {
                    log();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
    }

    private void log() throws SQLException {
        String id = view.getID();
        String password = view.getPassword();

        model.login(id+" "+password);
    }
}
