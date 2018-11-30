package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Department;
import com.danit.repositories.employee.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private DepartmentRepository departmentRepository;

  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public List<Department> getAllDepartments() {
    return departmentRepository.findAll();
  }

  @Override
  public Department getDepartmentById(long id) {
    return departmentRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find department with id=%d", id)));
  }

  @Override
  public Department saveDepartment(Department department) {
    return departmentRepository.save(department);

  }

  @Override
  public Department updateDepartment(Department department) {
    Department savedDepartment = new Department();
    Long id = department.getId();
    if (Objects.nonNull(id)) {
      Department targetDepartment = departmentRepository.findById(id).orElseThrow(() ->
          new EntityNotFoundException(String.format("Cant find Department with id=%d", id)));
      if (updateNonEqualFields(department, targetDepartment)) {
        savedDepartment = departmentRepository.save(targetDepartment);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedDepartment;

  }

  @Override
  public void deleteDepartment(long id) {
    departmentRepository.deleteById(id);
  }

  @Override
  public boolean departmentExists(long id) {
    return departmentRepository.existsById(id);
  }

  @Override
  public int getDepartmentQty() {
    return (int) departmentRepository.count();
  }
}
