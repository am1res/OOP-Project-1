package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;
import java.util.List;

public class MotorcycleController {
    private final IMotorcycleRepository repo;
    private final IUserRepository userRepo;
    private final ICategoryRepository categoryRepo;

    public MotorcycleController(IMotorcycleRepository repo, IUserRepository userRepo, ICategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public String addMotorcycle(int ownerId, int categoryId, String brand, String model, int year, double price, boolean available) {
        NewUser owner = userRepo.getById(ownerId);
        if (owner == null) return "❌ Owner not found!";

        Category category = categoryRepo.getById(categoryId);
        if (category == null) return "❌ Category not found!";

        Motorcycle motorcycle = new Motorcycle(0, owner, category, brand, model, year, price, available);
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
        for (Motorcycle m : motorcycles) {
            response.append("   ").append(m.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateMotorcycle(int id, double price, boolean available) {
        Motorcycle m = repo.getById(id);
        if (m == null) return "❌ Motorcycle not found!";
        Motorcycle updated = new Motorcycle(id, m.getOwner(), m.getCategory(), m.getBrand(), m.getModel(), m.getYear(), price, available);
        return repo.update(updated) ? "✅ Motorcycle updated successfully!" : "❌ Failed to update motorcycle.";
    }

    public String deleteMotorcycle(int id) {
        return repo.delete(id) ? "✅ Motorcycle deleted successfully!" : "❌ Failed to delete motorcycle.";
    }
}
