package company.controllers;

import company.models.Vehicle;
import company.repositories.interfaces.IVehicleRepository;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleController {
    private final IVehicleRepository vehicleRepository;

    public VehicleController(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public String addVehicle(int userId, int categoryId, String type, String brand, String model, int year, double price) {
        if (type == null || type.trim().isEmpty() || type.length() < 2) {
            return "ERROR: Type must be at least 2 characters!";
        }
        if (brand == null || brand.trim().isEmpty() || brand.length() < 2 || brand.length() > 100) {
            return "ERROR: Brand must be 2-100 characters!";
        }
        if (model == null || model.trim().isEmpty() || model.length() < 1 || model.length() > 100) {
            return "ERROR: Model must be 1-100 characters!";
        }
        int currentYear = java.time.Year.now().getValue();
        if (year < 1900 || year > currentYear) {
            return "ERROR: Year must be between 1900 and " + currentYear + "!";
        }
        if (price <= 0 || price > 1_000_000) {
            return "ERROR: Price must be between 0 and 1,000,000!";
        }

        if (UserController.currentUserRole == null ||
                (!UserController.currentUserRole.equals("seller") && !UserController.currentUserRole.equals("admin"))) {
            return "ERROR: Access denied! Only sellers and admins can add vehicles.";
        }

        Vehicle vehicle = new Vehicle(0, null, null, type, brand, model, year, price, true);
        return vehicleRepository.addVehicle(vehicle) ?
                "SUCCESS: Vehicle added!" : "ERROR: Failed to add vehicle.";
    }

    public String getVehicleById(int id) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        Vehicle vehicle = vehicleRepository.getVehicleById(id);
        return vehicle == null ? "ERROR: Vehicle not found!" : "SUCCESS: " + vehicle.toString();
    }

    public String getAllAvailableVehicles() {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Vehicle> vehicles = vehicleRepository.getAllVehiclesWithDetails();
        if (vehicles == null || vehicles.isEmpty()) return "ERROR: No vehicles found.";

        String result = vehicles.stream()
                .filter(Vehicle::isAvailable)
                .map(v -> "   " + v.toString())
                .collect(Collectors.joining("\n", "SUCCESS: Available Vehicles:\n", ""));

        return result.equals("SUCCESS: Available Vehicles:\n") ? "ERROR: No available vehicles." : result;
    }

    public String getVehiclesByPriceRange(double minPrice, double maxPrice) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Vehicle> vehicles = vehicleRepository.getAllVehiclesWithDetails();
        if (vehicles == null) return "ERROR: No vehicles found.";

        String result = vehicles.stream()
                .filter(v -> v.getPrice() >= minPrice && v.getPrice() <= maxPrice)
                .sorted((v1, v2) -> Double.compare(v1.getPrice(), v2.getPrice()))
                .map(v -> "   " + v.toString())
                .collect(Collectors.joining("\n", "SUCCESS: Vehicles between " + minPrice + " and " + maxPrice + ":\n", ""));

        return result.isEmpty() ? "ERROR: No vehicles in this price range." : result;
    }

    public String getAllVehicles() {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Vehicle> vehicles = vehicleRepository.getAllVehiclesWithDetails();
        if (vehicles == null || vehicles.isEmpty()) return "ERROR: No vehicles found.";
        StringBuilder response = new StringBuilder("SUCCESS: All Vehicles:\n");
        for (Vehicle v : vehicles) {
            response.append("   ").append(v.toString()).append("\n");
        }
        return response.toString();
    }

    public String getVehiclesByCategory(int categoryId) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByCategoryId(categoryId);
        if (vehicles == null || vehicles.isEmpty()) return "ERROR: No vehicles in this category.";
        StringBuilder response = new StringBuilder("SUCCESS: Vehicles by Category:\n");
        for (Vehicle v : vehicles) {
            response.append("   ").append(v.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateVehicleAvailability(int vehicleId, boolean available) {
        if (UserController.currentUserRole == null ||
                (!UserController.currentUserRole.equals("admin") && !UserController.currentUserRole.equals("seller"))) {
            return "ERROR: Access denied! Only admins and sellers can update vehicles.";
        }
        return vehicleRepository.updateVehicleAvailability(vehicleId, available) ?
                "SUCCESS: Vehicle availability updated!" : "ERROR: Failed to update.";
    }

    public String deleteVehicle(int id) {
        if (UserController.currentUserRole == null || !UserController.currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can delete vehicles.";
        }
        return vehicleRepository.deleteVehicle(id) ?
                "SUCCESS: Vehicle deleted!" : "ERROR: Failed to delete.";
    }
}
