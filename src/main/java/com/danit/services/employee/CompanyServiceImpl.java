package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Company;
import com.danit.repositories.employee.CompanyRepository;
import com.danit.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {

  private ServiceUtils serviceUtils;

  private CompanyRepository companyRepository;

  public CompanyServiceImpl(ServiceUtils serviceUtils, CompanyRepository companyRepository) {
    this.serviceUtils = serviceUtils;
    this.companyRepository = companyRepository;
  }

  @Override
  public List<Company> getAllCompanies() {
    return companyRepository.findAll();
  }

  @Override
  public Company getCompanyById(long id) {
    return companyRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find ccompany with id=%d", id)));
  }

  @Override
  public Company createCompany(Company company) {
    return companyRepository.save(company);
  }

  @Override
  public Company updateCompany(Company company) {
    Company savedCompany = new Company();
    Long id = company.getId();
    if (Objects.nonNull(id)) {
      Company targetCompany = companyRepository.findById(id).orElseThrow(() ->
          new EntityNotFoundException(String.format("Cant find Department with id=%d", id)));
      if (serviceUtils.updateNonEqualFields(company, targetCompany)) {
        savedCompany = companyRepository.save(targetCompany);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedCompany;

  }

  @Override
  public void deleteCompany(long id) {
    companyRepository.deleteById(id);

  }

  @Override
  public boolean companyExists(long id) {
    return companyRepository.existsById(id);
  }

  @Override
  public int getCompanyQty() {
    return (int) companyRepository.count();
  }
}
