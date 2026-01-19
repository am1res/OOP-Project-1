package com.company.repositories.interfaces;

import com.company.models.SpecialVehicle;

import java.util.List;

public interface ISpecialVehicleRepository {
    boolean add(SpecialVehicle specialVehicle);
    List<SpecialVehicle> getAll();
    SpecialVehicle getById(int id);
    boolean update(SpecialVehicle specialVehicle);
    boolean delete(int id);
}
