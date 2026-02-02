package company.controllers;

import company.models.Role;
import company.repositories.interfaces.IRoleRepository;
import java.util.List;

public class RoleController {
    private final IRoleRepository roleRepository;

    public RoleController(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String createRole(String name) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 20) {
            return "ERROR: Role name must be 2-20 characters!";
        }

        if (UserController.currentUserRole == null || !UserController.currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can create roles.";
        }

        return roleRepository.createRole(name) ?
                "SUCCESS: Role created!" : "ERROR: Failed to create role.";
    }

    public String getRoleById(int id) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        Role role = roleRepository.getRoleById(id);
        return role == null ? "ERROR: Role not found!" : "SUCCESS: " + role.toString();
    }

    public String getRoleByName(String name) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        Role role = roleRepository.getRoleByName(name);
        return role == null ? "ERROR: Role not found!" : "SUCCESS: " + role.toString();
    }

    public String getAllRoles() {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Role> roles = roleRepository.getAllRoles();
        if (roles == null || roles.isEmpty()) return "ERROR: No roles found.";
        StringBuilder response = new StringBuilder("SUCCESS: All Roles:\n");
        for (Role r : roles) {
            response.append("   ").append(r.toString()).append("\n");
        }
        return response.toString();
    }

    public String deleteRole(int id) {
        if (UserController.currentUserRole == null || !UserController.currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can delete roles.";
        }
        return roleRepository.deleteRole(id) ?
                "SUCCESS: Role deleted!" : "ERROR: Failed to delete.";
    }
}
