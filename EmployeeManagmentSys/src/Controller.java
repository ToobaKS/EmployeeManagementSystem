import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;

public class Controller implements ActionListener{

    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        System.out.println(command);

        switch (command){
            case "Approve":
                try {
                    model.approveRequest(String.valueOf(view.getNotifNo()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Submit WFO":
                try {
                    model.sendWFONotification(view.getCubicleCombo(), view.getDateCombo());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Submit Vacation":
                System.out.println("Here");
                vacationRequest();
                break;
            case "Submit Equipment":
                EquipmentRequest();
                break;
            default:
                break;
        }
    }

    public void caseReject(String notifNo, String reason) throws SQLException {
        model.rejectRequest(notifNo, reason);
    }

    private void vacationRequest(){
        String LeaveType = view.getLeaveType();
        Date StartDate = view.getStartDate();
        Date endDate = view.getEndDate();
        int  LeaveDays = view.getLeaveDays();
        int employeeID = model.getEmployeeID();
        model.saveToLeaveTable(LeaveType, LeaveDays, StartDate, endDate,employeeID );
    }

    private void EquipmentRequest(){
        String EquipmentType = view.getEquipmentType();
        int employeeID = model.getEmployeeID();
        String EquipmentVersion = view.getEquipmentVer();
        model.saveToEquipmentTable(EquipmentType,employeeID,EquipmentVersion);
    }
}
