package com.danit.models;

import com.danit.Application;
import com.danit.models.auditor.AuditorAwareImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ComponentScan("com.danit")
public class ContractTest {

  @Test
  public void gettersAndSettersContractTest() {
    /*Contract contract = new Contract();
    contract.setPackageId(3009L);
    contract.setActive(true);

    assertEquals(java.util.Optional.of(3009L), java.util.Optional.of(contract.getPackageId()));
    assertEquals(true, contract.isActive());*/
  }
}
