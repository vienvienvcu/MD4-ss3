package ra.model.service;

import ra.exception.CustomException;
import ra.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(String categoryId) throws CustomException;
    Category insert(Category category) throws CustomException;
    Category update(String categoryId,Category category) throws CustomException;
    void delete(String categoryId);
    boolean existsByCategoryId(String categoryId);
    boolean existsByCategoryName(String categoryName);
}
