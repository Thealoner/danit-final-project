package com.danit.controllers;

import com.danit.dto.PaketDto;
import com.danit.dto.Views;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.facades.PaketFacade;
import com.danit.models.Paket;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_NUMBER;
import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_SIZE;
import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/pakets")
public class PaketController {

  private final PaketFacade paketFacade;

  public PaketController(PaketFacade paketFacade) {
    this.paketFacade = paketFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createPakets(@RequestBody List<PaketDto> pakets, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(paketFacade.saveEntities(pakets)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllPaketsIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      PaketListRequestDto paketListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(paketFacade.getAllEntities(paketListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllPaketsShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      PaketListRequestDto paketListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(paketFacade.getAllEntities(paketListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllPaketsExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      PaketListRequestDto paketListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(paketFacade.getAllEntities(paketListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getPaketByIdExtended(@PathVariable(name = "id") Long id, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(paketFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<Map<String, Object>> addPakets(@RequestBody List<PaketDto> pakets, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(paketFacade.updateEntities(pakets)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePaketById(@PathVariable(name = "id") Long id, Principal principal) {
    paketFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deletePakets(@RequestBody List<Paket> pakets, Principal principal) {
    paketFacade.deleteEntities(pakets);
  }

}
