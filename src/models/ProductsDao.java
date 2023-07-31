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

public class ProductsDao {
    //connection instance
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
    
    //methods
    public boolean registerProductsQuery(Products product){
        String register_product_query = "INSERT INTO products "
                + "(code,name,description,unit_price,created,updated,category_id) "
                + "VALUES (?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_product_query);
            pst.setInt(1,product.getCode());
            pst.setString(2,product.getName());
            pst.setString(3,product.getDescription());
            pst.setDouble(4,product.getUnit_price());
            pst.setTimestamp(5,datetime);
            pst.setTimestamp(6,datetime);
            pst.setInt(7,product.getCategory_id());
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving product: " + e);
            return false;
        }
    }
    public List listProductsQuery(String value){
        List<Products> list_products = new ArrayList();
        String query_search_all = "SELECT products.*, categories.name AS category_name "
                + "FROM products, categories "
                + "WHERE products.category_id=categories.id";
        String query_search_product = "SELECT products.*, categories.name AS category_name "
                + "FROM products "
                + "INNER JOIN categories ON products.category_id=categories.id "
                + "WHERE products.name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query_search_all);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }
            while(rs.next()){
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProd_qty(rs.getInt("prod_qty"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            } 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_products;
    }
    public boolean updateProductsQuery(Products product){
        String update_query = "UPDATE products SET "
                + "code=?,name=?,description=?,unit_price=?,updated=?,category_id=? "
                + "WHERE id=?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(update_query);
            pst.setInt(1,product.getCode());
            pst.setString(2,product.getName());
            pst.setString(3,product.getDescription());
            pst.setDouble(4,product.getUnit_price());
            pst.setTimestamp(5,datetime);
            pst.setInt(6,product.getCategory_id());
            pst.setInt(7,product.getId());
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error updating product: " + e);
            return false;
        }
    }
    public boolean deleteProductsQuery(int id){
        String delete_products_query = "DELETE FROM products WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(delete_products_query);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Cannot delete product related to multiple tables.");
            return false;
        }
    }
    public Products searchProduct(int id){
        String query_search_product = "SELECT products.*, categories.name AS category_name "
                + "FROM products "
                + "INNER JOIN categories ON products.category_id=categories.id "
                + "WHERE products.id=?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_product);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    public Products searchCode(int code){
        String query_search_product = "SELECT id,name FROM products WHERE code=?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_product);
            pst.setInt(1,code);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    public Products searchId(int id){
        String query_search_product = "SELECT prod_qty FROM products WHERE id=?";
        Products product = new Products();
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_product);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setProd_qty(rs.getInt("prod_qty"));
                product.setName(rs.getString("name"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return product;
    }
    public boolean updateStockQuery(int newAmount, int thisProduct_id){
        String update_query = "UPDATE products SET "
                + "prod_qty=?,updated=? "
                + "WHERE id=?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(update_query);
            pst.setInt(1,newAmount);
            pst.setTimestamp(2,datetime);
            pst.setInt(3,thisProduct_id);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}
