package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;

import java.util.List;

public class TruckController {
    private final ITruckRepository repo;

    public TruckController(ITruckRepository repo) {
        this.repo = repo;
    }

    public String addTruck(String brand, String model, int year, double price, boolean available) {
        Truck truck = new Truck(0, brand, model, year, price, available);
        return repo.add(truck) ? "✅ Truck added successfully!" : "❌ Failed to add truck.";
    }

    public String getTruckById(int id) {
        Truck truck = repo.getById(id);
        return truck == null ? "❌ Truck not found!" : "✅ " + truck.toString();
    }

    public String getAllTrucks() {
        List<Truck> trucks = repo.getAll();
        if (trucks == null || trucks.isEmpty()) return "❌ No trucks found.";
        StringBuilder response = new StringBuilder("✅ All Trucks:\n");
        for (Truck truck : trucks) {
            response.append("   ").append(truck.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateTruck(int id, double price, boolean available) {
        Truck truck = repo.getById(id);
        if (truck == null) return "❌ Truck not found!";
        Truck updated = new Truck(id, truck.getBrand(), truck.getModel(), truck.getYear(), price, available);
        return repo.update(updated) ? "✅ Truck updated successfully!" : "❌ Failed to update truck.";
    }

    public String deleteTruck(int id) {
        return repo.delete(id) ? "✅ Truck deleted successfully!" : "❌ Failed to delete truck.";
    }
}
