package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.LoginView;
import views.SystemView;

public class LoginController implements ActionListener, KeyListener {
    private Employees employee;
    private EmployeesDao employees_dao;
    private LoginView login_view;

    public LoginController(Employees newEmployee, EmployeesDao newEmployees_dao, LoginView newLogin_view) {
        employee = newEmployee;
        employees_dao = newEmployees_dao;
        login_view = newLogin_view;
        login_view.btn_ok.addActionListener(this);
        login_view.txt_username.addKeyListener(this);
        login_view.txt_password.addKeyListener(this);
    }
    
    private void tryToLogIn() {
        //Get view data
        String user = login_view.txt_username.getText().trim(); //Get the username without spaces
        String pass = String.valueOf(login_view.txt_password.getPassword());

        if (!user.equals("") || !pass.equals("")) {
            employee = employees_dao.loginQuery(user, pass);
            if (employee.getUsername() != null) {
                if (employee.getRol().equals("Administrador")) {
                    SystemView admin = new SystemView();
                    admin.setVisible(true);
                } else {
                    SystemView aux = new SystemView();
                    aux.setVisible(true);
                }
                login_view.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong username or password.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Username or password cannot be empty.");
        }
    }

    @Override public void actionPerformed(ActionEvent e) { tryToLogIn(); }
    @Override public void keyPressed(KeyEvent e) { if(e.getKeyCode()==KeyEvent.VK_ENTER){ tryToLogIn(); } }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
