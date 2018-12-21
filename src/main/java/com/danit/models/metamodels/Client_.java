package com.danit.models.metamodels;

import com.danit.models.Client;
import com.danit.models.Contract;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public class Client_ {
  public static volatile SingularAttribute<Client, Long> id;
  public static volatile ListAttribute<Client, Contract> contracts;
}
