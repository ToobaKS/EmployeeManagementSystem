import javax.swing.*;
import java.sql.*;
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
    public String getValue(int id, String primaryKey, String attribute, String tableName) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT " + attribute + " FROM " + tableName +
                " WHERE " + primaryKey + "= '" + id + "';");

        rs.next();

        return rs.getString(1);
    }

    public boolean verifyID(int id) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT idEmployee from Employee where idEmployee = " + id);

        rs.next();
        System.out.println(rs.getString(1));

        if(rs !=null)
            return true;
        return false;
    }

    public void fillEmpJList(JList jList)
            throws SQLException {

        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery("select * from Employee");
        DefaultListModel listModel = new DefaultListModel();

        while(rs.next()) {
            String FirstName = rs.getString("FirstName");
            String LastName  = rs.getString("LastName");
            String result = FirstName + " " + LastName;
            listModel.addElement(result);
        }
        jList.setModel(listModel);
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
