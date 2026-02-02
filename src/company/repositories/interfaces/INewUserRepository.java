package company.repositories.interfaces;

import company.models.NewUser;
import java.util.List;

public interface INewUserRepository {
    boolean add(NewUser user);
    List<NewUser> getAll();
    NewUser getById(int id);
    boolean delete(int id);
    boolean updateUserRole(int id, String newRole);  // Add this line
}
