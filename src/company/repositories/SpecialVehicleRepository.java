package company.repositories;

import company.data.interfaces.IDB;
import company.models.SpecialVehicle; //
import company.repositories.interfaces.ISpecialVehicleRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialVehicleRepository implements ISpecialVehicleRepository {
    private final IDB db;

    public SpecialVehicleRepository(IDB db) { this.db = db; }

    @Override
    public boolean add(SpecialVehicle vehicle) {
        String sql = "INSERT INTO special_vehicles(brand, model, year, price, is_available) VALUES (?,?,?,?,?)";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, vehicle.getBrand());
            st.setString(2, vehicle.getModel());
            st.setInt(3, vehicle.getYear());
            st.setDouble(4, vehicle.getPrice());
            st.setBoolean(5, vehicle.isAvailable());
            st.execute();
            return true;
        } catch (SQLException e) { return false; }
    }

    @Override
    public List<SpecialVehicle> getAll() {
        String sql = "SELECT * FROM special_vehicles";
        try (Connection con = db.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            List<SpecialVehicle> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new SpecialVehicle(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")));
            }
            return list;
        } catch (SQLException e) { return null; }
    }

    @Override
    public SpecialVehicle getById(int id) {
        String sql = "SELECT * FROM special_vehicles WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new SpecialVehicle(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"));
            }
        } catch (SQLException ignored) { }
        return null;
    }

    @Override
    public boolean update(SpecialVehicle vehicle) {
        String sql = "UPDATE special_vehicles SET price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, vehicle.getPrice());
            st.setBoolean(2, vehicle.isAvailable());
            st.setInt(3, vehicle.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM special_vehicles WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}