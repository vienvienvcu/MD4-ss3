package ra.model.service;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;
import ra.exception.CustomException;
import ra.model.dto.ProductDtoInsert;
import ra.model.dto.ProductDtoUpdate;
import ra.model.entity.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Integer productId) throws CustomException;
    List<Product> getAllProducts();
    Product insertProduct(ProductDtoInsert productDto, MultipartFile file) throws CustomException;
    Product updateProduct(Integer productId, ProductDtoUpdate productUpdateDto, MultipartFile file) throws CustomException;
    void deleteProduct(Integer productId) throws CustomException;
    boolean existsByproductName(String productName);
}
