package company.controllers;

import company.models.Category;
import company.repositories.interfaces.ICategoryRepository;
import java.util.List;

public class CategoryController {
    private final ICategoryRepository categoryRepository;

    public CategoryController(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String createCategory(String name) {
        if (name == null || name.trim().isEmpty() || name.length() < 2 || name.length() > 50) {
            return "ERROR: Category name must be 2-50 characters!";
        }

        if (UserController.currentUserRole == null || !UserController.currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can create categories.";
        }

        return categoryRepository.createCategory(name) ?
                "SUCCESS: Category created!" : "ERROR: Failed to create category.";
    }

    public String getCategoryById(int id) {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        Category category = categoryRepository.getCategoryById(id);
        return category == null ? "ERROR: Category not found!" : "SUCCESS: " + category.toString();
    }

    public String getAllCategories() {
        if (UserController.currentUserRole == null) {
            return "ERROR: You must be logged in!";
        }
        List<Category> categories = categoryRepository.getAllCategories();
        if (categories == null || categories.isEmpty()) return "ERROR: No categories found.";
        StringBuilder response = new StringBuilder("SUCCESS: All Categories:\n");
        for (Category c : categories) {
            response.append("   ").append(c.toString()).append("\n");
        }
        return response.toString();
    }

    public String deleteCategory(int id) {
        if (UserController.currentUserRole == null || !UserController.currentUserRole.equals("admin")) {
            return "ERROR: Access denied! Only admins can delete categories.";
        }
        return categoryRepository.deleteCategory(id) ?
                "SUCCESS: Category deleted!" : "ERROR: Failed to delete.";
    }
}
