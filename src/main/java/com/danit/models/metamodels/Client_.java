package com.danit.models.metamodels;

import com.danit.models.Client;
import com.danit.models.Contract;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public class Client_ {
  public static volatile SingularAttribute<Client, Long> id;
  public static volatile SingularAttribute<Client, String> gender;
  public static volatile ListAttribute<Client, Contract> contracts;
}
