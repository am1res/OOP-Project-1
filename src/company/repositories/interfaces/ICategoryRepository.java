package company.repositories.interfaces;

import company.models.Category;
import java.util.List;

public interface ICategoryRepository {
    boolean createCategory(String name);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    boolean deleteCategory(int id);
}