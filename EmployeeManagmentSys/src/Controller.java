import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller implements ActionListener{

    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }


    public void caseReject(String notifNo, String reason) throws SQLException {
        model.rejectRequest(notifNo, reason);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        System.out.println(command);

        switch (command.split(" ")[0]){
            case "Approve":
                try {
                    model.approveRequest(command.split(" ")[1]);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Submit":
                try {
                    model.sendWFONotification(view.getCubicleCombo(), view.getDateCombo());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            default:
                break;
        }
    }
}
