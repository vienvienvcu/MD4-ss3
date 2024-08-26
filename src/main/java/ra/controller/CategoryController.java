package ra.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.exception.CustomException;
import ra.model.dto.DataResponse;

import ra.model.entity.Category;
import ra.model.service.ICategoryService;

import java.net.URI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping
    public ResponseEntity<DataResponse> getAllCategories() {
        return new ResponseEntity<>
                (new DataResponse(categoryService.findAll(), HttpStatus.OK),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<? extends DataResponse> addCategory(@Valid @RequestBody Category category) throws CustomException {

        return ResponseEntity.created(URI.create("/api/v1/category")).body(
                new DataResponse(categoryService.insert(category), HttpStatus.CREATED)
        );

    }
/*
*@param categoryId String
* @throws CustomException handle not found exception
* @apiNote handle find by id
* */
    @GetMapping("/{categoryId}")
    public ResponseEntity<? extends DataResponse> getCategoryById(@PathVariable String categoryId) throws CustomException {
        return ResponseEntity.ok().body(new DataResponse(categoryService.findById(categoryId), HttpStatus.OK));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<? extends DataResponse> updateCategory(@PathVariable String categoryId, @Valid @RequestBody Category category) throws CustomException {
        return ResponseEntity.ok()
                .body(new DataResponse(categoryService.update(categoryId, category), HttpStatus.OK));
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DataResponse> deleteCategory(@PathVariable String categoryId) throws CustomException {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().body(new DataResponse(categoryId, HttpStatus.OK));
    }

}
