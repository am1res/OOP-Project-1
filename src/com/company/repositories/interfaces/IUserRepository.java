package src.com.company.repositories.interfaces;

import src.com.company.models.User;

import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
}
