package company.repositories;

import company.data.interfaces.IDB;
import company.models.Motorcycle;
import company.models.Category;
import company.models.NewUser;
import company.repositories.interfaces.IMotorcycleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotorcycleRepository implements IMotorcycleRepository {
    private final IDB db;

    public MotorcycleRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Motorcycle motorcycle) {
        String sql = "INSERT INTO motorcycles(owner_id, category_id, type, brand, model, year, price, is_available) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, motorcycle.getOwner().getId());
            st.setInt(2, motorcycle.getCategory().getId());
            st.setString(3, motorcycle.getType());
            st.setString(4, motorcycle.getBrand());
            st.setString(5, motorcycle.getModel());
            st.setInt(6, motorcycle.getYear());
            st.setDouble(7, motorcycle.getPrice());
            st.setBoolean(8, motorcycle.isAvailable());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Motorcycle getById(int id) {
        String sql = "SELECT m.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM motorcycles m " +
                "JOIN users u ON m.owner_id = u.id " +
                "JOIN categories cat ON m.category_id = cat.id " +
                "WHERE m.id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToMotorcycle(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Motorcycle> getAll() {
        return getMotorcyclesByQuery("SELECT m.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM motorcycles m " +
                "JOIN users u ON m.owner_id = u.id " +
                "JOIN categories cat ON n.category_id = cat.id");
    }

    @Override
    public List<Motorcycle> getAllSortedByPrice() {
        return getMotorcyclesByQuery("SELECT m.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM motorcycle m" +
                "JOIN users u ON m.owner_id = u.id " +
                "JOIN categories cat ON m.category_id = cat.id " +
                "ORDER BY m.price ASC");
    }

    @Override
    public List<Motorcycle> getAllSortedByYear() {
        return getMotorcyclesByQuery("SELECT m.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM motorcycle m" +
                "JOIN users u ON m.owner_id = u.id " +
                "JOIN categories cat ON m.category_id = cat.id " +
                "ORDER BY m.year DESC");
    }

    @Override
    public boolean update(Motorcycle  motorcycle ) {
        String sql = "UPDATE motorcycles SET owner_id=?, category_id=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, motorcycle.getOwner().getId());
            st.setInt(2, motorcycle.getCategory().getId());
            st.setString(3, motorcycle.getType());
            st.setDouble(4, motorcycle.getPrice());
            st.setBoolean(5, motorcycle.isAvailable());
            st.setInt(6, motorcycle.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM motorcycles WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    private List<Motorcycle> getMotorcyclesByQuery(String sql) {
        List<Motorcycle> buses = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                buses.add(mapResultSetToMotorcycle(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return buses;
    }

    private Motorcycle mapResultSetToMotorcycle(ResultSet rs) throws SQLException {
        NewUser owner = new NewUser(
                rs.getInt("owner_id"),
                rs.getString("user_name"),
                rs.getString("user_surname"),
                rs.getBoolean("user_gender"),
                rs.getString("user_role")
        );

        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("cat_name")
        );

        return new Motorcycle(
                rs.getInt("id"),
                owner,
                category,
                rs.getString("type"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getBoolean("is_available")
        );
    }
}
