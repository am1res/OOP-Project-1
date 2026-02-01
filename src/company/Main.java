package company;

import company.data.PostgresDB;
import company.data.interfaces.IDB;

import company.models.Car;
import company.repositories.*;
import company.repositories.interfaces.*;
import company.controllers.UserController;
import company.controllers.interfaces.IUserController;

import java.util.List;

/**
 * Main entry point for Vehicle Management System
 * Initializes all repositories and starts the application
 */
public class  Main {

    public static void main(String[] args) {
        try {
            // ===== DATABASE CONFIGURATION =====
            String dbUrl = "jdbc:postgresql://localhost:5433";
            String dbUser = "postgres";
            String dbPassword = "0000";
            String dbName = "kolesa_db";

            System.out.println("🔌 Connecting to database...");
            PostgresDB db = new PostgresDB(dbUrl, dbUser, dbPassword, dbName);
            System.out.println("✅ Database connected successfully!\n");

            // ===== INITIALIZE ALL REPOSITORIES =====
            System.out.println("📦 Initializing repositories...");
            IUserRepository userRepo = new UserRepository(db);
            ICarRepository carRepo = new CarRepository(db) {
                @Override
                public boolean update(Car car) {
                    return false;
                }

                @Override
                public boolean delete(int id) {
                    return false;
                }

                @Override
                public List<Car> getAllSortedByPrice() {
                    return List.of();
                }

                @Override
                public List<Car> getAllSortedByYear() {
                    return List.of();
                }
            };
            IMotorcycleRepository motorcycleRepo = new MotorcycleRepository(db);
            BusRepository busRepo = new BusRepository(db) {
                @Override
                public boolean add(Car car) {
                    return false;
                }

                @Override
                public boolean update(Car car) {
                    return false;
                }
            };
            ITruckRepository truckRepo = new TruckRepository(db);
            ISpecialVehicleRepository specialVehicleRepo = new SpecialVehicleRepository(db);
            System.out.println("✅ All repositories initialized!\n");

            // ===== START APPLICATION =====
            MyApplication app = new MyApplication(
                    (IDB) db,
                    userRepo,
                    carRepo,
                    motorcycleRepo,
                    (IBusRepository) busRepo,
                    truckRepo,
                    specialVehicleRepo
            );

            app.start();

            // ===== CLEANUP =====
            System.out.println("\n🔌 Closing database connection...");
            db.close();
            System.out.println("✅ Database connection closed.");

        } catch (Exception e) {
            System.err.println("\n❌ FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}