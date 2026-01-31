package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;
import java.util.List;

public class CarController {
    private final ICarRepository repo;
    private final IUserRepository userRepo;       // для поиска владельца
    private final ICategoryRepository categoryRepo; // для поиска категории

    public CarController(ICarRepository repo, IUserRepository userRepo, ICategoryRepository categoryRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    // Добавление машины с owner и category
    public String addCar(int ownerId, int categoryId, String type, String brand, String model, int year, double price, boolean available) {
        NewUser owner = userRepo.getById(ownerId);
        if (owner == null) return "❌ Owner not found!";

        Category category = categoryRepo.getById(categoryId);
        if (category == null) return "❌ Category not found!";

        Car car = new Car(0, owner, category, type, brand, model, year, price, available);
        return repo.add(car) ? "✅ Car added successfully!" : "❌ Failed to add car.";
    }

    public String getCarById(int id) {
        Car car = repo.getById(id);
        return car == null ? "❌ Car not found!" : "✅ " + car.toString();
    }

    public String getAllCars() {
        List<Car> cars = repo.getAll();
        if (cars == null || cars.isEmpty()) return "❌ No cars found.";
        StringBuilder response = new StringBuilder("✅ All Cars:\n");
        for (Car car : cars) {
            response.append("   ").append(car.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateCar(int id, double price, boolean available) {
        Car car = repo.getById(id);
        if (car == null) return "❌ Car not found!";
        Car updated = new Car(id, car.getOwner(), car.getCategory(), car.getType(), car.getBrand(), car.getModel(), car.getYear(), price, available);
        return repo.update(updated) ? "✅ Car updated successfully!" : "❌ Failed to update car.";
    }

    public String deleteCar(int id) {
        return repo.delete(id) ? "✅ Car deleted successfully!" : "❌ Failed to delete car.";
    }
}
