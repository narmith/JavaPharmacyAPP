package models;

public class Sales {
    //Variables
    private int id;
    private String sale_date;
    private Double total_to_pay;
    private int customer_id;
    private String customer_name;
    private int employee_id;
    private String employee_name;
    
    //Constructor
    Sales(){}
    Sales(  int newId,
            String newSale_date,
            Double newTotal_to_pay,
            int newCustomer_id,
            String newCustomer_name,
            int newEmployee_id,
            String newEmployee_name) {
        id = newId;
        sale_date = newSale_date;
        total_to_pay = newTotal_to_pay;
        customer_id = newCustomer_id;
        customer_name = newCustomer_name;
        employee_id = newEmployee_id;
        employee_name = newEmployee_name;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public String   getSale_date() { return sale_date; }
    public void     setSale_date(String newSale_date) { sale_date = newSale_date; }
    public Double   getTotal_to_pay() { return total_to_pay; }
    public void     setTotal_to_pay(Double newTotal_to_pay) { total_to_pay = newTotal_to_pay; }
    public int      getCustomer_id() { return customer_id; }
    public void     setCustomer_id(int newCustomer_id) { customer_id = newCustomer_id; }
    public String   getCustomer_name() { return customer_name; }
    public void     setCustomer_name(String newCustomer_name) { customer_name = newCustomer_name; }
    public int      getEmployee_id() { return employee_id; }
    public void     setEmployee_id(int newEmployee_id) { employee_id = newEmployee_id; }
    public String   getEmployee_name() { return employee_name; }
    public void     setEmployee_name(String newEmployee_name) { employee_name = newEmployee_name; }
}
