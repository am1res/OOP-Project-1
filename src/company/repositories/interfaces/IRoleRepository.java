package company.repositories.interfaces;

import company.models.Role;
import java.util.List;

public interface IRoleRepository {
    boolean createRole(String name);
    Role getRoleById(int id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    boolean deleteRole(int id);
}
