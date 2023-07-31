package models;

public class Products {
    private int id;
    private int code;
    private String name;
    private String description;
    private Double unit_price;
    private int prod_qty;
    private String created;
    private String updated;
    private int category_id;
    private String category_name;
    
    //Constructors
    public Products() {}
    public Products(
            int newId,
            int newCode,
            String newName,
            String newDescription,
            Double newUnit_price,
            int newProd_qty,
            String newCreated,
            String newUpdated,
            int newCategory_id,
            String newCategory_name) {
        id = newId;
        code = newCode;
        name = newName;
        description = newDescription;
        unit_price = newUnit_price;
        prod_qty = newProd_qty;
        created = newCreated;
        updated = newUpdated;
        category_id = newCategory_id;
        category_name = newCategory_name;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public int      getCode() { return code; }
    public void     setCode(int newCode) { code = newCode; }
    public String   getName() { return name; }
    public void     setName(String newName) { name = newName; }
    public String   getDescription() { return description; }
    public void     setDescription(String newDescription) { description = newDescription; }
    public Double   getUnit_price() { return unit_price; }
    public void     setUnit_price(Double newUnit_price) { unit_price = newUnit_price; }
    public int      getProd_qty() { return prod_qty; }
    public void     setProd_qty(int newProd_qty) { prod_qty = newProd_qty; }
    public String   getCreated() { return created; }
    public void     setCreated(String newCreated) { created = newCreated; }
    public String   getUpdated() { return updated; }
    public void     setUpdated(String newUpdated) { updated = newUpdated; }
    public int      getCategory_id() { return category_id; }
    public void     setCategory_id(int newCreated) { category_id = newCreated; }
    public String   getCategory_name() { return category_name; }
    public void     setCategory_name(String newUpdated) { category_name = newUpdated; }
}
