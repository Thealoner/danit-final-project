package com.danit.controllers;

import com.danit.models.Contract;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaketController {

  Logger logger = LoggerFactory.getLogger(ContractController.class);

  private PaketService paketService;

  @Autowired
  public PaketController(PaketService paketService) {
    this.paketService = paketService;
  }

  @PostMapping("/pakets")
  private void createPakets(@RequestBody List<Paket> pakets) {
    logger.info("Adding new paket");
    paketService.savePakets(pakets);
    logger.info("Paket saved");
  }

  @GetMapping("/pakets/{id}")
  Paket getPaketById(@PathVariable(name = "id") long id) {
    return paketService.getPaketById(id);
  }

  @GetMapping("/pakets")
  List<Paket> getAllPakets() {
    return paketService.getAllPakets();
  }

  @PutMapping("/pakets")
  public void addPakets(@RequestBody List<Paket> pakets) {
    paketService.savePakets(pakets);
  }

  @DeleteMapping("/pakets/{id}")
  public void deletePaketById(@PathVariable(name = "id") long id) {
    paketService.deletePaketById(id);
  }

  @DeleteMapping("/pakets")
  public void deletePakets(@RequestBody List<Paket> pakets) {
    paketService.deletePakets(pakets);
  }
}
