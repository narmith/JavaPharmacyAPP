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
import models.Categories;
import models.CategoriesDao;
import models.DynamicCombobox;
import static models.EmployeesDao.rol_user;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.SystemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {
    private Categories category;
    private CategoriesDao categoriesDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    
    public CategoriesController (Categories newCategory, CategoriesDao newCategoriesDao, SystemView newViews) {
        category = newCategory;
        categoriesDao = newCategoriesDao;
        views = newViews;
        
        views.btn_category_register.addActionListener(this);
        views.btn_category_update.addActionListener(this);
        views.btn_category_delete.addActionListener(this);
        
        views.categories_table.addMouseListener(this);
        views.jLabelCategories.addMouseListener(this);
        views.jPanel_categories.addMouseListener(this);
        
        views.txt_category_search.addKeyListener(this);
        
        getCategoryName();
        AutoCompleteDecorator.decorate(views.cmb_product_category);
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btn_category_register) {
            if(views.txt_category_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Must fill the name field.");
                views.txt_category_name.requestFocus();
            }
            else {
                category.setName(views.txt_category_name.getText().trim());
                
                if(categoriesDao.registerCategoriesQuery(category)){
                    cleanFields();
                    listAllCategories();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error inserting category into DB.");
                }
            }
        }
        else if (e.getSource() == views.btn_category_update) {
            if (views.txt_category_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select any row to update first.");
            } else {
                if (views.txt_category_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Must fill the name field.");
                    views.txt_category_name.requestFocus();
                } else {
                    category.setId(Integer.parseInt(views.txt_category_id.getText().trim()));
                    category.setName(views.txt_category_name.getText().trim());

                    if (categoriesDao.updateCategoriesQuery(category)) {
                        cleanFields();
                        listAllCategories();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while updating category data to DB.");
                    }
                }
            }
        }
        else if(e.getSource()==views.btn_category_delete){
            int row = views.categories_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Must select a category first.");
            }
            else {
                int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Confirm deletion of category " + id + ".",null,YES_NO_OPTION);
                if(question == 0 && categoriesDao.deleteCategoriesQuery(id)){
                    cleanFields();
                    listAllCategories();
                }
            }
        }
    }
    
    public void listAllCategories() {
        if (rol.equals("Administrator")) {
            cleanTable();
            List<Categories> list = categoriesDao.listCategoriesQuery(views.txt_category_search.getText());
            tableModel = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2]; //the row line of our table, containing X qtys of columns.
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                tableModel.addRow(row);
            }
            views.categories_table.setModel(tableModel);
        }
    }
    
    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    
    public void cleanFields(){
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
        views.txt_category_name.setEditable(true);
        views.btn_category_register.setEnabled(true);
    }

    @Override public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jPanel_categories) {
            cleanFields();
            views.categories_table.clearSelection();
        } else if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint()); //Get the row where the mouse clicked.
            //populate text fields (boxes) from the info of the table item clicked (row)
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            //disable text fields
            views.txt_category_id.setEditable(false);
            views.btn_category_register.setEnabled(false);
        } else if (e.getSource() == views.jLabelCategories) {
            if (rol.equals("Administrator")) {
                views.jTabbedPaneMain.setSelectedIndex(5);
                cleanFields();
                listAllCategories();
            } else {
                views.jTabbedPaneMain.setEnabledAt(5, false);
                views.jLabelCategories.setEnabled(false);
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
        if(e.getSource()==views.txt_category_search){
            cleanTable();
            listAllCategories();
        }
    }
    
    public void getCategoryName(){
        List<Categories> list = categoriesDao.listCategoriesQuery(views.txt_category_search.getText());
        for(int i=0; i<list.size();i++){
            int id=list.get(i).getId();
            String name = list.get(i).getName();
            views.cmb_product_category.addItem(new DynamicCombobox(id, name));
        }
    }
    
}
