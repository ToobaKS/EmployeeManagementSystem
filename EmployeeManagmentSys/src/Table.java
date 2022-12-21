import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;


//still in the work

public class Table {

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException{

        ResultSetMetaData rsmd = rs.getMetaData();

        // names of columns will always be a string
        Vector<String> columnNames = new Vector<String>(); // creating a vector of col names
        int columnCount = rsmd.getColumnCount(); // get the length
        for (int column = 1; column <= columnCount; column++) {// go through each col
            columnNames.add(rsmd.getColumnName(column));// add the names to the vector columns
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>(); // creating a vector of objects to store the values inside the rows
        while (rs.next()) {// go by each row
            Vector<Object> vector = new Vector<Object>();// create a new vector to store the whole row
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));// add the rows to the vector
            }
            data.add(vector);// add the set of rows to the origial data vector
        }
        return new DefaultTableModel(data, columnNames);

    }
}