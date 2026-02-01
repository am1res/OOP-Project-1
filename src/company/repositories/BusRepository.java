package company.repositories;

import company.data.interfaces.IDB;
import company.models.Bus;
import company.models.Category;
import company.models.NewUser;
import company.repositories.interfaces.IBusRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class BusRepository implements IBusRepository {
    private final IDB db;

    public BusRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Bus bus) {
        String sql = "INSERT INTO buses(owner_id, category_id, type, brand, model, year, price, is_available) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, bus.getOwner().getId());
            st.setInt(2, bus.getCategory().getId());
            st.setString(3, bus.getType());
            st.setString(4, bus.getBrand());
            st.setString(5, bus.getModel());
            st.setInt(6, bus.getYear());
            st.setDouble(7, bus.getPrice());
            st.setBoolean(8, bus.isAvailable());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Bus getById(int id) {
        String sql = "SELECT b.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM buses b " +
                "JOIN users u ON b.owner_id = u.id " +
                "JOIN categories cat ON c.category_id = cat.id " +
                "WHERE b.id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToBus(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Bus> getAll() {
        return getBusesByQuery("SELECT b.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM buses b " +
                "JOIN users u ON b.owner_id = u.id " +
                "JOIN categories cat ON b.category_id = cat.id");
    }

    @Override
    public List<Bus> getAllSortedByPrice() {
        return getBusesByQuery("SELECT b.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM buses b " +
                "JOIN users u ON b.owner_id = u.id " +
                "JOIN categories cat ON b.category_id = cat.id " +
                "ORDER BY b.price ASC");
    }

    @Override
    public List<Bus> getAllSortedByYear() {
        return getBusesByQuery("SELECT b.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM buses b " +
                "JOIN users u ON b.owner_id = u.id " +
                "JOIN categories cat ON b.category_id = cat.id " +
                "ORDER BY b.year DESC");
    }

    @Override
    public boolean update(Bus bus) {
        String sql = "UPDATE buses SET owner_id=?, category_id=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, bus.getOwner().getId());
            st.setInt(2, bus.getCategory().getId());
            st.setString(3, bus.getType());
            st.setDouble(4, bus.getPrice());
            st.setBoolean(5, bus.isAvailable());
            st.setInt(6, bus.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM buses WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    private List<Bus> getBusesByQuery(String sql) {
        List<Bus> buses = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                buses.add(mapResultSetToBus(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return buses;
    }

    private Bus mapResultSetToBus(ResultSet rs) throws SQLException {
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

        return new Bus(
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
