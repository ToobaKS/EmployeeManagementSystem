import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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


    public DefaultComboBoxModel getDateList() throws SQLException {
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

    public DefaultComboBoxModel getCubicles(String selectedDate) {
        DefaultComboBoxModel cbm = new DefaultComboBoxModel();

        System.out.println(selectedDate);

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

    public HashMap<String, DefaultTableModel> setTimeCards(int id) throws SQLException {
        HashMap<String, DefaultTableModel> weeklyTimeCards = new HashMap<>();
        ArrayList<HashMap> employeeTimeCards = jdbc.getPreciseTable("TimeTracking", "Employee_idEmployee", id);

        if(employeeTimeCards.size() == 0){
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("Date");
            dtm.addColumn("Hours");
            dtm.addColumn("Start Time");
            dtm.addColumn("End Time");

            LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            weeklyTimeCards.put(String.valueOf(previousMonday), dtm);

            return weeklyTimeCards;
        }
        LocalDate date = LocalDate.parse((CharSequence) (employeeTimeCards.get(0)).get("DateWorked"));
        LocalDate previousMonday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Date");
        dtm.addColumn("Hours");
        dtm.addColumn("Start Time");
        dtm.addColumn("End Time");

        for(int i = 0; i < employeeTimeCards.size(); i++){
            HashMap<String, String> temp = employeeTimeCards.get(i);

            LocalDate currentSelected = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            if(previousMonday.equals(currentSelected)){
                dtm.addRow(new Object[]{temp.get("DateWorked"), temp.get("HoursWork"), temp.get("StartTime"), temp.get("endTime")});
                if(i == employeeTimeCards.size()-1){
                    weeklyTimeCards.put(String.valueOf(previousMonday), dtm);
                }
            }else{
                weeklyTimeCards.put(String.valueOf(previousMonday), dtm);

                dtm = new DefaultTableModel();
                dtm.addColumn("Date");
                dtm.addColumn("Hours");
                dtm.addColumn("Start Time");
                dtm.addColumn("End Time");

                dtm.addRow(new Object[]{temp.get("DateWorked"), temp.get("HoursWork"), temp.get("StartTime"), temp.get("endTime")});

                LocalDate tempDate = LocalDate.parse(String.valueOf((employeeTimeCards.get(i+1).get("DateWorked"))));
                previousMonday = tempDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            }

            if(i != employeeTimeCards.size()-1){
                date =  LocalDate.parse(String.valueOf((employeeTimeCards.get(i+1).get("DateWorked"))));
            }
        }

        return weeklyTimeCards;
    }

    public DefaultTableModel setSchedule(int id, LocalDate monday) throws SQLException {
        DefaultTableModel dtm = new DefaultTableModel();
        ArrayList<HashMap> employeeSchedule = jdbc.getPreciseTable("DailySchedule", "Employee_idEmployee", id);

        dtm.addColumn("Sunday");
        dtm.addColumn("Monday");
        dtm.addColumn("Tuesday");
        dtm.addColumn("Wednesday");
        dtm.addColumn("Thursday");
        dtm.addColumn("Friday");
        dtm.addColumn("Saturday");

        String[] wS = addWeeklyDates(monday, id);

        dtm.addRow(new Object[]{wS[0], wS[1], wS[2], wS[3], wS[4], wS[5], wS[6]});

        return dtm;
    }

    private String[] addWeeklyDates(LocalDate monday, int empId) throws SQLException {

        String[] weeklySchedule = new String[7];
        LocalDate date = monday;

        for(int i = 0; i < 7; i++){
            weeklySchedule[i] = "<html>" + String.valueOf(date.getDayOfMonth());
            String addOn = jdbc.getCustomValue("SELECT StartT FROM DailySchedule WHERE DateToWork = '" + date + "' AND Employee_idEmployee = " + empId +";");
            String addOn2 = jdbc.getCustomValue("SELECT EndT FROM DailySchedule WHERE DateToWork = '" + date + "' AND Employee_idEmployee = " + empId +";");
            if(!addOn.equals("empty")){
                weeklySchedule[i] += "<br><br>" + addOn + " - " + addOn2;
            }
            weeklySchedule[i] += "</html>";
            date = incrementDate(date);
        }
        return weeklySchedule;
    }

    public String getReceiver(int tempID) throws SQLException {
        receiver = jdbc.getValue(tempID, "Employee_idEmployee", "Receiver", "Notification");
        System.out.println(receiver);

        return receiver;
    }

    private ArrayList<HashMap> setList(String tableName, String AttributeName, int id) throws SQLException {
        ArrayList<HashMap> temp = jdbc.getPreciseTable(tableName, AttributeName, id);
        return temp;
    }

    public String getEmployeeName(int employeeIdEmployee) throws SQLException {
        return jdbc.getNameFromDB(employeeIdEmployee);
    }


    public ArrayList<HashMap> getNotesList(int employeeID) throws RuntimeException,SQLException {
        ArrayList<HashMap> notesList = new ArrayList<>();
        notesList = setList("Notes", "Employee_idEmployee", employeeID);

        return notesList;
    }

    public ArrayList<HashMap> getNotificationList() throws RuntimeException, SQLException {
        ArrayList<HashMap> notificationList = new ArrayList<>();
        notificationList = setList("Notification", "Receiver", employeeID);

        return notificationList;
    }


    /**
     * @param notifNo
     * @throws SQLException
     */
    public void approveRequest(String notifNo) throws SQLException {
        HashMap<String, String> notificationRow = jdbc.getOneRow("Notification", "NotificationNo", Integer.parseInt(notifNo));
        System.out.println(notifNo);
        int requestNo = getRequestNo(notificationRow);

        System.out.println(requestNo);

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
        System.out.println(notificationRow.get("NotificationContent"));
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

    public void createNote(String note) throws SQLException {
        System.out.println(note);
        String[] newNote = note.split(" ");

        String sql = "INSERT INTO Notes(WrittenBy, NotesTitle, NotesContent, NotesDate, Employee_idEmployee) VALUES (" + employeeID + ", '"
                + newNote[0]  + "', '" + newNote[1]  + "', '" + newNote[2]  + "', " + newNote[3] + ")";

        System.out.println(sql);

        jdbc.insertIntoTable(sql);

        notifyView("Note Successfully Created");

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
            notificationContent = jdbc.getNameFromDB(employeeID) + "  has requested a " + requestType+ " for " + LeaveDays + " days.\n" + " From " + StartDate + " to " + endDate + " " + leaveIDReader;
            String reciver1 = jdbc.getValue(employeeID, "idEmployee","Employee_idEmployee", "Employee");
            int reciver = Integer.parseInt(reciver1);
            String temp2 = "insert into Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee)"+
                    " Values ('" + notificationStatus + "','" + LocalDate.now() + "','"+ notificationTitle + "','" + notificationContent + "','"+ requestType + "',"+ reciver + "," + employeeID + ")";

            jdbc.insertData(temp2);
            System.out.println("submitted to database");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
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
            notificationContent = jdbc.getNameFromDB(employeeID) + " has requested a " + requestType+ "\nThe equipment type is " +  EquipmentType + " of version " + EquipmentVersion  + " " + eqIDReader;
            String reciver1 = jdbc.getValue(employeeID, "idEmployee","Employee_idEmployee", "Employee");
            int reciver = Integer.parseInt(reciver1);
            String temp2 = "insert into Notification(NotificationStatus, NotificationDate, NotificationTitle, NotificationContent, RequestType, Receiver, Employee_idEmployee)"+
                    " Values ('" + notificationStatus + "','" + LocalDate.now() + "','"+ notificationTitle + "','" + notificationContent + "','"+ requestType + "',"+ reciver + "," + employeeID + ")";

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
        m.setSchedule(2, LocalDate.now());
    }
}

