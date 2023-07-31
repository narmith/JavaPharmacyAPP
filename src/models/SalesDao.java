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

public class SalesDao {
    //Connection instance
    ConnectionMySQL cn = new ConnectionMySQL(); //MySQL connection instance
    Connection conn;                            //connection handler
    PreparedStatement pst;                      //query statement
    ResultSet rs;                               //query result
    
    //Public Methods
    public boolean registerSaleQuery(int customer_id, int employee_id, double total){
        String reg_sale_query = "INSERT INTO sales "
                + "(customer_id,employee_id,total,sale_date) "
                + "VALUES (?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(reg_sale_query);
            pst.setInt(1,customer_id);
            pst.setInt(2,employee_id);
            pst.setDouble(3,total);
            pst.setTimestamp(4,datetime);
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    public boolean registerSaleDetailsQuery(int product_id, double sale_id,
            int sale_qty, double sale_price, double sale_subtotal){
        String reg_sale_details_query = "INSERT INTO sale_details "
                + "(product_id,sale_id,sale_quantity,"
                + "sale_price,sale_subtotal) "
                + "VALUES (?,?,?,?,?)";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(reg_sale_details_query);
            pst.setInt(1,product_id);
            pst.setDouble(2,sale_id);
            pst.setInt(3,sale_qty);
            pst.setDouble(4,sale_price);
            pst.setDouble(5,sale_subtotal);            
            pst.execute();
            return true;
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
    public int saleID(){
        int id = 0;
        String search_query = "SELECT MAX(id) AS id FROM sales";
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(search_query);
            pst.executeQuery();
            if(rs.next()){ id = rs.getInt("id"); }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return id;
    }
    public List listAllSalesQuery(){
        List<Sales> list_sales = new ArrayList();
        String query_search_all = 
                "SELECT sales.id AS invoice,customers.full_name AS customer,"
                + "employees.full_name AS employee, sales.total, sales.sale_date "
                + "FROM sales "
                + "INNER JOIN customers ON sales.customer_id=customers.id "
                + "INNER JOIN employees ON sales.employee_id=employees.id "
                + "ORDER BY sales.id ASC";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query_search_all);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Sales sale = new Sales();
                sale.setId(rs.getInt("invoice"));
                sale.setCustomer_name(rs.getString("customer"));
                sale.setEmployee_name(rs.getString("employee"));
                sale.setTotal_to_pay(rs.getDouble("total"));
                sale.setSale_date(rs.getString("sale_date"));
                list_sales.add(sale);
            } 
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list_sales;
    }
}
