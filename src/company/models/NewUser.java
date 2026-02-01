package company.models;

public class NewUser {
    private int id;
    private String name;
    private String surname;
    private boolean gender;
    private String role;

    public NewUser(int id, String name, String surname, boolean gender, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public boolean getGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "NewUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }
}
