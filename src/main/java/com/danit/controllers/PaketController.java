package com.danit.controllers;

import com.danit.dto.PageDataDto;
import com.danit.dto.PaketDto;
import com.danit.dto.Views;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.facades.PaketFacade;
import com.danit.models.Paket;
import com.danit.services.PaketService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class PaketController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all pakets data";
  private final PaketFacade paketFacade;
  private final PaketService paketService;

  @Autowired
  public PaketController(PaketFacade paketFacade, PaketService paketService) {
    this.paketFacade = paketFacade;
    this.paketService = paketService;
  }

  //------not dto------
  @PostMapping("/pakets")
  public ResponseEntity<List<Paket>> createPakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is saving new pakets: " + pakets);
    return ResponseEntity.status(HttpStatus.CREATED).body(paketService.savePakets(pakets));
  }

  //--------dto--------
  @JsonView(Views.Ids.class)
  @PostMapping("/pakets/ids")
  public ResponseEntity<List<PaketDto>> createPaketsAndReturnIds(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + pakets);
    return ResponseEntity.status(HttpStatus.CREATED).body(paketFacade.savePakets(pakets));
  }

  //------not dto------
  @GetMapping("/pakets")
  public ResponseEntity<Map<String, Object>> getAllPakets(Pageable pageable,
                                                          Principal principal,
                                                          PaketListRequestDto paketListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("paketListRequestDto=" + paketListRequestDto);
    return ResponseEntity.ok(convertToMap(paketService.getAllPakets(paketListRequestDto, pageable)));
  }

  //--------dto--------
  @JsonView(Views.Short.class)
  @GetMapping(path = "/pakets/short")
  public ResponseEntity<Map<String, Object>> getAllPaketsShort(Pageable pageable,
                                                               Principal principal) throws ParseException {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(paketFacade.getAllPakets(pageable))); // NOSONAR
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping(path = "/pakets/extended")
  public ResponseEntity<Map<String, Object>> getAllPaketsExtended(Pageable pageable,
                                                                  Principal principal) throws ParseException {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(paketFacade.getAllPakets(pageable))); // NOSONAR
  }

  //------not dto------
  @GetMapping("/pakets/{id}")
  ResponseEntity<Paket> getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got paket data with id: " + id);
    return ResponseEntity.ok(paketService.getPaketById(id));
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping("/pakets/{id}/extended")
  ResponseEntity<PaketDto> getPaketByIdExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got paket data with id: " + id);
    return ResponseEntity.ok(paketFacade.getPaketById(id));
  }

  //------not dto------
  @PutMapping("/pakets")
  public ResponseEntity<List<Paket>> addPakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is updating pakets data: " + pakets);
    return ResponseEntity.ok(paketService.updatePakets(pakets));
  }


  //--------dto--------
  @JsonView(Views.Ids.class)
  @PutMapping("/pakets/ids")
  public ResponseEntity<List<PaketDto>> addPaketsAndReturnIds(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is updating pakets data: " + pakets);
    return ResponseEntity.ok(paketFacade.updatePakets(pakets));
  }

  //------not dto------
  @DeleteMapping("/pakets/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePaketById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete paket with id: " + id);
    paketService.deletePaketById(id);
  }

  //--------dto--------
  @DeleteMapping("/pakets/{id}/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deletePaketByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete paket with id: " + id);
    paketFacade.deletePaketById(id);
  }

  //------not dto------
  @DeleteMapping("/pakets")
  @ResponseStatus(HttpStatus.OK)
  public void deletePakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is trying to delete pakets: " + pakets);
    paketService.deletePakets(pakets);
  }

  //--------dto--------
  @DeleteMapping("/pakets/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deletePaketsDto(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is trying to delete pakets: " + pakets);
    paketFacade.deletePakets(pakets);
  }

  private <T> Map<String, Object> convertToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("meta", new PageDataDto(pageData.getTotalElements(),
        pageData.getNumber() + 1, pageData.getTotalPages(),
        pageData.getSize(), pageData.getNumberOfElements()));
    return outputData;
  }
}
