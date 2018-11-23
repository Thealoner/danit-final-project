package ua.com.danit.controllers;

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
import ua.com.danit.models.Paket;
import ua.com.danit.services.PaketService;

import java.security.Principal;
import java.util.List;

@RestController
public class PaketController {

  private Logger logger = LoggerFactory.getLogger(PaketController.class);

  private PaketService paketService;

  @Autowired
  public PaketController(PaketService paketService) {
    this.paketService = paketService;
  }

  @PostMapping("/pakets")
  List<Paket> createPakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is saving new pakets: " + pakets);
    return paketService.savePakets(pakets);
  }

  @GetMapping("/pakets")
  List<Paket> getAllPakets(Principal principal) {
    logger.info(principal.getName() + " got all pakets data");
    return paketService.getAllPakets();
  }

  @GetMapping("/pakets/{id}")
  Paket getPaketById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got paket data with id: " + id);
    return paketService.getPaketById(id);
  }

  @PutMapping("/pakets")
  List<Paket> addPakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is updating pakets data: " + pakets);
    return paketService.savePakets(pakets);
  }

  @DeleteMapping("/pakets/{id}")
  public void deletePaketById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete paket with id: " + id);
    paketService.deletePaketById(id);
  }

  @DeleteMapping("/pakets")
  public void deletePakets(@RequestBody List<Paket> pakets, Principal principal) {
    logger.info(principal.getName() + " is trying to delete pakets: " + pakets);
    paketService.deletePakets(pakets);
  }
}
