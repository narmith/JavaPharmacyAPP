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

public class SuppliersDao {
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
       
    public boolean registerSupplierQuery(Suppliers supplier){
        String register_supplier_query = "INSERT INTO suppliers "
                + "(name,description,address,telephone,email,city,created,updated) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_supplier_query);
            pst.setString(1,supplier.getName());
            pst.setString(2,supplier.getDescription());
            pst.setString(3,supplier.getAddress());
            pst.setString(4,supplier.getTelephone());
            pst.setString(5,supplier.getEmail());
            pst.setString(6,supplier.getCity());
            pst.setTimestamp(7,datetime);
            pst.setTimestamp(8,datetime);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving supplier: " + e);
            return false;
        }
    }
    public List listSuppliersQuery(String value){
        List<Suppliers> list_suppliers = new ArrayList();
        String query_search_all = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query_search_all);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                list_suppliers.add(supplier);
            }
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_suppliers;
    }
    public boolean updateSupplierQuery(Suppliers supplier){
        String update_query = "UPDATE suppliers SET "
                + "name = ?,description = ?,address = ?,telephone = ?,email = ?,city = ?,updated = ? "
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(update_query);
            pst.setString(1,supplier.getName());
            pst.setString(2,supplier.getDescription());
            pst.setString(3,supplier.getAddress());
            pst.setString(4,supplier.getTelephone());
            pst.setString(5,supplier.getEmail());
            pst.setString(6,supplier.getCity());
            pst.setTimestamp(7,datetime);
            pst.setInt(8,supplier.getId());
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error updating supplier: " + e);
            return false;
        }
    }
    public boolean deleteSupplierQuery(int id){
        String delete_supplier_query = "DELETE FROM suppliers WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(delete_supplier_query);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Cannot delete supplier related to multiple tables.");
            return false;
        }
    }
    
}
