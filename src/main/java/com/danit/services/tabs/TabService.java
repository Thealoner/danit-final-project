package com.danit.services.tabs;

import com.danit.models.User;
import com.danit.models.service.Tab;
import com.danit.repositories.service.TabRepository;
import com.danit.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class TabService {

  private TabRepository tabRepository;

  private ServiceUtils serviceUtils;

  @Autowired
  public TabService(TabRepository tabRepository, ServiceUtils serviceUtils) {
    this.tabRepository = tabRepository;
    this.serviceUtils = serviceUtils;
  }

  public Tab saveTab(Tab tab) {
    tab.setUser(serviceUtils.getUserFromAuthContext());
    return tabRepository.save(tab);
  }

  public List<Tab> saveTabs(List<Tab> tabs) {
    User user = serviceUtils.getUserFromAuthContext();
    tabs.forEach(tab -> tab.setUser(user));
    return (List<Tab>) tabRepository.saveAll(tabs);
  }

  public void deleteTab(Tab tab) {
    tabRepository.delete(tabRepository.findByUserAndBaseEntityNameAndBaseEntityId(
        serviceUtils.getUserFromAuthContext(), tab.getBaseEntityName(), tab.getBaseEntityId()));
  }

  public void deleteTabs(List<Tab> tabs) {
    tabs.forEach(this::deleteTab);
  }

  public Tab checkIfTabIsUsed(Tab tab) {
    return tabRepository.findByUserAndBaseEntityNameAndBaseEntityId(
        serviceUtils.getUserFromAuthContext(), tab.getBaseEntityName(), tab.getBaseEntityId());
  }

  public List<Tab> checkIfTabsIsUsed(List<Tab> tabs) {
    List<Tab> openedTabs = new ArrayList<>();
    tabs.forEach(tab -> openedTabs.add(tabRepository.findByUserAndBaseEntityNameAndBaseEntityId(
        serviceUtils.getUserFromAuthContext(), tab.getBaseEntityName(), tab.getBaseEntityId())));
    return openedTabs;
  }

  public void deleteAllUserTabs(String userName) {
    tabRepository.deleteAllByUser_Username(userName);
  }

  public void deleteAllUserTabs() {
    tabRepository.deleteAllByUser_Username(serviceUtils.getUserFromAuthContext().getUsername());
  }

}
