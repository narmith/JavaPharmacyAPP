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
import models.Customers;
import models.CustomersDao;
import static models.EmployeesDao.id_user;
import views.SystemView;

public class CustomersController implements ActionListener, MouseListener, KeyListener {
    private Customers customer;
    private CustomersDao customerDao;
    private SystemView views;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    
    public CustomersController (Customers newCustomer, CustomersDao newCustomerDao, SystemView newViews) {
        customer = newCustomer;
        customerDao = newCustomerDao;
        views = newViews;
        
        views.btn_customer_register.addActionListener(this);
        views.btn_customer_update.addActionListener(this);
        views.btn_customer_delete.addActionListener(this);
        views.btn_customer_cancel.addActionListener(this);
        
        views.customers_table.addMouseListener(this);
        views.jLabelCustomers.addMouseListener(this);
        
        views.txt_customer_search.addKeyListener(this);
    }

    @Override public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == views.btn_customer_register) {
            if(views.txt_customer_id.getText().equals("")
                || views.txt_customer_name.getText().equals("")
                || views.txt_customer_address.getText().equals("")
                || views.txt_customer_telephone.getText().equals("")
                || views.txt_customer_email.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Must fill all fields first.");
            }
            else {
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_name.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());
                
                if(customerDao.registerCustomerQuery(customer)){
                    cleanFields();
                    listAllCustomers();
                    //JOptionPane.showMessageDialog(null, "Customer inserted into DB.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error inserting customer into DB.");
                }
            }
        }
        else if(e.getSource()==views.btn_customer_update){
            if(views.txt_customer_id.equals("")){
                JOptionPane.showMessageDialog(null, "Select any row to update first.");
            }
            else {
                if(views.txt_customer_id.getText().equals("")
                || views.txt_customer_name.getText().equals("")
                || views.txt_customer_address.toString().equals("")
                || views.txt_customer_telephone.toString().equals("")
                || views.txt_customer_email.toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill all fields first.");
                }
                else {
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    customer.setFull_name(views.txt_customer_name.getText().trim());
                    customer.setAddress(views.txt_customer_address.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());
                    
                    if(customerDao.updateCustomerQuery(customer)){
                        cleanFields();
                        listAllCustomers();
                        views.btn_customer_register.setEnabled(true);
                        //JOptionPane.showMessageDialog(null, "Customer updated correctly.");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Error while updating customer data to DB.");
                    }
                }
            }
        }
        else if(e.getSource()==views.btn_customer_delete){
            int row = views.customers_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Must select a customer first.");
            }
            else if(views.customers_table.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "You cannot delete yourself.");
            }
            else {
                int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Confirm deletion of customer " + id + ".");
                if(question == 0 && customerDao.deleteCustomerQuery(id)){
                    cleanFields();
                    listAllCustomers();
                    views.btn_customer_register.setEnabled(true);
                    //JOptionPane.showMessageDialog(null, "Customer deleted.");
                }
            }
        }
        else if(e.getSource()==views.btn_customer_cancel){
            cleanFields();
            listAllCustomers();
            views.btn_customer_register.setEnabled(true);
            views.txt_customer_id.setEnabled(true);
        }
    }
    
    public void listAllCustomers() {
        cleanTable();
        
        List<Customers> list = customerDao.listCustomersQuery(views.txt_customer_search.getText());
        tableModel = (DefaultTableModel) views.customers_table.getModel();
        Object[] row = new Object[5];

        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            tableModel.addRow(row);
        }
        views.customers_table.setModel(tableModel);
    }
    
    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    
    public void cleanFields(){
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_name.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    @Override public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customers_table) {
            int row = views.customers_table.rowAtPoint(e.getPoint()); //Get the row where the mouse clicked.

            //populate text fields (boxes) from the info of the table item clicked (row)
            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_name.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());
            //disable text fields
            views.txt_customer_id.setEditable(false);
            views.btn_customer_register.setEnabled(false);
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jTabbedPaneMain.setSelectedIndex(2);
            cleanFields();
            listAllCustomers();
        }
    }
    
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }

    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyPressed(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) {
        if(e.getSource()==views.txt_customer_search){
            cleanTable();
            listAllCustomers();
        }
    }
}
