package com.danit.services.tabs;

import com.danit.models.User;
import com.danit.models.service.Tab;
import com.danit.repositories.service.TabRepository;
import com.danit.services.UserService;
import com.danit.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    User user = serviceUtils.getUserFromAuthContext();
    tab.setUserId(user.getId());
    tab.setTabOwnerName(user.getUsername());
    return tabRepository.save(tab);
  }

  public List<Tab> saveTabs(List<Tab> tabs) {
    User user = serviceUtils.getUserFromAuthContext();
    tabs.forEach(tab -> {
      tab.setTabOwnerName(user.getUsername());
      tab.setUserId(user.getId());
    });
    return (List<Tab>) tabRepository.saveAll(tabs);
  }

  public List<Tab> getAllTabOwnersByTab(Tab tab) {
    List<Tab> tabs = tabRepository.findByBaseEntityNameAndBaseEntityIdOrderByCreationDateAsc(
        tab.getBaseEntityName(),
        tab.getBaseEntityId()
    );
    if (!tabs.isEmpty()) {
      User user = userService.getEntityById(tabs.get(0).getUserId());
      for (int i = 0; i < tabs.size(); i++) {
        tabs.get(i).setTabOwnerName(user.getUsername());
        if (i > 0) {
          tabs.get(i).setBusy(true);
          tabs.get(i).setMessage("tab is already opened by user - " + user.getUsername());
        }
      }
    }
    return tabs;
  }

  public void deleteTab(Tab tab) {
    tabRepository.delete(tabRepository.findTopByUserIdAndBaseEntityNameAndBaseEntityId(
        serviceUtils.getUserFromAuthContext().getId(), tab.getBaseEntityName(), tab.getBaseEntityId()));
  }

  public void deleteTabs(List<Tab> tabs) {
    tabs.forEach(this::deleteTab);
  }

  public Tab checkIfTabIsUsed(Tab tab) {
    Tab editableTab = tabRepository.findTopByBaseEntityNameAndBaseEntityIdOrderByCreationDateAsc(
        tab.getBaseEntityName(), tab.getBaseEntityId());
    String username = userService.getEntityById(editableTab.getUserId()).getUsername();
    editableTab.setTabOwnerName(username);
    if (!tab.equals(editableTab)) {
      editableTab.setMessage("tab is already opened by user - " + username);
      editableTab.setBusy(true);
    } else {
      editableTab.setBusy(false);
    }
    return editableTab;
  }

  public List<Tab> checkIfTabsIsUsed(List<Tab> tabs) {
    List<Tab> openedTabs = new ArrayList<>();
    tabs.forEach(tab -> openedTabs.add(checkIfTabIsUsed(tab)));
    return openedTabs;
  }

  @Transactional
  public void deleteAllUserTabs(Long userId) {
    tabRepository.deleteAllByUserId(userId);
  }

  @Transactional
  public void deleteAllUserTabs() {
    tabRepository.deleteAllByUserId(serviceUtils.getUserFromAuthContext().getId());
  }

}
