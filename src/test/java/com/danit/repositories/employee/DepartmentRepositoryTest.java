package com.danit.repositories.employee;

import com.danit.models.employee.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DepartmentRepositoryTest {

  @Autowired
  private DepartmentRepository departmentRepository;

  @Test
  public void createDepartmentTest() {
    Department department = new Department();
    department.setName("Test dept");
    Assert.assertNotNull(departmentRepository.save(department));
  }

  @Test
  public void getDepartmentByIdTest() {
    Assert.assertNotNull(departmentRepository.findById(1L));
  }

  @Test
  public void deleteDepartmentTest() {
    int departmentQuant = (int) departmentRepository.count();
    Department department = new Department();
    department.setName("Test dept");
    long savedId = departmentRepository.save(department).getId();
    departmentRepository.deleteById(savedId);
    Assert.assertEquals(departmentRepository.findById(savedId), Optional.empty());
  }

  @Test
  public void updateDepartmentTest() {
    Department departmentToSave = new Department();
    departmentToSave.setName("Test dept");
    long savedDepartmentId = departmentRepository.save(departmentToSave).getId();
    Assert.assertEquals(departmentToSave.getName(), departmentRepository.findById(savedDepartmentId).get().getName());
  }

  @Test
  public void getAllDepartment() {
    long Quantity = departmentRepository.findAll().size();
    System.out.println(Quantity);
    Department departmentToSave = new Department();
    departmentToSave.setName("Test dept");
    departmentToSave.setSname("Test dept");
    departmentToSave.setDateFrom(new Date());
    departmentToSave.setDateTo(new Date());
    departmentRepository.save(departmentToSave);
    System.out.println(departmentRepository.findAll().size());
    Assert.assertEquals(Quantity + 1, departmentRepository.findAll().size());

  }
}
