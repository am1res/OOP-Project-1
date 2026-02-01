package company.repositories;

import company.data.interfaces.IDB;
import company.models.Car;
import company.models.Category;
import company.models.NewUser;
import company.repositories.interfaces.ICarRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class CarRepository implements ICarRepository {
    private final IDB db;

    public CarRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(Car car) {
        String sql = "INSERT INTO cars(owner_id, category_id, type, brand, model, year, price, is_available) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, car.getOwner().getId());
            st.setInt(2, car.getCategory().getId());
            st.setString(3, car.getType());
            st.setString(4, car.getBrand());
            st.setString(5, car.getModel());
            st.setInt(6, car.getYear());
            st.setDouble(7, car.getPrice());
            st.setBoolean(8, car.isAvailable());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Car getById(int id) {
        String sql = "SELECT c.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM cars c " +
                "JOIN users u ON c.owner_id = u.id " +
                "JOIN categories cat ON c.category_id = cat.id " +
                "WHERE c.id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToCar(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Car> getAll() {
        return getCarsByQuery("SELECT c.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM cars c " +
                "JOIN users u ON c.owner_id = u.id " +
                "JOIN categories cat ON c.category_id = cat.id");
    }

    @Override
    public List<Car> getAllSortedByPrice() {
        return getCarsByQuery("SELECT c.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM cars c " +
                "JOIN users u ON c.owner_id = u.id " +
                "JOIN categories cat ON c.category_id = cat.id " +
                "ORDER BY c.price ASC");
    }

    @Override
    public List<Car> getAllSortedByYear() {
        return getCarsByQuery("SELECT c.*, u.name as user_name, u.surname as user_surname, cat.name as cat_name " +
                "FROM cars c " +
                "JOIN users u ON c.owner_id = u.id " +
                "JOIN categories cat ON c.category_id = cat.id " +
                "ORDER BY c.year DESC");
    }

    @Override
    public boolean update(Car car) {
        String sql = "UPDATE cars SET owner_id=?, category_id=?, type=?, price=?, is_available=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, car.getOwner().getId());
            st.setInt(2, car.getCategory().getId());
            st.setString(3, car.getType());
            st.setDouble(4, car.getPrice());
            st.setBoolean(5, car.isAvailable());
            st.setInt(6, car.getId());
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

    private List<Car> getCarsByQuery(String sql) {
        List<Car> cars = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return cars;
    }

    private Car mapResultSetToCar(ResultSet rs) throws SQLException {
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

        return new Car(
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