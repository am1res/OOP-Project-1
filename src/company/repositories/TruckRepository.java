package company.repositories;

import company.data.interfaces.IDB;
import company.models.Truck;
import company.models.Category;
import company.models.NewUser;
import company.repositories.interfaces.ITruckRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TruckRepository implements ITruckRepository {
    private final IDB db;

    public TruckRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Truck truck) {
        String sql = "INSERT INTO trucks(owner_id, category_id, type, brand, model, year, price, is_available) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, truck.getOwner().getId());
            st.setInt(2, truck.getCategory().getId());
            st.setString(3, truck.getType());
            st.setString(4, truck.getBrand());
            st.setString(5, truck.getModel());
            st.setInt(6, truck.getYear());
            st.setDouble(7, truck.getPrice());
            st.setBoolean(8, truck.isAvailable());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Truck getById(int id) {
        String sql = "SELECT t.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM trucks t " +
                "JOIN users u ON t.owner_id = u.id " +
                "JOIN categories cat ON t.category_id = cat.id " +
                "WHERE t.id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToSpecialTruck(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Truck> getAll() {
        return getTrucksByQuery("SELECT t.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM trucks t  " +
                "JOIN users u ON t.owner_id = u.id " +
                "JOIN categories cat ON t.category_id = cat.id");
    }

    @Override
    public List<Truck> getAllSortedByPrice() {
        return getTrucksByQuery("SELECT t.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM trucks t" +
                "JOIN users u ON t.owner_id = u.id " +
                "JOIN categories cat ON t.category_id = cat.id " +
                "ORDER BY t.price ASC");
    }

    @Override
    public List<Truck> getAllSortedByYear() {
        return getTrucksByQuery("SELECT t.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM trucks t" +
                "JOIN users u ON t.owner_id = u.id " +
                "JOIN categories cat ON t.category_id = cat.id " +
                "ORDER BY t.year DESC");
    }

    @Override
    public boolean update(Truck truck ) {
        String sql = "UPDATE trucks SET owner_id=?, category_id=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, truck.getOwner().getId());
            st.setInt(2, truck.getCategory().getId());
            st.setString(3, truck.getType());
            st.setDouble(4, truck.getPrice());
            st.setBoolean(5, truck.isAvailable());
            st.setInt(6, truck.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM trucks WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    private List<Truck> getTrucksByQuery(String sql) {
        List<Truck> buses = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                buses.add(mapResultSetToSpecialTruck(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return buses;
    }

    private Truck mapResultSetToSpecialTruck(ResultSet rs) throws SQLException {
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

        return new Truck(
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
