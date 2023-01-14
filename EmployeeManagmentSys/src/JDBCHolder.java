import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JDBCHolder {
    //private HashMap<String,String> table=new HashMap<String,String>();



    static Connection connection = null;
    static String databaseName = "mydb";
    static String url = "jdbc:mysql://database-ems.csd6bruhg1kp.ca-central-1.rds.amazonaws.com:3306/" + databaseName;
    static String username = "team24TZ";
    static String password = "erp1998TZ";


    private Statement stmt = null;
    public JDBCHolder(){
        initializer();
    }

    public void initializer() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void exit() throws SQLException {
        connection.close();
    }

    public String[][] getTable(String tableName) throws SQLException {

        String[][] table = null;
        String sql = "select * from Employee";

        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnS = rsmd.getColumnCount();
        int rowNumb = 0;

        rs.last();
        rowNumb = rs.getRow();
        rs.beforeFirst();

        table = new String[rowNumb][columnS];

        int i=0;
        while(rs.next()) {
            for (int j = 0; j < columnS; j++) {
                table[i][j] = rs.getString(j + 1);
            }
            i++;
        }

        return table;
    }

    /**
     *
     * Returns the attribute wanted based on the ID entered
     *
     * @param id
     * @param primaryKey
     * @param attribute
     * @param tableName
     * @return
     * @throws SQLException
     */

    // used to get info from any table using the primary key
    public String getValue(int id, String primaryKey, String attribute, String tableName) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT " + attribute + " FROM " + tableName +
                " WHERE " + primaryKey + "= '" + id + "';");

        rs.next();

        return rs.getString(1);
    }

    // used to get any inforamtion form any table with using the forgin key only
    public String getEmpInfo(int id, String tableName, String attribute) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT " + attribute + " FROM " + tableName +  " WHERE Employee_idEmployee = " + id);

        if (rs.next()){
            return rs.getString(1);
        }else {
            return "";
        }

    }

    public int getLeaveLatest(int empID ) throws SQLException {
        ResultSet rs = stmt.executeQuery("select max(LeaveNo) from `Leave` where Employee_idEmployee = " + empID);
        rs.next();
        return rs.getInt(1);
    }

    public int getEQLatest(int empID ) throws SQLException {
        ResultSet rs = stmt.executeQuery("select max(EquipmentNo) from  Equipment where Employee_idEmployee = " + empID);
        rs.next();
        return rs.getInt(1);
    }

    public ArrayList<String> getAvailableEqType() throws SQLException {
        ResultSet rs = stmt.executeQuery("select distinct EquipmentType from Equipment where isnull (Employee_idEmployee)" );
        ArrayList<String> eq = new ArrayList();
        while (rs.next()){
            eq.add(rs.getString("EquipmentType"));
        }

        return eq;
    }

    public ArrayList<String> getAvailableEqVer(String eqType) throws SQLException {
        ResultSet rs = stmt.executeQuery("select distinct EquipmentVersion from Equipment where isnull (Employee_idEmployee) and EquipmentType = " + "'" + eqType + "'");
        ArrayList<String> eq = new ArrayList();
        while (rs.next()){
            eq.add(rs.getString("EquipmentVersion"));
        }

        return eq;
    }


    public boolean verifyID(int id) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT idEmployee from Employee where idEmployee = " + id);

        if(rs.next() != false)
            return true;
        return false;
    }

    // this is for filling the address textfiled in employee details page
    public void filltextField(JTextField textField, int ID )
            throws SQLException {

        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("SELECT StreetNo, StreetName, City, Province FROM Address where Employee_idEmployee = " + ID);
        // SELECT StreetNo + ' ' + StreetName + ' ' as StreetAddress FROM adress.....

        while(rs.next()) {
            int streetNum = Integer.parseInt(rs.getString("StreetNo"));
            String streetName  = rs.getString("StreetName");
            String city  = rs.getString("City");
            String province  = rs.getString("Province");
            String result = streetNum + " " + streetName + " "+ city + " " + province;
            textField.setText(result);
        }

    }




    // inserting data from program to sql database
    public static void insertData(String sql) throws Exception{
        getConnection().createStatement().executeUpdate(sql);

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        JDBCHolder j = new JDBCHolder();
        String[][] tempTable = j.getTable("Employee");
        System.out.println(Arrays.deepToString(tempTable));

        //String temp= j.getValue(1, "id", "lName","Employee");
       // System.out.println(temp);

        boolean temp2= j.verifyID(1);
        System.out.println(temp2);



        j.exit();
    }

}
