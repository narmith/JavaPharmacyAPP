package models;

public class Employees {
    private int id;
    private String full_name;
    private String username;
    private String Address;
    private String telephone;
    private String email;
    private String password;
    private String rol;
    private String created;
    private String updated;

    //Constructors
    public Employees() {}
    public Employees(
            int newId,
            String newFull_name,
            String newUsername,
            String newAddress,
            String newTelephone,
            String newEmail,
            String newPassword,
            String newRol,
            String newCreated,
            String newUpdated) {
        id = newId;
        full_name = newFull_name;
        username = newUsername;
        Address = newAddress;
        telephone = newTelephone;
        email = newEmail;
        password = newPassword;
        rol = newRol;
        created = newCreated;
        updated = newUpdated;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public String   getFull_name() { return full_name; }
    public void     setFull_name(String newFull_name) { full_name = newFull_name; }
    public String   getUsername() { return username; }
    public void     setUsername(String newUsername) { username = newUsername; }
    public String   getAddress() { return Address; }
    public void     setAddress(String newAddress) { Address = newAddress; }
    public String   getTelephone() { return telephone; }
    public void     setTelephone(String newTelephone) { telephone = newTelephone; }
    public String   getEmail() { return email; }
    public void     setEmail(String newEmail) { email = newEmail; }
    public String   getPassword() { return password; }
    public void     setPassword(String newPassword) { password = newPassword; }
    public String   getRol() { return rol; }
    public void     setRol(String newRol) { rol = newRol; }
    public String   getCreated() { return created; }
    public void     setCreated(String newCreated) { created = newCreated; }
    public String   getUpdated() { return updated; }
    public void     setUpdated(String newUpdated) { updated = newUpdated; }
    
    
}
