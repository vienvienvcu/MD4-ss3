package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Category;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Category c WHERE c.categoryName = :categoryName")
    boolean existsByCategoryName(@Param("categoryName") String categoryName);

}
