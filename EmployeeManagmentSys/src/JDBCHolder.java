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
        String sql = "select * from " + tableName +";";

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


    public ArrayList<HashMap> getPreciseTable(String tableName, String attribute, int id) throws SQLException {

        ArrayList<HashMap> table = new ArrayList<>();

        String sql = "select * from " + tableName + " where " + attribute +" = '" +id +"';";

        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnS = rsmd.getColumnCount();

        if(rs.next() != false){
            rs.beforeFirst();
            while(rs.next()) {
                HashMap<String, String> data = new HashMap<>();
                for (int i = 1; i <= columnS; i++) {
                    data.put(rsmd.getColumnName(i), rs.getString(i));
                }
                table.add(data);
            }
        }
        return table;
    }


    public HashMap getOneRow(String tableName, String primaryAttribute, int primaryKey) throws SQLException {

        HashMap<String, String> data = new HashMap<>();

        String sql = "select * from " + tableName + " where " + primaryAttribute +" = '" + primaryKey +"';";

        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnS = rsmd.getColumnCount();

        if(rs.next() != false){
            rs.beforeFirst();
            while(rs.next()) {
                for (int i = 1; i <= columnS; i++) {
                    data.put(rsmd.getColumnName(i), rs.getString(i));
                }
            }
        }
        return data;
    }

    public boolean updateStringAttributes(String tableName, String attribute, String info, int key, String keyAttributeName) throws SQLException {

        boolean update = false;

        String sql= "";

        if(info != null){
            sql = "update " + tableName + " set " + attribute + " = '" + info + "' where " + keyAttributeName + " = " + key + ";";
            stmt.executeUpdate(sql);
            if(info.equals(getValue(key, keyAttributeName, attribute, tableName))){
                update = true;
            }

        }else{
            sql = "update " + tableName + " set " + attribute + " = " + info + " where " + keyAttributeName + " = " + key + ";";
            stmt.executeUpdate(sql);
            update = true;
        }

        return update;
    }

    /**
     *
     * Returns the attribute wanted based on the ID entered
     *
     * @param id
     * @param attribute
     * @param tableName
     * @return
     * @throws SQLException
     */
    public String getValue(int id, String primaryKeyAttribute, String attribute, String tableName) throws SQLException {

        String sql = "SELECT " + attribute + " FROM " + tableName + " WHERE " + primaryKeyAttribute + "= " + id + ";";

        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);



        rs.next();

        return rs.getString(1);
    }

    public String getNameFromDB(int id) throws SQLException {
        String name = "";

        ResultSet rs = stmt.executeQuery("SELECT FirstName, LastName from Employee where idEmployee = " + id);

        rs.next();

        System.out.println(rs.getString(1));

        name = rs.getString(1) + " " + rs.getString(2);

        return name;
    }

    public boolean verifyID(int id) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT idEmployee from Employee where idEmployee = " + id);

        if(rs.next() != false)
            return true;
        return false;
    }

    public boolean insertIntoTable(String sql) throws SQLException {
        boolean result = false;

        stmt.executeUpdate(sql);

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
        String[][] tempTable = j.getTable("'Leave'");
        System.out.println(Arrays.deepToString(tempTable));

        boolean temp2= j.verifyID(1);
        System.out.println(temp2);

        String name = j.getNameFromDB(1);
        System.out.println(name);

        j.exit();
    }

}
