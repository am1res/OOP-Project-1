package company.controllers;

import company.models.NewUser;
import company.repositories.interfaces.INewUserRepository;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    private final INewUserRepository userRepository;
    public static String currentUserRole = null;
    public static String currentUserName = null;
    public static int currentUserId = 0;
    public static double currentUserBalance = 0.0;

    public UserController(INewUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String login, String password) {
        if (login == null || login.isEmpty() ||
                password == null || password.isEmpty()) {
            return "ERROR: Login and password are required!";
        }

        NewUser user = userRepository.getByLoginAndPassword(login, password);
        if (user == null) {
            return "ERROR: Invalid login or password!";
        }

        currentUserId = user.getId();
        currentUserName = user.getName();
        currentUserRole = user.getRole();
        currentUserBalance = user.getBalance();

        return "SUCCESS: Logged in as " + currentUserRole +
                " (" + currentUserName + "), balance: " + currentUserBalance;
    }

    public static void logout() {
        currentUserId = 0;
        currentUserName = null;
        currentUserRole = null;
        currentUserBalance = 0.0;
    }

    public String createUser(String name, String surname, boolean gender, String role) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 100) {
            return "ERROR: Name must be 2-100 characters!";
        }
        if (surname == null || surname.trim().isEmpty() || surname.length() < 2 || surname.length() > 100) {
            return "ERROR: Surname must be 2-100 characters!";
        }
        if (!role.equals("admin") && !role.equals("user") && !role.equals("seller")) {
            return "ERROR: Invalid role! Allowed: admin, user, seller";
        }

        if (currentUserRole == null || !currentUserRole.equals("ADMIN")) {
            return "ERROR: Access denied! Only admins can create users.";
        }

        NewUser user = new NewUser(0, name, surname, gender, role, "", "", 0.0);
        return userRepository.add(user) ? "SUCCESS: User created!" : "ERROR: Failed to create user.";
    }

    public String getUserById(int id) {
        NewUser user = userRepository.getById(id);
        return user == null ? "ERROR: User not found!" : "SUCCESS: " + user.toString();
    }

    public String getAllUsers() {
        if (currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }

        List<NewUser> users = userRepository.getAll();
        if (users == null || users.isEmpty()) return "ERROR: No users found.";

        String result = users.stream()
                .filter(u -> u.getRole() != null)
                .map(u -> "   " + u.toString())
                .collect(Collectors.joining("\n", "SUCCESS: All Users:\n", ""));

        return result;
    }

    public String getUsersByRole(String role) {
        if (currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }

        List<NewUser> users = userRepository.getAll();
        if (users == null) return "ERROR: No users found.";

        String result = users.stream()
                .filter(u -> u.getRole().equalsIgnoreCase(role))
                .map(u -> "   " + u.toString())
                .collect(Collectors.joining("\n", "SUCCESS: Users with role '" + role + "':\n", ""));

        return result.equals("SUCCESS: Users with role '" + role + "':\n") ? "ERROR: No users found with this role." : result;
    }

    public String updateUserRole(int id, String newRole) {
        if (currentUserRole == null || !currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can update roles.";
        }
        if (!newRole.equals("admin") && !newRole.equals("user") && !newRole.equals("seller")) {
            return "ERROR: Invalid role!";
        }
        return userRepository.updateUserRole(id, newRole) ?
                "SUCCESS: User role updated!" : "ERROR: Failed to update.";
    }

    public String deleteUser(int id) {
        if (currentUserRole == null || !currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can delete users.";
        }
        return userRepository.delete(id) ? "SUCCESS: User deleted!" : "ERROR: Failed to delete.";
    }
}
