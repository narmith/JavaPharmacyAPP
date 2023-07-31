package models;

public class Suppliers {
    private int id;
    private String name;
    private String description;
    private String Address;
    private String telephone;
    private String email;
    private String city;
    private String created;
    private String updated;

    //Constructors
    public Suppliers() {}
    public Suppliers(
            int newId,
            String newName,
            String newDescription,
            String newAddress,
            String newTelephone,
            String newEmail,
            String newCity,
            String newCreated,
            String newUpdated) {
        id = newId;
        name = newName;
        description = newDescription;
        Address = newAddress;
        telephone = newTelephone;
        email = newEmail;
        city = newCity;
        created = newCreated;
        updated = newUpdated;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public String   getName() { return name; }
    public void     setName(String newName) { name = newName; }
    public String   getDescription() { return description; }
    public void     setDescription(String newDescription) { description = newDescription; }
    public String   getAddress() { return Address; }
    public void     setAddress(String newAddress) { Address = newAddress; }
    public String   getTelephone() { return telephone; }
    public void     setTelephone(String newTelephone) { telephone = newTelephone; }
    public String   getEmail() { return email; }
    public void     setEmail(String newEmail) { email = newEmail; }
    public String   getCity() { return city; }
    public void     setCity(String newCity) { city = newCity; }
    public String   getCreated() { return created; }
    public void     setCreated(String newCreated) { created = newCreated; }
    public String   getUpdated() { return updated; }
    public void     setUpdated(String newUpdated) { updated = newUpdated; }
    
}
