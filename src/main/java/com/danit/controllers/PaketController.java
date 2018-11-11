package com.danit.controllers;

import com.danit.models.Paket;
import com.danit.services.PaketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class PaketController {

  private Logger logger = LoggerFactory.getLogger(PaketController.class);

  private PaketService paketService;

  @Autowired
  public PaketController(PaketService paketService) {
    this.paketService = paketService;
  }

  @PostMapping("/pakets")
  @ResponseStatus(HttpStatus.CREATED)
  private void createPakets(@RequestBody List<Paket> pakets) {
    logger.info(getCurrentPrincipalName() + " is saving new pakets: " + pakets);
    paketService.savePakets(pakets);
  }

  @GetMapping("/pakets")
  List<Paket> getAllPakets() {
    logger.info(getCurrentPrincipalName() + " got all pakets data");
    return paketService.getAllPakets();
  }

  @GetMapping("/pakets/{id}")
  Paket getPaketById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " got paket data with id: " + id);
    return paketService.getPaketById(id);
  }

  @PutMapping("/pakets")
  public void addPakets(@RequestBody List<Paket> pakets) {
    logger.info(getCurrentPrincipalName() + " is updating pakets data: " + pakets);
    paketService.savePakets(pakets);
  }

  @DeleteMapping("/pakets/{id}")
  public void deletePaketById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " try to delete paket with id: " + id);
    paketService.deletePaketById(id);
  }

  @DeleteMapping("/pakets")
  public void deletePakets(@RequestBody List<Paket> pakets) {
    logger.info(getCurrentPrincipalName() + " is trying to delete pakets: " + pakets);
    paketService.deletePakets(pakets);
  }
}
