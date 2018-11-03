package com.danit.services;

import com.danit.models.Contract;

import java.util.List;

public interface ContractService {
  List<Contract> getAllClients();

  Contract getClientById(long id);
}
