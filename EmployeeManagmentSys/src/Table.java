import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;




public class Table extends DefaultTableModel {

    Connection connection;
    private Statement stmt = null;
    private JDBCHolder jdbcHolder;

    public Table(Connection connection) {
        this.connection = connection;

    }

    public DefaultTableModel buildTableModel(String sql)
            throws SQLException {

        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();


        int c = rsmd.getColumnCount();
        Vector column = new Vector(c);
        for(int i = 1; i<=c; i++){
            column.add(rsmd.getColumnLabel(i));
        }
        Vector data = new Vector();
        Vector row = new Vector(c);
        while(rs.next()){
            //row = new Vector(c);
            for(int i =1; i<=c; i++){
                row.add(rs.getString(i));
            }
            data.add(row);
           // row.clear();
            row = new Vector(c);
        }
        this.columnIdentifiers= column;
        this.dataVector = data;
        //this.addColumn("Hello");
        //this.insertRow(0, new Object[]{"x"});
        return null;
        //return new DefaultTableModel(data, column);// returning a new table with rows and coulmns populated from the schema

    }

   // function int f(x){ return 5;}
    // f(2)


}




