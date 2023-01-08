import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;

public class Controller implements ActionListener, Serializable {

    private Model model;
    private View view;
    public Controller(Model model, View view){
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


        System.out.println();
        try {
            model.run(command);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void log() throws SQLException {
        String id = view.getID();
        String password = view.getPassword();

        model.login(id+" "+password);
    }
}
