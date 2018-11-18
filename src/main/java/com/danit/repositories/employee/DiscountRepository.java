package com.danit.repositories.employee;

import com.danit.models.employee.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository  extends JpaRepository<Discount, Long> {

}
