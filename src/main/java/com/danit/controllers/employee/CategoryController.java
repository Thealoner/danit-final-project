package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.Category;
import com.danit.repositories.employee.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("/category")
  public List<Category> retrieveAllCategory() {
    return categoryRepository.findAll();
  }

  @GetMapping("/category/{id}")
  public Category retrieveCategory(@PathVariable long id) {
    Optional<Category> category = categoryRepository.findById(id);

    if (!category.isPresent()) {
      throw new EntityNotFoundException("Запись категории с id = " + id);
    }
    return category.get();
  }

  @DeleteMapping("/category/{id}")
  public void deleteCategory(@PathVariable long id) {
    categoryRepository.deleteById(id);
  }

  @PostMapping("/category")
  public ResponseEntity<Object> createCategory(@RequestBody Category category) {
    Category savedCategory = categoryRepository.save(category);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedCategory.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/category/{id}")
  public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable long id) {

    Optional<Category> categoryOptional = categoryRepository.findById(id);

    if (!categoryOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    category.setId(id);

    categoryRepository.save(category);

    return ResponseEntity.noContent().build();
  }
}
