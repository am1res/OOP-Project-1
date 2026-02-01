package company.repositories.interfaces;

import company.models.Bus;
import java.util.List;

public interface IBusRepository {
    boolean add(Bus bus);
    List<Bus> getAll();
    Bus getById(int id);
    boolean update(Bus bus);
    boolean delete(int id);
    List<Bus> getAllSortedByPrice();
    List<Bus> getAllSortedByYear();
}