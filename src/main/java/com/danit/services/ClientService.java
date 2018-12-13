package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class ClientService extends AbstractBaseEntityService<Client, ClientListRequestDto> {

}
