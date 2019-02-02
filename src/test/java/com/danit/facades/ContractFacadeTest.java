package com.danit.facades;

import com.danit.Application;
import com.danit.dto.ContractDto;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ContractFacadeTest {

  @InjectMocks
  ContractFacade contractFacade;

  @Mock
  ContractService contractService;

  @Mock
  ModelMapper modelMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void convertToDtoTest() {
    Contract contract = mock(Contract.class);
    contractFacade.convertToDto(contract);
    verify(modelMapper, times(1)).map(contract, ContractDto.class);
  }

  @Test
  public void convertToDtosTest() {
    Contract contract1 = mock(Contract.class);
    Contract contract2 = mock(Contract.class);
    List<Contract> contracts = Arrays.asList(new Contract[]{contract1, contract2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Contract> page = new PageImpl<>(contracts, pageable, 4);
    contractFacade.convertToDtos(page);
    verify(modelMapper, times(1)).map(contracts.get(0), ContractDto.class);
    verify(modelMapper, times(1)).map(contracts.get(1), ContractDto.class);
  }

  @Test
  public void convertDtoToEntityTest() {
    ContractDto contractDto = mock(ContractDto.class);
    contractFacade.convertDtoToEntity(contractDto);
    verify(modelMapper, times(1)).map(contractDto, Contract.class);
  }

  @Test
  public void getAllEntitiesTest() {
    Contract contract1 = mock(Contract.class);
    Contract contract2 = mock(Contract.class);
    List<Contract> contracts = Arrays.asList(new Contract[]{contract1, contract2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Contract> page = new PageImpl<>(contracts, pageable, 4);

    when(contractService.getAllEntities(pageable)).thenReturn(page);

    Page<ContractDto> allEntities = contractFacade.getAllEntities(pageable);

    assertEquals(2, allEntities.getNumberOfElements());

    verify(contractService, times(1)).getAllEntities(pageable);
    verify(modelMapper, times(1)).map(contracts.get(0), ContractDto.class);
    verify(modelMapper, times(1)).map(contracts.get(1), ContractDto.class);
  }

  @Test
  public void getEntityByIdTest() {
    Contract contract = mock(Contract.class);

    when(contractService.getEntityById(1L)).thenReturn(contract);

    contractFacade.getEntityById(1L);

    verify(contractService, times(1)).getEntityById(1L);
    verify(modelMapper, times(1)).map(contract, ContractDto.class);
  }

  /*@Test
  public void saveEntitiesTest() {
    Contract contract1 = mock(Contract.class);
    Contract contract2 = mock(Contract.class);
    List<Contract> contracts = Arrays.asList(new Contract[]{contract1, contract2});
    List<ContractDto> contractsDto = contractFacade.convertToDtos(contracts);


    when(contractService.saveEntities(contracts)).thenReturn(contracts);

    contractFacade.saveEntities(contractsDto);

    verify(modelMapper, times(1)).map(contracts.get(0), ContractDto.class);
    verify(modelMapper, times(1)).map(contracts.get(1), ContractDto.class);
  }*/

  /*@Test
  public void updateEntitiesTest() {
    Contract contract1 = mock(Contract.class);
    Contract contract2 = mock(Contract.class);
    List<Contract> contracts = Arrays.asList(new Contract[]{contract1, contract2});
    List<ContractDto> contractsDto = contractFacade.convertToDtos(contracts);

    when(contractService.updateEntities(contracts)).thenReturn(contracts);

    contractFacade.updateEntities(contractsDto);

    verify(modelMapper, times(1)).map(contracts.get(0), ContractDto.class);
    verify(modelMapper, times(1)).map(contracts.get(1), ContractDto.class);
  }*/

}
