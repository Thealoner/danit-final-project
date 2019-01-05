package com.danit.facades;

import com.danit.dto.ClientDto;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientFacade extends AbstractDtoFacade<ClientDto, Client, ClientListRequestDto> {

}
