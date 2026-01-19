package com.company.repositories.interfaces;

import com.company.models.Bus;
import java.util.List;

public interface IBusRepository {
    boolean add(Bus bus);
    List<Bus> getAll();
    Bus getById(int id);
    boolean update(Bus bus);
    boolean delete(int id);
}