import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public class JDBCHolder {
    //private HashMap<String,String> table=new HashMap<String,String>();



    static Connection connection = null;
    static String databaseName = "empdatabase";
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
        String sql = "select * from Employees";

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

    public String getValue(int id, String primaryKey, String attribute, String tableName) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT " + attribute + " FROM " + tableName +
                " WHERE " + primaryKey + "= '" + id + "';");

        rs.next();

        return rs.getString(1);
    }

    public static void main(String[] args) throws SQLException {
        JDBCHolder j = new JDBCHolder();
        String[][] tempTable = j.getTable("Employees");
        System.out.println(Arrays.deepToString(tempTable));

        String temp= j.getValue(1, "id", "lName","Employees");
        System.out.println(temp);

        j.exit();
    }

}
