package com.radixile.panvel.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, String> departmentName;
	public static volatile SingularAttribute<Department, String> phone;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, Branch> branch;
	public static volatile SingularAttribute<Department, String> email;

}

