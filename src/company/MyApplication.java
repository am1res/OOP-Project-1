package company;

import company.controllers.MotorcycleController;
import company.controllers.UserController;
import company.controllers.CarController;
import company.controllers.BusController;
import company.controllers.TruckController;
import company.controllers.SpecialVehicleController;

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
    private final MotorcycleController MotorcycleController;
    private final BusController busController;
    private final TruckController truckController;
    private final SpecialVehicleController specialVehicleController;


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
        this.MotorcycleController = new MotorcycleController(motorcycleRepo);
        this.truckController = new TruckController(truckRepo);
        this.busController = new BusController(busRepo);
        this.specialVehicleController = new SpecialVehicleController(specialVehicleRepo);

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
            System.out.println("2. 🔐 Login");
            System.out.println("3. 🔍 Get User by ID");
            System.out.println("4. 📋 Get All Users");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        createUserMenu();
                        break;
                    case 2:
                        loginMenu();
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
        scanner.nextLine();
        System.out.print("\nEnter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("Gender (true=male/false=female): ");
        boolean gender = getBooleanInput();
        System.out.print("Enter login: ");
        String login = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // Updated controller call
        System.out.println(userController.register(name, surname, login, password, gender));
    }

    private void loginMenu() {
        scanner.nextLine();
        System.out.print("\nEnter login: ");
        String login = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.println(userController.login(login, password));
    }

   /* private void getUserByIdMenu() {
        System.out.print("\nEnter user ID: ");
        int id = getIntInput();
        System.out.println(userController.getUser(id));
    }*/

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
    // ==================== MOTORCYCLE MANAGEMENT ====================
    private void motorcycleManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- 🏍️  MOTORCYCLE MANAGEMENT ---");
            System.out.println("1. ➕ Add Motorcycle");
            System.out.println("2. 🔍 Get Motorcycle by ID");
            System.out.println("3. 📋 Get All Motorcycles");
            System.out.println("4. ✏️  Update Motorcycle");
            System.out.println("5. 🗑️  Delete Motorcycle");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addMotorcycleMenu();
                        break;
                    case 2:
                        getMotorcycleByIdMenu();
                        break;
                    case 3:
                        getAllMotorcyclesMenu();
                        break;
                    case 4:
                        updateMotorcycleMenu();
                        break;
                    case 5:
                        deleteMotorcycleMenu();
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

    private void addMotorcycleMenu() {
        scanner.nextLine();
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

        System.out.println(MotorcycleController.addMotorcycle(brand, model, year, price, available));
    }

    private void getMotorcycleByIdMenu() {
        System.out.print("\nEnter motorcycle ID: ");
        int id = getIntInput();
        System.out.println(MotorcycleController.getMotorcycleById(id));
    }

    private void getAllMotorcyclesMenu() {
        System.out.println(MotorcycleController.getAllMotorcycles());
    }

    private void updateMotorcycleMenu() {
        System.out.print("\nEnter motorcycle ID to update: ");
        int id = getIntInput();
        System.out.print("Enter new price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(MotorcycleController.updateMotorcycle(id, price, available));
    }

    private void deleteMotorcycleMenu() {
        System.out.print("\nEnter motorcycle ID to delete: ");
        int id = getIntInput();
        System.out.println(MotorcycleController.deleteMotorcycle(id));
    }


    // ==================== BUS MANAGEMENT ====================
    // ==================== BUS MANAGEMENT ====================
    private void busManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- 🚌 BUS MANAGEMENT ---");
            System.out.println("1. ➕ Add Bus");
            System.out.println("2. 🔍 Get Bus by ID");
            System.out.println("3. 📋 Get All Buses");
            System.out.println("4. 💰 Get All Sorted by Price");
            System.out.println("5. 📅 Get All Sorted by Year");
            System.out.println("6. ✏️  Update Bus");
            System.out.println("7. 🗑️  Delete Bus");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addBusMenu();
                        break;
                    case 2:
                        getBusByIdMenu();
                        break;
                    case 3:
                        getAllBusesMenu();
                        break;
                    case 4:
                        getAllBusesSortedByPriceMenu();
                        break;
                    case 5:
                        getAllBusesSortedByYearMenu();
                        break;
                    case 6:
                        updateBusMenu();
                        break;
                    case 7:
                        deleteBusMenu();
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


    private void addBusMenu() {
        scanner.nextLine();
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

        System.out.println(busController.addBus(brand, model, year, price, available));
    }

    private void getBusByIdMenu() {
        System.out.print("\nEnter bus ID: ");
        int id = getIntInput();
        System.out.println(busController.getBusById(id));
    }

    private void getAllBusesMenu() {
        System.out.println(busController.getAllBuses());
    }

    private void updateBusMenu() {
        System.out.print("\nEnter bus ID to update: ");
        int id = getIntInput();
        System.out.print("Enter new price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(busController.updateBus(id, price, available));
    }

    private void deleteBusMenu() {
        System.out.print("\nEnter bus ID to delete: ");
        int id = getIntInput();
        System.out.println(busController.deleteBus(id));
    }
    private void getAllBusesSortedByPriceMenu() {
        System.out.println(busController.getAllBusesSortedByPrice());
    }

    private void getAllBusesSortedByYearMenu() {
        System.out.println(busController.getAllBusesSortedByYear());
    }




    // ==================== TRUCK MANAGEMENT ====================
    // ==================== TRUCK MANAGEMENT ====================
    private void truckManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- 🚚 TRUCK MANAGEMENT ---");
            System.out.println("1. ➕ Add Truck");
            System.out.println("2. 🔍 Get Truck by ID");
            System.out.println("3. 📋 Get All Trucks");
            System.out.println("4. ✏️  Update Truck");
            System.out.println("5. 🗑️  Delete Truck");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addTruckMenu();
                        break;
                    case 2:
                        getTruckByIdMenu();
                        break;
                    case 3:
                        getAllTrucksMenu();
                        break;
                    case 4:
                        updateTruckMenu();
                        break;
                    case 5:
                        deleteTruckMenu();
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

    private void addTruckMenu() {
        scanner.nextLine();
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

        System.out.println(truckController.addTruck(brand, model, year, price, available));
    }

    private void getTruckByIdMenu() {
        System.out.print("\nEnter truck ID: ");
        int id = getIntInput();
        System.out.println(truckController.getTruckById(id));
    }

    private void getAllTrucksMenu() {
        System.out.println(truckController.getAllTrucks());
    }

    private void updateTruckMenu() {
        System.out.print("\nEnter truck ID to update: ");
        int id = getIntInput();
        System.out.print("Enter new price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(truckController.updateTruck(id, price, available));
    }

    private void deleteTruckMenu() {
        System.out.print("\nEnter truck ID to delete: ");
        int id = getIntInput();
        System.out.println(truckController.deleteTruck(id));
    }


    // ==================== SPECIAL VEHICLE MANAGEMENT ====================
    // ==================== SPECIAL VEHICLE MANAGEMENT ====================
    private void specialVehicleManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- ⚙️  SPECIAL VEHICLE MANAGEMENT ---");
            System.out.println("1. ➕ Add Special Vehicle");
            System.out.println("2. 🔍 Get Special Vehicle by ID");
            System.out.println("3. 📋 Get All Special Vehicles");
            System.out.println("4. ✏️  Update Special Vehicle");
            System.out.println("5. 🗑️  Delete Special Vehicle");
            System.out.println("0. 🔙 Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addSpecialVehicleMenu();
                        break;
                    case 2:
                        getSpecialVehicleByIdMenu();
                        break;
                    case 3:
                        getAllSpecialVehiclesMenu();
                        break;
                    case 4:
                        updateSpecialVehicleMenu();
                        break;
                    case 5:
                        deleteSpecialVehicleMenu();
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

    private void addSpecialVehicleMenu() {
        scanner.nextLine();
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

        System.out.println(specialVehicleController.addSpecialVehicle(brand, model, year, price, available));
    }

    private void getSpecialVehicleByIdMenu() {
        System.out.print("\nEnter special vehicle ID: ");
        int id = getIntInput();
        System.out.println(specialVehicleController.getSpecialVehicleById(id));
    }

    private void getAllSpecialVehiclesMenu() {
        System.out.println(specialVehicleController.getAllSpecialVehicles());
    }

    private void updateSpecialVehicleMenu() {
        System.out.print("\nEnter special vehicle ID to update: ");
        int id = getIntInput();
        System.out.print("Enter new price: ");
        double price = getDoubleInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();

        System.out.println(specialVehicleController.updateSpecialVehicle(id, price, available));
    }

    private void deleteSpecialVehicleMenu() {
        System.out.print("\nEnter special vehicle ID to delete: ");
        int id = getIntInput();
        System.out.println(specialVehicleController.deleteSpecialVehicle(id));
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