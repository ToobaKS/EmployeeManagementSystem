import java.util.Date;

public interface View {


    String getLeaveType();
    String getEquipmentType();
    java.sql.Date getStartDate();
    java.sql.Date getEndDate();
    int getLeaveDays();

}

