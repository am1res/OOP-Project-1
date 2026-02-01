package company.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private boolean gender;
    private String login;
    private String password;

    public User() {

    }

    public User(String name, String surname, boolean gender) {
        setName(name);
        setSurname(surname);
        setGender(gender);
    }

    public User(int id, String name, String surname,boolean gender, String login, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.login = login;
        this.password = password;
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return String.format(
                "%d -  %s %s, %s",
                id, name, surname, gender ? "male" : "female"
        );
    }
}
