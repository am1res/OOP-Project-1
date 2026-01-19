package company.repositories;

import company.data.interfaces.IDB;
import company.models.Motorcycle; //
import company.repositories.interfaces.IMotorcycleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotorcycleRepository implements IMotorcycleRepository {
    private final IDB db;

    public MotorcycleRepository(IDB db) { this.db = db; }

    @Override
    public boolean add(Motorcycle moto) {
        String sql = "INSERT INTO motorcycles(brand, model, year, price, is_available) VALUES (?,?,?,?,?)";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, moto.getBrand());
            st.setString(2, moto.getModel());
            st.setInt(3, moto.getYear());
            st.setDouble(4, moto.getPrice());
            st.setBoolean(5, moto.isAvailable());
            st.execute();
            return true;
        } catch (SQLException e) { System.out.println("sql error: " + e.getMessage()); }
        return false;
    }

    @Override
    public List<Motorcycle> getAll() {
        String sql = "SELECT * FROM motorcycles";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            List<Motorcycle> motos = new ArrayList<>();
            while (rs.next()) {
                motos.add(new Motorcycle(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")));
            }
            return motos;
        } catch (SQLException e) { return null; }
    }

    @Override
    public Motorcycle getById(int id) {
        String sql = "SELECT * FROM motorcycles WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Motorcycle(rs.getInt("id"),
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
    public boolean update(Motorcycle moto) {
        String sql = "UPDATE motorcycles SET price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, moto.getPrice());
            st.setBoolean(2, moto.isAvailable());
            st.setInt(3, moto.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM motorcycles WHERE id=?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}