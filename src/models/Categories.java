package models;

public class Categories {
    private int id;
    private String name;
    private String created;
    private String updated;

    //Constructors
    public Categories() {}
    public Categories(
            int newId,
            String newName,
            String newCreated,
            String newUpdated) {
        id = newId;
        name = newName;
        created = newCreated;
        updated = newUpdated;
    }

    //Getters and Setters
    public int      getId() { return id; }
    public void     setId(int newId) { id = newId; }
    public String   getName() { return name; }
    public void     setName(String newName) { name = newName; }
    public String   getCreated() { return created; }
    public void     setCreated(String newCreated) { created = newCreated; }
    public String   getUpdated() { return updated; }
    public void     setUpdated(String newUpdated) { updated = newUpdated; }
    
}
