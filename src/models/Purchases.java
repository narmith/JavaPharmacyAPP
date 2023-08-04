package models;

public class Purchases {
    private int id;
    private int code;
    private String product_name;
    private int purchase_amount;
    private Double purchase_price;
    private Double purchase_subtotal;
    private Double purchase_total;
    private String created;
    private String supplier_name_product;
    private String purchaser;
    
    //Constructors
    public Purchases() {}
    public Purchases(
            int newId,
            int newCode,
            String newProduct_name,
            int newPurchase_amount,
            Double newPurchase_price,
            Double newPurchase_subtotal,
            Double newPurchase_total,
            String newCreated,
            String newSupplier_name_product,
            String newPurchaser) {
        id = newId;
        code = newCode;
        product_name = newProduct_name;
        purchase_amount = newPurchase_amount;
        purchase_price = newPurchase_price;
        purchase_subtotal = newPurchase_subtotal;
        purchase_total = newPurchase_total;
        created = newCreated;
        supplier_name_product = newSupplier_name_product;
        purchaser = newPurchaser;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public int      getCode() { return code; }
    public void     setCode(int newCode) { code = newCode; }
    public String   getProduct_name() { return product_name; }
    public void     setProduct_name(String newProduct_name) { product_name = newProduct_name; }
    public int      getPurchase_amount() { return purchase_amount; }
    public void     setPurchase_amount(int newPurchase_amount) { purchase_amount = newPurchase_amount; }
    public Double   getPurchase_price() { return purchase_price; }
    public void     setPurchase_price(Double newPurchase_price) { purchase_price = newPurchase_price; }
    public Double   getPurchase_subtotal() { return purchase_subtotal; }
    public void     setPurchase_subtotal(Double newPurchase_subtotal) { purchase_subtotal = newPurchase_subtotal; }
    public Double   getPurchase_total() { return purchase_total; }
    public void     setPurchase_total(Double newPurchase_total) { purchase_total = newPurchase_total; }
    public String   getCreated() { return created; }
    public void     setCreated(String newCreated) { created = newCreated; }
    public String   getSupplier_name_product() { return supplier_name_product; }
    public void     setSupplier_name_product(String newSupplier_name_product) { supplier_name_product = newSupplier_name_product; }
    public String   getPurchaser() { return purchaser; }
    public void     setPurchaser(String newPurchaser) { purchaser = newPurchaser; }

}
