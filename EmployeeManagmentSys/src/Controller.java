import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

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

        switch (command){
            case "Submit":
                vacationRequest();

            case "Submit Equipment":
                EquipmentRequest();

        }
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
        model.saveToEquipmentTable(EquipmentType,employeeID);
    }


}
