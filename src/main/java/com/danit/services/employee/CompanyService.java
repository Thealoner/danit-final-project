package com.danit.services.employee;

import com.danit.models.employee.Company;

import java.util.List;

public interface CompanyService {

  List<Company> getAllCompanies();

  Company getCompanyById(long id);

  Company createCompany(Company company);

  Company updateCompany(Company company);

  void deleteCompany(long id);

  boolean companyExists(long id);

}
