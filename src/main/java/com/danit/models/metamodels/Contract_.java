package com.danit.models.metamodels;

import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Contract.class)
public class Contract_ {
  public static volatile SingularAttribute<Contract, Long> id;
  public static volatile SingularAttribute<Contract, Client> client;
  public static volatile SingularAttribute<Contract, Paket> paket;
}
