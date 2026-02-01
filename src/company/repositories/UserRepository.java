package company.repositories;

import company.data.interfaces.IDB;
import company.models.User;
import company.repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public User login(String login, String password) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, name, surname, login, gender, password FROM users WHERE login=? AND password=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, login);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("login"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO users(name, surname,gender, login, password) VALUES (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setBoolean(3, user.getGender());
            st.setString(4, user.getLogin());
            st.setString(5, user.getPassword());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return false;
    }

    @Override
    public User getUser(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, name, surname, gender, login, password FROM users WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("login"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        Connection con = null;
        List<User> users = new ArrayList<>();
        try {
            con = db.getConnection();
            String sql = "SELECT id, name, surname, gender, login, password FROM users";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("login"),
                        rs.getString("password")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return users;
    }
}