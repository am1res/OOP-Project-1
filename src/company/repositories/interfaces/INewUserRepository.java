package company.repositories.interfaces;

import company.models.NewUser;
import java.util.List;

public interface INewUserRepository {
    boolean add(NewUser user);
    NewUser getById(int id);
    List<NewUser> getAll();
    boolean delete(int id);
}