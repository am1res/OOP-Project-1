package company;

import company.controllers.*;
import company.data.interfaces.IDB;
import company.repositories.interfaces.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IDB db;

    private final UserController userController;
    private final CategoryController categoryController;
    private final RoleController roleController;
    private final VehicleController vehicleController;

    public MyApplication(
            IDB db,
            INewUserRepository userRepo,
            ICategoryRepository categoryRepo,
            IRoleRepository roleRepo,
            IVehicleRepository vehicleRepo
    ) {
        this.db = db;

        this.userController = new UserController(userRepo);
        this.categoryController = new CategoryController(categoryRepo);
        this.roleController = new RoleController(roleRepo);
        this.vehicleController = new VehicleController(vehicleRepo);
    }

    private void mainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     VEHICLE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. User Management");
        System.out.println("2. Role Management");
        System.out.println("3. Category Management");
        System.out.println("4. Vehicle Management");
        System.out.println("5. Logout");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter option (0-5): ");
    }

    public void start() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("     WELCOME TO VEHICLE MANAGEMENT");
        System.out.println("=".repeat(50));

        boolean loggedIn = false;
        while (!loggedIn) {
            loginMenu();
            if (UserController.currentUserRole != null) {
                loggedIn = true;
            }
        }

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
                        roleManagementMenu();
                        break;
                    case 3:
                        categoryManagementMenu();
                        break;
                    case 4:
                        vehicleManagementMenu();
                        break;
                    case 5:
                        logoutMenu();
                        running = false;
                        break;
                    case 0:
                        System.out.println("\nSUCCESS: Thank you for using Vehicle Management System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("ERROR: Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input. Please enter a number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }


    // ==================== LOGIN/LOGOUT ====================
    private void loginMenu() {
        System.out.println("\n--- LOGIN ---");
        scanner.nextLine();
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();

        System.out.println(userController.login(name));

        if (UserController.currentUserRole == null) {
            System.out.println("ERROR: Login failed. Try again.");
            loginMenu();
        }
    }

    private void logoutMenu() {
        System.out.println("SUCCESS: Logged out successfully!");
        UserController.logout();
    }

    // ==================== USER MANAGEMENT ====================
    private void userManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- USER MANAGEMENT ---");
            System.out.println("1. Create User");
            System.out.println("2. Get User by ID");
            System.out.println("3. Get All Users");
            System.out.println("4. Get Users by Role");
            System.out.println("5. Update User Role");
            System.out.println("6. Delete User");
            System.out.println("0. Back");
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
                    case 4:
                        getUsersByRoleMenu();
                        break;
                    case 5:
                        updateUserRoleMenu();
                        break;
                    case 6:
                        deleteUserMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("ERROR: Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input.");
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
        System.out.print("Enter role (admin/user/seller): ");
        String role = scanner.nextLine().trim();

        System.out.println(userController.createUser(name, surname, gender, role));
    }

    private void getUserByIdMenu() {
        System.out.print("\nEnter user ID: ");
        int id = getIntInput();
        System.out.println(userController.getUserById(id));
    }

    private void getAllUsersMenu() {
        System.out.println(userController.getAllUsers());
    }

    private void getUsersByRoleMenu() {
        scanner.nextLine();
        System.out.print("\nEnter role: ");
        String role = scanner.nextLine().trim();
        System.out.println(userController.getUsersByRole(role));
    }

    private void updateUserRoleMenu() {
        System.out.print("\nEnter user ID: ");
        int id = getIntInput();
        scanner.nextLine();
        System.out.print("Enter new role: ");
        String newRole = scanner.nextLine().trim();
        System.out.println(userController.updateUserRole(id, newRole));
    }

    private void deleteUserMenu() {
        System.out.print("\nEnter user ID to delete: ");
        int id = getIntInput();
        System.out.println(userController.deleteUser(id));
    }

    // ==================== ROLE MANAGEMENT ====================
    private void roleManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- ROLE MANAGEMENT ---");
            System.out.println("1. Create Role");
            System.out.println("2. Get Role by ID");
            System.out.println("3. Get Role by Name");
            System.out.println("4. Get All Roles");
            System.out.println("5. Delete Role");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        createRoleMenu();
                        break;
                    case 2:
                        getRoleByIdMenu();
                        break;
                    case 3:
                        getRoleByNameMenu();
                        break;
                    case 4:
                        getAllRolesMenu();
                        break;
                    case 5:
                        deleteRoleMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("ERROR: Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input.");
                scanner.nextLine();
            }
        }
    }

    private void createRoleMenu() {
        scanner.nextLine();
        System.out.print("\nEnter role name: ");
        String name = scanner.nextLine().trim();
        System.out.println(roleController.createRole(name));
    }

    private void getRoleByIdMenu() {
        System.out.print("\nEnter role ID: ");
        int id = getIntInput();
        System.out.println(roleController.getRoleById(id));
    }

    private void getRoleByNameMenu() {
        scanner.nextLine();
        System.out.print("\nEnter role name: ");
        String name = scanner.nextLine().trim();
        System.out.println(roleController.getRoleByName(name));
    }

    private void getAllRolesMenu() {
        System.out.println(roleController.getAllRoles());
    }

    private void deleteRoleMenu() {
        System.out.print("\nEnter role ID to delete: ");
        int id = getIntInput();
        System.out.println(roleController.deleteRole(id));
    }

    // ==================== CATEGORY MANAGEMENT ====================
    private void categoryManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- CATEGORY MANAGEMENT ---");
            System.out.println("1. Create Category");
            System.out.println("2. Get Category by ID");
            System.out.println("3. Get All Categories");
            System.out.println("4. Delete Category");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        createCategoryMenu();
                        break;
                    case 2:
                        getCategoryByIdMenu();
                        break;
                    case 3:
                        getAllCategoriesMenu();
                        break;
                    case 4:
                        deleteCategoryMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("ERROR: Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input.");
                scanner.nextLine();
            }
        }
    }

    private void createCategoryMenu() {
        scanner.nextLine();
        System.out.print("\nEnter category name (Car, Bus, Truck, etc): ");
        String name = scanner.nextLine().trim();
        System.out.println(categoryController.createCategory(name));
    }

    private void getCategoryByIdMenu() {
        System.out.print("\nEnter category ID: ");
        int id = getIntInput();
        System.out.println(categoryController.getCategoryById(id));
    }

    private void getAllCategoriesMenu() {
        System.out.println(categoryController.getAllCategories());
    }

    private void deleteCategoryMenu() {
        System.out.print("\nEnter category ID to delete: ");
        int id = getIntInput();
        System.out.println(categoryController.deleteCategory(id));
    }

    // ==================== VEHICLE MANAGEMENT ====================
    private void vehicleManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- VEHICLE MANAGEMENT ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Get Vehicle by ID");
            System.out.println("3. Get Vehicles by Category");
            System.out.println("4. Delete Vehicle");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            try {
                int choice = getIntInput();
                switch (choice) {
                    case 1:
                        addVehicleMenu();
                        break;
                    case 2:
                        getVehicleByIdMenu();
                        break;
                        case 3:
                        getVehiclesByCategoryMenu();
                        break;

                    case 4:
                        deleteVehicleMenu();
                        break;
                    case 0:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("ERROR: Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid input.");
                scanner.nextLine();
            }
        }
    }

    private void addVehicleMenu() {
        scanner.nextLine();
        System.out.print("\nEnter user ID (owner): ");
        int userId = getIntInput();
        System.out.print("Enter category ID: ");
        int categoryId = getIntInput();
        scanner.nextLine();
        System.out.print("Enter vehicle type (Car, Bus, Truck, etc): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter year: ");
        int year = getIntInput();
        System.out.print("Enter price: ");
        double price = getDoubleInput();

        System.out.println(vehicleController.addVehicle(userId, categoryId, type, brand, model, year, price));
    }

    private void getVehicleByIdMenu() {
        System.out.print("\nEnter vehicle ID: ");
        int id = getIntInput();
        System.out.println(vehicleController.getVehicleById(id));
    }

    private void getAllVehiclesMenu() {
        System.out.println(vehicleController.getAllVehicles());
    }

    private void getAllAvailableVehiclesMenu() {
        System.out.println(vehicleController.getAllAvailableVehicles());
    }

    private void getVehiclesByCategoryMenu() {
        System.out.print("\nEnter category ID: ");
        int categoryId = getIntInput();
        System.out.println(vehicleController.getVehiclesByCategory(categoryId));
    }

    private void getVehiclesByPriceRangeMenu() {
        System.out.print("\nEnter minimum price: ");
        double minPrice = getDoubleInput();
        System.out.print("Enter maximum price: ");
        double maxPrice = getDoubleInput();
        System.out.println(vehicleController.getVehiclesByPriceRange(minPrice, maxPrice));
    }

    private void updateVehicleAvailabilityMenu() {
        System.out.print("\nEnter vehicle ID: ");
        int vehicleId = getIntInput();
        System.out.print("Is available? (true/false): ");
        boolean available = getBooleanInput();
        System.out.println(vehicleController.updateVehicleAvailability(vehicleId, available));
    }

    private void deleteVehicleMenu() {
        System.out.print("\nEnter vehicle ID to delete: ");
        int id = getIntInput();
        System.out.println(vehicleController.deleteVehicle(id));
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
        scanner.nextLine();
        return input.equals("true") || input.equals("yes") || input.equals("1");
    }
}
