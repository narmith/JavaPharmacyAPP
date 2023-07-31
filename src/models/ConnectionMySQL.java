package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name = "pharmacy_db";
    private String user = "root";
    private String password = "2233";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;
    
    public Connection getConnection(){
        try{
            //get driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //get connection
            conn = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            System.err.println("Error: ClassNotFoundException " + e.getMessage());
        }catch(SQLException e){
            System.err.println("Error: SQLException " + e.getMessage());
        }
        return conn;
    }
}
