package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @NotBlank(message = "khong dc null")
    @Column(unique = true, nullable = false)
    private String categoryId;
    @NotBlank(message = "khong dc null")
    @Column(unique = true, nullable = false)
    private String categoryName;
    @NotNull(message = "khong duoc null")
    private Boolean categoryStatus;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> productList;
}
