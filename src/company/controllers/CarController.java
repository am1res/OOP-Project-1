package company.controllers;

import company.models.*;
import company.repositories.interfaces.*;

import company.models.Car;
import company.repositories.interfaces.ICarRepository;

import java.util.List;

public class CarController {
    private final ICarRepository repo;

    public CarController(ICarRepository repo) {
        this.repo = repo;
    }

    public String addCar(String brand, String model, int year, double price, boolean available) {
        Car car = new Car(0, brand, model, year, price, available);
        return repo.add(car) ? "✅ Car added successfully!" : "❌ Failed to add car.";
    }

    public String getCarById(int id) {
        Car car = repo.getById(id);
        return car == null ? "❌ Car not found!" : "✅ " + car.toString();
    }


    public String getAllCars() {
        return formatCarList(repo.getAll(), "All Cars");
    }

    public String getAllCarsSortedByPrice() {
        return formatCarList(repo.getAllSortedByPrice(), "Cars sorted by Price (Cheapest first)");
    }

    public String getAllCarsSortedByYear() {
        return formatCarList(repo.getAllSortedByYear(), "Cars sorted by Year (Newest first)");
    }

    private String formatCarList(List<Car> cars, String title) {
        if (cars == null || cars.isEmpty()) return "❌ No cars found.";

        StringBuilder response = new StringBuilder("✅ " + title + ":\n");
        for (Car car : cars) {
            response.append("   ").append(car.toString()).append("\n");
        }
        return response.toString();
    }

    public String updateCar(int id, double price, boolean available) {
        Car car = new Car(id, "", "", 0, price, available);
        return repo.update(car) ? "✅ Car updated!" : "❌ Update failed.";
    }

    public String deleteCar(int id) {
        return repo.delete(id) ? "✅ Car deleted!" : "❌ Delete failed.";
    }
}