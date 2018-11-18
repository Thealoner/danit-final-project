package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.employee.Discount;
import com.danit.repositories.employee.DiscountRepository;
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
public class DiscountController {
  @Autowired
  private DiscountRepository discountRepository;

  @GetMapping("/discount")
  public List<Discount> retrieveAllDiscount() {
    return discountRepository.findAll();
  }

  @GetMapping("/discount/{id}")
  public Discount retrieveDiscount(@PathVariable long id) {
    Optional<Discount> discount = discountRepository.findById(id);

    if (!discount.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return discount.get();
  }

  @DeleteMapping("/discount/{id}")
  public void deleteDiscount(@PathVariable long id) {
    discountRepository.deleteById(id);
  }

  @PostMapping("/discount")
  public ResponseEntity<Object> createDiscount(@RequestBody Discount discount) {
    Discount savedDiscount = discountRepository.save(discount);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedDiscount.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/discount/{id}")
  public ResponseEntity<Object> updateDiscount(@RequestBody Discount discount, @PathVariable long id) {

    Optional<Discount> discountOptional = discountRepository.findById(id);

    if (!discountOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    discount.setId(id);

    discountRepository.save(discount);

    return ResponseEntity.noContent().build();
  }
}
