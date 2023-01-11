import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Model {

    private final List<View> views;
    private final List<LoginView> loginViews;

    private String receiver = "";
    private String employeeLevel = "";
    private int employeeID = 2;
    private JDBCHolder jdbc;

    private ArrayList<HashMap> holderArray;

    private HashMap<HashMap, String> dataPairs = new HashMap<>();

    public Model(){
        views = new ArrayList<>();
        loginViews = new ArrayList<>();
        jdbc = new JDBCHolder();
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
            tableName = "'Leave'";
        } else if(tableName.equals("WFO")){
            keyAtrributeName = "CubicleID";
        }

        boolean b = jdbc.updateStringAttributes(tableName, attribute, "Approved", requestNo, keyAtrributeName);

        if(b){
            notifyView("Approved");
        }else{
            System.out.println(b);
        }

        sendNotificationResponse(notificationRow, "Approved");

    }

    public void rejectRequest(String notifNo) throws SQLException {
        HashMap<String, String> notificationRow = jdbc.getOneRow("Notification", "NotificationNo", Integer.parseInt(notifNo));
        int requestNo = getRequestNo(notificationRow);

        String attribute = notificationRow.get("RequestType") + "Status";
        String keyAtrributeName = notificationRow.get("RequestType") + "No";

        String tableName = notificationRow.get("RequestType");

        if(tableName.equals("Leave")){
            tableName = "'Leave'";
        } else if(tableName.equals("WFO")){
            keyAtrributeName = "CubicleID";
        }

        boolean b = jdbc.updateStringAttributes(tableName, attribute, "Rejected", requestNo, keyAtrributeName);

        if(!notificationRow.get("RequestType").equals("Leave")){
            attribute = "Employee_idEmployee";
            jdbc.updateStringAttributes(tableName, attribute, null, requestNo, keyAtrributeName);
        }

        if(b){
            notifyView("Rejected");
        }else{
            System.out.println(b);
        }

        sendNotificationResponse(notificationRow, "Rejected");
    }

    private void sendNotificationResponse(HashMap<String, String> notificationRow, String Status) throws SQLException {
        String NotificationStatus = "unread";
        LocalDate NotificationDate = LocalDate.now();
        String NotificationTitle = "Response to " + notificationRow.get("RequestType") + " Request";
        String NotificationContent = "The following Request has been " + Status + ": " + notificationRow.get("NotificationContent") + " 0";
        String RequestType = "Request Update";
        String Receiver = notificationRow.get("Employee_idEmployee");
        String Employee_idEmployee = notificationRow.get("Receiver");

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

    public HashMap<String, String> getRow(String tableName, String keyAttributeName, int key) throws SQLException {
        HashMap<String, String> row = jdbc.getOneRow(tableName, keyAttributeName, key);
        return row;
    }

    public void updateAttribute(String tableName, String attribute, String info, int key, String keyAttributeName) throws SQLException {
        jdbc.updateStringAttributes(tableName, attribute, info, key, keyAttributeName);
    }

    public String getAttributeValue(int id, String primaryKeyAttribute, String attribute, String tableName) throws SQLException {
        if(tableName.equals("Leave")){
            tableName = "'Leave'";
        }

        String value = jdbc.getValue(id, primaryKeyAttribute, attribute, tableName);
        return value;
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

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public static void main(String[] args) throws SQLException {
        Model m = new Model();
        m.getReceiver(1);
    }



}
