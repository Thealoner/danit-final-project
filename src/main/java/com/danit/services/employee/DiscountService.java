package com.danit.services.employee;

import com.danit.models.employee.Discount;

import java.util.List;

public interface DiscountService {

  List<Discount> getAllDiscounts();

  Discount getDiscountById(long id);

  Discount createDiscount(Discount discount);

  Discount updateDiscount(Discount discount);

  void deleteDiscount(long id);

  boolean discountExists(long id);
}
