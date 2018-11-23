package com.danit.repositories.employee;

import com.danit.models.employee.Company;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

  @Autowired
  private CompanyRepository companyRepository;

  @Test
  public void createCompanyTest() {
    Company company = new Company();
    company.setName("Test Company");
    Assert.assertNotNull(companyRepository.save(company));
    Assert.assertEquals("Test Company", companyRepository.save(company).getName());
  }

  @Test
  public void getCompanyByIdTest() {
    Assert.assertNotNull(companyRepository.findById(1L));
  }

  @Test
  public void deleteCompanyTest() {
    int companyQuant = (int) companyRepository.count();
    Company company = new Company();
    company.setName("Boxing gym");
    long savedId = companyRepository.save(company).getId();
    companyRepository.deleteById(savedId);
    Assert.assertEquals(companyRepository.findById(savedId), Optional.empty());
  }
}
