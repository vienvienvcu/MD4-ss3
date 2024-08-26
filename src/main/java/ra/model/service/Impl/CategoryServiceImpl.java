package ra.model.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ra.exception.CustomException;
import ra.model.entity.Category;
import ra.model.repository.ICategoryRepository;
import ra.model.service.ICategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(String categoryId) throws CustomException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()->new CustomException("not exist " + categoryId, HttpStatus.NOT_FOUND));
    }

    @Override
    public Category insert(Category category) throws CustomException {
        // Kiểm tra categoryId có tồn tại chưa
        if (existsByCategoryId(category.getCategoryId())) {
            throw new CustomException("Category ID already exists", HttpStatus.CONFLICT);
        }

        // Kiểm tra categoryName có tồn tại chưa
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new CustomException("Category Name already exists", HttpStatus.CONFLICT);
        }

        // Lưu category nếu không có lỗi
        return categoryRepository.save(category);
    }

    @Override
    public Category update(String categoryId, Category category) throws CustomException {

        categoryRepository.findById(categoryId).orElseThrow(()->new CustomException("not exist " + categoryId,HttpStatus.NOT_FOUND));
        category.setCategoryId(categoryId);
        // Kiểm tra categoryName có tồn tại không
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new CustomException("Category Name already exists", HttpStatus.CONFLICT);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->new NoSuchElementException("not exist " + categoryId));
        categoryRepository.deleteById(categoryId);

    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }
}
