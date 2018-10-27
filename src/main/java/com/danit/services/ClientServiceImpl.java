package com.danit.services;

import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }


    @Override
    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }
}
