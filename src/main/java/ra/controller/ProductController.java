package ra.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.exception.CustomException;
import ra.model.dto.DataResponse;
import ra.model.dto.ProductDtoInsert;
import ra.model.dto.ProductDtoUpdate;
import ra.model.entity.Product;
import ra.model.service.IProductService;

import java.net.URI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping
    public ResponseEntity<? extends DataResponse> getAllProducts() {
        return ResponseEntity.ok().body(new DataResponse(productService.getAllProducts(), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<? extends DataResponse> addProduct(
            @Valid @ModelAttribute ProductDtoInsert productDto) throws CustomException {

        // Kiểm tra nếu ProductDto có giá trị hợp lệ
        if (productDto.getProductImageUrl() == null || productDto.getProductImageUrl().isEmpty()) {
            throw new CustomException("Product image cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Product createdProduct = productService.insertProduct(productDto, productDto.getProductImageUrl());

        return ResponseEntity.created(URI.create("/api/v1/products/" + createdProduct.getProductId()))
                .body(new DataResponse(createdProduct, HttpStatus.CREATED));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<? extends DataResponse> getProductById(@PathVariable Integer productId) throws CustomException {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok().body(new DataResponse(product, HttpStatus.OK));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<? extends DataResponse> updateProduct(
            @PathVariable Integer productId,
            @Valid @ModelAttribute ProductDtoUpdate productUpdateDto,
            @RequestParam(value = "file", required = false) MultipartFile file) throws CustomException {

        // Kiểm tra tính hợp lệ của productUpdateDto
        if (productUpdateDto.getCategoryId() == null || productUpdateDto.getCategoryId().isEmpty()) {
            throw new CustomException("Category ID cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        // Cập nhật sản phẩm với thông tin và ảnh (nếu có)
        Product updatedProduct = productService.updateProduct(productId, productUpdateDto, file);

        return ResponseEntity.ok().body(new DataResponse(updatedProduct, HttpStatus.OK));
    }



    @DeleteMapping("/{productId}")
    public ResponseEntity<? extends DataResponse> deleteProduct(@PathVariable Integer productId) throws CustomException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body(new DataResponse(productId, HttpStatus.OK));
    }



}
