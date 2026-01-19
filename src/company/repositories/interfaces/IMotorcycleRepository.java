package company.repositories.interfaces;
import company.models.Motorcycle;

import java.util.List;

public interface IMotorcycleRepository {
    boolean add(Motorcycle motorcycle);
    List<Motorcycle> getAll();
    Motorcycle getById(int id);
    boolean update(Motorcycle motorcycle);
    boolean delete(int id);
}