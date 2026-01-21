package company.repositories;

import company.data.interfaces.IDB;
import company.models.Car;
import company.repositories.interfaces.ICarRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements ICarRepository {
    private final IDB db;

    public CarRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Car car) {
        String sql = "INSERT INTO cars(brand, model, year, price, is_available) VALUES (?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, car.getBrand());
            st.setString(2, car.getModel());
            st.setInt(3, car.getYear());
            st.setDouble(4, car.getPrice());
            st.setBoolean(5, car.isAvailable());

            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }


    private List<Car> getCarsByQuery(String sql) {
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available")
                ));
            }
            return cars;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Car> getAll() {
        return getCarsByQuery("SELECT * FROM cars");
    }

    @Override
    public List<Car> getAllSortedByPrice() {
        String sql = "SELECT * FROM cars ORDER BY price ASC";
        return getCarsByQuery(sql);
    }

    @Override
    public List<Car> getAllSortedByYear() {
        String sql = "SELECT * FROM cars ORDER BY year DESC";
        return getCarsByQuery(sql);
    }

    @Override
    public Car getById(int id) {
        String sql = "SELECT * FROM cars WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Car(rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("price"),
                        rs.getBoolean("is_available"));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Car car) {
        String sql = "UPDATE cars SET price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setDouble(1, car.getPrice());
            st.setBoolean(2, car.isAvailable());
            st.setInt(3, car.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM cars WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }
}