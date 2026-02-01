package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;
import java.util.List;

public class BusController {
    private final IBusRepository repo;
    private final IUserRepository userRepo;
    private final ICategoryRepository categoryRepo;

    public BusController(IBusRepository repo, IUserRepository userRepo, ICategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public String addBus(int ownerId, int categoryId, String brand, String model, int year, double price, boolean available) {
        NewUser owner = userRepo.getById(ownerId);
        if (owner == null) return "❌ Owner not found!";

        Category category = categoryRepo.getById(categoryId);
        if (category == null) return "❌ Category not found!";

        Bus bus = new Bus(0, owner, category, brand, model, year, price, available);
        return repo.add(bus) ? "✅ Bus added successfully!" : "❌ Failed to add bus.";
    }

    public String getBusById(int id) {
        Bus b = repo.getById(id);
        return b == null ? "❌ Bus not found!" : "✅ " + b.toString();
    }

    public String getAllBuses() {
        List<Bus> buses = repo.getAll();
        if (buses == null || buses.isEmpty()) return "❌ No buses found.";
        StringBuilder response = new StringBuilder("✅ All Buses:\n");
        for (Bus b : buses) {
            response.append("   ").append(b.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateBus(int id, double price, boolean available) {
        Bus b = repo.getById(id);
        if (b == null) return "❌ Bus not found!";
        Bus updated = new Bus(id, b.getOwner(), b.getCategory(), b.getBrand(), b.getModel(), b.getYear(), price, available);
        return repo.update(updated) ? "✅ Bus updated successfully!" : "❌ Failed to update bus.";
    }

    public String deleteBus(int id) {
        return repo.delete(id) ? "✅ Bus deleted successfully!" : "❌ Failed to delete bus.";
    }
}
