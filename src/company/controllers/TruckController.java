package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;
import java.util.List;

public class TruckController {
    private final ITruckRepository repo;
    private final IUserRepository userRepo;
    private final ICategoryRepository categoryRepo;

    public TruckController(ITruckRepository repo, IUserRepository userRepo, ICategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public String addTruck(int ownerId, int categoryId, String brand, String model, int year, double price, boolean available) {
        NewUser owner = userRepo.getById(ownerId);
        if (owner == null) return "❌ Owner not found!";

        Category category = categoryRepo.getById(categoryId);
        if (category == null) return "❌ Category not found!";

        Truck truck = new Truck(0, owner, category, brand, model, year, price, available);
        return repo.add(truck) ? "✅ Truck added successfully!" : "❌ Failed to add truck.";
    }

    public String getTruckById(int id) {
        Truck t = repo.getById(id);
        return t == null ? "❌ Truck not found!" : "✅ " + t.toString();
    }

    public String getAllTrucks() {
        List<Truck> trucks = repo.getAll();
        if (trucks == null || trucks.isEmpty()) return "❌ No trucks found.";
        StringBuilder response = new StringBuilder("✅ All Trucks:\n");
        for (Truck t : trucks) {
            response.append("   ").append(t.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateTruck(int id, double price, boolean available) {
        Truck t = repo.getById(id);
        if (t == null) return "❌ Truck not found!";
        Truck updated = new Truck(id, t.getOwner(), t.getCategory(), t.getBrand(), t.getModel(), t.getYear(), price, available);
        return repo.update(updated) ? "✅ Truck updated successfully!" : "❌ Failed to update truck.";
    }

    public String deleteTruck(int id) {
        return repo.delete(id) ? "✅ Truck deleted successfully!" : "❌ Failed to delete truck.";
    }
}
