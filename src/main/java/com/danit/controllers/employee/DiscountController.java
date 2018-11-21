package com.danit.controllers.employee;

import com.danit.models.employee.Discount;
import com.danit.services.employee.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscountController {

  private DiscountService discountService;

  @Autowired
  public DiscountController(DiscountService discountService) {
    this.discountService = discountService;
  }

  @GetMapping("/discount")
  public List<Discount> getAllDiscount() {
    return discountService.getAllDiscounts();
  }

  @GetMapping("/discount/{id}")
  public Discount getDiscountById(@PathVariable long id) {
    return discountService.getDiscountById(id);
  }

  @DeleteMapping("/discount/{id}")
  public void deleteDiscount(@PathVariable long id) {
    discountService.deleteDiscount(id);
  }

  @PostMapping("/discount")
  public Discount createDiscount(@RequestBody Discount discount) {
    return discountService.createDiscount(discount);

  }

  @PutMapping("/discount/{id}")
  public Discount updateDiscount(@RequestBody Discount discount, @PathVariable long id) {
    return discountService.updateDiscount(discount);
  }
}
