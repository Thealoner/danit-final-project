package com.danit.services.tabs;

import com.danit.models.User;
import com.danit.models.service.Tab;
import com.danit.repositories.service.TabRepository;
import com.danit.services.UserService;
import com.danit.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TabService {

  private TabRepository tabRepository;

  private UserService userService;

  private ServiceUtils serviceUtils;

  @Autowired
  public TabService(TabRepository tabRepository, UserService userService,
                    ServiceUtils serviceUtils) {
    this.tabRepository = tabRepository;
    this.userService = userService;
    this.serviceUtils = serviceUtils;
  }

  public Tab saveTab(Tab tab) {
    tab.setUserId(serviceUtils.getUserFromAuthContext().getId());
    return tabRepository.save(tab);
  }

  public List<Tab> saveTabs(List<Tab> tabs) {
    User user = serviceUtils.getUserFromAuthContext();
    tabs.forEach(tab -> tab.setId(user.getId()));
    return (List<Tab>) tabRepository.saveAll(tabs);
  }

  public void deleteTab(Tab tab) {
    tabRepository.delete(tabRepository.findByUserIdAndBaseEntityNameAndBaseEntityId(
        serviceUtils.getUserFromAuthContext().getId(), tab.getBaseEntityName(), tab.getBaseEntityId()));
  }

  public void deleteTabs(List<Tab> tabs) {
    tabs.forEach(this::deleteTab);
  }

  public Tab checkIfTabIsUsed(Tab tab) {
    Tab editableTab = tabRepository.findTopByBaseEntityNameAndBaseEntityIdOrderByCreationDateDesc(
        tab.getBaseEntityName(), tab.getBaseEntityId());
    if (Objects.nonNull(editableTab)) {
      editableTab.setMessage("tab is already opened by user - " +
          userService.getEntityById(editableTab.getId()).getUsername());
      editableTab.setBusy(true);
      return editableTab;
    } else {
      tab.setBusy(false);
      return tab;
    }
  }

  public List<Tab> checkIfTabsIsUsed(List<Tab> tabs) {
    List<Tab> openedTabs = new ArrayList<>();
    tabs.forEach(tab -> openedTabs.add(checkIfTabIsUsed(tab)));
    return openedTabs;
  }

  public void deleteAllUserTabs(Long userId) {
    tabRepository.deleteAllByUserId(userId);
  }

  public void deleteAllUserTabs() {
    tabRepository.deleteAllByUserId(serviceUtils.getUserFromAuthContext().getId());
  }

}
