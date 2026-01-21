package company.repositories.interfaces;

import company.models.User;
import java.util.List;

public interface IUserRepository {
    User login(String login, String password); // Авторизация
    boolean createUser(User user);             // Регистрация
    User getUser(int id);                      // Поиск по ID
    List<User> getAllUsers();                  // Список всех
}
