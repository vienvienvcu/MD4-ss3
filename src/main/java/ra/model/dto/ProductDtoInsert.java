package ra.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ra.exception.uploadFileValid.ValidMultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDtoInsert {
    @NotBlank(message = "Product Name cannot be empty")
    private String productName;
    @NotBlank(message = "Product Description cannot be empty")
    private String productDescription;
    @ValidMultipartFile(message = "Hình ảnh sản phẩm không thể trống hoặc không hợp lệ")
    private MultipartFile productImageUrl;
    @NotNull(message = "Product Price cannot be empty")
    private Double productPrice;
    @NotNull(message = "Product Quantity cannot be empty")
    private Integer productQuantity;
    @NotNull(message = "Product Status cannot be empty")
    private Boolean productStatus;
    @NotBlank(message = "Category Id cannot be empty")
    private String categoryId; // Optional
}
