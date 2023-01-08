import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Model {

    private final String ADD = "add";                   //to add an employee
    private final String REM = "remove";                //to remove an employee
    private final String UPDATE = "update";             //to update the employees information
    private final String LOGIN = "Login";               //to check the login
    private final String ACCESS = "access";             //to check the level of access to the system
    private final String ADDNOTE = "add note";          //to add notes to employee file
    private final String REMNOTE = "remove note";       //to remove notes from employee file
    private final String EQUIPREQ = "request equipment";//to request equipment
    private final String WFOREQ = "request WFO";        //to request WFO
    private final String VACREQ = "request vacation";   //to request vacation

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



    private final List<View> views;
    private final List<LoginView> loginViews;

    private String employeeLevel = "";
    private int employeeID = 0;
    private JDBCHolder jdbc;
    public Model(){
        views = new ArrayList<>();
        loginViews = new ArrayList<>();
        jdbc = new JDBCHolder();
    }

    public void run(String command) throws SQLException {

        if(ADD.equals(command)){

        }
        if(REM.equals(command)){

        }
        if(UPDATE.equals(command)){

        }
        if(LOGIN.equals(command.split(" ")[0])){

        }
        if(ACCESS.equals(command)){

        }
        if(ADDNOTE.equals(command)){

        }
        if(REMNOTE.equals(command)){

        }
        if(EQUIPREQ.equals(command)){

        }
        if(WFOREQ.equals(command)){

        }
        if(VACREQ.equals(command)) {

        }
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
/*
    // logic to insert data into both vacation and notification table
    public void saveToLeaveTable(String LeaveType, int LeaveDays, Date StartDate,Date endDate, String vacationStatus, String notificationStatus,   ){

        try{

            //jdbc.insertData("insert into Leave(LeaveType, LeaveDays, LeaveStartDate, LeaveEndDate, LeaveStatus, Employee_idEmployee )"+
                    //"Values ('"+ + ","+  "')");


            System.out.println("submitted to database");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
*/
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
                    init(Integer.parseInt(temp[0]));
                    result = "valid";
                }
            }
        }

        notifyLoginView(result);
    }

    private void init(int id) throws SQLException {
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

}
