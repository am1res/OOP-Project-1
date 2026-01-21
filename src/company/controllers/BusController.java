package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;

import java.util.List;

public class BusController {
    private final IBusRepository repo;

    public BusController(IBusRepository repo) {
        this.repo = repo;
    }

    public String addBus(String brand, String model, int year, double price, boolean available) {
        Bus bus = new Bus(0, brand, model, year, price, available);
        return repo.add(bus) ? "✅ Bus added successfully!" : "❌ Failed to add bus.";
    }

    public String getBusById(int id) {
        Bus bus = repo.getById(id);
        return bus == null ? "❌ Bus not found!" : "✅ " + bus.toString();
    }

    public String getAllBuses() {
        List<Bus> buses = repo.getAll();
        if (buses == null || buses.isEmpty()) return "❌ No buses found.";
        StringBuilder response = new StringBuilder("✅ All Buses:\n");
        for (Bus bus : buses) {
            response.append("   ").append(bus.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateBus(int id, double price, boolean available) {
        Bus bus = repo.getById(id);
        if (bus == null) return "❌ Bus not found!";
        Bus updated = new Bus(id, bus.getBrand(), bus.getModel(), bus.getYear(), price, available);
        return repo.update(updated) ? "✅ Bus updated successfully!" : "❌ Failed to update bus.";
    }

    public String deleteBus(int id) {
        return repo.delete(id) ? "✅ Bus deleted successfully!" : "❌ Failed to delete bus.";
    }
}
