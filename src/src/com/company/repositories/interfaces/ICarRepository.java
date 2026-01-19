package com.company.repositories.interfaces;

import com.company.models.Car;
import java.util.List;

public interface ICarRepository {
    boolean add(Car car);
    List<Car> getAll();
    Car getById(int id);
    boolean update(Car car);
    boolean delete(int id);
}