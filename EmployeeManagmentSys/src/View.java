import java.util.Date;

public interface View {
    void systemUpdate(String info);

    String getCubicleCombo();
    String getDateCombo();


    String getLeaveType();
    String getEquipmentType();
    String getEquipmentVer();
    java.sql.Date getStartDate();
    java.sql.Date getEndDate();
    int getLeaveDays();

}

