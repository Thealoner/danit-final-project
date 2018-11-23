package com.danit.repositories.employee;

import com.danit.models.employee.EmployeeCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeCategoryRepositoryTest {

  @Autowired
  private EmployeeCategoryRepository employeeCategoryRepository;

  @Test
  public void createEmployeeCategoryTest() {
    EmployeeCategory employeeCategory = new EmployeeCategory();
    employeeCategory.setName("Test category");
    Assert.assertNotNull(employeeCategoryRepository.save(employeeCategory));
  }

  @Test
  public void getEmployeeCategoryByIdTest() {
    Assert.assertNotNull(employeeCategoryRepository.findById(1L));
  }

  @Test
  public void deleteEmployeeCategoryTest() {
    int employeeCategoryQuant = (int) employeeCategoryRepository.count();
    EmployeeCategory employeeCategory = new EmployeeCategory();
    employeeCategory.setName("Test category");
    long savedId = employeeCategoryRepository.save(employeeCategory).getId();
    employeeCategoryRepository.deleteById(savedId);
    Assert.assertEquals(employeeCategoryRepository.findById(savedId), Optional.empty());
  }

  @Test
  public void updateEmployeeCategoryTest() {
    EmployeeCategory employeeCategoryToSave = new EmployeeCategory();
    employeeCategoryToSave.setName("Test category");
    long savedEmployeeCategoryId = employeeCategoryRepository.save(employeeCategoryToSave).getId();
    Assert.assertEquals(employeeCategoryToSave.getName(), employeeCategoryRepository.findById(savedEmployeeCategoryId).get().getName());
  }

  @Test
  public void getAllEmployeeCategory() {
    long Quantity = employeeCategoryRepository.findAll().size();
    System.out.println(Quantity);
    EmployeeCategory employeeCategoryToSave = new EmployeeCategory();
    employeeCategoryToSave.setName("Test category");
    employeeCategoryRepository.save(employeeCategoryToSave);
    System.out.println(employeeCategoryRepository.findAll().size());
    Assert.assertEquals(Quantity + 1, employeeCategoryRepository.findAll().size());

  }
}
