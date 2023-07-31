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

public class CategoriesDao {
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
       
    public boolean registerCategoriesQuery(Categories category){
        String register_category_query = "INSERT INTO categories "
                + "(name,created,updated) "
                + "VALUES (?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(register_category_query);
            pst.setString(1,category.getName());
            pst.setTimestamp(2,datetime);
            pst.setTimestamp(3,datetime);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error saving category: " + e);
            return false;
        }
    }
    public List listCategoriesQuery(String value){
        List<Categories> list_categories = new ArrayList();
        String query_search_all = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query_search_all);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            while(rs.next()){
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);
            } 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_categories;
    }
    public boolean updateCategoriesQuery(Categories category){
        String update_query = "UPDATE categories SET "
                + "name = ?,updated = ? "
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(update_query);
            pst.setString(1,category.getName());
            pst.setTimestamp(2,datetime);
            pst.setInt(3,category.getId());
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error updating category: " + e);
            return false;
        }
    }
    public boolean deleteCategoriesQuery(int id){
        String delete_category_query = "DELETE FROM categories WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(delete_category_query);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Cannot delete category related to multiple tables.");
            return false;
        }
    }
    
}
