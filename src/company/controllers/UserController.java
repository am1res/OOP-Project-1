package company.controllers;

import company.models.NewUser;
import company.repositories.interfaces.IUserRepository;
import java.util.List;

public class UserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    public String login(String login, String password) {
        NewUser user = repo.login(login, password);
        if (user == null) return "❌ Error, invalid login or password";
        return "✅ Welcome, " + user.getName() + " " + user.getSurname() + "!";
    }

    public String register(String name, String surname, String login, String password, boolean gender) {
        // Просто передаем значения в порядке конструктора
        NewUser user = new NewUser(0, name, surname, gender, login, password);
        boolean created = repo.createUser(user);
        return created ? "✅ The user was created successfully" : "❌ Error during registration!";
    }

    public String getAllUsers() {
        List<NewUser> users = repo.getAllUsers();
        if (users == null || users.isEmpty()) return "❌ No users found.";
        StringBuilder response = new StringBuilder("📋 Registered Users List:\n");
        for (NewUser u : users) {
            response.append("   ID: ").append(u.getId())
                    .append(" | Name: ").append(u.getName()).append(" ").append(u.getSurname())
                    .append(" | Login: ").append(u.getLogin())
                    .append("\n");
        }
        return response.toString();
    }
}
