package com.danit.facades;

import com.danit.Application;
import com.danit.dto.ContractDto;
import com.danit.models.Contract;
import com.danit.models.auditor.AuditorAwareImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ComponentScan("com.danit")
public class ContractFacadeTest {

  @Autowired
  ContractFacade contractFacade;

  @Test
  public void whenConvertContractEntityToContractDto_thenCorrect() {

    Contract contract = new Contract();
    contract.setId(10020L);
    contract.setActive(true);
    contract.setPackageId(1001L);

    ContractDto contractDto = contractFacade.convertToDto(contract);

    assertEquals(contract.getId(), contractDto.getId());
    assertEquals(contract.getPackageId(), contractDto.getPackageId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteContractByExistingId_thenEntityNotFoundExceptionWhenGetContractById() {
    contractFacade.deleteContractById(1005L);
    contractFacade.getContractById(1005L);
  }

}
