package company.repositories;

import company.data.interfaces.IDB;
import company.models.SpecialVehicle;
import company.models.Category;
import company.models.NewUser;
import company.repositories.interfaces.ISpecialVehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialVehicleRepository implements ISpecialVehicleRepository {
    private final IDB db;

    public SpecialVehicleRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(SpecialVehicle vehicle) {
        String sql = "INSERT INTO special_vehicles(owner_id, category_id, type, brand, model, year, price, is_available) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, vehicle.getOwner().getId());
            st.setInt(2, vehicle.getCategory().getId());
            st.setString(3, vehicle.getType());
            st.setString(4, vehicle.getBrand());
            st.setString(5, vehicle.getModel());
            st.setInt(6, vehicle.getYear());
            st.setDouble(7, vehicle.getPrice());
            st.setBoolean(8, vehicle.isAvailable());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public SpecialVehicle getById(int id) {
        String sql = "SELECT sv.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM special_vehicles sv " +
                "JOIN users u ON sv.owner_id = u.id " +
                "JOIN categories cat ON sv.category_id = cat.id " +
                "WHERE sv.id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToSpecialVehicle(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SpecialVehicle> getAll() {
        return getSpecialVehiclesByQuery("SELECT sv.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM special_vehicles sv " +
                "JOIN users u ON sv.owner_id = u.id " +
                "JOIN categories cat ON sv.category_id = cat.id");
    }

    @Override
    public List<SpecialVehicle> getAllSortedByPrice() {
        return getSpecialVehiclesByQuery("SELECT sv.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM special_vehicles sv" +
                "JOIN users u ON sv.owner_id = u.id " +
                "JOIN categories cat ON sv.category_id = cat.id " +
                "ORDER BY sv.price ASC");
    }

    @Override
    public List<SpecialVehicle> getAllSortedByYear() {
        return getSpecialVehiclesByQuery("SELECT sv.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM special_vehicles sv" +
                "JOIN users u ON sv.owner_id = u.id " +
                "JOIN categories cat ON sv.category_id = cat.id " +
                "ORDER BY sv.year DESC");
    }

    @Override
    public boolean update(SpecialVehicle  vehicle ) {
        String sql = "UPDATE special_vehicles SET owner_id=?, category_id=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, vehicle.getOwner().getId());
            st.setInt(2, vehicle.getCategory().getId());
            st.setString(3, vehicle.getType());
            st.setDouble(4, vehicle.getPrice());
            st.setBoolean(5, vehicle.isAvailable());
            st.setInt(6, vehicle.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM special_vehicles WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    private List<SpecialVehicle> getSpecialVehiclesByQuery(String sql) {
        List<SpecialVehicle> buses = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                buses.add(mapResultSetToSpecialVehicle(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return buses;
    }

    private SpecialVehicle mapResultSetToSpecialVehicle(ResultSet rs) throws SQLException {
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

        return new SpecialVehicle(
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
