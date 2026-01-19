package company;

import company.controllers.UserController;
import company.controllers.CarController;
import company.controllers.interfaces.IUserController;
import company.data.interfaces.IDB;
import company.repositories.interfaces.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IDB db;

    // Controllers
    private final UserController userController;
    private final CarController carController;

    // Repositories
    private final IUserRepository userRepo;
    private final ICarRepository carRepo;
    private final IMotorcycleRepository motorcycleRepo;
    private final IBusRepository busRepo;
    private final ITruckRepository truckRepo;
    private final ISpecialVehicleRepository specialVehicleRepo;

    public MyApplication(
            IDB db,
            IUserRepository userRepo,
            ICarRepository carRepo,
            IMotorcycleRepository motorcycleRepo,
            IBusRepository busRepo,
            ITruckRepository truckRepo,
            ISpecialVehicleRepository specialVehicleRepo
    ) {
        this.db = db;
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.motorcycleRepo = motorcycleRepo;
        this.busRepo = busRepo;
        this.truckRepo = truckRepo;
        this.specialVehicleRepo = specialVehicleRepo;

        // Initialize controllers
        this.userController = new UserController(userRepo);
        this.carController = new CarController(carRepo);
    }

    private void mainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     🚗 VEHICLE MANAGEMENT SYSTEM 🚗");
        System.out.println("=".repeat(50));
        System.out.println("1. 👤 User Management");
        System.out.println("2. 🚗 Car Management");
        System.out.println("3. 🏍️  Motorcycle Management");
        System.out.println("4. 🚌 Bus Management");
        System.out.println("5. 🚚 Truck Management");
        System.out.println("6. ⚙️  Special Vehicle Management");
        System.out.println("0. ❌ Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter option (0-6): ");
    }

    public void start() {
        boolean running = true;
        while (running) {
            mainMenu();
            try {
                int option = getIntInput();
                switch (option) {
                    case 1:
                        userManagementMenu();
                        break;
                    case 2:
                        carManagementMenu();
                        break;
                    case 3:
                        motorcycleManagementMenu();
                        break;
                    case 4:
                        busManagementMenu();
                        break;
                    case 5:
                        truckManagementMenu();
                        break;
                    case 6:
                        specialVehicleManagementMenu();
                        break;
                    case 0:
                        System.out.println("\n✅ Thank you for using Vehicle Management System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ==================== USER MANAGEMENT ====================
    private void userManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- 👤 USER MANAGEMENT ---");
            System.out.println("1. ➕ Create User");
            System.out.println("2. 🔍 Get User by ID");
            System.out.println("3. 📋 Get All Users");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        createUserMenu();
                        break;
                    case 2:
                        getUserByIdMenu();
                        break;
                    case 3:
                        getAllUsersMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("❌ Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input.");
                scanner.nextLine();
            }
        }
    }

    private void createUserMenu() {
        scanner.nextLine(); // consume newline
        System.out.print("\nEnter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("Enter gender (male/female): ");
        String gender = scanner.nextLine().trim();

        System.out.println(userController.createUser(name, surname, gender));
    }

    private void getUserByIdMenu() {
        System.out.print("\nEnter user ID: ");
        int id = getIntInput();
        System.out.println(userController.getUser(id));
    }

    private void getAllUsersMenu() {
        System.out.println(userController.getAllUsers());
    }

    // ==================== CAR MANAGEMENT ====================
    private void carManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- 🚗 CAR MANAGEMENT ---");
            System.out.println("1. ➕ Add Car");
            System.out.println("2. 🔍 Get Car by ID");
            System.out.println("3. 📋 Get All Cars");
            System.out.println("4. ✏️  Update Car");
            System.out.println("5. 🗑️  Delete Car");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addCarMenu();
                        break;
                    case 2:
                        getCarByIdMenu();
                        break;
                    case 3:
                        getAllCarsMenu();
                        break;
                    case 4:
                        updateCarMenu();
                        break;
                    case 5:
                        deleteCarMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("❌ Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input.");
                scanner.nextLine();
            }
        }
    }

    private void addCarMenu() {
        scanner.nextLine(); // consume newline
        System.out.print("\nEnter brand: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter year: ");
        int year = getIntInput();
        System.out.print("Enter price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(carController.addCar(brand, model, year, price, available));
    }

    private void getCarByIdMenu() {
        System.out.print("\nEnter car ID: ");
        int id = getIntInput();
        System.out.println(carController.getCarById(id));
    }

    private void getAllCarsMenu() {
        System.out.println(carController.getAllCars());
    }

    private void updateCarMenu() {
        System.out.print("\nEnter car ID to update: ");
        int id = getIntInput();
        System.out.print("Enter new price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(carController.updateCar(id, price, available));
    }

    private void deleteCarMenu() {
        System.out.print("\nEnter car ID to delete: ");
        int id = getIntInput();
        System.out.println(carController.deleteCar(id));
    }

    // ==================== MOTORCYCLE MANAGEMENT ====================
    private void motorcycleManagementMenu() {
        System.out.println("\n--- 🏍️  MOTORCYCLE MANAGEMENT ---");
        System.out.println("Coming soon!");
    }

    // ==================== BUS MANAGEMENT ====================
    private void busManagementMenu() {
        System.out.println("\n--- 🚌 BUS MANAGEMENT ---");
        System.out.println("Coming soon!");
    }

    // ==================== TRUCK MANAGEMENT ====================
    private void truckManagementMenu() {
        System.out.println("\n--- 🚚 TRUCK MANAGEMENT ---");
        System.out.println("Coming soon!");
    }

    // ==================== SPECIAL VEHICLE MANAGEMENT ====================
    private void specialVehicleManagementMenu() {
        System.out.println("\n--- ⚙️  SPECIAL VEHICLE MANAGEMENT ---");
        System.out.println("Coming soon!");
    }

    // ==================== HELPER METHODS ====================
    private int getIntInput() {
        return scanner.nextInt();
    }

    private double getDoubleInput() {
        return scanner.nextDouble();
    }

    private boolean getBooleanInput() {
        String input = scanner.next().trim().toLowerCase();
        scanner.nextLine(); // consume newline
        return input.equals("true") || input.equals("yes") || input.equals("1");
    }
}