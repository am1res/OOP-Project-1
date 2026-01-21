package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;

import java.util.List;

public class MotorcycleController {
    private final IMotorcycleRepository repo;

    public MotorcycleController(IMotorcycleRepository repo) {
        this.repo = repo;
    }

    public String addMotorcycle(String brand, String model, int year, double price, boolean available) {
        Motorcycle motorcycle = new Motorcycle(0, brand, model, year, price, available);
        return repo.add(motorcycle) ? "✅ Motorcycle added successfully!" : "❌ Failed to add motorcycle.";
    }

    public String getMotorcycleById(int id) {
        Motorcycle motorcycle = repo.getById(id);
        return motorcycle == null ? "❌ Motorcycle not found!" : "✅ " + motorcycle.toString();
    }

    public String getAllMotorcycles() {
        List<Motorcycle> motorcycles = repo.getAll();
        if (motorcycles == null || motorcycles.isEmpty()) return "❌ No motorcycles found.";
        StringBuilder response = new StringBuilder("✅ All Motorcycles:\n");
        for (Motorcycle motorcycle : motorcycles) {
            response.append("   ").append(motorcycle.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateMotorcycle(int id, double price, boolean available) {
        Motorcycle motorcycle = repo.getById(id);
        if (motorcycle == null) return "❌ Motorcycle not found!";
        Motorcycle updated = new Motorcycle(id, motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), price, available);
        return repo.update(updated) ? "✅ Motorcycle updated successfully!" : "❌ Failed to update motorcycle.";
    }

    public String deleteMotorcycle(int id) {
        return repo.delete(id) ? "✅ Motorcycle deleted successfully!" : "❌ Failed to delete motorcycle.";
    }
}
