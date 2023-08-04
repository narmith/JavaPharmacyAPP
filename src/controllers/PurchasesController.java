package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombobox;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import models.Products;
import models.ProductsDao;
import models.Purchases;
import models.PurchasesDao;
import views.SystemView;

public class PurchasesController implements ActionListener, MouseListener, KeyListener {
    private Purchases purchase;
    private PurchasesDao purchaseDao;
    private SystemView views;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel tableModel = new DefaultTableModel(); //Table objects instance
    DefaultTableModel tableAuxTemp = new DefaultTableModel(); //Table objects instance
    Products product = new Products();
    ProductsDao productDao = new ProductsDao();
    String rol = rol_user;
    
    public PurchasesController (Purchases newPurchase, PurchasesDao newPurchasesDao, SystemView newViews) {
        purchase = newPurchase;
        purchaseDao = newPurchasesDao;
        views = newViews;
        
        views.txt_purchase_prod_code.addKeyListener(this);
        views.txt_purchase_amount.addKeyListener(this);
        views.txt_purchase_price.addKeyListener(this);
        
        views.btn_purchase_add.addActionListener(this);
        views.btn_purchase_buy.addActionListener(this);
        views.btn_purchase_remove.addActionListener(this);
        views.btn_purchase_new.addActionListener(this);
        
        views.purchases_table.addMouseListener(this);
        views.jLabelPurchases.addMouseListener(this);
        views.jLabelReports.addMouseListener(this);
    }
    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_purchase_add) {
            DynamicCombobox supplier_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
            int supplier_id = supplier_cmb.getId();

            if (getIdSupplier == 0) {
                getIdSupplier = supplier_id;
            } else if (getIdSupplier != supplier_id) {
                JOptionPane.showMessageDialog(null, "Only one purchase per supplier allowed.");
                return;
            }

            int amount = Integer.parseInt(views.txt_purchase_amount.getText());
            String product_name = views.txt_purchase_prod_name.getText();
            double price = Double.parseDouble(views.txt_purchase_price.getText());
            int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
            String supplier_name = views.cmb_purchase_supplier.getSelectedItem().toString();

            if (amount > 0) {
                tableAuxTemp = (DefaultTableModel) views.purchases_table.getModel();
                for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                    if (views.purchases_table.getValueAt(i, 1).equals(views.txt_purchase_prod_name.getText())) {
                        JOptionPane.showMessageDialog(null, "Product already added to the purchase list.");
                        return;
                    }
                }
            }

            ArrayList list = new ArrayList();
            item = 1;
            list.add(item);
            list.add(purchase_id);
            list.add(product_name);
            list.add(amount);
            list.add(price);
            list.add(amount * price);
            list.add(supplier_name);

            Object[] obj = new Object[6];
            obj[0] = list.get(1);
            obj[1] = list.get(2);
            obj[2] = list.get(3);
            obj[3] = list.get(4);
            obj[4] = list.get(5);
            obj[5] = list.get(6);
            tableAuxTemp.addRow(obj);
            views.purchases_table.setModel(tableAuxTemp);

            cleanFields();
            views.cmb_purchase_supplier.setEditable(false);
            views.txt_purchase_prod_code.requestFocus();
            calculatePurchase();
        } else if (e.getSource() == views.btn_purchase_buy) {
            insertPurchase();
        } else if (e.getSource() == views.btn_purchase_remove) {
            tableModel = (DefaultTableModel) views.purchases_table.getModel();
            tableModel.removeRow(views.purchases_table.getSelectedRow());
            calculatePurchase();
            views.txt_purchase_prod_code.requestFocus();
        } else if (e.getSource() == views.btn_purchase_new) {
            cleanTableAux();
            cleanFields();
        }
    }
    @Override public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jLabelPurchases) {
            if (rol.equals("Administrator")) {
                views.jTabbedPaneMain.setSelectedIndex(1);
                cleanFields();
                cleanTable();
            } else {
                views.jTabbedPaneMain.setEnabledAt(1, false);
                views.jLabelPurchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Must be an admin to access this tab.");
            }
        } else if (e.getSource() == views.jLabelReports) {
            views.jTabbedPaneMain.setSelectedIndex(6);
            cleanTable();
            listAllPurchases();
        }
    }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (views.txt_purchase_prod_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Must give a product code.");
                views.txt_purchase_prod_code.requestFocus();
            } else if (e.getSource() == views.txt_purchase_prod_code) {
                int id = Integer.parseInt(views.txt_purchase_prod_code.getText());
                product = productDao.searchCode(id);
                views.txt_purchase_prod_name.setText(product.getName());
                views.txt_purchase_id.setText("" + product.getId());
                if(views.txt_purchase_prod_name.getText().equals("")){
                    views.txt_purchase_prod_name.setText("Unknown code");
                    views.txt_purchase_id.setText("");
                }
            }
            if (e.getSource() == views.txt_purchase_price
                    || e.getSource() == views.txt_purchase_amount) {
                calculateSubtotal();
            }
        }
    }
    @Override public void keyReleased(KeyEvent e) {
        if (!views.txt_purchase_prod_code.getText().equals("")
                && e.getSource() == views.txt_purchase_prod_code) {
            int id = Integer.parseInt(views.txt_purchase_prod_code.getText());
            product = productDao.searchCode(id);
            views.txt_purchase_prod_name.setText(product.getName());
            views.txt_purchase_id.setText("" + product.getId());
            if (views.txt_purchase_prod_name.getText().equals("")) {
                views.txt_purchase_prod_name.setText("Unknown code");
                views.txt_purchase_id.setText("");
            }
        }
        if (e.getSource() == views.txt_purchase_price
                || e.getSource() == views.txt_purchase_amount) {
            calculateSubtotal();
        }
    }
    private void calculateSubtotal(){
        int qty = 1;
        double price = 0.00;
        if(views.txt_purchase_amount.getText().equals("")){
            views.txt_purchase_amount.setText("" + qty);
        }
        if(views.txt_purchase_price.getText().equals("")){
            views.txt_purchase_price.setText("" + price);
        }
        qty = Integer.parseInt(views.txt_purchase_amount.getText());
        price = Double.parseDouble(views.txt_purchase_price.getText());
        views.txt_purchase_subtotal.setText("" + qty*price);
    }
    private void calculatePurchase(){
        double total = 0;
        int numRow = views.purchases_table.getRowCount();

        for(int i=0; i<numRow; i++){
            total += Double.parseDouble(String.valueOf(views.purchases_table.getValueAt(i, 4)));
        }
        views.txt_purchase_totalcost.setText("" + total);
    }
    private void insertPurchase(){
        double total = Double.parseDouble(views.txt_purchase_totalcost.getText());
        int employee_id = id_user;
        if(purchaseDao.registerPurchaseQuery(getIdSupplier, employee_id, total)){
            int purchase_id = purchaseDao.purchaseId();
            for(int i=0; i<views.purchases_table.getRowCount(); i++){
                int product_id = Integer.parseInt(views.purchases_table.getValueAt(i, 0).toString());
                int purchase_amount = Integer.parseInt(views.purchases_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.purchases_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;
                
                purchaseDao.registerPurchaseDetailsQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);
                
                product = productDao.searchId(product_id);
                int amount = product.getProd_qty() + purchase_amount;
                productDao.updateStockQuery(amount, product_id);
            }
            cleanTableAux();
            cleanFields();
        }
    }
    public void listAllPurchases() {
        if (rol.equals("Administrator")) {
            List<Purchases> list = purchaseDao.listAllPurchasesQuery();
            tableModel = (DefaultTableModel) views.reports_table.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSupplier_name_product();
                row[2] = list.get(i).getPurchase_total();
                row[3] = list.get(i).getCreated();
                tableModel.addRow(row);
            }
            views.reports_table.setModel(tableModel);
        }
    }
    public void cleanTableAux(){
        for(int i=0; i<tableAuxTemp.getRowCount(); i++){
            tableAuxTemp.removeRow(i);
            i -= 1;
        }
    }
    public void cleanTable(){
        for(int i=0; i<tableModel.getRowCount();i++){
            tableModel.removeRow(i);
            i -= 1;
        }
    }
    public void cleanFields() {
        views.txt_purchase_prod_code.setText("");
        views.txt_purchase_prod_name.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_totalcost.setText("");
    }
}
