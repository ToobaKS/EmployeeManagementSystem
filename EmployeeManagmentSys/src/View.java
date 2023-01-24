public interface View {
    void systemUpdate(String info);

    String getCubicleCombo();
    String getDateCombo();
    int getNotifNo();
    String getNote();
    String getLeaveType();
    String getEquipmentType();
    String getEquipmentVer();
    java.sql.Date getStartDate();
    java.sql.Date getEndDate();
    int getLeaveDays();

}

