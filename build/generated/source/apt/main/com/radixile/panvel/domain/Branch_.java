package com.radixile.panvel.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Branch.class)
public abstract class Branch_ {

	public static volatile SingularAttribute<Branch, String> phone;
	public static volatile SingularAttribute<Branch, String> branchName;
	public static volatile SingularAttribute<Branch, Location> location;
	public static volatile SingularAttribute<Branch, Company> company;
	public static volatile SingularAttribute<Branch, Long> id;
	public static volatile SingularAttribute<Branch, String> email;

}

