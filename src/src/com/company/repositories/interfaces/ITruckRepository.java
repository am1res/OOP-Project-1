package com.company.repositories.interfaces;
import com.company.models.Truck;
import java.util.List;

public interface ITruckRepository {
    boolean add(Truck truck);
    List<Truck> getAll();
    Truck getById(int id);
    boolean update(Truck truck);
    boolean delete(int id);
}