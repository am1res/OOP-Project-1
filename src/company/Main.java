package company;

import company.data.interfaces.PostgresDB;
import company.data.interfaces.IDB;
import company.repositories.*;
import company.repositories.interfaces.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Database configuration
            String dbUrl = "jdbc:postgresql://localhost:5432";
            String dbUser = "postgres";
            String dbPassword = "0000";
            String dbName = "kolesakz";

            System.out.println("Connecting to database...");
            PostgresDB db = PostgresDB.getInstance(dbUrl, dbUser, dbPassword, dbName);
            System.out.println("SUCCESS: Database connected!\n");

            // Initialize repositories directly (no factory needed)
            System.out.println("Initializing repositories...");
            INewUserRepository userRepo = new NewUserRepository(db);
            ICategoryRepository categoryRepo = new CategoryRepository(db);
            IRoleRepository roleRepo = new RoleRepository(db);
            IVehicleRepository vehicleRepo = new VehicleRepository(db) {};
            System.out.println("SUCCESS: All repositories initialized!\n");

            // Start application
            MyApplication app = new MyApplication(
                    db,
                    userRepo,
                    categoryRepo,
                    roleRepo,
                    vehicleRepo
            );

            app.start();

            // Cleanup
            System.out.println("\nClosing database connection...");
            db.close();
            System.out.println("SUCCESS: Database connection closed.");

        } catch (Exception e) {
            System.err.println("\nERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
