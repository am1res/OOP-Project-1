package company.repositories.interfaces;

import company.models.Vehicle;
import java.util.List;

public interface IVehicleRepository {
    boolean add(Vehicle vehicle);
    boolean addVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehiclesWithDetails();
    Vehicle getVehicleById(int id);
    List<Vehicle> getVehiclesByCategoryId(int categoryId);
}