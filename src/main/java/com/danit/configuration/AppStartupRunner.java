package com.danit.configuration;

import com.danit.models.Client;
import com.danit.models.User;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class AppStartupRunner implements ApplicationRunner {
  @Autowired
  UserRepository userRepository;

  @Autowired
  ClientRepository clientRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<Client>> typeReference = new TypeReference<List<Client>>() {
    };
    InputStream inputStream = TypeReference.class.getResourceAsStream("/json/clients.json");
    try {
      List<Client> clients = mapper.readValue(inputStream, typeReference);
      clientRepository.saveAll(clients);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
