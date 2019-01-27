package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.EmployeeCategory;
import com.danit.repositories.employee.EmployeeCategoryRepository;
import com.danit.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class EmployeeCategoryServiceImpl implements EmployeeCategoryService {

  private ServiceUtils serviceUtils;

  private EmployeeCategoryRepository employeeCategoryRepository;

  public EmployeeCategoryServiceImpl(ServiceUtils serviceUtils, EmployeeCategoryRepository employeeCategoryRepository) {
    this.serviceUtils = serviceUtils;
    this.employeeCategoryRepository = employeeCategoryRepository;
  }

  @Override
  public List<EmployeeCategory> getAllEmployeeCategories() {
    return employeeCategoryRepository.findAll();
  }

  @Override
  public EmployeeCategory getEmployeeCategoryById(long id) {
    return employeeCategoryRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find ccompany with id=%d", id)));

  }

  @Override
  public EmployeeCategory createEmployeeCategory(EmployeeCategory employeeCategory) {
    return employeeCategoryRepository.save(employeeCategory);
  }

  @Override
  public EmployeeCategory updateEmployeeCategory(EmployeeCategory employeeCategory) {
    EmployeeCategory savedEmployeeCategory = new EmployeeCategory();
    Long id = employeeCategory.getId();
    if (Objects.nonNull(id)) {
      EmployeeCategory targetEmployeeCategory = employeeCategoryRepository.findById(id).orElseThrow(() ->
          new EntityNotFoundException(String.format("Cant find Employee with id=%d", id)));
      if (serviceUtils.updateNonEqualFields(employeeCategory, targetEmployeeCategory)) {
        savedEmployeeCategory = employeeCategoryRepository.save(targetEmployeeCategory);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedEmployeeCategory;
  }

  @Override
  public void deleteEmployeeCategory(long id) {
    employeeCategoryRepository.deleteById(id);

  }

  @Override
  public int getEmployeeCategoryQty() {
    return (int) employeeCategoryRepository.count();
  }
}
