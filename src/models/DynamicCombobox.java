package models;

public class DynamicCombobox {
    private int id;
    private String name;

    public DynamicCombobox(int newId, String newName) {
        id = newId;
        name = newName;
    }
    public DynamicCombobox(String newId, String newName) {
        id = Integer.parseInt(newId);
        name = newName;
    }

    public int getId() { return id; }
    public void setId(int newId) { id = newId; }
    public String getName() { return name; }
    public void setName(String newName) { name = newName; }
    
    @Override public String toString(){
        return getName();
    }
}
