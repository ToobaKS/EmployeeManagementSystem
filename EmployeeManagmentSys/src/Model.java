import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Model {


    private String LeaveType;
    private int LeaveDays;
    private Date StartDate;
    private Date endDate;
    private String vacationStatus;
    private String notificationStatus;
    private java.sql.Date notificationDate;
    private String notificationTitle;
    private String notificationContent;
    private String requestType;
    private int reciver;
    private String EquipmentType;
    private String EquipmentVersion;

    private final List<View> views;
    private final List<LoginView> loginViews;

    private static final int NUMOFCUBICLES = 20;

    private String receiver = "";
    private String employeeLevel = "";
    private int employeeID = 2;

    private JDBCHolder jdbc;

    private ArrayList<HashMap> holderArray;

    private HashMap<String, ArrayList> dateCubiclePairs = new HashMap<>();
    ArrayList<Integer> cubicles;
    ArrayList<LocalDate> dateList;
    private LocalDate todayDate;

    public Model(){
        views = new ArrayList<>();
        loginViews = new ArrayList<>();
        jdbc = new JDBCHolder();
        cubicles = new ArrayList<>();
        dateList = new ArrayList<>();

        long millis = System.currentTimeMillis();
        this.notificationDate = new java.sql.Date(millis);

        for(int i = 1; i<= NUMOFCUBICLES; i++){
            cubicles.add(i);
        }

        todayDate = LocalDate.now();
    }


    public ComboBoxModel getDateList() throws SQLException {
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();
        LocalDate temp = todayDate;

        for(int i = 1; i <= 7; i++){
            ArrayList<Integer> t = new ArrayList<>();
            Boolean b = jdbc.verifyDate(String.valueOf(temp), employeeID);
            if(!b){
                cbm.addElement(temp);
                for(int c : cubicles){
                    Boolean d = jdbc.verifyCubicle(c, String.valueOf(temp));
                    if(!d){
                        t.add(c);
                    }
                }
                dateCubiclePairs.put(String.valueOf(temp), t);
            }
            temp = incrementDate(temp);
        }
        return cbm;
    }



    public ComboBoxModel getCubicles(String selectedDate) {
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();

        ArrayList<Integer> temp = dateCubiclePairs.get(selectedDate);

        for(int t: temp){
            cbm.addElement(t);
        }

        return cbm;
    }

    public void login(String info) throws SQLException {

        System.out.println(info);

        String result = "";
        String[] temp = info.split(" ");

        if(temp.length < 2){
            result = "Invalid Input";
        }
        else if (temp[0].equals("")){
            result = "Invalid Input";
        }
        else{
            boolean b = jdbc.verifyID(Integer.parseInt(temp[0]));

            if(!b){
                result = "Invalid Id";
            }else if(b){
                String pass = jdbc.getValue((Integer.parseInt(temp[0])), "idEmployee", "Password","Employee");
                if(!pass.equals(temp[1])){
                    result = "Invalid Password";
                }else{
                    initLogin(Integer.parseInt(temp[0]));
                    result = "valid";
                }
            }
        }

        notifyLoginView(result);
    }

    private void initLogin(int id) throws SQLException {
        this.employeeID = id;
        String temp = jdbc.getValue(id, "idEmployee", "Position","Employee");
        if(temp.contains("HR")){
            this.employeeLevel = "HR";
        }else if(temp.contains("Manager")){
            this.employeeLevel = "manager";
        }else{
            this.employeeLevel= "employee";
        }
    }

    public String getReceiver(int tempID) throws SQLException {

        receiver = jdbc.getValue(tempID, "Employee_idEmployee", "Receiver", "Notification");

        System.out.println(receiver);

        return receiver;
    }

    public void setListNotifications() throws SQLException {
        holderArray = jdbc.getPreciseTable("Notification", "Receiver", employeeID);
    }

    public String getEmployeeName(int employeeIdEmployee) throws SQLException {
        return jdbc.getNameFromDB(employeeIdEmployee);
    }

    public ArrayList<HashMap> getHolderArray() {
        return holderArray;
    }

    public void approveRequest(String notifNo) throws SQLException {
        HashMap<String, String> notificationRow = jdbc.getOneRow("Notification", "NotificationNo", Integer.parseInt(notifNo));
        int requestNo = getRequestNo(notificationRow);

        String attribute = notificationRow.get("RequestType") + "Status";
        String keyAtrributeName = notificationRow.get("RequestType") + "No";

        String tableName = notificationRow.get("RequestType");

        if(tableName.equals("Leave")){
            tableName = "`Leave`";
        }

        boolean b = jdbc.updateStringAttributes(tableName, attribute, "Approved", requestNo, keyAtrributeName);

        if(b){
            notifyView("Approved");
        }else{
            System.out.println(b);
        }

        sendNotificationResponse(notificationRow, "Approved", "");

    }

    public void rejectRequest(String notifNo, String reason) throws SQLException {
        HashMap<String, String> notificationRow = jdbc.getOneRow("Notification", "NotificationNo", Integer.parseInt(notifNo));
        int requestNo = getRequestNo(notificationRow);

        String attribute = notificationRow.get("RequestType") + "Status";
        String keyAtrributeName = notificationRow.get("RequestType") + "No";

        String tableName = notificationRow.get("RequestType");

        if(tableName.equals("Leave")){
            tableName = "`Leave`";
        }

        boolean b = jdbc.updateStringAttributes(tableName, attribute, "Rejected", requestNo, keyAtrributeName);

        if(notificationRow.get("RequestType").equals("Equipment")){
            attribute = "Employee_idEmployee";
            jdbc.updateStringAttributes(tableName, attribute, null, requestNo, keyAtrributeName);
        }

        if(b){
            notifyView("Rejected");
        }else{
            System.out.println(b);
        }

        sendNotificationResponse(notificationRow, "Rejected", reason);
    }

    private void sendNotificationResponse(HashMap<String, String> notificationRow, String Status, String reason) throws SQLException {
        String NotificationStatus = "unread";
        LocalDate NotificationDate = LocalDate.now();
        String NotificationTitle = "Response to " + notificationRow.get("RequestType") + " Request";
        String RequestType = "Request Update";
        String Receiver = notificationRow.get("Employee_idEmployee");
        String Employee_idEmployee = notificationRow.get("Receiver");
        String content = notificationRow.get("NotificationContent");
        content = content.substring(0, content.length() - 2);
        String NotificationContent = "The following Request has been " + Status + ": " + content + "\nReason: " + reason + " 0";

        String sql = "INSERT INTO Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee) VALUES ('" + NotificationStatus + "', '"
                + NotificationDate + "', '" + NotificationTitle + "', '" + NotificationContent + "', '"
                + RequestType + "', " + Receiver + ", " + Employee_idEmployee + ")";

        System.out.println(sql);

        jdbc.insertIntoTable(sql);
    }
    private int getRequestNo(HashMap<String, String> notificationRow){
        String content = notificationRow.get("NotificationContent");
        String temp[] = notificationRow.get("NotificationContent").split(" ");

        String substring = content.substring(content.length() - 1, content.length());
        System.out.println(substring);

        int requestNo = Integer.parseInt(substring);

        return requestNo;
    }

    public void sendWFONotification(String CubicleNo, String date) throws SQLException {
        String WFOStatus = "Pending";
        LocalDate WFODate = LocalDate.parse(date);
        int cub = Integer.parseInt(CubicleNo);
        String sql = "INSERT INTO WFO(CubicleNo, WFODate, WFOStatus, Employee_idEmployee) VALUES (" + cub + ", '"
                + WFODate + "', '" + WFOStatus + "', " + employeeID+ ")";

        System.out.println(sql);

        jdbc.insertIntoTable(sql);

        sql = "SELECT WFONo FROM WFO WHERE CubicleNo = " + CubicleNo + " AND WFODate = '"  + WFODate + "' AND Employee_idEmployee = " + employeeID +";";
        String wfoId = jdbc.getCustomValue(sql);

        String NotificationStatus = "unread";
        LocalDate NotificationDate = LocalDate.now();
        String NotificationTitle = "Work From Office Request";
        String NotificationContent = "Requesting Cubicle " + cub + " on " + WFODate + " " + wfoId;
        String RequestType = "WFO";
        int Receiver = Integer.parseInt(jdbc.getValue(employeeID, "idEmployee","Employee_idEmployee", "Employee"));
        int Employee_idEmployee = employeeID;

        sql = "INSERT INTO Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee) VALUES ('" + NotificationStatus + "', '"
                + NotificationDate + "', '" + NotificationTitle + "', '" + NotificationContent + "', '"
                + RequestType + "', " + Receiver + ", " + Employee_idEmployee + ")";

        System.out.println(sql);

        jdbc.insertIntoTable(sql);

        notifyView("WFO Request Successfully Submitted");
    }

    public LocalDate incrementDate(LocalDate date){
        return date.plusDays(1);
    }



    public HashMap<String, String> getRow(String tableName, String keyAttributeName, int key) throws SQLException {
        HashMap<String, String> row = jdbc.getOneRow(tableName, keyAttributeName, key);
        return row;
    }

    public void updateAttribute(String tableName, String attribute, String info, int key, String keyAttributeName) throws SQLException {
        jdbc.updateStringAttributes(tableName, attribute, info, key, keyAttributeName);
    }

    public String getAttributeValue(int id, String primaryKeyAttribute, String attribute, String tableName) throws SQLException {
        if(tableName.equals("Leave")){
            tableName = "`Leave`";
        }

        String value = jdbc.getValue(id, primaryKeyAttribute, attribute, tableName);
        return value;
    }

    /*
     private String LeaveType;
    private int LeaveDays;
    private Date StartDate;
    private Date endDate;
    private String vacationStatus;
    private String notificationStatus;
    private Date notificationDate;
    private String notificationTitle;
    private String notificationContent;
    private String requestType;
     */

    // logic to insert data into both leave and notification table
    public void saveToLeaveTable(String LeaveType, int LeaveDays, Date StartDate,Date endDate,  int employeeID){

        this.LeaveType = LeaveType;
        this.LeaveDays = LeaveDays;
        this.StartDate = StartDate;
        this.endDate = endDate;
        String LeaveStatus = "Pending";
        String notificationStatus ="unread";
        java.sql.Date notificationDate = getNotificationDate();
        String notificationTitle = "Leave Request";
        String notificationContent;
        String requestType = "Leave";
        this.employeeID = employeeID;


        try{


            String temp = "insert into `Leave`(LeaveType, LeaveDays, LeaveStartDate, LeaveEndDate, LeaveStatus, Employee_idEmployee )"+
                    " Values ('"+ LeaveType+ "',"+ LeaveDays +",'" + StartDate + "','"+ endDate + "','"+ LeaveStatus +"',"+ employeeID +")";
            jdbc.insertData(temp);
            int leaveIDReader = jdbc.getLeaveLatest(employeeID);
            notificationContent = employeeID + "  has requested a " + requestType+ " for " + LeaveDays + " days " + " from " + StartDate + " to " + endDate + " the request number is  " + leaveIDReader;
            String reciver1 = jdbc.getValue(employeeID, "idEmployee","Employee_idEmployee", "Employee");
            int reciver = Integer.parseInt(reciver1);
            String temp2 = "insert into Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee)"+
                    " Values ('" + notificationStatus + "','" + notificationDate + "','"+ notificationTitle + "','" + notificationContent + "','"+ requestType + "',"+ reciver + "," + employeeID + ")";

            jdbc.insertData(temp2);
            System.out.println("submitted to database");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

        notifyView();

    }



    public void saveToEquipmentTable(String EquipmentType,int employeeID, String EquipmentVersion){
        this.employeeID = employeeID;
        this.EquipmentType = EquipmentType;
        this.EquipmentVersion = EquipmentVersion;
        java.sql.Date notificationDate = getNotificationDate();
        String requestType = "Equipment";
        String notificationTitle = "Equipment Request";
        String notificationStatus ="unread";

        try{

            String temp3 = "update Equipment " + "set Employee_idEmployee = " + employeeID + " where EquipmentType = " + "'"+ EquipmentType + "' and EquipmentVersion = '" + EquipmentVersion + "'" ;
            jdbc.insertData(temp3);

            //System.out.println(temp3);

            int eqIDReader = jdbc.getEQLatest(employeeID);
            //int eqID = Integer.parseInt(eqIDReader);
            notificationContent = employeeID + "  has requested a " + requestType+ " the equipment type is " +  EquipmentType + " of version " + EquipmentVersion  + " the request number is  " + eqIDReader;
            String reciver1 = jdbc.getValue(employeeID, "idEmployee","Employee_idEmployee", "Employee");
            int reciver = Integer.parseInt(reciver1);
            String temp2 = "insert into Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee)"+
                    " Values ('" + notificationStatus + "','" + notificationDate + "','"+ notificationTitle + "','" + notificationContent + "','"+ requestType + "',"+ reciver + "," + employeeID + ")";

            jdbc.insertData(temp2);
            System.out.println("submitted to database");



        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            e.printStackTrace();
        }
    }

    private void notifyView(String info){
        for (View view : views) {
            view.systemUpdate(info);
        }
    }

    public void addView(View view) {
        views.add(view);
    }

    public void removeView(View view){
        views.remove(view);
    }

    public void notifyLoginView(String info) {
        for (LoginView view : loginViews) {
            view.systemUpdate(info);
        }
    }

    public void addLoginView(LoginView view) {
        loginViews.add(view);
    }
    public void removeLoginView(LoginView view) {
        loginViews.remove(view);
    }

    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    public JDBCHolder getJdbc() {
        return jdbc;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setNotificationDate(java.sql.Date notificationDate){
        this.notificationDate = notificationDate;

    }

    public java.sql.Date getNotificationDate (){
        return notificationDate;
    }

    public static void main(String[] args) throws SQLException {
        Model m = new Model();
        m.getReceiver(1);

        m.sendWFONotification("2", String.valueOf(LocalDate.now().plusDays(1)));
    }

}

