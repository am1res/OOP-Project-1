package company.repositories;

import company.data.interfaces.IDB;
import company.models.Bus;
import company.repositories.interfaces.IBusRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusRepository implements IBusRepository {
    private final IDB db;

    public BusRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Bus bus) {
        String sql = "INSERT INTO cars(brand, model, year, price, is_available) VALUES (?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, bus.getBrand());
            st.setString(2, bus.getModel());
            st.setInt(3, bus.getYear());
            st.setDouble(4, bus.getPrice());
            st.setBoolean(5, bus.isAvailable());

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Bus> getAll() {
        String sql = "SELECT * FROM cars";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            List<Bus> cars = new ArrayList<>();
            while (rs.next()) {
                cars.add(new Bus(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")));
            }
            return cars;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Bus getById(int id) {
        String sql = "SELECT * FROM buses WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Bus(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Bus bus) {
        String sql = "UPDATE buses SET price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, bus.getPrice());
            st.setBoolean(2, bus.isAvailable());
            st.setInt(3, bus.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
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
            System.out.println("sql error: " + e.getMessage());
        }
        return false;
    }
}