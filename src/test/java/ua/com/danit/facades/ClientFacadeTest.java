package ua.com.danit.facades;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.danit.Application;
import ua.com.danit.dto.ClientDto;
import ua.com.danit.exceptions.EntityNotFoundException;
import ua.com.danit.models.Client;
import ua.com.danit.models.auditor.AuditorAwareImpl;

import static org.junit.Assert.assertEquals;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ComponentScan("ua.com.danit")
public class ClientFacadeTest {

  @Autowired
  private ClientFacade clientFacade;

  @Test
  public void whenConvertClientEntityToClientDto_thenCorrect() {

    Client client = new Client();
    client.setId(1020L);
    client.setFirstName("Adams");
    client.setEmail("adolf@gmail.com");

    ClientDto clientDto = clientFacade.convertToDto(client);

    assertEquals(client.getFirstName(), clientDto.getFirstName());
    assertEquals(client.getEmail(), clientDto.getEmail());
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteClientByExistingId_thenEntityNotFoundExceptionWhenGetClientById() {
    clientFacade.deleteClientById(1005L);
    clientFacade.getClientById(1005L);
  }

}
