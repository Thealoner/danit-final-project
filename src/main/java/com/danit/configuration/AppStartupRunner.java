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
    TypeReference<List<Client>> clientTypeReference = new TypeReference<List<Client>>() {
    };
    TypeReference<List<User>> userTypeReference = new TypeReference<List<User>>() {
    };

    InputStream clientInputStream = TypeReference.class.getResourceAsStream("/json/clients.json");
    InputStream userInputStream = TypeReference.class.getResourceAsStream("/json/users.json");
    try {
      List<Client> clients = mapper.readValue(clientInputStream, clientTypeReference);
      clientRepository.saveAll(clients);
      List<User> users = mapper.readValue(userInputStream, userTypeReference);
      userRepository.saveAll(users);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
