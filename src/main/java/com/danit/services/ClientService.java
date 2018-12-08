package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends AbstractEntityService<Client, ClientListRequestDto> {
}
