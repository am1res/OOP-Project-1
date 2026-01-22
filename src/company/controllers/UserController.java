package company.controllers;

import company.models.User;
import company.repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    // Метод для входа
    public String login(String login, String password) {
        User user = repo.login(login, password);
        if (user == null) {
            return "❌ Error, invalid login or password";
        }
        return "✅ Welcome, " + user.getName() + " " + user.getSurname() + "!";
    }


    public String register(String name, String surname, String login, String password, boolean gender) {
        User user = new User(0, name, surname,true, login, password);
        boolean created = repo.createUser(user);

        return (created) ? "✅ The user was created successfully" : "❌ Error during registration!";
    }


    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        if (users == null || users.isEmpty()) {
            return "❌ No users found.";
        }

        StringBuilder response = new StringBuilder("📋 Registered Users List:\n");
        for (User user : users) {
            response.append("   ID: ").append(user.getId())
                    .append(" | Name: ").append(user.getName())
                    .append(" ").append(user.getSurname())
                    .append(" ").append(user.getGender())
                    .append(" | Login: ").append(user.getLogin())
                    .append("\n");
        }
        return response.toString();
    }
}