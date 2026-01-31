package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;
import java.util.List;

public class SpecialVehicleController {
    private final ISpecialVehicleRepository repo;
    private final IUserRepository userRepo;
    private final ICategoryRepository categoryRepo;

    public SpecialVehicleController(ISpecialVehicleRepository repo, IUserRepository userRepo, ICategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    public String addSpecialVehicle(int ownerId, int categoryId, String brand, String model, int year, double price, boolean available) {
        NewUser owner = userRepo.getById(ownerId);
        if (owner == null) return "❌ Owner not found!";

        Category category = categoryRepo.getById(categoryId);
        if (category == null) return "❌ Category not found!";

        SpecialVehicle vehicle = new SpecialVehicle(0, owner, category, brand, model, year, price, available);
        return repo.add(vehicle) ? "✅ Special Vehicle added successfully!" : "❌ Failed to add special vehicle.";
    }

    public String getSpecialVehicleById(int id) {
        SpecialVehicle v = repo.getById(id);
        return v == null ? "❌ Special Vehicle not found!" : "✅ " + v.toString();
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
        SpecialVehicle v = repo.getById(id);
        if (v == null) return "❌ Special Vehicle not found!";
        SpecialVehicle updated = new SpecialVehicle(id, v.getOwner(), v.getCategory(), v.getBrand(), v.getModel(), v.getYear(), price, available);
        return repo.update(updated) ? "✅ Special Vehicle updated successfully!" : "❌ Failed to update special vehicle.";
    }

    public String deleteSpecialVehicle(int id) {
        return repo.delete(id) ? "✅ Special Vehicle deleted successfully!" : "❌ Failed to delete special vehicle.";
    }
}
