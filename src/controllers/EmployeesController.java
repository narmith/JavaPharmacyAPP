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
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    
    public EmployeesController(Employees newEmployee, EmployeesDao newEmployeeDao, SystemView newViews) {
        employee = newEmployee;
        employeeDao = newEmployeeDao;
        views = newViews;
        
        views.btn_employee_register.addActionListener(this);
        views.btn_employee_update.addActionListener(this);
        views.btn_employee_delete.addActionListener(this);
        views.btn_employee_cancel.addActionListener(this);
        views.btn_profile_update.addActionListener(this);
        
        views.employees_table.addMouseListener(this);
        views.jLabelEmployees.addMouseListener(this);
        
        views.txt_employee_search.addKeyListener(this);
    }
    
    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource()==views.btn_employee_register){
            if(        views.txt_employee_id.getText().equals("")
                    || views.txt_employee_name.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_address.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_employee_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null, "Must fill in all information.");
            }
            else {
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_name.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_employee_rol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployeeQuery(employee)){
                    cleanFields();
                    listAllEmployees();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error inserting employee into DB.");
                }
            }
        }
        else if(e.getSource()==views.btn_employee_update){
            if(views.txt_employee_id.equals("")){
                JOptionPane.showMessageDialog(null, "Select any row to update first.");
            }
            else {
                if(views.txt_employee_id.getText().equals("")
                || views.txt_employee_name.getText().equals("")
                || views.cmb_employee_rol.getSelectedItem().toString().equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill all fields first.");
                }
                else {
                    employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    employee.setFull_name(views.txt_employee_name.getText().trim());
                    employee.setUsername(views.txt_employee_username.getText().trim());
                    employee.setAddress(views.txt_employee_address.getText().trim());
                    employee.setTelephone(views.txt_employee_telephone.getText().trim());
                    employee.setEmail(views.txt_employee_email.getText().trim());
                    employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                    employee.setRol(views.cmb_employee_rol.getSelectedItem().toString());
                    
                    if(employeeDao.updateEmployeeQuery(employee)){
                        cleanFields();
                        listAllEmployees();
                        views.btn_employee_register.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Error while updating employee data to DB.");
                    }
                }
            }
        }
        else if(e.getSource()==views.btn_employee_delete){
            int row = views.employees_table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Must select an employee first.");
            }
            else if(views.employees_table.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "You cannot delete yourself.");
            }
            else {
                int id = Integer.parseInt(views.employees_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "Confirm deletion of employee " + id + ".");
                if(question == 0 && employeeDao.deleteEmployeeQuery(id)){
                    cleanFields();
                    views.btn_employee_register.setEnabled(true);
                    views.txt_employee_password.setEnabled(true);
                    listAllEmployees();
                }
            }
        }
        else if(e.getSource()==views.btn_employee_cancel){
            cleanFields();
            views.btn_employee_register.setEnabled(true);
            views.txt_employee_password.setEnabled(true);
            views.txt_employee_id.setEnabled(true);
        }
        else if(e.getSource()==views.btn_profile_update){
            String _pass = String.valueOf(views.txt_profile_password.getPassword());
            String _confirmPass = String.valueOf(views.txt_profile_password_confirm.getPassword());
            if(!_pass.equals("") && _pass.equals(_confirmPass)){
                employee.setPassword(_pass);
                if(employeeDao.updateEmployeePassword(employee)){
                    JOptionPane.showMessageDialog(null, "Password changed.");
                } else { JOptionPane.showMessageDialog(null, "Error while changing your password."); }
            } else { JOptionPane.showMessageDialog(null, "Passwords must be filled and match, please retry."); }
        }
    }
    
    public void listAllEmployees(){
        if(rol.equals("Administrator")){
            cleanTable();
            
            List<Employees> list = employeeDao.listEmployeesQuery(views.txt_employee_search.getText());
            tableModel = (DefaultTableModel) views.employees_table.getModel();
            Object[] row = new Object[7];
            
            for(int i=0; i<list.size(); i++){
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                tableModel.addRow(row);
            }
            views.employees_table.setModel(tableModel);
        }
    }
    
    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    
    public void cleanFields(){
        views.txt_employee_id.setText("");
        views.txt_employee_id.setEditable(true);
        views.txt_employee_name.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
        views.txt_employee_password.setEditable(true);
        views.txt_employee_password.setEnabled(true);
        views.cmb_employee_rol.setSelectedIndex(0);
    }
    
    @Override public void mouseClicked(MouseEvent e) {
        if(e.getSource()==views.employees_table){
            int row = views.employees_table.rowAtPoint(e.getPoint()); //Get the row where the mouse clicked.
            
            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_name.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_employee_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
            
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_employee_register.setEnabled(false);
        }
        else if (e.getSource() == views.jLabelEmployees){
            if(rol.equals("Administrator")){
                views.jTabbedPaneMain.setSelectedIndex(3);
                cleanFields();
                listAllEmployees();
            }
            else {
                views.jTabbedPaneMain.setEnabledAt(3, false);
                views.jLabelEmployees.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Must be an admin to access this tab.");
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) {
        if(e.getSource()==views.txt_employee_search){
            listAllEmployees();
        }
    }
}
