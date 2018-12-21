package com.danit.models.metamodels;

import com.danit.models.Contract;
import com.danit.models.Paket;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Paket.class)
public class Paket_ {
  public static volatile SingularAttribute<Paket, Long> id;
  public static volatile ListAttribute<Paket, Contract> contracts;
}
