package company.repositories;

import company.data.interfaces.IDB;
import company.models.Truck; //
import company.repositories.interfaces.ITruckRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TruckRepository implements ITruckRepository {
    private final IDB db;

    public TruckRepository(IDB db) { this.db = db; }

    @Override
    public boolean add(Truck truck) {
        String sql = "INSERT INTO trucks(brand, model, year, price, is_available) VALUES (?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, truck.getBrand());
            st.setString(2, truck.getModel());
            st.setInt(3, truck.getYear());
            st.setDouble(4, truck.getPrice());
            st.setBoolean(5, truck.isAvailable());
            st.execute();
            return true;
        } catch (SQLException e) { System.out.println("sql error: " + e.getMessage()); }
        return false;
    }

    @Override
    public List<Truck> getAll() {
        String sql = "SELECT * FROM trucks";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            List<Truck> trucks = new ArrayList<>();
            while (rs.next()) {
                trucks.add(new Truck(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")));
            }
            return trucks;
        } catch (SQLException e) { System.out.println("sql error: " + e.getMessage()); }
        return null;
    }

    @Override
    public Truck getById(int id) {
        String sql = "SELECT * FROM trucks WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Truck(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"));
            }
        } catch (SQLException e) { System.out.println("sql error: " + e.getMessage()); }
        return null;
    }

    @Override
    public boolean update(Truck truck) {
        String sql = "UPDATE trucks SET price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, truck.getPrice());
            st.setBoolean(2, truck.isAvailable());
            st.setInt(3, truck.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM trucks WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}