package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombobox;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;
import views.SystemView;

public class ProductsController implements ActionListener, MouseListener, KeyListener {
    private Products product;
    private ProductsDao productDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    
    public ProductsController (Products newProduct, ProductsDao newProductsDao, SystemView newViews) {
        product = newProduct;
        productDao = newProductsDao;
        views = newViews;
        
        views.btn_product_register.addActionListener(this);
        views.btn_product_update.addActionListener(this);
        views.btn_product_delete.addActionListener(this);
        views.btn_product_cancel.addActionListener(this);
        
        views.products_table.addMouseListener(this);
        views.jLabelProducts.addMouseListener(this);
        views.jPanel_products.addMouseListener(this);
        
        views.txt_product_search.addKeyListener(this);
        
        
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btn_product_register) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Must fill all field.");
            }
            else {
                product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                product.setName(views.txt_product_name.getText().trim());
                product.setDescription(views.txt_product_description.getText().trim());
                product.setUnit_price(Double.valueOf(views.txt_product_price.getText()));
                DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                product.setCategory_id(category_id.getId());
                
                if(productDao.registerProductsQuery(product)){
                    cleanFields();
                    listAllProducts();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error inserting the product into DB.");
                }
            }
        }
        else if (e.getSource() == views.btn_product_update) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Must fill all field.");
            } else {
                if (views.txt_product_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Must fill the name field.");
                } else {
                    product.setId(Integer.parseInt(views.txt_product_id.getText()));
                    product.setCode(Integer.parseInt(views.txt_product_code.getText()));
                    product.setName(views.txt_product_name.getText().trim());
                    product.setDescription(views.txt_product_description.getText().trim());
                    product.setUnit_price(Double.valueOf(views.txt_product_price.getText()));
                    DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                    product.setCategory_id(category_id.getId());
                    
                    if (productDao.updateProductsQuery(product)) {
                        cleanFields();
                        listAllProducts();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while updating the product data into DB.");
                    }
                }
            }
        }
        else if(e.getSource()==views.btn_product_delete){
            int row = views.products_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Must select a product first.");
            }
            else {
                int id = Integer.parseInt(views.products_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Confirm deletion of the product " + id + ".",null,YES_NO_OPTION);
                if(question == 0 && productDao.deleteProductsQuery(id)){
                    cleanFields();
                    listAllProducts();
                }
            }
        } else if(e.getSource()==views.btn_product_cancel){
            cleanFields();
            listAllProducts();
        }
    }
    
    public void listAllProducts() {
        if (rol.equals("Administrador") || rol.equals("User")) {
            cleanTable();
            List<Products> list = productDao.listProductsQuery(views.txt_product_search.getText());
            tableModel = (DefaultTableModel) views.products_table.getModel();
            Object[] row = new Object[7]; //the row line of our table, containing X qtys of columns.
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnit_price();
                row[5] = list.get(i).getProd_qty();
                row[6] = list.get(i).getCategory_name();
                tableModel.addRow(row);
            }
            views.products_table.setModel(tableModel);
        }
    }

    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    
    public void cleanFields() {
        views.txt_product_code.setText("");
        views.txt_product_name.setText("");
        views.txt_product_price.setText("");
        views.txt_product_description.setText("");
        views.txt_product_id.setText("");
        
        if (rol.equals("Administrador")) {
            views.btn_product_register.setEnabled(true);
            views.btn_product_update.setEnabled(true);
            views.btn_product_delete.setEnabled(true);
        } else if (rol.equals("User")) {
            views.txt_product_code.setEditable(false);
            views.txt_product_name.setEditable(false);
            views.txt_product_price.setEditable(false);
            views.txt_product_description.setEditable(false);
            views.cmb_product_category.setEditable(false);
            
            views.btn_product_register.setEnabled(false);
            views.btn_product_update.setEnabled(false);
            views.btn_product_delete.setEnabled(false);
            views.btn_product_cancel.setEnabled(false);
        }
    }
    
    @Override public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jPanel_products) {
            cleanFields();
            views.products_table.clearSelection();
        } else if (e.getSource() == views.products_table) {
            int row = views.products_table.rowAtPoint(e.getPoint()); //Get the row where the mouse clicked.
            //populate text fields (boxes) from the info of the table item clicked (row)
            views.txt_product_id.setText(views.products_table.getValueAt(row, 0).toString());
            product = productDao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            views.txt_product_code.setText(""+product.getCode());
            views.txt_product_name.setText(""+product.getName());
            views.txt_product_description.setText(""+product.getDescription());
            views.txt_product_price.setText(""+product.getUnit_price());
            views.cmb_product_category.setSelectedItem(new DynamicCombobox(product.getCategory_id(),product.getCategory_name()));
            //disable text fields
            views.txt_product_id.setEditable(false);
            views.btn_product_register.setEnabled(false);
        } else if (e.getSource() == views.jLabelProducts) {
            if (rol.equals("Administrador") || rol.equals("User")) {
                views.jTabbedPaneMain.setSelectedIndex(0);
                cleanFields();
                listAllProducts();
            } else {
                views.jTabbedPaneMain.setEnabledAt(0, false);
                views.jLabelProducts.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Must be an admin to access this tab.");
            }
        }
    }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }

    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyPressed(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) {
        if(e.getSource()==views.txt_product_search){
            cleanTable();
            listAllProducts();
        }
    }
    
}
