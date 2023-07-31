package models;

public class Customers {
    private int id;
    private String full_name;
    private String address;
    private String telephone;
    private String email;
    private String created;
    private String updated;

    public Customers() {}

    public Customers(
            int newId,
            String newFull_name,
            String newAddress,
            String newTelephone,
            String newEmail,
            String newCreated,
            String newUpdated) {
        id = newId;
        full_name = newFull_name;
        address = newAddress;
        telephone = newTelephone;
        email = newEmail;
        created = newCreated;
        updated = newUpdated;
    }

    public int getId() { return id; }
    public void setId(int newId) { id = newId; }
    public String getFull_name() { return full_name; }
    public void setFull_name(String newFull_name) { full_name = newFull_name; }
    public String getAddress() { return address; }
    public void setAddress(String newAddress) { address = newAddress; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String newTelephone) { telephone = newTelephone; }
    public String getEmail() { return email; }
    public void setEmail(String newEmail) { email = newEmail; }
    public String getCreated() { return created; }
    public void setCreated(String newCreated) { created = newCreated; }
    public String getUpdated() { return updated; }
    public void setUpdated(String newUpdated) { updated = newUpdated; }
    
}
