package ra.model.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.exception.CustomException;
import ra.model.dto.ProductDtoInsert;
import ra.model.dto.ProductDtoUpdate;
import ra.model.entity.Product;
import ra.model.repository.ICategoryRepository;
import ra.model.repository.IProductRepository;
import ra.model.service.IProductService;
import ra.model.service.IUploadFile;

import java.util.List;


@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IUploadFile uploadFile;
    @Override
    public Product getProductById(Integer productId) throws CustomException {

        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Not exist"+productId, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Product insertProduct(ProductDtoInsert productDto, MultipartFile file) throws CustomException {
        // Kiểm tra categoryId để đảm bảo không bị null
        if (productDto.getCategoryId() == null || productDto.getCategoryId().isEmpty()) {
            throw new CustomException("Category ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (productRepository.existsByproductName(productDto.getProductName())){
            throw new CustomException("Product name already exist", HttpStatus.CONFLICT);
        }

        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productDescription(productDto.getProductDescription())
                .productPrice(productDto.getProductPrice())
                .productQuantity(productDto.getProductQuantity())
                .productStatus(productDto.getProductStatus())
                .category(categoryRepository.findById(productDto.getCategoryId())
                        .orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND)))
                .build();

        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadFile.uploadLocal(file);
            product.setProductImage(imageUrl);
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, ProductDtoUpdate productUpdateDto, MultipartFile file) throws CustomException {
        // Tìm sản phẩm theo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Product not found", HttpStatus.NOT_FOUND));
        if (productRepository.existsByproductName(productUpdateDto.getProductName())){
            throw new CustomException("Product name already exist", HttpStatus.CONFLICT);
        }
        // Cập nhật các thông tin sản phẩm
        product.setProductName(productUpdateDto.getProductName());
        product.setProductDescription(productUpdateDto.getProductDescription());
        product.setProductPrice(productUpdateDto.getProductPrice());
        product.setProductQuantity(productUpdateDto.getProductQuantity());
        product.setProductStatus(productUpdateDto.getProductStatus());
        product.setCategory(categoryRepository.findById(productUpdateDto.getCategoryId())
                .orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND)));

        // Xử lý ảnh (nếu có)
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            // Thực hiện lưu trữ tệp hoặc xử lý theo yêu cầu của bạn
            product.setProductImage(fileName); // Lưu tên tệp hoặc URL vào thuộc tính sản phẩm
        }

        // Lưu sản phẩm đã cập nhật
        return productRepository.save(product);
    }



    @Override
    public void deleteProduct(Integer productId) throws CustomException {
        productRepository.findById(productId)
                .orElseThrow(() -> new CustomException("Not exist"+productId,HttpStatus.NOT_FOUND));
        productRepository.deleteById(productId);
    }

    @Override
    public boolean existsByproductName(String productName) {
        return productRepository.existsByproductName(productName);
    }
}
