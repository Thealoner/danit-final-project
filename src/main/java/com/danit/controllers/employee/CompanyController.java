package com.danit.controllers.employee;

import com.danit.models.employee.Company;
import com.danit.services.employee.CompanyService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CompanyController {

  private CompanyService companyService;

  @Autowired
  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/company")
  public List<Company> getAllCompanies() {
    return companyService.getAllCompanies();
  }

  @GetMapping("/company/{id}")
  public Company getCompanyById(@PathVariable(name = "id") long id) {
    return companyService.getCompanyById(id);
  }

  @DeleteMapping("/company/{id}")
  public void deleteCompany(@PathVariable(name = "id") long id) {
    companyService.deleteCompany(id);
  }

  @PostMapping("/company")
  public Company createCompany(@RequestBody Company company) {
    return companyService.createCompany(company);

  }

  @PutMapping("/company/{id}")
  public Company updateCompany(@RequestBody Company company) {
    return companyService.updateCompany(company);
  }
}
