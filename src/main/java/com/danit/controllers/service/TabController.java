package com.danit.controllers.service;

import com.danit.models.service.Tab;
import com.danit.services.tabs.TabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class TabController {

  private TabService tabService;

  @Autowired
  public TabController(TabService tabService) {
    this.tabService = tabService;
  }

  @PostMapping("/tab")
  void saveTab(@RequestBody Tab tab, Principal principal) {
    tabService.saveTab(tab);
  }

  @PostMapping("/tabs")
  void saveTabs(@RequestBody List<Tab> tabs, Principal principal) {
    tabService.saveTabs(tabs);
  }

  @DeleteMapping("/tab")
  void deleteTab(@RequestBody Tab tab, Principal principal) {
    tabService.deleteTab(tab);
  }

  @DeleteMapping("/tabs")
  void deleteTabs(@RequestBody List<Tab> tabs, Principal principal) {
    tabService.deleteTabs(tabs);
  }

  @GetMapping("/tab")
  ResponseEntity<Tab> checkIfTabIsUsed(@RequestBody Tab tab, Principal principal) {
    return ResponseEntity.ok(tabService.checkIfTabIsUsed(tab));
  }

  @GetMapping("/tabs")
  ResponseEntity<List<Tab>> checkIfTabIsUsed(@RequestBody List<Tab> tabs, Principal principal) {
    return ResponseEntity.ok(tabService.checkIfTabsIsUsed(tabs));
  }


}
