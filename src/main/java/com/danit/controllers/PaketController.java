package com.danit.controllers;

import com.danit.dto.PaketDto;
import com.danit.dto.Views;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.facades.PaketFacade;
import com.danit.models.Paket;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.convertToMap;

@RestController
@RequestMapping("/pakets")
@Slf4j
public class PaketController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all pakets data";


  private final PaketFacade paketFacade;

  public PaketController(PaketFacade paketFacade) {
    this.paketFacade = paketFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<List<PaketDto>> createPakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + pakets);
    return ResponseEntity.status(HttpStatus.CREATED).body(paketFacade.saveEntities(pakets));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllPaketsShort(Pageable pageable,
                                                               Principal principal,
                                                               PaketListRequestDto paketListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertToMap(paketFacade.getAllEntities(paketListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllPaketsExtended(Pageable pageable,
                                                                  Principal principal,
                                                                  PaketListRequestDto paketListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertToMap(paketFacade.getAllEntities(paketListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<PaketDto> getPaketByIdExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got paket data with id: " + id);
    return ResponseEntity.ok(paketFacade.getEntityById(id));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<List<PaketDto>> addPakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is updating pakets data: " + pakets);
    return ResponseEntity.ok(paketFacade.updateEntities(pakets));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePaketById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete paket with id: " + id);
    paketFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deletePakets(@RequestBody List<Paket> pakets, Principal principal) {
    log.info(principal.getName() + " is trying to delete pakets: " + pakets);
    paketFacade.deleteEntities(pakets);
  }

}
