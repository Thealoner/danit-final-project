package com.danit.configuration;

import com.danit.models.CardColor;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import com.danit.models.User;
import com.danit.models.UserRoles;
import com.danit.repositories.CardColorRepository;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.ContractRepository;
import com.danit.repositories.PaketRepository;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import com.danit.repositories.UserRepository;
import com.danit.repositories.UserRolesRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AppStartupRunner implements ApplicationRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserRolesRepository userRolesRepository;

  @Autowired
  private BCryptPasswordEncoder bcryptPasswordEncoder;

  @Autowired
  private ContractRepository contractRepository;

  @Autowired
  private ServiceRepository servicesRepository;

  @Autowired
  private ServiceCategoryRepository serviceCategoryRepository;

  @Autowired
  private PaketRepository paketRepository;

  @Autowired
  private CardColorRepository cardColorRepository;

  @Override
  public void run(ApplicationArguments args) throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    TypeReference<Set<UserRoles>> roleTypeReference = new TypeReference<Set<UserRoles>>() {
    };
    InputStream roleInputStream = TypeReference.class.getResourceAsStream("/json/roles.json");
    Set<UserRoles> rolesSet = new HashSet<>(mapper.readValue(roleInputStream, roleTypeReference));
    userRolesRepository.saveAll(rolesSet);

    TypeReference<List<User>> userTypeReference = new TypeReference<List<User>>() {
    };
    InputStream userInputStream = TypeReference.class.getResourceAsStream("/json/users.json");
    List<User> users = mapper.readValue(userInputStream, userTypeReference);
    users.forEach(user -> user.setPassword(bcryptPasswordEncoder.encode(user.getPassword())));
    userRepository.saveAll(users);

    TypeReference<List<Client>> clientTypeReference = new TypeReference<List<Client>>() {
    };
    InputStream clientInputStream = TypeReference.class.getResourceAsStream("/json/clients.json");
    List<Client> clients = mapper.readValue(clientInputStream, clientTypeReference);
    clientRepository.saveAll(clients);

    TypeReference<List<Paket>> paketTypeReference = new TypeReference<List<Paket>>() {
    };
    InputStream paketInputStream = TypeReference.class.getResourceAsStream("/json/pakets.json");
    List<Paket> pakets = mapper.readValue(paketInputStream, paketTypeReference);
    paketRepository.saveAll(pakets);

    TypeReference<List<Contract>> contractTypeReference = new TypeReference<List<Contract>>() {
    };
    InputStream contractInputStream = TypeReference.class.getResourceAsStream("/json/contracts.json");
    List<Contract> contracts = mapper.readValue(contractInputStream, contractTypeReference);
    contractRepository.saveAll(contracts);


    TypeReference<List<CardColor>> cardColorTypeReference = new TypeReference<List<CardColor>>() {
    };
    InputStream cardColorInputStream = TypeReference.class.getResourceAsStream("/json/cards.json");
    List<CardColor> cards = mapper.readValue(cardColorInputStream, cardColorTypeReference);
    cardColorRepository.saveAll(cards);


    TypeReference<List<ServiceCategory>> serviceCategoryTypeReference = new TypeReference<List<ServiceCategory>>() {
    };
    InputStream serviceCategoryInputStream = TypeReference.class.getResourceAsStream("/json/servicecategory.json");
    List<ServiceCategory> serviceCategory = mapper.readValue(serviceCategoryInputStream, serviceCategoryTypeReference);
    serviceCategoryRepository.saveAll(serviceCategory);


    TypeReference<List<Services>> servicesTypeReference = new TypeReference<List<Services>>() {
    };
    InputStream servicesInputStream = TypeReference.class.getResourceAsStream("/json/services.json");
    List<Services> services = mapper.readValue(servicesInputStream, servicesTypeReference);
    servicesRepository.saveAll(services);

  }
}
