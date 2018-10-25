package com.danit.controllers;


import com.danit.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

  @Autowired
  ClientRepository repository;

  @RequestMapping("/api/test")
  List testRestService() {
    return repository.findAll();
  }
}
