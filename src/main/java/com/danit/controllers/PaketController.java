package com.danit.controllers;

import com.danit.models.Paket;
import com.danit.services.PaketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class PaketController {

  private Logger logger = LoggerFactory.getLogger(PaketController.class);

  private PaketService paketService;

  @Autowired
  public PaketController(PaketService paketService) {
    this.paketService = paketService;
  }

  @PostMapping
  List<Paket> createPakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is saving new pakets: " + pakets);
    return paketService.savePakets(pakets);
  }

  @GetMapping
  List<Paket> getAllPakets(Principal principal) {
    logger.info(principal.getName() + " got all pakets data");
    return paketService.getAllPakets();
  }

  @GetMapping("/{id}")
  Paket getPaketById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got paket data with id: " + id);
    return paketService.getPaketById(id);
  }

  @PutMapping
  List<Paket> addPakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is updating pakets data: " + pakets);
    return paketService.savePakets(pakets);
  }

  @DeleteMapping("/{id}")
  public void deletePaketById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete paket with id: " + id);
    paketService.deletePaketById(id);
  }

  @DeleteMapping
  public void deletePakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is trying to delete pakets: " + pakets);
    paketService.deletePakets(pakets);
  }
}
