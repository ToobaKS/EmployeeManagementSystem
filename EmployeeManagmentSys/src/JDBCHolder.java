import java.sql.*;

public class JDBCHolder {

    static Connection connection = null;
    static String databaseName = "empdatabase";
    static String url = "jdbc:mysql://database-ems.csd6bruhg1kp.ca-central-1.rds.amazonaws.com:3306/" + databaseName;
    static String username = "team24TZ";
    static String password = "erp1998TZ";

    private Statement stmt = null;
    public JDBCHolder(){
        initializer();
    }

    public void initializer(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Employees");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            connection.close();
        } catch(Exception e){ System.out.println(e);}
    }

    public void getNotification(){

    }

    public void getEquipRequest(){

    }
    public void getVacRequest(){

    }



    public static void main(String[] args) {
        JDBCHolder j = new JDBCHolder();
    }

}
