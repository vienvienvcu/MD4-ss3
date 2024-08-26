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
public class ProductDtoUpdate {
    @NotBlank(message = "Product Name cannot be empty")
    private String productName;

    @NotBlank(message = "Product Description cannot be empty")
    private String productDescription;

    @ValidMultipartFile(message = "Hình ảnh sản phẩm không thể trống hoặc không hợp lệ")
    private MultipartFile productImageUrl; // Không bắt buộc, có thể có hoặc không

    @NotNull(message = "Product Price cannot be empty")
    private Double productPrice;

    @NotNull(message = "Product Quantity cannot be empty")
    private Integer productQuantity;

    @NotNull(message = "Product Status cannot be empty")
    private Boolean productStatus;

    private String categoryId; // Optional
}
