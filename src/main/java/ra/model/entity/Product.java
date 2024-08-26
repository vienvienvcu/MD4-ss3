package ra.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @NotBlank(message = "product name cannot is empty")
    private String productName;

    @NotBlank(message = "product description cannot is empty")
    private String productDescription;

    @NotBlank(message = "product image cannot is empty")
    private String productImage;

    @NotNull (message = "product price cannot is empty")
    private Double productPrice;

    @NotNull (message = "product quantity cannot is empty")
    private Integer productQuantity;

    @NotNull (message = "product boolean cannot is empty")
    private Boolean productStatus;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
