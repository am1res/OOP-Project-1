package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;

import java.util.List;

public class SpecialVehicleController {
    private final ISpecialVehicleRepository repo;

    public SpecialVehicleController(ISpecialVehicleRepository repo) {
        this.repo = repo;
    }

    public String addSpecialVehicle(String brand, String model, int year, double price, boolean available) {
        SpecialVehicle vehicle = new SpecialVehicle(0, brand, model, year, price, available);
        return repo.add(vehicle) ? "✅ Special Vehicle added successfully!" : "❌ Failed to add special vehicle.";
    }

    public String getSpecialVehicleById(int id) {
        SpecialVehicle vehicle = repo.getById(id);
        return vehicle == null ? "❌ Special Vehicle not found!" : "✅ " + vehicle.toString();
    }

    public String getAllSpecialVehicles() {
        List<SpecialVehicle> vehicles = repo.getAll();
        if (vehicles == null || vehicles.isEmpty()) return "❌ No special vehicles found.";
        StringBuilder response = new StringBuilder("✅ All Special Vehicles:\n");
        for (SpecialVehicle v : vehicles) {
            response.append("   ").append(v.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateSpecialVehicle(int id, double price, boolean available) {
        SpecialVehicle vehicle = repo.getById(id);
        if (vehicle == null) return "❌ Special Vehicle not found!";
        SpecialVehicle updated = new SpecialVehicle(id, vehicle.getBrand(), vehicle.getModel(), vehicle.getYear(), price, available);
        return repo.update(updated) ? "✅ Special Vehicle updated successfully!" : "❌ Failed to update special vehicle.";
    }

    public String deleteSpecialVehicle(int id) {
        return repo.delete(id) ? "✅ Special Vehicle deleted successfully!" : "❌ Failed to delete special vehicle.";
    }
}
