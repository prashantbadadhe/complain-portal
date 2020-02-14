package com.radixile.panvel.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, String> phone;
	public static volatile SingularAttribute<Company, String> companyName;
	public static volatile SingularAttribute<Company, Location> location;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, String> email;

}

