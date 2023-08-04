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

//DataAccessObject for Purchases
public class PurchasesDao {
    //Connection instance
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
    
    //Methods
    public boolean registerPurchaseQuery(int supplier_id, int employee_id, double total){
        String register_purchase_query = "INSERT INTO purchases "
                + "(supplier_id,employee_id,total,created) "
                + "VALUES (?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_purchase_query);
            pst.setInt(1,supplier_id);
            pst.setInt(2,employee_id);
            pst.setDouble(3,total);
            pst.setTimestamp(4,datetime);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving purchase: " + e);
            return false;
        }
    }
    
    public boolean registerPurchaseDetailsQuery(int purchase_id, double purchase_price,
            int purchase_amount, double purchase_subtotal, int product_id){
        String register_purchase_query = "INSERT INTO purchase_details "
                + "(purchase_price,purchase_amount,purchase_subtotal,purchase_id,product_id) "
                + "VALUES (?,?,?,?,?)";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_purchase_query);
            pst.setDouble(1,purchase_price);
            pst.setInt(2,purchase_amount);
            pst.setDouble(3,purchase_subtotal);
            pst.setInt(4,purchase_id);
            pst.setInt(5,product_id);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving purchase details: " + e);
            return false;
        }
    }
    
    public int purchaseId(){
        String search_query = "SELECT MAX(id) AS purchase_id FROM purchases";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(search_query);
            rs = pst.executeQuery();
            if(rs.next()){
                return rs.getInt("purchase_id");
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return 0;
    }
    public List listAllPurchasesQuery(){
        List<Purchases> list_purchases = new ArrayList();
        String search_query = "SELECT purchases.*, suppliers.name AS supplier_name "
                + "FROM purchases, suppliers "
                + "WHERE purchases.supplier_id=suppliers.id "
                + "ORDER BY purchases.id ASC";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(search_query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Purchases purchase = new Purchases();
                purchase.setId(rs.getInt("id"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setPurchase_total(rs.getDouble("total"));
                purchase.setCreated(rs.getString("created"));
                list_purchases.add(purchase);
            } 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return list_purchases;
    }
    public List listAllPurchaseDestailsQuery(int thisId){
        List<Purchases> list_purchases = new ArrayList();
        String query_search_all = "SELECT "
                + "purchases.created, purchase_details.purchase_price, purchase_details.purchase_amount, "
                + "purchase_details.purchase_subtotal, suppliers.name AS supplier_name, "
                + "products.name AS product_name, employees.full_name AS full_name "
                + "FROM purchases "
                + "INNER JOIN purchase_details ON purchases.id=purchase_details.purchase_id "
                + "INNER JOIN products ON products.id=purchase_details.product_id "
                + "INNER JOIN suppliers ON suppliers.id=purchases.supplier_id "
                + "INNER JOIN employees ON employees.id=purchases.employee_id "
                + "WHERE purchases.id=? ";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_all);
            pst.setInt(1, thisId);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Purchases purchase = new Purchases();
                purchase.setProduct_name(rs.getString("product_name"));
                purchase.setPurchase_amount(rs.getInt("purchase_amount"));
                purchase.setPurchase_price(rs.getDouble("purchase_price"));
                purchase.setPurchase_subtotal(rs.getDouble("purchase_subtotal"));
                purchase.setSupplier_name_product(rs.getString("supplier_name"));
                purchase.setCreated(rs.getString("created"));
                purchase.setPurchaser(rs.getString("full_name"));
                list_purchases.add(purchase);
            } 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }
}
