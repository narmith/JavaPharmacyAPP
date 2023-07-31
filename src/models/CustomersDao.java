package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
    
    public boolean registerCustomerQuery(Customers customer){
        String register_customer_query = "INSERT INTO customers "
                + "(id,full_name,address,telephone,email,created,updated) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_customer_query);
            pst.setInt(1,customer.getId());
            pst.setString(2,customer.getFull_name());
            pst.setString(3,customer.getAddress());
            pst.setString(4,customer.getTelephone());
            pst.setString(5,customer.getEmail());
            pst.setTimestamp(6,datetime);
            pst.setTimestamp(7,datetime);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving customer: " + e);
            return false;
        }
    }
    public List listCustomersQuery(String value){
        List<Customers> list_customers = new ArrayList();
        String query_search_all = "SELECT * FROM customers";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query_search_all);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);
            }
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customers;
    }
    public boolean updateCustomerQuery(Customers customer){
        String update_query = "UPDATE customers SET "
                + "full_name = ?,address = ?,telephone = ?,email = ?,updated = ? "
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(update_query);
            pst.setString(1,customer.getFull_name());
            pst.setString(2,customer.getAddress());
            pst.setString(3,customer.getTelephone());
            pst.setString(4,customer.getEmail());
            pst.setTimestamp(5,datetime);
            pst.setInt(6,customer.getId());
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error updating customer: " + e);
            return false;
        }
    }
    public boolean deleteCustomerQuery(int id){
        String delete_customer_query = "DELETE FROM customers WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(delete_customer_query);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Cannot delete customer related to multiple tables.");
            return false;
        }
    }
    
}
