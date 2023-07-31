package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Suppliers;
import models.SuppliersDao;
import views.SystemView;
import static models.EmployeesDao.rol_user;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {
    private Suppliers supplier;
    private SuppliersDao suppliersDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    
    public SuppliersController (Suppliers newSupplier, SuppliersDao newSupplierDao, SystemView newViews) {
        supplier = newSupplier;
        suppliersDao = newSupplierDao;
        views = newViews;
        
        views.btn_supplier_register.addActionListener(this);
        views.btn_supplier_update.addActionListener(this);
        views.btn_supplier_delete.addActionListener(this);
        views.btn_supplier_cancel.addActionListener(this);
        
        views.suppliers_table.addMouseListener(this);
        views.jLabelSuppliers.addMouseListener(this);
        
        views.txt_supplier_search.addKeyListener(this);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btn_supplier_register) {
            if(views.txt_supplier_name.getText().equals("")
                || views.txt_supplier_description.getText().equals("")
                || views.txt_supplier_address.getText().equals("")
                || views.txt_supplier_telephone.getText().equals("")
                || views.txt_supplier_email.getText().equals("")
                || views.cmb_supplier_city.getSelectedItem().toString().equals("")){
                JOptionPane.showMessageDialog(null, "Must fill all fields first.");
            }
            else {
                supplier.setName(views.txt_supplier_name.getText().trim());
                supplier.setDescription(views.txt_supplier_description.getText().trim());
                supplier.setAddress(views.txt_supplier_address.getText().trim());
                supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                supplier.setEmail(views.txt_supplier_email.getText().trim());
                supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString().trim());
                
                if(suppliersDao.registerSupplierQuery(supplier)){
                    cleanFields();
                    listAllSuppliers();
                    //JOptionPane.showMessageDialog(null, "Supplier inserted into DB.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error inserting supplier into DB.");
                }
            }
        }
        else if (e.getSource() == views.btn_supplier_update) {
            if (views.txt_supplier_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Select any row to update first.");
            } else {
                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_address.toString().equals("")
                        || views.txt_supplier_telephone.toString().equals("")
                        || views.txt_supplier_email.toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields first.");
                } else {
                    supplier.setId(Integer.parseInt(views.txt_supplier_id.getText().trim()));
                    supplier.setName(views.txt_supplier_name.getText().trim());
                    supplier.setDescription(views.txt_supplier_description.getText().trim());
                    supplier.setAddress(views.txt_supplier_address.getText().trim());
                    supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
                    supplier.setEmail(views.txt_supplier_email.getText().trim());
                    supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString().trim());

                    if (suppliersDao.updateSupplierQuery(supplier)) {
                        cleanFields();
                        listAllSuppliers();
                        views.btn_supplier_register.setEnabled(true);
                        //JOptionPane.showMessageDialog(null, "Supplier updated correctly.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while updating suppliers data to DB.");
                    }
                }
            }
        }
        else if(e.getSource()==views.btn_supplier_delete){
            int row = views.suppliers_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Must select a suppliers first.");
            }
            else {
                int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Confirm deletion of supplier " + id + ".");
                if(question == 0 && suppliersDao.deleteSupplierQuery(id)){
                    cleanFields();
                    listAllSuppliers();
                    views.btn_supplier_register.setEnabled(true);
                    //JOptionPane.showMessageDialog(null, "Supplier deleted.");
                }
            }
        }
        else if(e.getSource()==views.btn_supplier_cancel){
            cleanFields();
            listAllSuppliers();
            views.btn_supplier_register.setEnabled(true);
            views.txt_supplier_id.setEnabled(true);
        }
    }
    
    public void listAllSuppliers() {
        cleanTable();
        
        List<Suppliers> list = suppliersDao.listSuppliersQuery(views.txt_supplier_search.getText());
        tableModel = (DefaultTableModel) views.suppliers_table.getModel();
        Object[] row = new Object[7];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getDescription();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getTelephone();
            row[5] = list.get(i).getEmail();
            row[6] = list.get(i).getCity();
            tableModel.addRow(row);
        }
        views.suppliers_table.setModel(tableModel);
    }
    
    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    
    public void cleanFields(){
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);
    }

    @Override public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliers_table) {
            int row = views.suppliers_table.rowAtPoint(e.getPoint()); //Get the row where the mouse clicked.

            //populate text fields (boxes) from the info of the table item clicked (row)
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());
            //disable text fields
            views.txt_supplier_id.setEditable(false);
            views.btn_supplier_register.setEnabled(false);
        } else if (e.getSource() == views.jLabelSuppliers){
            if(rol.equals("Administrador")){
                views.jTabbedPaneMain.setSelectedIndex(4);
                cleanFields();
                listAllSuppliers();
            }
            else {
                views.jTabbedPaneMain.setEnabledAt(4, false);
                views.jLabelSuppliers.setEnabled(false);
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
        if(e.getSource()==views.txt_supplier_search){
            cleanTable();
            listAllSuppliers();
        }
    }
}
