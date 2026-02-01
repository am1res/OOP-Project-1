package company.repositories.interfaces;

import company.models.Motorcycle;
import company.models.SpecialVehicle;

import java.util.List;

public interface ISpecialVehicleRepository {
    boolean add(SpecialVehicle specialVehicle);
    List<SpecialVehicle> getAll();
    SpecialVehicle getById(int id);
    boolean update(SpecialVehicle specialVehicle);
    boolean delete(int id);
    List<SpecialVehicle> getAllSortedByPrice();
    List<SpecialVehicle> getAllSortedByYear();

}
