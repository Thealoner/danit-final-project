package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Discount;
import com.danit.repositories.employee.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

@Service
public class DiscountServiceImpl implements DiscountService {

  private DiscountRepository discountRepository;

  @Autowired
  public DiscountServiceImpl(DiscountRepository discountRepository) {
    this.discountRepository = discountRepository;
  }

  @Override
  public List<Discount> getAllDiscounts() {
    return discountRepository.findAll();
  }

  @Override
  public Discount getDiscountById(long id) {
    return discountRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find discount with id=%d", id)));
  }

  @Override
  public Discount createDiscount(Discount discount) {
    return discountRepository.save(discount);
  }

  @Override
  public Discount updateDiscount(Discount discount) {
    Discount savedCompany = new Discount();
    Long id = discount.getId();
    if (Objects.nonNull(id)) {
      Discount targetCompany = discountRepository.findById(id).orElseThrow(() ->
          new EntityNotFoundException(String.format("Cant find discount with id=%d", id)));
      if (updateNonEqualFields(discount, targetCompany)) {
        savedCompany = discountRepository.save(targetCompany);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedCompany;
  }

  @Override
  public void deleteDiscount(long id) {
    discountRepository.deleteById(id);

  }

  @Override
  public boolean discountExists(long id) {
    return discountRepository.existsById(id);
  }

  @Override
  public int getDiscountQuant() {
    return (int) discountRepository.count();
  }
}
