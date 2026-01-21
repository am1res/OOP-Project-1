package company.repositories.interfaces;

import company.models.Car;
import java.util.List;

public interface ICarRepository {
    boolean add(Car car);
    List<Car> getAll();
    Car getById(int id);
    boolean update(Car car);
    boolean delete(int id);
    List<Car> getAllSortedByPrice();
    List<Car> getAllSortedByYear();
}