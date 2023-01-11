import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;

public class Controller implements ActionListener{

    private Model model;
    private View view;
    private View loginView;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command.split(" ")[0]){
            case "Approve":
                try {
                    model.approveRequest(command.split(" ")[1]);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Reject":
                try {
                    model.rejectRequest(command.split(" ")[1]);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            default:
                break;
        }
    }
}
