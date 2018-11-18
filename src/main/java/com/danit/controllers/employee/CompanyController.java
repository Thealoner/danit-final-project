package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.Company;
import com.danit.repositories.employee.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController {
  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping("/company")
  public List<Company> retrieveAllCompany() {
    return companyRepository.findAll();
  }

  @GetMapping("/company/{id}")
  public Company retrieveCompany(@PathVariable long id) {
    Optional<Company> company = companyRepository.findById(id);

    if (!company.isPresent()) {
      throw new EntityNotFoundException("Company id-" + id);
    }
    return company.get();
  }

  @DeleteMapping("/company/{id}")
  public void deleteCompany(@PathVariable long id) {
    companyRepository.deleteById(id);
  }

  @PostMapping("/company")
  public ResponseEntity<Object> createCompany(@RequestBody Company company) {
    Company savedCompany = companyRepository.save(company);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedCompany.getId()).toUri();
    return ResponseEntity.created(location).build();

  }

  @PutMapping("/company/{id}")
  public ResponseEntity<Object> updateCompany(@RequestBody Company company, @PathVariable long id) {

    Optional<Company> companyOptional = companyRepository.findById(id);

    if (!companyOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    company.setId(id);

    companyRepository.save(company);

    return ResponseEntity.noContent().build();
  }
}
