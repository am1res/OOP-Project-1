package company.repositories;

import company.data.interfaces.IDB;
import company.models.Category;
import company.models.NewUser;
import company.models.Vehicle;
import company.repositories.interfaces.IVehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository  implements IVehicleRepository {
    private final IDB db;

    public VehicleRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        return addVehicle(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehiclesWithDetails() {
        return getAllVehiclesWithDetails();
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT v.*, u.name as user_name, u.surname as user_surname, u.gender, u.role, " +
                "c.name as category_name " +
                "FROM vehicles v " +
                "JOIN users u ON v.user_id = u.id " +
                "JOIN categories c ON v.category_id = c.id " +
                "WHERE v.id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return createVehicleFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Vehicle> getVehiclesByCategoryId(int categoryId) {
        String sql = "SELECT v.*, u.name as user_name, u.surname as user_surname, u.gender, u.role, " +
                "c.name as category_name " +
                "FROM vehicles v JOIN users u ON v.user_id = u.id " +
                "JOIN categories c ON v.category_id = c.id " +
                "WHERE v.category_id = ?";

        List<Vehicle> list = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, categoryId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(createVehicleFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean updateVehicleAvailability(int vehicleId, boolean available) {
        String sql = "UPDATE vehicles SET is_available = ? WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setBoolean(1, available);
            st.setInt(2, vehicleId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteVehicle(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }


    private Vehicle createVehicleFromResultSet(ResultSet rs) throws SQLException {
        NewUser owner = new NewUser(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("user_surname"),
                rs.getBoolean("gender"),
                rs.getString("role")
        );

        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("category_name")
        );

        return new Vehicle(
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
