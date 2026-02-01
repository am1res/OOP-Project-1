package company.repositories;

import company.data.interfaces.IDB;
import company.models.NewUser;
import company.repositories.interfaces.INewUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewUserRepository implements INewUserRepository {
    private final IDB db;

    public NewUserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(NewUser user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getRole() == null) {
            System.out.println("Validation Error: Name and Role cannot be empty!");
            return false;
        }

        String sql = "INSERT INTO users(name, surname, gender, role) VALUES (?, ?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setBoolean(3, user.getGender());
            st.setString(4, user.getRole());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<NewUser> getAll() {
        String sql = "SELECT * FROM users";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            List<NewUser> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new NewUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("role")
                ));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public NewUser getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new NewUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }
}