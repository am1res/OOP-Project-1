package company.repositories.interfaces;

import company.models.User;
import java.util.List;

public interface IUserRepository {
    User login(String login, String password);
    boolean createUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
}